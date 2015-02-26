package at.fhv.tedapt.customexample.edapt;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.edapt.migration.CustomMigration;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.spi.migration.Instance;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

/**
 * 
 * @author Damian Drexel
 * Example for migrating an integer to two boolean if model is persisted as XMI
 *
 */
public class IntegerToBooleanExample extends CustomMigration {

	private EAttribute _integer; 
	
	@Override
	public void migrateBefore(Model model, Metamodel metamodel)
			throws MigrationException {
		//Get the integer attribute
		_integer = metamodel.getEAttribute("example.ExampleClass.myInteger");		
	}
	
	@Override
	public void migrateAfter(Model model, Metamodel metamodel)
			throws MigrationException {
		//For each instance of ExampleClass
		for(Instance exampleClass : model.getAllInstances("library.ExampleClass")) {
			//Get the integer value
			int myInteger = exampleClass.unset(_integer);
			
			//Set boolean values according to integer value
			switch (myInteger) {
			case 0:
				exampleClass.set("library.ExampleClass.boolean1", 0);
				exampleClass.set("library.ExampleClass.boolean2", 0);
				
				break;
			case 1:
				exampleClass.set("library.ExampleClass.boolean1", 0);
				exampleClass.set("library.ExampleClass.boolean2", 1);
				break;
			case 2:
				exampleClass.set("library.ExampleClass.boolean1", 1);
				exampleClass.set("library.ExampleClass.boolean2", 0);
				break;
			case 3:
			default:
				exampleClass.set("library.ExampleClass.boolean1", 1);
				exampleClass.set("library.ExampleClass.boolean2", 1);
				break;
			}
		}
	}
}
