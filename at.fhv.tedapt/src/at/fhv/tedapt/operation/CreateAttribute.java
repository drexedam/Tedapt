package at.fhv.tedapt.operation;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.edapt.common.MetamodelFactory;
import org.eclipse.emf.edapt.declaration.EdaptConstraint;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

import at.fhv.tedapt.TedaptMigration;
import at.fhv.tedapt.helper.CommonTasks;
import at.fhv.tedapt.hibernate.HibernateHandler;

/**
 * {@description}
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */

@SuppressWarnings("restriction")
@EdaptOperation(identifier="createAttributeTedapt", label="Create Attribute Tedapt", description="A new attribute and the coresponding column are created.")
public class CreateAttribute extends TedaptMigration {

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
		String query;
		
		//Name of most abstract class
		String superClass = CommonTasks.getMostAbstract(eClass).getName();
		

		//Attributes upperBound == 1 are mapped as column otherwise as table
		if(!CommonTasks.mapAsTable(upperBound, type.getName())) {
			query = HibernateHandler.getQueryFactory().createAttributeQuery(
					superClass, 
					name, 
					HibernateHandler.mapDataType(type), 
					(upperBound == lowerBound),
					defaultValue); 
		} else {
			query = HibernateHandler.getQueryFactory().createAttributeTableQuery(
					eClass.getName(), 
					superClass, 
					name, 
					HibernateHandler.mapDataType(type));
		}
		
		HibernateHandler.executeQuery(query);	
		
	}


}
