package at.fhv.tedapt.operation;

import static at.fhv.tedapt.helper.NamingConstants.E_CON;
import static at.fhv.tedapt.helper.NamingConstants.E_CON_CLASS;
import static at.fhv.tedapt.helper.NamingConstants.E_CON_FEAT;
import static at.fhv.tedapt.helper.NamingConstants.FK_SUFFIX;

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
		
		// metamodel adaptation
		metamodel.delete(feature);
		if (feature instanceof EReference) {
			EReference reference = (EReference) feature;
			if (reference.getEOpposite() != null) {
				metamodel.delete(reference.getEOpposite());
			}
		}	
		
		Change change = null, change2 = null;
		DSLContext context = DatabaseHandler.getContext();
		if (feature instanceof EAttribute) {
			
			EAttribute atr = (EAttribute) feature;
			
			EClass contClass = CommonTasks.getMostAbstract(atr.getEContainingClass());
			EDataType dataType = atr.getEAttributeType();

			if(!CommonTasks.mapAsTable(atr.getUpperBound(), dataType.getName())) {
				//Attribute is not mapped as table
				
				change = new SQLChange(context.alterTable(contClass.getName()).drop(atr.getName()).getSQL());
			} else {
				
				change = new SQLChange(context.dropTable(contClass.getName()+"_"+atr.getName()).getSQL());
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
		
		
		

	}

	/**
	 * @param context DSL context used by jOOQ
	 * @return A change to be added to the changelog
	 */
	private Change deleteReference(DSLContext context, EReference ref) {
		Change change;
		
		EClass superClass = CommonTasks.getMostAbstract(ref.getEContainingClass());
		EClass type = ref.getEReferenceType();
		EClass refSuperClass  = CommonTasks.getMostAbstract(type);
		
		if(ref.isContainment()) {
			
			SQLChange tempChange = new SQLChange();
			
			if(CommonTasks.existingContReference(superClass)) {
				String dropEconClass = context.alterTable(refSuperClass.getName()).dropColumn(E_CON_CLASS).getSQL();
				String dropEcon = context.alterTable(refSuperClass.getName()).dropColumn(E_CON).getSQL();
				String dropEconFeatName = context.alterTable(refSuperClass.getName()).dropColumn(E_CON_FEAT).getSQL();
				tempChange.addQueries(dropEcon, dropEconClass, dropEconFeatName);
			}
			
			String dropFK, dropCol;
			if(ref.getUpperBound() != 1) {
				dropFK = context.alterTable(refSuperClass.getName()).dropConstraint(superClass.getName()+"_"+ref.getName()+FK_SUFFIX).getSQL();
				dropCol = context.alterTable(refSuperClass.getName()).dropColumn(superClass.getName()+"_"+ref.getName()).getSQL();
			} else {
				dropFK = context.alterTable(superClass.getName()).dropConstraint(type.getName()+"_"+ref.getName()+FK_SUFFIX).getSQL();
				dropCol = context.alterTable(superClass.getName()).dropColumn(type.getName()+"_"+ref.getName()).getSQL();
			}
			
			tempChange.addQueries(dropFK, dropCol);
			
			change = tempChange;
			
		} else {
			if(ref.getUpperBound() != 1) { 
				String dropTable;
				EClass eClass = ref.getEContainingClass();
				
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
