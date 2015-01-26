package at.fhv.tedapt;

import org.eclipse.emf.edapt.declaration.EdaptParameter;
import org.eclipse.emf.edapt.declaration.OperationImplementation;

import at.fhv.tedapt.hibernate.HibernateHandler;


/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public abstract class TedaptMigration extends OperationImplementation {

	
	/** {@description} */
	@EdaptParameter(description="Database user")
	public String user = HibernateHandler.USER;
	
	/** {@description} */
	@EdaptParameter(description="Adress and Databasename")
	public String dataBase = HibernateHandler.DATABASE;

	/** {@description} */
	@EdaptParameter(description="Database password")
	public String password = HibernateHandler.PW;
	
}
