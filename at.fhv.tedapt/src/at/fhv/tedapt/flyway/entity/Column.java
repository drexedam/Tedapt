package at.fhv.tedapt.flyway.entity;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class Column {
	private String _name;
	private String _type;
	private boolean _autoIncrement;
	private boolean _notNull;
	private String _defaultValue;
	
	/**
	 * 
	 * @param name The name of the column
	 * @param type The columns type
	 */
	public Column(String name, String type) {
		_name = name;
		_type = type;
		_autoIncrement = false;
		_notNull = false;
	}
	
	/**
	 * 
	 * @param name The name of the column
	 * @param type The columns type
	 * @param doAutoIncrement If the column auto increments
	 * @param notNull If the column may not contain null
	 */
	public Column(String name, String type, boolean doAutoIncrement, boolean notNull) {
		_name = name;
		_type = type;
		_autoIncrement = doAutoIncrement;
		_notNull = notNull;
	}
	
	/**
	 * 
	 * @param name The name of the column
	 * @param type The columns type
	 * @param notNull If the column may not contain null
	 */
	public Column(String name, String type, boolean notNull) {
		_name = name;
		_type = type;
		_autoIncrement = false;
		_notNull = notNull;
	}
	
	/**
	 * Set the default value of the column
	 * @param value The default value represented as string
	 */
	public void setDefault(String value) {
		_defaultValue = value;
	}
	
	/**
	 * 
	 * @return The set default value or null if non is set
	 */
	public String getDefault() {
		return _defaultValue;
	}
	
	/**
	 * 
	 * @return The columns name
	 */
	public String getName() {
		return _name.toLowerCase();
	}
	
	/**
	 * 
	 * @return The columns type
	 */
	public String getType() {
		return _type;
	}
	
	/**
	 * 
	 * @return True if the column does auto increment else false
	 */
	public boolean doesAutoIncrement() {
		return _autoIncrement;
	}
	
	/**
	 * 
	 * @return True if the column may not be null false othwerwise
	 */
	public boolean notNull() {
		return _notNull;
	}
	
	
}
