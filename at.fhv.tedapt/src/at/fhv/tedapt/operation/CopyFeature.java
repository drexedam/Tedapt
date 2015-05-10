package at.fhv.tedapt.operation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.history.util.HistoryUtils;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

import at.fhv.tedapt.flyway.FlywayHandler;
import at.fhv.tedapt.flyway.change.Change;
import at.fhv.tedapt.flyway.change.CopyColumn;
import at.fhv.tedapt.flyway.change.CopyTable;
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
public class CopyFeature extends org.eclipse.emf.edapt.declaration.creation.CopyFeature {

	
	@Override
	public void execute(Metamodel metamodel, Model model) {
		EClass contextClass = feature.getEContainingClass();
		Change change = null;
		
		// metamodel adaptation
		super.execute(metamodel, model);
		
		if(feature instanceof EReference) {
			change = copyReference(contextClass);
		} else if(feature instanceof EAttribute) {
			change = copyAttribute(contextClass);
		}
		
		
		FlywayHandler.addChange(change);
		FlywayHandler.saveChangelog(
				HistoryUtils.getHistoryURI(
						metamodel.getEPackages().get(0).eResource()), "Copy Feature "+feature.getName());
	
		

	}
	

	/**
	 * 
	 * @param contextClass The context class of the attribute
	 * @return A change for creating a copy of the attribute at the database
	 */
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

	/**
	 * 
	 * @param contextClass The context class of the reference
	 * @return A change for creating a copy of the reference at the database
	 */
	private Change copyReference(EClass contextClass) {
		EReference reference = (EReference)feature;
		EClass refSuperClass = CommonTasks.getMostAbstract(reference.getEReferenceType());
		EClass superClass = CommonTasks.getMostAbstract(contextClass);
		
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
