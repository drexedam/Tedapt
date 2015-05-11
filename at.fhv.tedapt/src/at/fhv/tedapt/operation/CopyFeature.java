package at.fhv.tedapt.operation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.declaration.OperationImplementation;
import org.eclipse.emf.edapt.history.util.HistoryUtils;
import org.eclipse.emf.edapt.migration.MigrationException;
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
public class CopyFeature extends OperationImplementation {

	
    /** {@description} */
    @EdaptParameter(main = true, description = "The feature to be copied")
    public EStructuralFeature feature;

    /** {@description} */
    @EdaptParameter(description = "The name of the copy")
    public String name;
	
	@Override
	public void execute(Metamodel metamodel, Model model) throws MigrationException {
		EClass contextClass = feature.getEContainingClass();
		Change change = null;

		
		if(feature instanceof EReference) {
			
            CreateReference cf = new CreateReference();
            cf.eClass = contextClass;
            cf.lowerBound = feature.getLowerBound();
            cf.upperBound = feature.getUpperBound();
            cf.containment = ((EReference) feature).isContainment();
            cf.name = name;
            cf.type = ((EReference) feature).getEReferenceType();
            cf.checkAndExecute(metamodel, model);
            //createReference(contextClass, metamodel, model);

			
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
