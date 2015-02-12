package at.fhv.tedapt.flyway.change;

import at.fhv.tedapt.flyway.entity.Column;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class AddColumn implements Change {

	private String _tableName;
	private Column _column;
	
	/**
	 * 
	 * @param tableName The name of the table the column should be added
	 * @param column The column to be added
	 */
	public AddColumn(String tableName, Column column) {
		_tableName = tableName.toLowerCase();
		_column = column;
	}
	
	
	@Override
	public String getSQL() {
		StringBuilder sb = new StringBuilder("ALTER TABLE ");
		sb.append(_tableName);
		sb.append(" ADD ");
		sb.append(_column.getName());
		sb.append(" ");
		sb.append(_column.getType());
				
		if(_column.getDefault() != null) {
			sb.append(" DEFAULT ");
			sb.append(_column.getDefault());
		}
		
		if(_column.notNull()) {
			sb.append(" NOT NULL");
		}
		
		if(_column.doesAutoIncrement()) {
			sb.append(" AUTO INCREMENT");
		}
		
		sb.append(";");
		return sb.toString();
	}

}
