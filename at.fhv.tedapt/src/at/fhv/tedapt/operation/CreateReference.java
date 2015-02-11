package at.fhv.tedapt.operation;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edapt.common.MetamodelFactory;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.declaration.OperationImplementation;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

import at.fhv.tedapt.exception.PrimaryKeyCanBeNullException;
import at.fhv.tedapt.flyway.FlywayHandler;
import at.fhv.tedapt.flyway.change.AddColumn;
import at.fhv.tedapt.flyway.change.AddReferenceColumn;
import at.fhv.tedapt.flyway.change.CreateTable;
import at.fhv.tedapt.flyway.entity.Column;
import at.fhv.tedapt.helper.CommonTasks;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
@SuppressWarnings("restriction")
@EdaptOperation(identifier = "newReferenceTedapt", label = "Create Reference Tedapt", description = "Creates a new reference and corresponding database entries.")
public class CreateReference extends OperationImplementation {

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

		if(containment) {
			//create containment columns
			FlywayHandler.addChange(new AddColumn(
					refSuperClass.getName(), 
					new Column("econtainer_class", "varchar(255)")));
			FlywayHandler.addChange(new AddColumn(
					refSuperClass.getName(), 
					new Column("e_container", "varchar(255)")));
			FlywayHandler.addChange(new AddColumn(
					refSuperClass.getName(),
					new Column("e_container_feature_name", "varchar(255)")));
	
			if(upperBound != 1) {
				// additional columns in (super-)class which is contained
				FlywayHandler.addChange(new AddReferenceColumn(
						new Column(superClass.getName()+"_"+name, "bigint(28)",notNull),
						refSuperClass.getName(), 
						superClass.getName(), 
						"e_id"));
			} else {
				// additional columns in (super-)class which contains the other class
				FlywayHandler.addChange(new AddReferenceColumn(
						new Column(type.getName()+"_"+name, "bigint(28)",notNull),
						superClass.getName(), 
						refSuperClass.getName(), 
						"e_id"));
			}
		} else {
			if(upperBound != 1) {
				// create table for reference
				CreateTable ct = new CreateTable(eClass.getName()+"_"+name);
				
				Column c1 = new Column(eClass.getName()+"_e_id", "bigint(20)", true);
				Column c2 = new Column(eClass.getName()+"_"+name+"_idx", "int(11)", true);
				ct.addColumn(c2);
				ct.addForeignKey(c1, superClass.getName(), "(e_id)");
				ct.addForeignKey(new Column(type.getName()+"_e_id", "bigint(20)", true), refSuperClass.getName(), "(e_id)");
				
				try {
					ct.addPrimaryKey(c1);
					ct.addPrimaryKey(c2);
				} catch (PrimaryKeyCanBeNullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			} else {
				// add column in containing class
				FlywayHandler.addChange(new AddReferenceColumn(
						new Column(type.getName()+"_"+name, "bigint(28)",notNull),
						superClass.getName(), 
						refSuperClass.getName(), 
						"e_id"));
			}
		}
		
		FlywayHandler.saveChangelog(metamodel.getEPackages().get(0).getNsPrefix());
		
	}

}
