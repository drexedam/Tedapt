package at.fhv.tedapt.flyway;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.flywaydb.core.Flyway;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import at.fhv.tedapt.Activator;
import at.fhv.tedapt.flyway.change.Change;
import at.fhv.tedapt.helper.MigrationData;
import at.fhv.tedapt.preferences.PreferenceConstants;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class FlywayHandler {

	public static final String TEDAPT_POSTFIX = "_tedapt";
	public static final String CHANGELOG_FOLDER = "/changelogs";
	
	private static Changelog _changeLog;
	
	
	private static long _numOfTask = 1;
	private static long _version;
	private static String _projectFolder = "";
	
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
	public static void migrateChanges(String path) {
		Flyway flyway = getFlywayObj();
		
		//Are out of order migrations allowed? (e.g. V1 and V3 are applied but V2 is found)
		flyway.setOutOfOrder(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.FLYWAY_OUT_OF_ORDER));
		
		//Experimental
		int baseLineVersion = Activator.getDefault().getPreferenceStore().getInt(PreferenceConstants.FLYWAY_BASE_VERSION);
		if(baseLineVersion > 0) {
			flyway.setBaselineVersion(""+baseLineVersion);
		}
		
		//Experimental
		int targetVersion = Activator.getDefault().getPreferenceStore().getInt(PreferenceConstants.FLYWAY_TARGET_VERSION);
		if(targetVersion > 0) {
			flyway.setTarget(""+targetVersion);
		}
		
		//flyway.baseline();
		
		flyway.setLocations("filesystem:"+path);
		flyway.migrate();
	
		saveVersionInfo();

	}
	
	public static void migrateChanges(String path, MigrationData data) {
		Flyway flyway = new Flyway();
		flyway.setBaselineOnMigrate(true);
		flyway.setDataSource(DatabaseHandler.getJDBCURL(data.getDBAdr(), data.getDBName()),
				data.getUName(), 
				data.getPW());
		
		
		flyway.setOutOfOrder(data.outOfOrder());
		
		//Experimental
		int baseLineVersion = data.getBaseVersion();
		if(baseLineVersion > 0) {
			flyway.setBaselineVersion(""+baseLineVersion);
		}
		
		//Experimental
		int targetVersion = data.getMaxVersion();
		if(targetVersion > 0) {
			flyway.setTarget(""+targetVersion);
		}
		
		flyway.setLocations("filesystem:"+path);
		flyway.migrate();
		
		saveVersionInfo();
	}
	
	public static void repairDatabase() {
		getFlywayObj().repair();
	}

	/**
	 * @return A Flyway object configured with the current settings
	 */
	private static Flyway getFlywayObj() {
		Flyway flyway = new Flyway();
		flyway.setBaselineOnMigrate(true);
		flyway.setDataSource(DatabaseHandler.getJDBCURL(),
				Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_UNAME), 
				Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PW));
		return flyway;
	}
	
	
	
	/**
	 * 
	 * @param uri The histories URI
	 * @param operationName The operations name which calls the method
	 */
	public static void saveChangelog(URI uri, String operationName) {

		setProjectFolder(uri);
		
		if(getLog().isEmpty()) {
			return;
		}
		
		try {
			
			File d = new File(Platform.getLocation()+getChangelogFolder());
			if(!d.exists()) {
				d.mkdirs();
			}
			
			int tempV = getNumOfReleases(uri);
			
			if(tempV != _version) {
				_version = tempV;
				_numOfTask = 1;
			}
			
			
			System.out.println(d.getCanonicalPath());
			File f = new File(Platform.getLocation()
								+getChangelogFolder()
								+"/V"+_version+"_"+_numOfTask+"__"+operationName.replace(" ", "_")+".sql");

			FileWriter fw = new FileWriter(f,true);
			fw.write(getLog().getSQL());
			fw.flush();
			fw.close();
			
			_changeLog = new Changelog();
			_numOfTask++;
			
			saveVersionInfo();
			
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
	 * 
	 * @return The current nsPrefix
	 */
	public static String getProjectFolder() {
		return _projectFolder;
	}
	
	public static void setProjectFolder(URI uri) {
		String temp = uri.toPlatformString(false);
		temp = temp.substring(0, temp.length()-(".history").length());
		
		if(!_projectFolder.equals(temp)) {
			_projectFolder = temp;
			readVersionInfo();
		}
		
	}
	
	/**
	 * 
	 * @return The path to the folder to be used for saving changelogs
	 */
	public static String getChangelogFolder() {
		return getProjectFolder()+TEDAPT_POSTFIX+CHANGELOG_FOLDER;
	}
	
	
	/**
	 * Reads version information from corresponding json file
	 */
	private static void readVersionInfo() {
		File f = new File(Platform.getLocation()+getChangelogFolder()+".json");
		if(!f.exists()) {
			_version = 1;
			_numOfTask = 1;
			return;
		}
		
		JSONParser jparser = new JSONParser();
		
		try {
			JSONObject jobj = (JSONObject) jparser.parse(new FileReader(f));
			_version = (Long) jobj.get("version");
			_numOfTask = (Long) jobj.get("numOfTask");
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
		jobject.put("numOfTask", new Integer((int) _numOfTask));
		
		File f = new File(Platform.getLocation()+getChangelogFolder()+".json");
		
		try {
			FileWriter fw = new FileWriter(f, false);
			fw.write(jobject.toJSONString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param uri The history's URI 
	 * @return Number of releases
	 */
	private static int getNumOfReleases(URI uri) {
		File xmlFile = new File(Platform.getLocation()+uri.toPlatformString(false));

		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
			NodeList nList = doc.getElementsByTagName("releases");
			return nList.getLength();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		
		return -1;
	}
}
