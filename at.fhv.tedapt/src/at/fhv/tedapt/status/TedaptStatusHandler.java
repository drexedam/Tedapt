package at.fhv.tedapt.status;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.statushandlers.AbstractStatusHandler;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;

@SuppressWarnings("restriction")
public class TedaptStatusHandler extends AbstractStatusHandler {

	@Override
	public void handle(StatusAdapter statusAdapter, int style) {
		// TODO Auto-generated method stub
		
		IStatus status = statusAdapter.getStatus();
		
		
		if((style & StatusManager.SHOW) == StatusManager.SHOW) {
			
			Display.getDefault().asyncExec(new Runnable() {		
				@Override
				public void run() {
					if(status.getSeverity() == IStatus.ERROR) {
						ErrorDialog.openError(Display.getDefault().getActiveShell(), "Tedapt Error", status.getMessage(), status);
				
					} else {
						MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Tedapt Notification", status.getMessage());
					}
				}
			});
		}
		
		if((style & StatusManager.LOG) == StatusManager.LOG ) {
			StatusManager.getManager().addLoggedStatus(status);
			WorkbenchPlugin.getDefault().getLog().log(status);
		}
	}
	
	

}
