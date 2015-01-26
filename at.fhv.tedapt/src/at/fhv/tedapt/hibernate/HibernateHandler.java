package at.fhv.tedapt.hibernate;

import org.eclipse.emf.ecore.EDataType;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class HibernateHandler {
	private static SessionFactory _sf;
	
	//DB-Access data used by each operation
	public static String USER = "root";
	public static String PW = "";
	public static String DATABASE = "localhost/test";
	
	private static QueryFactory _factory;
	
	
	private HibernateHandler(){};
	
	/**
	 * 
	 * @return Returns a new session factory with currently set access data
	 */
	public static SessionFactory getFactory() {
		if(_sf == null) {
			init();
		}
		
		return _sf;
	}
	
	/**
	 * 
	 * @param user User for DB access
	 * @param pw Password for DB access
	 * @param database DB adrress and name
	 * @return Returns a new session factory if none exists or access data has changed
	 */
	public static SessionFactory getFactory(String user, String pw, String database) {
		if(changed(user, pw, database) || _sf == null) {
			init();
		}
		
		
		return _sf;
	}
	
	/**
	 * 
	 * @param user User for DB access
	 * @param pw Password for DB access
	 * @param database DB adrress and name
	 * @return Returns true if any of the parameters have changed else otherwise
	 */
	private static boolean changed(String user, String pw, String database) {
		boolean temp = false;
		
		if(!USER.equals(user)) {
			USER = user;
			temp = true;
		}
		
		if(!PW.equals(pw)) {
			PW = pw;
			temp = true;
		}
		
		if(!DATABASE.equals(database)) {
			DATABASE = database;
			temp = true;
		}
		
		return temp;
	}
	
	/**
	 * Initializes the session factory
	 */
	private static void init() {
		try {
			Configuration config = new Configuration();
			config.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
			config.setProperty(Environment.URL, "jdbc:mysql://"+DATABASE);
			config.setProperty(Environment.USER, USER);
			config.setProperty(Environment.PASS, PW);
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
	
}

