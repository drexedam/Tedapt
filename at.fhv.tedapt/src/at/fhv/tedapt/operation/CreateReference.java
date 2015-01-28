package at.fhv.tedapt.operation;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edapt.common.MetamodelFactory;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

import at.fhv.tedapt.TedaptMigration;
import at.fhv.tedapt.helper.CommonTasks;
import at.fhv.tedapt.hibernate.HibernateHandler;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
@SuppressWarnings("restriction")
@EdaptOperation(identifier = "newReferenceTedapt", label = "Create Reference Tedapt", description = "Creates a new reference and corresponding database entries.")
public class CreateReference extends TedaptMigration {

	/** {@description} */
	@EdaptParameter(main = true, description = "The class in which the reference is created")
	public EClass eClass;

	/** {@description} */
	@EdaptParameter(description = "The name of the new reference")
	public String name;

	/** {@description} */
	@EdaptParameter(description = "The type of the new reference")
	public EClass type;

	/** {@description} */
	@EdaptParameter(description = "The lower bound of the new reference")
	public int lowerBound = 0;

	/** {@description} */
	@EdaptParameter(description = "The upper bound of the new reference")
	public int upperBound = 1;

	/** {@description} */
	@EdaptParameter(description = "Whether the new reference is a containment reference")
	public Boolean containment = false;

	/** {@description} */
	@EdaptParameter(description = "The opposite reference of the new reference", optional = true)
	public EReference opposite;
	

	/** {@inheritDoc} */
	@Override
	public void execute(Metamodel metamodel, Model model) {
		if(opposite != null) {
			//For now because opposite seems to be mapped strange
			return;
		}
		//Metamodel changes
//		EReference reference = 
		MetamodelFactory.newEReference(eClass, name, type,
				lowerBound, upperBound, containment);
		
		//Not needed while opposite is not possible
//		if (opposite != null) {
//			metamodel.setEOpposite(opposite, reference);
//		}
		
		//DB changes
		
		EClass superClass = CommonTasks.getMostAbstract(eClass);
		EClass refSuperClass = CommonTasks.getMostAbstract(type);
		boolean notNull = (upperBound == 1 && lowerBound == 1);
		String query = null;
		if(containment) {
			//create containment columns
			String q1 = HibernateHandler.getQueryFactory().createContainerCol(refSuperClass.getName());
			String q2 = "";
			if(upperBound != 1) {
				// additional columns in (super-)class which is contained
				q2 = query = HibernateHandler.getQueryFactory().createSimpleReferenceQuery(
						refSuperClass.getName(), 
						superClass.getName(), 
						superClass.getName(), 
						name,
						notNull);
				q2 = HibernateHandler.getQueryFactory().concatQueries(q2, 
						HibernateHandler.getQueryFactory().addIDXQuery(eClass.getName(), refSuperClass.getName(), name));
			} else {
				// additional columns in (super-)class which contains the other class
				q2 = HibernateHandler.getQueryFactory().createSimpleReferenceQuery(
						superClass.getName(), 
						type.getName(), 
						refSuperClass.getName(), 
						name,
						notNull);
			}
			
			query = HibernateHandler.getQueryFactory().concatQueries(q1, q2);
			
		} else {
			if(upperBound != 1) {
				// create table for reference
				
				query = HibernateHandler.getQueryFactory().createReferenceTableQuery(
						eClass.getName(), 
						superClass.getName(), 
						type.getName(), 
						refSuperClass.getName(), 
						name);
			} else {
				// add column in containing class
				query = HibernateHandler.getQueryFactory().createSimpleReferenceQuery(
						superClass.getName(), 
						type.getName(), 
						refSuperClass.getName(), 
						name,
						notNull);
			}
		}
		
		HibernateHandler.executeQuery(query);
	}

}
