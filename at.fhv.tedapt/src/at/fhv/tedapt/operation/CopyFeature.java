package at.fhv.tedapt.operation;

import static at.fhv.tedapt.helper.NamingConstants.FK_SUFFIX;
import static at.fhv.tedapt.helper.NamingConstants.ID;
import static at.fhv.tedapt.helper.NamingConstants.IDX_SUFFIX;
import static at.fhv.tedapt.helper.NamingConstants.ID_SUFFIX;
import static at.fhv.tedapt.helper.NamingConstants.PK_SUFFIX;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edapt.common.MetamodelFactory;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.declaration.OperationImplementation;
import org.eclipse.emf.edapt.history.util.HistoryUtils;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import at.fhv.tedapt.flyway.DatabaseHandler;
import at.fhv.tedapt.flyway.FlywayHandler;
import at.fhv.tedapt.flyway.change.Change;
import at.fhv.tedapt.flyway.change.CopyColumn;
import at.fhv.tedapt.flyway.change.CopyTable;
import at.fhv.tedapt.flyway.change.SQLChange;
import at.fhv.tedapt.helper.CommonTasks;

/**
 * {@description}
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
@SuppressWarnings("restriction")
@EdaptOperation(identifier = "copyFeatureTedapt", label = "Copy Feature Tedapt", description = "Copy a feature in the metamodel with a new name and copy values in databse.")
public class CopyFeature extends OperationImplementation {

	/** {@description} */
	@EdaptParameter(main = true, description = "The feature to be copied")
	public EStructuralFeature feature;

	/** {@description} */
	@EdaptParameter(description = "The name of the copy")
	public String name;
	
	@Override
	protected void execute(Metamodel metamodel, Model model)
			throws MigrationException {
		EClass contextClass = feature.getEContainingClass();
		Change change = null;
		// metamodel adaptation
//		EStructuralFeature copiedFeature = MetamodelUtils.copy(feature);
//		copiedFeature.setName(name);
//		contextClass.getEStructuralFeatures().add(copiedFeature);
//		if (copiedFeature instanceof EReference) {
//			EReference copiedReference = (EReference) copiedFeature;
//			if (copiedReference.isContainment()) {
//				copiedReference.setContainment(false);
//			}
//		}
		
		if(feature instanceof EReference) {
//			CreateReference cr = new CreateReference();
//			cr.containment = false;
//			cr.eClass = contextClass;
//			cr.lowerBound = feature.getLowerBound();
//			cr.upperBound = feature.getUpperBound();
//			cr.name = name;
//			cr.type = ((EReference) feature).getEReferenceType();
//			cr.checkAndExecute(metamodel, model);
			createReference(contextClass, metamodel, model);
			change = copyReference(contextClass);
		} else if(feature instanceof EAttribute) {
			CreateAttribute ca = new CreateAttribute();
			ca.eClass = contextClass;
			ca.lowerBound = feature.getLowerBound();
			ca.upperBound = feature.getUpperBound();
			ca.name = name;
			ca.type = ((EAttribute) feature).getEAttributeType();
			ca.defaultValue = feature.getDefaultValueLiteral();
			ca.checkAndExecute(metamodel, model);
			change = copyAttribute(contextClass);
		}
		
		
		FlywayHandler.addChange(change);
		FlywayHandler.saveChangelog(
				HistoryUtils.getHistoryURI(
						metamodel.getEPackages().get(0).eResource()), "Copy Feature "+feature.getName());
	
		

	}
	

	
	private Change copyAttribute(EClass contextClass) {
		String superClass = CommonTasks.getMostAbstract(contextClass).getName();

		if(!CommonTasks.mapAsTable(feature.getUpperBound(), ((EAttribute) feature).getEAttributeType().getName())) {
			
			return new CopyColumn(
					superClass, 
					feature.getName(), 
					name);
			
			
		} else {
			String tableName = contextClass.getName()+"_"+name;
			String sectbl = contextClass.getName()+"_"+feature.getName();
			
			return new CopyTable(sectbl, tableName);
			
		}
		

		
	}

	

	private void createReference(EClass eClass, Metamodel metamodel, Model model) {
		//Can not be done via class CreateReference because it would result in duplicated rows
		EReference oldReference = (EReference)feature;
		EClass type = oldReference.getEReferenceType();
		int lowerBound = oldReference.getLowerBound();
		int upperBound = oldReference.getUpperBound();
		//TODO Check if OK
		Boolean containment = oldReference.isContainment();
		
		
		//Metamodel changes
//		EReference reference = 
		MetamodelFactory.newEReference(eClass, name, type,
				lowerBound, upperBound, containment);

		MetamodelFactory.newEReference(eClass, name, type,
				lowerBound, upperBound, containment);
		
		//DB changes
		
		EClass superClass = CommonTasks.getMostAbstract(eClass);
		EClass refSuperClass = CommonTasks.getMostAbstract(type);
		boolean notNull = (upperBound == 1 && lowerBound == 1);
		Change change;
		DSLContext context = DatabaseHandler.getContext();
		if(containment) {
			
	
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
			change = new SQLChange(addRefCol, addRef);
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
		
	}
	
	private Change copyReference(EClass contextClass) {
		EReference reference = (EReference)feature;
		EClass refSuperClass = CommonTasks.getMostAbstract(reference.getEReferenceType());
		EClass superClass = CommonTasks.getMostAbstract(contextClass);
		
		//TODO Check if ok
		if(reference.isContainment()) {
			
			if(reference.getUpperBound() != 1) {	
				return new CopyColumn(
						refSuperClass.getName(), 
						superClass.getName()+"_"+name, 
						superClass.getName()+"_"+reference.getName());

				
				
			} else {
				
				return new CopyColumn(
						superClass.getName(), 
						reference.getEReferenceType().getName()+"_"+reference.getName(), 
						reference.getEReferenceType().getName()+"_"+name);
			}
			
		} else {
			if(reference.getUpperBound() != 1) {
				return new CopyTable(superClass.getName()+"_"+name, superClass.getName()+"_"+reference.getName());

			} else {
				
				return new CopyColumn(
						superClass.getName(), 
						reference.getEReferenceType().getName()+"_"+reference.getName(), 
						reference.getEReferenceType().getName()+"_"+name);

			}
		}
	}
	


}
