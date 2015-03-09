package at.fhv.tedapt.operation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.declaration.OperationImplementation;
import org.eclipse.emf.edapt.history.util.HistoryUtils;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;
import org.jooq.DSLContext;

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
@EdaptOperation(identifier="deleteFeaterTedapt", label="Delete Feature Tedapt", description="Deletes a feature and its corresponding DB entries.")
public class DeleteFeature extends OperationImplementation {

	/** {@description} */
	@EdaptParameter(main = true, description = "The feature to be deleted")
	public EStructuralFeature feature;
	
	@Override
	protected void execute(Metamodel metamodel, Model model)
			throws MigrationException {
		
		Change change = null, change2 = null;
		DSLContext context = DatabaseHandler.getContext();
		if (feature instanceof EAttribute) {
			
			EAttribute atr = (EAttribute) feature;
			
			EClass contClass = CommonTasks.getMostAbstract(atr.getEContainingClass());
			EDataType dataType = atr.getEAttributeType();

			if(!CommonTasks.mapAsTable(atr.getUpperBound(), dataType.getName())) {
				//Attribute is not mapped as table
				
				change = new SQLChange(context.alterTable(contClass.getName()).drop(atr.getName()).getSQL());
				
//				FlywayHandler.addChange(new DeleteColumn(contClass.getName(), atr.getName()));
			} else {
				
				change = new SQLChange(context.dropTable(contClass.getName()+"_"+atr.getName()).getSQL());
				
//				FlywayHandler.addChange(new DeleteTable(contClass.getName()+"_"+atr.getName()));
			}
			
			
		} else if(feature instanceof EReference) {
			change = deleteReference(context, (EReference) feature);
			
			EReference opposite;
			if((opposite=((EReference) feature).getEOpposite()) != null) {
				change2 = deleteReference(context, ((EReference)opposite));
			}
			
			
			
		}
		
		
		if(change != null) {
			FlywayHandler.addChange(change);
			
			if(change2 != null) {
				FlywayHandler.addChange(change2);
			}
			
			FlywayHandler.saveChangelog(
					HistoryUtils.getHistoryURI(
							metamodel.getEPackages().get(0).eResource()), "Delete Feature "+feature.getName());
		}
		
		
		// metamodel adaptation
		metamodel.delete(feature);
		if (feature instanceof EReference) {
			EReference reference = (EReference) feature;
			if (reference.getEOpposite() != null) {
				metamodel.delete(reference.getEOpposite());
			}
		}	

	}

	/**
	 * @param context
	 * @return
	 */
	private Change deleteReference(DSLContext context, EReference ref) {
		Change change;
		
		EClass superClass = CommonTasks.getMostAbstract(ref.getEContainingClass());
		EClass type = ref.getEReferenceType();
		EClass refSuperClass  = CommonTasks.getMostAbstract(type);
		
		if(ref.isContainment()) {
			String dropEconClass = context.alterTable(refSuperClass.getName()).dropColumn("econtainer_class").getSQL();
			String dropEcon = context.alterTable(refSuperClass.getName()).dropColumn("e_container").getSQL();
			String dropEconFeatName = context.alterTable(refSuperClass.getName()).dropColumn("e_container_feature_name").getSQL();
			
			String dropFK, dropCol;
			if(ref.getUpperBound() != 1) {
				dropFK = context.alterTable(refSuperClass.getName()).dropConstraint(superClass.getName()+"_"+ref.getName()+"_fk").getSQL();
				dropCol = context.alterTable(refSuperClass.getName()).dropColumn(superClass.getName()+"_"+ref.getName()).getSQL();
			} else {
				dropFK = context.alterTable(superClass.getName()).dropConstraint(type.getName()+"_"+ref.getName()+"_fk").getSQL();
				dropCol = context.alterTable(superClass.getName()).dropColumn(type.getName()+"_"+ref.getName()).getSQL();
			}
			
			change = new SQLChange(dropEconClass, dropEcon, dropEconFeatName, dropFK, dropCol);
			
		} else {
			if(ref.getUpperBound() != 1) {
//					String dropFK2, dropFK1, dropPKs, 
				String dropTable;
				EClass eClass = ref.getEContainingClass();
//					dropFK2 = context.alterTable(eClass.getName()+"_"+ref.getName())
//							.dropConstraint(eClass.getName()+"_"+ref.getName()+"_type_e_id_fk").getSQL();
//					dropFK1 = context.alterTable(eClass.getName()+"_"+ref.getName())
//							.dropConstraint(eClass.getName()+"_"+ref.getName()+"_e_id_fk").getSQL();
//					dropPKs = context.alterTable(eClass.getName()+"_"+ref.getName())
//							.dropConstraint(eClass.getName()+"_"+ref.getName()+"_pk").getSQL();
				
				dropTable = context.dropTable(eClass.getName()+"_"+ref.getName()).getSQL();
				
				
				change = new SQLChange(dropTable);
			} else {
				String dropColumn;
				
				
				dropColumn = context.alterTable(superClass.getName()).dropColumn(type.getName()+"_"+ref.getName()).getSQL();
				
				change = new SQLChange(dropColumn);
			}
		}
		return change;
	}

}
