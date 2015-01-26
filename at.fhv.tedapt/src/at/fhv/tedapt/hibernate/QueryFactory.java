package at.fhv.tedapt.hibernate;


/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public interface QueryFactory {
	
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
	public String createAttributeQuery(String superClass, String attrName, String type, boolean notNull);
	
	/**
	 * 
	 * @param className The name of the class the attribute is assigned to
	 * @param superClass The name of the most abstract class in the class hierarchy
	 * @param attrName The name of the attribute to be created
	 * @param type The SQL data type of the attribute
	 * @return A SQL query to create a table for more complex attributes
	 */
	public String createAttributeTableQuery(String className, String superClass, String attrName, String type);
}
