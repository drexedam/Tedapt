package at.fhv.tedapt.flyway;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.flywaydb.core.Flyway;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	private static final String _saveTo = "./plugins/at.fhv.tedapt/logs";
	
	private static long _version;
	private static boolean _migrated;
	private static String _nsPrefix = "";
	
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

		flyway.setLocations("filesystem:"+_saveTo+_nsPrefix);
		flyway.migrate();
	
		_migrated = true;
		saveVersionInfo();

	}
	

	private static void saveChangelog() {

		if(getLog().isEmpty()) {
			return;
		}
		
		try {

			if(_migrated) {
				_version++;
				_migrated = false;
				saveVersionInfo();
			}
			
			//TODO redo saving
			File d = new File(_saveTo+_nsPrefix);
			if(!d.exists()) {
				d.mkdirs();
			}
			System.out.println(d.getCanonicalPath());
			File f = new File(_saveTo+_nsPrefix+"/V"+_version+"__tedapt_changelog.sql");

			FileWriter fw = new FileWriter(f,true);
			fw.write(getLog().getSQL());
			fw.flush();
			fw.close();
			_changeLog = new Changelog();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Adds a change to the changelog.
	 * @param c The change to be added
	 */
	public static void addChange(Change c) {
		getLog().addChange(c);
	}

	/**
	 * Saves the changelog to the file system. 
	 * Only logs saved to the proper directory will be detected by flyway.
	 */
	public static void saveChangelog(String nsPrefix) {
		setNSPrefix(nsPrefix);
		saveChangelog();
	}
	
	/**
	 * 
	 * @return The current nsPrefix
	 */
	public static String getNSPrefix() {
		return _nsPrefix.replace("/", "");
	}
	
	public static void setNSPrefix(String value) {
		if(!_nsPrefix.equals(value)) {
			_nsPrefix = "/"+value;
			readVersionInfo();
		}
		
	}
	

	/**
	 * Reads version information from corresponding json file
	 */
	private static void readVersionInfo() {
		File f = new File(_saveTo+_nsPrefix+".json");
		if(!f.exists()) {
			_version = 1;
			_migrated = false;
			return;
		}
		
		JSONParser jparser = new JSONParser();
		
		try {
			JSONObject jobj = (JSONObject) jparser.parse(new FileReader(f));
			_version = (Long) jobj.get("version");
			_migrated = (boolean) jobj.get("migrated");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Writes version information to corresponding json file
	 */
	@SuppressWarnings("unchecked")
	private static void saveVersionInfo() {
		JSONObject jobject = new JSONObject();
		jobject.put("version", new Integer((int) _version));
		jobject.put("migrated", new Boolean(_migrated));
		
		File f = new File(_saveTo+_nsPrefix+".json");
		
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(jobject.toJSONString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
