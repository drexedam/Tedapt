package at.fhv.tedapt.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.handlers.HandlerUtil;

import at.fhv.tedapt.flyway.FlywayHandler;

public class MigrateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		ISelectionService isSer = HandlerUtil.getActiveWorkbenchWindowChecked(event).getSelectionService();
		
		IStructuredSelection isSel = (IStructuredSelection) isSer.getSelection();
		
		if (isSel.getFirstElement() instanceof IFile) {
			
			
			IFile file = (IFile) isSel.getFirstElement();
			
			IPath path = file.getLocation();
						
			
			if(path.getFileExtension().equals("history")) {
				String pathToChangelogs = path.toPortableString();

				pathToChangelogs = pathToChangelogs.substring(0, pathToChangelogs.length()-(".history").length());
				pathToChangelogs = pathToChangelogs + FlywayHandler.TEDAPT_POSTFIX + FlywayHandler.CHANGELOG_FOLDER;
				
				FlywayHandler.migrateChanges(pathToChangelogs);
			}
			
		}

	 
		return null;
		
//		MigrateDialog md = new MigrateDialog(null);
//		md.create();
//		if(md.open() == Window.OK) {
//			if(md.getNSURI() == null || md.getNSURI().isEmpty()) {
//				return null;
//			}
//			
//			
//			//TODO: Redo
////			FlywayHandler.setNSPrefix(md.getNSURI());
//			System.out.println(md.getNSURI());
//		}
////		MigrateDialog md = new MigrateDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
//		FlywayHandler.migrateChanges();
//		return null;
	}

}
