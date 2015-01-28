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
			String type, boolean notNull, String defaultValue) {
		superClass = superClass.toLowerCase();
		attrName = attrName.toLowerCase();
		return "ALTER TABLE "+superClass+" ADD "
				+attrName+" "
				+type
				+((defaultValue != null) ? " DEFAULT "+defaultValue:"")
				+(notNull?" NOT NULL":"");
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

	@Override
	public String deleteColumn(String tableName, String columnName) {
		return "ALTER TABLE "+tableName+" DROP "+columnName;
	}

	@Override
	public String deleteTable(String tableName) {
		return "DROP TABLE "+tableName;
	}

	@Override
	public String createSimpleReferenceQuery(String superClass, String refClassName, String refSuperClass,
			String refName, boolean notNull) {
		superClass = superClass.toLowerCase();
		refClassName = refClassName.toLowerCase();
		refSuperClass = refSuperClass.toLowerCase();
		refName = refName.toLowerCase();
		return "ALTER TABLE " + superClass + " ADD " + refClassName+"_"+refName+"_e_id bigint(20)"+(notNull?" NOT NULL;":";")
				+"ALTER TABLE " + superClass +" ADD FOREIGN KEY (" + refClassName+"_"+refName+"_e_id)"
				+" REFERENCES "+refSuperClass+"(e_id)";
	}

	@Override
	public String createReferenceTableQuery(String className,
			String superClass, String refClassName, String refSuperClass, String attrName) {
		
		className = className.toLowerCase();
		superClass = superClass.toLowerCase();
		refClassName = refClassName.toLowerCase();
		refSuperClass = refSuperClass.toLowerCase();
		attrName = attrName.toLowerCase();
		String tableName = className+"_"+attrName;
		
		return "CREATE TABLE "+tableName
				+" ("+className+"_e_id bigint(20) NOT NULL,"
					+refClassName+"_e_id bigint(20) NOT NULL,"
					+tableName+"_idx int(11) NOT NULL"
					+",FOREIGN KEY ("+className+"_e_id) REFERENCES "+superClass+"(e_id)"
					+",FOREIGN KEY ("+refClassName+"_e_id) REFERENCES "+refSuperClass+"(e_id)"
					+",PRIMARY KEY ("+className+"_e_id,"+tableName+"_idx))";
	}

	@Override
	public String concatQueries(String q1, String q2) {
		return q1+";"+q2;
	}

	@Override
	public String createContainerCol(String refSuperClass) {
		refSuperClass = refSuperClass.toLowerCase();		
		return "ALTER TABLE "+refSuperClass
				+ " ADD econtainer_class varchar(255),"
				+ " ADD e_container varchar(255),"
				+ " ADD e_container_feature_name varchar(255)";
	}

	@Override
	public String addIDXQuery(String className, String superClassName, String refName) {
		superClassName = superClassName.toLowerCase();
		className = className.toLowerCase();
		refName = refName.toLowerCase();
		return "ALTER TABLE "+superClassName
				+" ADD "+className+"_"+refName+"_idx int(11)";
	}


	

}
