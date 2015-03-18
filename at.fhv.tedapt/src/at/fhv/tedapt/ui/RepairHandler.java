package at.fhv.tedapt.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import at.fhv.tedapt.flyway.FlywayHandler;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class RepairHandler extends AbstractHandler {

	/**
	 * Repairs the database after a failed migration.
	 * See Flyway documentation for cases where manual actions are required
	 */
	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		//TODO configure Flyway for loading migrations
		FlywayHandler.repairDatabase();
		return null;
	}

}
