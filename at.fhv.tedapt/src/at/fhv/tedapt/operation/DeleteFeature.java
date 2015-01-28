package at.fhv.tedapt.operation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

import at.fhv.tedapt.TedaptMigration;
import at.fhv.tedapt.helper.CommonTasks;
import at.fhv.tedapt.hibernate.HibernateHandler;

@EdaptOperation(identifier="deleteFeaterTedapt", label="Delete Feature Tedapt", description="Deletes a feature and its corresponding DB entries.")
public class DeleteFeature extends TedaptMigration {

	/** {@description} */
	@EdaptParameter(main = true, description = "The feature to be deleted")
	public EStructuralFeature feature;
	
	@Override
	protected void execute(Metamodel metamodel, Model model)
			throws MigrationException {
		
		if (feature instanceof EAttribute) {
			
			EAttribute atr = (EAttribute) feature;
			
			EClass contClass = CommonTasks.getMostAbstract(atr.getEContainingClass());
			EDataType dataType = atr.getEAttributeType();
			String query;
			if(!CommonTasks.mapAsTable(atr.getUpperBound(), dataType.getName())) {
				//Attribute is not mapped as table
				query = HibernateHandler.getQueryFactory().deleteColumn(contClass.getName(), atr.getName());
			} else {
				query = HibernateHandler.getQueryFactory().deleteTable(contClass.getName()+"_"+atr.getName());
			}
			
			HibernateHandler.executeQuery(query);
			
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

}
