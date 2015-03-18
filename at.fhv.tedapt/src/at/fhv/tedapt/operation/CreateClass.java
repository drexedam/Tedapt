package at.fhv.tedapt.operation;

import static at.fhv.tedapt.helper.NamingConstants.DTYPE;
import static at.fhv.tedapt.helper.NamingConstants.E_VERS;
import static at.fhv.tedapt.helper.NamingConstants.ID;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edapt.common.MetamodelFactory;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.declaration.OperationImplementation;
import org.eclipse.emf.edapt.history.util.HistoryUtils;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;
import org.jooq.SQLDialect;

import at.fhv.tedapt.exception.PrimaryKeyCanBeNullException;
import at.fhv.tedapt.flyway.DatabaseHandler;
import at.fhv.tedapt.flyway.FlywayHandler;
import at.fhv.tedapt.flyway.change.CreateIndex;
import at.fhv.tedapt.flyway.change.CreateTable;
import at.fhv.tedapt.flyway.entity.Column;


/**
 * {@description}
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */

@SuppressWarnings("restriction")
@EdaptOperation(identifier="createClassTedapt", label="Create Class Tedapt", description="A new class and the coresponding table are created.")
public class CreateClass extends OperationImplementation {

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
			
			//Special case: Primary Key needs to be auto_increment -> By now not possible with jOOQ.
			//TODO DBMS independent solution (By now only MySQL)
			
			if(!SQLDialect.MYSQL.equals(DatabaseHandler.getDialect())) {
				return;
			}
			
			CreateTable ct = CreateTable.getInstance(name);
			
			try {
				ct.addPrimaryKey(new Column(ID, "bigint(20)", true, true));
			} catch (PrimaryKeyCanBeNullException e) {
				e.printStackTrace();
			}
			
			Column dType = new Column(DTYPE, "varchar(255)", true);
			ct.addColumn(dType);
			
			ct.addColumn(new Column(E_VERS, "int(11)", true));
			
			FlywayHandler.addChange(ct);
			
			FlywayHandler.addChange(new CreateIndex(name+DTYPE, name, dType));
	
			
			FlywayHandler.saveChangelog(
					HistoryUtils.getHistoryURI(
							metamodel.getEPackages().get(0).eResource()), "Create Class "+name);
		}
			
	}


}
