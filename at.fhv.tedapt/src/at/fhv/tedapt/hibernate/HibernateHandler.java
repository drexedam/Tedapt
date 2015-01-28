package at.fhv.tedapt.hibernate;

import org.eclipse.emf.ecore.EDataType;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import at.fhv.tedapt.Activator;
import at.fhv.tedapt.preferences.PreferenceConstants;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class HibernateHandler {
	private static SessionFactory _sf;
	
	private static String _user;
	private static String _password;
	private static String _dbAddr;
	private static String _dbName;
	
	
	private static QueryFactory _factory;
	
	
	private HibernateHandler(){};
	
	/**
	 * @deprecated Use provided functions (e.g. executeQuery) instead
	 * @return Returns a new session factory with currently set access data
	 */
	@Deprecated
	public static SessionFactory getFactory() {
		if(_sf == null) {
			update();
		}
		
		return _sf;
	}
	
	
	public static void update() {
		if(_sf  != null) {
			_sf.close();
		}
		_user = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_UNAME);
		_password = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PW);
		_dbName = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.DB_NAME);
		_dbAddr = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.DB_ADDRESS);
		setUpSessionFactory();
	}
	
	
	/**
	 * Initializes the session factory
	 */
	private static void setUpSessionFactory() {
		try {
			Configuration config = new Configuration();
			config.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
			config.setProperty(Environment.URL, "jdbc:mysql://"+_dbAddr+"/"+_dbName);
			config.setProperty(Environment.USER, _user);
			config.setProperty(Environment.PASS, _password);
			config.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");
			config.setProperty(Environment.SHOW_SQL, "true");
			config.setProperty(Environment.ORDER_UPDATES, "true");
			config.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "org.hibernate.context.internal.ThreadLocalSessionContext");

			ServiceRegistry servReg = new StandardServiceRegistryBuilder()
											.applySettings(config.getProperties()).build();
			_sf = config.buildSessionFactory(servReg);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param dataType Some EMF data type
	 * @return Returns the corresponding SQL data type
	 */
	public static String mapDataType(EDataType dataType) {
		String dType = dataType.getName();
		switch (dType) {
		case "EBigDecimal":
		case "EBigInteger":
			return "decimal(19,2)";
		case "EBoolean":
		case "EBooleanObject":
			return "bit(1)";
		case "EByte":
		case "EByteObject":
		case "EByteArray":
			return "tinyint(4)";
		case "EChar":
		case "ECharacterObject":
			return "char(1)";
		case "EDate":
			return "datetime";
		case "EFloat":
		case "EFloatObject":
			return "float";
		case "EInt":
		case "EIntegerObject":
			return "int(11)";
		case "ELong":
		case "ELongObject":
			return "bigint(20)";
		case "EShort":
		case "EShortObject":
			return "smallint(6)";
		case "EJavaObject":
		case "EString":
		default:
			return "varchar(255)";
		}
	}
	
	/**
	 * 
	 * @return Returns a query factory 
	 */
	public static QueryFactory getQueryFactory() {
		if(_factory == null) {
			_factory = new MySQLQueryFactory();
		}
		return _factory;
	}
	
	public static void executeQuery(String query) {
		System.out.println(query);
//		Session ses = _sf.getCurrentSession();
//		
//		ses.getTransaction().begin();
//		
//		
//		ses.createSQLQuery(query).executeUpdate();
//		
//		
//		ses.getTransaction().commit();
	}
}

