package at.fhv.tedapt.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import at.fhv.tedapt.flyway.FlywayHandler;
import at.fhv.tedapt.ui.dialog.MigrationDialog;

public class MigrateCustomHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		MigrationDialog md = new MigrationDialog(window.getShell());
		md.create();
		if(md.open() != Window.OK) {
			return null;
		}
		
		ISelectionService isSer = window.getSelectionService();
		
		IStructuredSelection isSel = (IStructuredSelection) isSer.getSelection();
		
		if (isSel.getFirstElement() instanceof IFile) {
			
			
			IFile file = (IFile) isSel.getFirstElement();
			
			IPath path = file.getLocation();
						
			
			if(path.getFileExtension().equals("history")) {
				String pathToChangelogs = path.toPortableString();

				pathToChangelogs = pathToChangelogs.substring(0, pathToChangelogs.length()-(".history").length());
				pathToChangelogs = pathToChangelogs + FlywayHandler.TEDAPT_POSTFIX + FlywayHandler.CHANGELOG_FOLDER;
				
				FlywayHandler.migrateChanges(pathToChangelogs, md.getData());
			}
			
		}

	 
		return null;
	}

}
