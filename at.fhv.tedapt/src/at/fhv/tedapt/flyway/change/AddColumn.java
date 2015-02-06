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
		_tableName = tableName;
		_column = column;
	}
	
	
	@Override
	public String getSQL() {
		StringBuilder sb = new StringBuilder("ALTER TABLE "+_tableName.toLowerCase()+" ADD "+_column.getName().toLowerCase()+" "+_column.getType());
		if(_column.getDefault() != null) {
			sb.append(" DEFAULT "+_column.getDefault());
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
