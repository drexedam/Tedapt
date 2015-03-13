package at.fhv.tedapt.operation;

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
import at.fhv.tedapt.flyway.change.SQLChange;
import at.fhv.tedapt.helper.CommonTasks;

/**
 * {@description}
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
@EdaptOperation(identifier = "copyFeatureTedapt", label = "Copy Feature Tedapt", description = "Copy a feature in the metamodel with a new name and copy values in databse.")
public class CopyFeature extends OperationImplementation {

	/** {@description} */
	@EdaptParameter(main = true, description = "The feature to be copied")
	public EStructuralFeature feature;

	/** {@description} */
	@EdaptParameter(description = "The name of the copy")
	public String name;
	
	@SuppressWarnings("restriction")
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
		
//		if(feature instanceof EReference) {
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
//		} else if(feature instanceof EAttribute) {
//			CreateAttribute ca = new CreateAttribute();
//			ca.eClass = contextClass;
//			ca.lowerBound = feature.getLowerBound();
//			ca.upperBound = feature.getUpperBound();
//			ca.name = name;
//			ca.type = ((EAttribute) feature).getEAttributeType();
//			ca.defaultValue = feature.getDefaultValueLiteral();
//			ca.checkAndExecute(metamodel, model);
			change = copyAttribute(contextClass);
//		}
		
		
		FlywayHandler.addChange(change);
		FlywayHandler.saveChangelog(
				HistoryUtils.getHistoryURI(
						metamodel.getEPackages().get(0).eResource()), "Copy Feature "+feature.getName());
		
		//TODO copy values
		
		

	}
	

	
	private Change copyAttribute(EClass contextClass) {
		String superClass = CommonTasks.getMostAbstract(contextClass).getName();
		Change change = null;
		if(!CommonTasks.mapAsTable(feature.getUpperBound(), ((EAttribute) feature).getEAttributeType().getName())) {
			
			change = new SQLChange(
					updateQuery(superClass, feature.getName(), name));
			
			System.out.println(change.getSQL());
			
		} else {
			String tableName = contextClass.getName()+"_"+name;
			String sectbl = contextClass.getName()+"_"+feature.getName();
			
			change = new SQLChange("insert into "+tableName+"("+tableName+"_e_id,elt,"+tableName+"_idx) SELECT "+sectbl+"_e_id,elt,"+sectbl+"_idx FROM" + sectbl);
			
			System.out.println(change.getSQL());
			
		}
		
		return change;
		
	}
	
	@SuppressWarnings("restriction")
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
						.add(DSL.constraint(superClass.getName()+"_"+name+"_fk")
								.foreignKey(superClass.getName()+"_"+name)
								.references(superClass.getName(), 
										"e_id"))
						.getSQL();

			} else {
				// additional columns in (super-)class which contains the other class
				
				addRefCol = context.alterTable(superClass.getName())
						.add(type.getName()+"_"+name, SQLDataType.BIGINT.nullable(!notNull)).getSQL();
				
				addRef = context.alterTable(superClass.getName())
						.add(DSL.constraint(type.getName()+"_"+name+"_fk")
								.foreignKey(type.getName()+"_"+name)
								.references(refSuperClass.getName(), 
										"e_id"))
						.getSQL();
				
			}
			change = new SQLChange(addRefCol, addRef);
		} else {
			if(upperBound != 1) {
				// create table for reference
				
				String createTable = context.createTable(eClass.getName()+"_"+name)
						.column(eClass.getName()+"_e_id", SQLDataType.BIGINT.nullable(false))
						.column(eClass.getName()+"_"+name+"_idx", SQLDataType.INTEGER.nullable(false))
						.column(type.getName()+"_e_id", SQLDataType.BIGINT.nullable(false)).getSQL();
				
				String addPKs = context.alterTable(eClass.getName()+"_"+name)
						.add(DSL.constraint(eClass.getName()+"_"+name+"_pk")
								.primaryKey(eClass.getName()+"_e_id",eClass.getName()+"_"+name+"_idx"))
						.getSQL();
				
				String addFK1 = context.alterTable(eClass.getName()+"_"+name)
						.add(DSL.constraint(eClass.getName()+"_"+name+"_e_id_fk")
								.foreignKey(eClass.getName()+"_e_id").references(superClass.getName(), "e_id"))
								.getSQL();
				
				String addFK2 = context.alterTable(eClass.getName()+"_"+name)
						.add(DSL.constraint(eClass.getName()+"_"+name+"_type_e_id_fk")
								.foreignKey(type.getName()+"_e_id")
								.references(refSuperClass.getName(), "e_id"))
								.getSQL();
				
				change = new SQLChange(createTable, addPKs, addFK1, addFK2);
				

			} else {
				// add column in containing class
				String addColumn = context.alterTable(superClass.getName())
						.add(type.getName()+"_"+name, SQLDataType.BIGINT.nullable(!notNull))
						.getSQL();
				
				String addFK = context.alterTable(superClass.getName())
						.add(DSL.constraint(superClass.getName()+type.getName()+"_"+name+"_fk")
								.foreignKey(type.getName()+"_"+name).references(refSuperClass.getName(), "e_id"))
								.getSQL();
				
				change = new SQLChange(addColumn, addFK);

			}
		}
		
		FlywayHandler.addChange(change);
		
	}
	
	private Change copyReference(EClass contextClass) {
		EReference reference = (EReference)feature;
		EClass refSuperClass = CommonTasks.getMostAbstract(reference.getEReferenceType());
		EClass superClass = CommonTasks.getMostAbstract(reference.getEContainingClass());
		
		//TODO Check if ok
		if(reference.isContainment()) {
			
			if(reference.getUpperBound() != 1) {					
				return new SQLChange(
						updateQuery(refSuperClass.getName(),
								superClass.getName()+"_"+name,
								superClass.getName()+"_"+reference.getName())
								);
				
				
			} else {
				
				return new SQLChange(
						updateQuery(superClass.getName(), 
						reference.getEReferenceType().getName()+"_"+reference.getName(), 
						reference.getEReferenceType().getName()+"_"+name)
						);
			}
			
		} else {
			
		}
		return null;
	}
	
	private String updateQuery(String table, String origin, String goal) {
		StringBuilder sb = new StringBuilder("update ");
		sb.append(table);
		sb.append(" set ");
		sb.append(goal);
		sb.append("=");
		sb.append(origin);
		return sb.toString();
	}

}
