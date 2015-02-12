package at.fhv.tedapt.flyway;

import org.eclipse.emf.ecore.EDataType;

import at.fhv.tedapt.Activator;
import at.fhv.tedapt.preferences.PreferenceConstants;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class DatabaseHandler {

	private DatabaseHandler(){};
	
	/**
	 * 
	 * @return The JDBC URL for the selected DBMS
	 */
	public static String getJDBCURL() {
		String dbName = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.DB_NAME);
		String dbAddr = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.DB_ADDRESS);
		switch (Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.DB_MS)) {
			case PreferenceConstants.DBMS_HSQL:
				//TODO http://hsqldb.org/doc/src/org/hsqldb/jdbc/JDBCConnection.html
				return"jdbc:hsqldb:"+dbAddr+"/"+dbName;
			case PreferenceConstants.DBMS_PSQL:
				return "jdbc:postgresql://"+dbAddr+"/"+dbName;
			case PreferenceConstants.DBMS_SQLSERV:
				//TODO https://msdn.microsoft.com/de-de/library/ms378428%28v=sql.110%29.aspx
				return "jdbc:sqlserver://"+dbAddr+";databaseName="+dbName;
			case PreferenceConstants.DBMS_MYSQL:
			default:
				System.out.println("jdbc:mysql://"+dbAddr+"/"+dbName);
				return "jdbc:mysql://"+dbAddr+"/"+dbName;
				
		}
	}
	
	/**
	 * 
	 * @return The JDBC Driver class for the selected DBMS
	 */
	public static String getJDBCDriver() {
		switch (Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.DB_MS)) {
			case PreferenceConstants.DBMS_HSQL:
				return "org.hsqldb.jdbc.JDBCDriver";
			case PreferenceConstants.DBMS_PSQL:
				return "org.postgresql.Driver";
			case PreferenceConstants.DBMS_SQLSERV:
				//TODO Check if ok
				return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			case PreferenceConstants.DBMS_MYSQL:
			default:
				return "com.mysql.jdbc.Driver";
			
		}
		
	}
	
	/**
	 * 
	 * @param dataType Some EMF data type
	 * @return The corresponding SQL data type
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
}
