package at.fhv.tedapt.flyway.change;

import at.fhv.tedapt.flyway.entity.Column;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class DeleteColumn implements Change {

	private String _tableName;
	private String _columnName;
	
	/**
	 * 
	 * @param tableName The table containing the column to be deleted
	 * @param columnName The name of the column to be deleted
	 */
	public DeleteColumn(String tableName, String columnName) {
		_tableName = tableName.toLowerCase();
		_columnName = columnName.toLowerCase();
	}
	
	/**
	 * 
	 * @param tableName The table containing the column to be deleted
	 * @param columnName The column to be deleted
	 */
	public DeleteColumn(String tableName, Column column) {
		this(tableName, column.getName());
	}
	
	
	@Override
	public String getSQL() {
		StringBuilder sb = new StringBuilder("ALTER TABLE ");
		sb.append(_tableName);
		sb.append(" DROP ");
		sb.append(_columnName);
		sb.append(";");
		return sb.toString();
	}

}
