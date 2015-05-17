/**
 */
package libraryExample.presentation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import libraryExample.Book;
import libraryExample.BookCategory;
import libraryExample.Library;
import libraryExample.LibraryFactory;
import libraryExample.LibraryPackage;
import libraryExample.Writer;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.teneo.PersistenceOptions;
import org.eclipse.emf.teneo.hibernate.HbDataStore;
import org.eclipse.emf.teneo.hibernate.HbHelper;
import org.eclipse.emf.teneo.hibernate.resource.HibernateResource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Environment;
import org.osgi.framework.BundleContext;


/**
 * This is the central singleton for the Library editor plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class LibraryEditorPlugin extends EMFPlugin {
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final LibraryEditorPlugin INSTANCE = new LibraryEditorPlugin();
	
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Implementation plugin;

	/**
	 * Create the instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryEditorPlugin() {
		super
			(new ResourceLocator [] {
			});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}
	
	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}
	
	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Implementation extends EclipseUIPlugin {
		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Implementation() {
			super();
	
			// Remember the static instance.
			//
			plugin = this;
		}
		

		private boolean NEW_DB = false;

		public static final String HBNAME = "library";
		public static final String CASCADE_POLICY = "REFRESH,PERSIST,MERGE";
		public static final String DB_DIALECT = org.hibernate.dialect.MySQL5InnoDBDialect.class.getName();
		public static final String DB_PASS = "";
		public static final String DB = "library";
		public static final String JDBC_URL = "jdbc:mysql://localhost/"+DB;
		public static final String DB_USER = "root";
		public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		public static double VERSION = 2.1;
		//Change this to your changelog folder
		public static final String PATH = "/changelogs";
		//public static final String PATH = "E:\\Eclipse_Workspaces\\Example_Bach\\Library\\models\\Library_tedapt\\changelogs";

		@Override
		/**
		 * @generated NOT
		 */
		public void start(BundleContext context) throws Exception 
		{
			Flyway flyway = new Flyway();
			flyway.setBaselineOnMigrate(true);
			flyway.setDataSource(JDBC_URL,DB_USER,DB_PASS);
			flyway.setLocations("filesystem:"+PATH);
			
			if(!isNewDB()) {
				//flyway.migrate();
			} 

			final Properties props = new Properties();
			props.setProperty(Environment.DRIVER, JDBC_DRIVER);
			props.setProperty(Environment.USER, DB_USER);
			props.setProperty(Environment.URL, JDBC_URL);
			props.setProperty(Environment.PASS, DB_PASS);
			props.setProperty(Environment.DIALECT, DB_DIALECT);
			props.setProperty(Environment.SHOW_SQL, "true");
			//props.setProperty(PersistenceOptions.CASCADE_POLICY_ON_NON_CONTAINMENT, CASCADE_POLICY);
			props.setProperty(PersistenceOptions.CASCADE_POLICY_ON_CONTAINMENT, "ALL");
			props.setProperty(PersistenceOptions.CASCADE_POLICY_ON_NON_CONTAINMENT, 
			  "MERGE,PERSIST,REFRESH");
			
			
			final HbDataStore hbds = HbHelper.INSTANCE.createRegisterDataStore(HBNAME);
			
			hbds.setDataStoreProperties(props);
			hbds.setEPackages(new EPackage[] {LibraryPackage.eINSTANCE});
			
			try {
				hbds.initialize();
			} finally {
				//System.err.println(hbds.getMappingXML());
			}
		 
			if(NEW_DB) {
				flyway.setBaselineVersion(String.valueOf(VERSION));
				flyway.baseline();
			}
			
			
			//If there is no entry yet we add a new library
			String uriStr = "hibernate://?"+HibernateResource.DS_NAME_PARAM+"="+DB;
			final URI uri = URI.createURI(uriStr);
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = resourceSet.createResource(uri);
			try {
				resource.load(null);
				if(resource.getContents().size() == 0) {
					Library lib = LibraryFactory.eINSTANCE.createLibrary();
					lib.setName("My Library");
					resource.getContents().add(lib);
					resource.save(null);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			
			super.start(context);
		
		}
		
		

		/**
		 * @generated NOT
		 */
		private boolean isNewDB() {
			Connection con = null;
			try {
				Class.forName(JDBC_DRIVER);
				con = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				DatabaseMetaData meta = con.getMetaData();
				ResultSet result = meta.getTables(null, DB, "schema_version", null);
				NEW_DB = !result.next();
				return NEW_DB;
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
					try {
						if(con != null)
							con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			return true;
		}
	}

}
