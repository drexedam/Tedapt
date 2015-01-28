package at.fhv.tedapt.hibernate;


/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public interface QueryFactory {
	
	public String concatQueries(String q1, String q2);
	
	/**
	 * 
	 * @param className The name of the created class
	 * @return A SQL query to create the corresponding table
	 */
	public String createClassQuery(String className);
	
	/**
	 * 
	 * @param superClass The name of the most abstract class in the class hierarchy
	 * @param attrName The name of the attribute to be created
	 * @param type The SQL data type of the attribute
	 * @param notNull If the attribute can be null
	 * @return A SQL query to create a new column for simple attributes
	 */
	public String createAttributeQuery(String superClass, String attrName, String type, boolean notNull, String defaultValue);
	
	/**
	 * 
	 * @param className The name of the class the attribute is assigned to
	 * @param superClass The name of the most abstract class in the class hierarchy
	 * @param attrName The name of the attribute to be created
	 * @param type The SQL data type of the attribute
	 * @return A SQL query to create a table for more complex attributes
	 */
	public String createAttributeTableQuery(String className, String superClass, String attrName, String type);

	public String createSimpleReferenceQuery(String superClass, String refClassName, String refSuperClass,
			String refName, boolean notNull);
	
	public String createReferenceTableQuery(String className,
			String superClass, String refClassName, String refSuperClass, String attrName);
	
	/**
	 * 
	 * @param tableName The name of the table containing the column
	 * @param columnName The name of the column to be dropped 
	 * @return A SQL query to drop a column
	 */
	public String deleteColumn(String tableName, String columnName);
	
	/**
	 * 
	 * @param tableName The table to be deleted
	 * @return A SQL query to delete a table
	 */
	public String deleteTable(String tableName);
	
	public String createContainerCol(String refSuperClass);
	
	public String addIDXQuery(String className, String superClassName, String refName);
}
