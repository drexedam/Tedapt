package at.fhv.tedapt.flyway.entity;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class ForeignKey {
	
	private String _column, _refTable, _refColumn;

	/**
	 * 
	 * @param column The foreign key columns name
	 * @param refTable The name of the table containing the referenced column
	 * @param refColumn The referenced columns name
	 */
	public ForeignKey(String column, String refTable, String refColumn) {
		_column = column;
		_refTable = refTable;
		_refColumn = refColumn;
	}
	
	/**
	 * 
	 * @return The foreign key columns name
	 */
	public String getColumn() {
		return _column;
	}
	
	/**
	 * 
	 * @returnThe referenced columns name
	 */
	public String getRefColumn() {
		return _refColumn;
	}
	
	/**
	 * 
	 * @return The name of the table containing the referenced column
	 */
	public String getRefTable() {
		return _refTable;
	}
	
	
	
}
