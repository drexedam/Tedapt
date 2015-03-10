package at.fhv.tedapt.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import at.fhv.tedapt.flyway.FlywayHandler;

public class RepairHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		FlywayHandler.repairDatabase();
		return null;
	}

}
