package at.fhv.tedapt.operation;

import static at.fhv.tedapt.helper.NamingConstants.E_CON;
import static at.fhv.tedapt.helper.NamingConstants.E_CON_CLASS;
import static at.fhv.tedapt.helper.NamingConstants.E_CON_FEAT;
import static at.fhv.tedapt.helper.NamingConstants.FK_SUFFIX;
import static at.fhv.tedapt.helper.NamingConstants.ID;
import static at.fhv.tedapt.helper.NamingConstants.IDX_SUFFIX;
import static at.fhv.tedapt.helper.NamingConstants.ID_SUFFIX;
import static at.fhv.tedapt.helper.NamingConstants.PK_SUFFIX;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edapt.common.MetamodelFactory;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.declaration.OperationImplementation;
import org.eclipse.emf.edapt.history.util.HistoryUtils;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import at.fhv.tedapt.flyway.DatabaseHandler;
import at.fhv.tedapt.flyway.FlywayHandler;
import at.fhv.tedapt.flyway.change.Change;
import at.fhv.tedapt.flyway.change.SQLChange;
import at.fhv.tedapt.helper.CommonTasks;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
@SuppressWarnings("restriction")
@EdaptOperation(identifier = "newReferenceTedapt", label = "Create Reference Tedapt", description = "Creates a new reference and corresponding database entries.")
public class CreateReference extends OperationImplementation {

	/** {@description} */
	@EdaptParameter(main = true, description = "The class in which the reference is created")
	public EClass eClass;

	/** {@description} */
	@EdaptParameter(description = "The name of the new reference")
	public String name;

	/** {@description} */
	@EdaptParameter(description = "The type of the new reference")
	public EClass type;

	/** {@description} */
	@EdaptParameter(description = "The lower bound of the new reference")
	public int lowerBound = 0;

	/** {@description} */
	@EdaptParameter(description = "The upper bound of the new reference")
	public int upperBound = 1;

	/** {@description} */
	@EdaptParameter(description = "Whether the new reference is a containment reference")
	public Boolean containment = false;

	/** {@description} */
	@EdaptParameter(description = "The opposite reference of the new reference", optional = true)
	public EReference opposite;
	

	/** {@inheritDoc} */
	@Override
	public void execute(Metamodel metamodel, Model model) {


		//Metamodel changes
		EReference reference = MetamodelFactory.newEReference(eClass, name, type,
				lowerBound, upperBound, containment);
		if (opposite != null) {
			metamodel.setEOpposite(opposite, reference);
		}
		
		//DB changes
		
		EClass superClass = CommonTasks.getMostAbstract(eClass);
		EClass refSuperClass = CommonTasks.getMostAbstract(type);
		boolean notNull = (upperBound == 1 && lowerBound == 1);
		Change change;
		DSLContext context = DatabaseHandler.getContext();
		if(containment) {
			String addCol1 = context.alterTable(refSuperClass.getName())
					.add(E_CON_CLASS, SQLDataType.VARCHAR.length(255)).getSQL();
			
			String addCol2 = context.alterTable(refSuperClass.getName())
					.add(E_CON, SQLDataType.VARCHAR.length(255)).getSQL();
			
			String addCol3 = context.alterTable(refSuperClass.getName())
					.add(E_CON_FEAT, SQLDataType.VARCHAR.length(255)).getSQL();
	
			String addRefCol, addRef;
			
			if(upperBound != 1) {
				// additional columns in (super-)class which is contained
				
				addRefCol = context.alterTable(refSuperClass.getName())
						.add(superClass.getName()+"_"+name, SQLDataType.BIGINT.nullable(!notNull)).getSQL();
				
				addRef = context.alterTable(refSuperClass.getName())
						.add(DSL.constraint(superClass.getName()+"_"+name+FK_SUFFIX)
								.foreignKey(superClass.getName()+"_"+name)
								.references(superClass.getName(), 
										ID))
						.getSQL();

			} else {
				// additional columns in (super-)class which contains the other class
				
				addRefCol = context.alterTable(superClass.getName())
						.add(type.getName()+"_"+name, SQLDataType.BIGINT.nullable(!notNull)).getSQL();
				
				addRef = context.alterTable(superClass.getName())
						.add(DSL.constraint(type.getName()+"_"+name+FK_SUFFIX)
								.foreignKey(type.getName()+"_"+name)
								.references(refSuperClass.getName(), 
										ID))
						.getSQL();
				
			}
			change = new SQLChange(addCol1, addCol2, addCol3, addRefCol, addRef);
		} else {
			if(upperBound != 1) {
				// create table for reference
				
				String createTable = context.createTable(eClass.getName()+"_"+name)
						.column(eClass.getName()+ID_SUFFIX, SQLDataType.BIGINT.nullable(false))
						.column(eClass.getName()+"_"+name+IDX_SUFFIX, SQLDataType.INTEGER.nullable(false))
						.column(type.getName()+ID_SUFFIX, SQLDataType.BIGINT.nullable(false)).getSQL();
				
				String addPKs = context.alterTable(eClass.getName()+"_"+name)
						.add(DSL.constraint(eClass.getName()+"_"+name+PK_SUFFIX)
								.primaryKey(eClass.getName()+ID_SUFFIX,eClass.getName()+"_"+name+IDX_SUFFIX))
						.getSQL();
				
				String addFK1 = context.alterTable(eClass.getName()+"_"+name)
						.add(DSL.constraint(eClass.getName()+"_"+name+ID_SUFFIX+FK_SUFFIX)
								.foreignKey(eClass.getName()+ID_SUFFIX).references(superClass.getName(), ID))
								.getSQL();
				
				String addFK2 = context.alterTable(eClass.getName()+"_"+name)
						.add(DSL.constraint(eClass.getName()+"_"+name+"_type"+ID_SUFFIX+FK_SUFFIX)
								.foreignKey(type.getName()+ID_SUFFIX)
								.references(refSuperClass.getName(), ID))
								.getSQL();
				
				change = new SQLChange(createTable, addPKs, addFK1, addFK2);
				

			} else {
				// add column in containing class
				String addColumn = context.alterTable(superClass.getName())
						.add(type.getName()+"_"+name, SQLDataType.BIGINT.nullable(!notNull))
						.getSQL();
				
				String addFK = context.alterTable(superClass.getName())
						.add(DSL.constraint(superClass.getName()+type.getName()+"_"+name+FK_SUFFIX)
								.foreignKey(type.getName()+"_"+name).references(refSuperClass.getName(), ID))
								.getSQL();
				
				change = new SQLChange(addColumn, addFK);

			}
		}
		
		FlywayHandler.addChange(change);
		
		FlywayHandler.saveChangelog(
				HistoryUtils.getHistoryURI(
						metamodel.getEPackages().get(0).eResource()), "Create Reference "+name);
	}

}
