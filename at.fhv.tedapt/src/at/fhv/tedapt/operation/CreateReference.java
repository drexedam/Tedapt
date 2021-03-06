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
import org.eclipse.emf.edapt.declaration.EdaptOperation;
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
public class CreateReference extends org.eclipse.emf.edapt.declaration.creation.NewReference {


	

	/** {@inheritDoc} */
	@Override
	public void execute(Metamodel metamodel, Model model) {

		//DB changes
		
		EClass superClass = CommonTasks.getMostAbstract(eClass);
		EClass refSuperClass = CommonTasks.getMostAbstract(type);
		boolean notNull = (upperBound == 1 && lowerBound == 1);
		Change change;
		DSLContext context = DatabaseHandler.getContext();
		if(containment) {
			SQLChange tempChange = new SQLChange();
			
			if(!CommonTasks.existingContReference(superClass)) {
				//These columns may only be created once!
				String addCol1 = context.alterTable(refSuperClass.getName())
						.add(E_CON_CLASS, SQLDataType.VARCHAR.length(255)).getSQL();
				
				String addCol2 = context.alterTable(refSuperClass.getName())
						.add(E_CON, SQLDataType.VARCHAR.length(255)).getSQL();
				
				String addCol3 = context.alterTable(refSuperClass.getName())
						.add(E_CON_FEAT, SQLDataType.VARCHAR.length(255)).getSQL();
				
				tempChange.addQueries(addCol1, addCol2, addCol3);
			}
			
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
			
			tempChange.addQueries(addRefCol, addRef);
			
			change = tempChange;
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
		
		
		//Metamodel changes
		super.execute(metamodel, model);
		
		//Save DB changes
		FlywayHandler.addChange(change);
		
		FlywayHandler.saveChangelog(
				HistoryUtils.getHistoryURI(
						metamodel.getEPackages().get(0).eResource()), "Create Reference "+name);
	}


}
