package at.fhv.tedapt.operation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edapt.common.MetamodelFactory;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

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
@EdaptOperation(identifier="createClassTedapt", label="Create Class Tedapt", description="A new class and the coresponding table are created.")
public class CreateClass extends TedaptMigration {

	/** {@description} */
	@EdaptParameter(main=true,description="The package to create the class within")
	public EPackage ePackage;
	
	/** {@description} */
	@EdaptParameter(description="The name of the new class")
	public String name;
	
	/** {@description} */
	@EdaptParameter(optional = true, description = "The super classes of the new class")
	public List<EClass> superClasses = new ArrayList<EClass>();
	
	/** {@description} */
	@EdaptParameter(description = "Whether the class is abstract or not")
	public Boolean abstr = false;
	
	/** {@inheritDoc} */
	@Override
	protected void execute(Metamodel metamodel, Model model)
			throws MigrationException {
		MetamodelFactory.newEClass(ePackage, name, superClasses, abstr);
		
		//Only super class needs to be represented as table
		if(superClasses.isEmpty()) {
			HibernateHandler.executeQuery(HibernateHandler.getQueryFactory().createClassQuery(name));
		}
		
		
		
	}


}
