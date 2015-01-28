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

	/**
	 * 
	 * @param superClass The most abstract class of the container class
	 * @param refClassName The class the reference points to
	 * @param refSuperClass The most abstract class of the class the reference points to
	 * @param refName The name of the reference
	 * @param notNull If lower and upper bound are one
	 * @return A query to create a column for a reference
	 */
	//TODO How to prevent errors if there is already data in the table and the column may not be null
	public String createSimpleReferenceQuery(String superClass, String refClassName, String refSuperClass,
			String refName, boolean notNull);
	
	/**
	 * 
	 * @param className Container class
	 * @param superClass The most abstract class of the container class
	 * @param refClassName The referenced class
	 * @param refSuperClass The most abstract class of the referenced class
	 * @param refName The references name
	 * @return A query to create a table for references
	 */
	public String createReferenceTableQuery(String className,
			String superClass, String refClassName, String refSuperClass, String refName);
	
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
	
	/**
	 * 
	 * @param refSuperClass The most abstract class of the referenced class
	 * @return A SQL query to create required columns for containment refferences
	 */
	public String createContainerCol(String refSuperClass);
	
	/**
	 * 
	 * @param className Class name
	 * @param superClassName The most abstract class the previous class inherits from
	 * @param refName The name of the reference
	 * @return A SQL query to add the idx column for a reference
	 */
	public String addIDXQuery(String className, String superClassName, String refName);
}
