package at.fhv.tedapt.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;

import at.fhv.tedapt.flyway.FlywayHandler;

public class MigrateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		MigrateDialog md = new MigrateDialog(null);
		md.create();
		if(md.open() == Window.OK) {
			if(md.getNSURI() == null || md.getNSURI().isEmpty()) {
				return null;
			}
			FlywayHandler.setNSUIR(md.getNSURI());
			System.out.println(md.getNSURI());
		}
//		MigrateDialog md = new MigrateDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		FlywayHandler.migrateChanges();
		return null;
	}

}
