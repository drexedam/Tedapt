package at.fhv.tedapt.flyway;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.flywaydb.core.Flyway;

import at.fhv.tedapt.Activator;
import at.fhv.tedapt.flyway.change.Change;
import at.fhv.tedapt.preferences.PreferenceConstants;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class FlywayHandler {

	private static Changelog _changeLog;
	
	/**
	 * 
	 * @return The currently used changelog or a new one if none exists
	 */
	protected static Changelog getLog() {
		if(_changeLog == null) {
			_changeLog = new Changelog();
		}
		
		return _changeLog;
	}
	
	/**
	 * Updates the database to the newest version based on all changelogs safed by.
	 */
	public static void migrateChanges() {
		Flyway flyway = new Flyway();
		flyway.setDataSource(DatabaseHandler.getJDBCURL(),
				Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_UNAME), 
				Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PW));		
		
		flyway.setLocations("filesystem:./"+_folderPath);
		flyway.migrate();
		
		int version = Activator.getDefault().getPreferenceStore().getInt(PreferenceConstants.CL_VERSION);
		Activator.getDefault().getPreferenceStore().setValue(PreferenceConstants.CL_VERSION, ++version);

	}
	
	/**
	 * Saves the changelog to the file system. Only logs saved to the proper directory will be detected by flyway.
	 */
	public static void saveChangelog() {

		if(getLog().isEmpty()) {
			return;
		}
		
		try {

			int version = Activator.getDefault().getPreferenceStore().getInt(PreferenceConstants.CL_VERSION);

			File d = new File("./"+_folderPath);
			if(!d.exists()) {
				d.mkdir();
			}
			File f = new File("./"+_folderPath+"/V"+version+"__tedapt_changelog.sql");

			FileWriter fw = new FileWriter(f,true);
			fw.write(getLog().getSQL());
			fw.flush();
			fw.close();
			_changeLog = new Changelog();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static String editorInputName() {
		IWorkbench workBench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workBench==null?null:workBench.getActiveWorkbenchWindow();
		IWorkbenchPage page = window==null?null:window.getActivePage();
		IEditorPart editor = page==null?null:page.getActiveEditor();
		
		
		return editor==null?"":editor.getEditorInput().getName();
	}
	
	/**
	 * Adds a change to the changelog.
	 * @param c The change to be added
	 */
	public static void addChange(Change c) {
		getLog().addChange(c);
	}

	private static String _folderPath = "";
	public static void saveChangelog(String nsURI) {
		_folderPath = nsURI;
		saveChangelog();
	}
}
