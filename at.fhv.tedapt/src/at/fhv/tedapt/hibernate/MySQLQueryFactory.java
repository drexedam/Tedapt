package at.fhv.tedapt.hibernate;


/**
 * MySQL implementation of the QueryFactory interface
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class MySQLQueryFactory implements QueryFactory {


	@Override
	public String createClassQuery(String className) {
		return "CREATE TABLE " +className.toLowerCase()+" "
				+ "(e_id bigint(20) AUTO_INCREMENT PRIMARY KEY NOT NULL,"
				+ "dtype varchar(255) NOT NULL,"
				+ "e_version int(11) NOT NULL)";
	}

	@Override
	public String createAttributeQuery(String superClass, String attrName,
			String type, boolean notNull) {
		superClass = superClass.toLowerCase();
		attrName = attrName.toLowerCase();
		return "ALTER TABLE "+superClass+" ADD "+attrName+" "+type+(notNull?" NOT NULL":"");
	}

	@Override
	public String createAttributeTableQuery(String className,
			String superClass, String attrName, String type) {
		className = className.toLowerCase();
		superClass = superClass.toLowerCase();
		attrName = attrName.toLowerCase();
		String tableName = className+"_"+attrName;
		return "CREATE TABLE "+tableName
				+" ("+tableName+"_e_id bigint(20),"
					+"elt "+type+","
					+tableName+"_idx int(11)"
					+",FOREIGN KEY ("+tableName+"_e_id) REFERENCES "+superClass+"(e_id)"
					+",PRIMARY KEY ("+tableName+"_e_id,"+tableName+"_idx))";
	}

	

	

}
