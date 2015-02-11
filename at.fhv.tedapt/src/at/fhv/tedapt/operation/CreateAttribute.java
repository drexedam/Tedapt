package at.fhv.tedapt.operation;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.edapt.common.MetamodelFactory;
import org.eclipse.emf.edapt.declaration.EdaptConstraint;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.declaration.OperationImplementation;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

import at.fhv.tedapt.exception.PrimaryKeyCanBeNullException;
import at.fhv.tedapt.flyway.DatabaseHandler;
import at.fhv.tedapt.flyway.FlywayHandler;
import at.fhv.tedapt.flyway.change.AddColumn;
import at.fhv.tedapt.flyway.change.Change;
import at.fhv.tedapt.flyway.change.CreateTable;
import at.fhv.tedapt.flyway.entity.Column;
import at.fhv.tedapt.helper.CommonTasks;

/**
 * {@description}
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */

@SuppressWarnings("restriction")
@EdaptOperation(identifier="createAttributeTedapt", label="Create Attribute Tedapt", description="A new attribute and the coresponding column are created.")
public class CreateAttribute extends OperationImplementation {

	/** {@description} */
	@EdaptParameter(main = true, description = "The class in which the attribute is created")
	public EClass eClass;

	/** {@description} */
	@EdaptParameter(description = "The name of the new attribute")
	public String name;

	/** {@description} */
	@EdaptParameter(description = "The type of the new attribute")
	public EDataType type;

	/** {@description} */
	@EdaptParameter(description = "The lower bound of the new attribute")
	public int lowerBound = 0;

	/** {@description} */
	@EdaptParameter(description = "The upper bound of the new reference")
	public int upperBound = 1;

	/** {@description} */
	@EdaptParameter(description = "The default value literal", optional = true)
	public String defaultValue;
	
	/** {@description} */
	@EdaptConstraint(description="Not nullable attributes need a default value")
	public boolean checkDefault() {
		if(upperBound == 1 && upperBound==lowerBound) {
			return defaultValue != null && !defaultValue.isEmpty();
		}
		
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void execute(Metamodel metamodel, Model model) {
		
		//Metamodel adaption
		MetamodelFactory.newEAttribute(eClass, name, type, lowerBound,
				upperBound, defaultValue);
		
		//DB adaption
		
		//Name of most abstract class
		String superClass = CommonTasks.getMostAbstract(eClass).getName();
		
		
		
		Change change;
		//Attributes upperBound == 1 are mapped as column otherwise as table
		if(!CommonTasks.mapAsTable(upperBound, type.getName())) {
			Column c = new Column(name, DatabaseHandler.mapDataType(type),(upperBound == lowerBound));
			c.setDefault(defaultValue);
			change = new AddColumn(superClass, c);
		} else {
			String tableName = eClass.getName()+"_"+name;
			
			CreateTable ct = new CreateTable(tableName);
			Column pk = new Column(tableName+"_e_id", "bigint(20)");
			try {
				ct.addPrimaryKey(pk);
			} catch (PrimaryKeyCanBeNullException e) {
				e.printStackTrace();
			}
			
			
			Column elt = new Column("elt", DatabaseHandler.mapDataType(type));
			ct.addColumn(elt);
			
			Column idx = new Column(tableName+"_idx", "int(11)");
			try {
				ct.addPrimaryKey(idx);
			} catch (PrimaryKeyCanBeNullException e) {
				e.printStackTrace();
			}
			
			
			ct.addForeignKey(idx, superClass, "e_id");
			change = ct;			
		}
		
		FlywayHandler.addChange(change);
		
		FlywayHandler.saveChangelog(metamodel.getEPackages().get(0).getNsPrefix());
		
	}


}
