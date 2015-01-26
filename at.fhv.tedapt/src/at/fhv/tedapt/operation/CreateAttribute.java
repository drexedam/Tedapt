package at.fhv.tedapt.operation;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.edapt.common.MetamodelFactory;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;
import org.hibernate.Session;

import at.fhv.tedapt.TedaptMigration;
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

	/** {@inheritDoc} */
	@Override
	public void execute(Metamodel metamodel, Model model) {
		MetamodelFactory.newEAttribute(eClass, name, type, lowerBound,
				upperBound, defaultValue);
		
		String query;
		
		//Need name of most abstract class
		String superClass;
		if(eClass.getEAllSuperTypes().isEmpty()) {
			superClass = eClass.getName();
		} else {
			superClass = eClass.getEAllSuperTypes().get(0).getName();
		}
		

		//Common attributes are mapped as columns
		//Attributes which upper bound > 1 and EByteArrays are mapped as Table
		if(upperBound == 1 && !type.getName().equals("EByteArray")) {
			query = HibernateHandler.getQueryFactory().createAttributeQuery(
					superClass, 
					name, 
					HibernateHandler.mapDataType(type), 
					(upperBound == lowerBound)); 
		} else {
			query = HibernateHandler.getQueryFactory().createAttributeTableQuery(
					eClass.getName(), 
					superClass, 
					name, 
					HibernateHandler.mapDataType(type));
		}
		
		
		Session ses = HibernateHandler.getFactory().getCurrentSession();
		
		ses.getTransaction().begin();
		
		ses.createSQLQuery(query).executeUpdate();
		
		
		ses.getTransaction().commit();
		
		
	}


}
