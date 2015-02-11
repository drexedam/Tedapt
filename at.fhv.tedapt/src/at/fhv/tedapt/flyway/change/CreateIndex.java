package at.fhv.tedapt.flyway.change;

import at.fhv.tedapt.flyway.entity.Column;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 */
public class CreateIndex implements Change {
	
	private String _iName, _tableName, _columnName;
	private boolean _isUnique;
	
	/**
	 * 
	 * @param indexName The name of the index
	 * @param tableName The name of the table containing the index column
	 * @param columnName The name of the index column
	 * @param isUnique If duplicated values are allowed
	 */
	public CreateIndex(String indexName, String tableName, String columnName, boolean isUnique) {
		_iName = indexName;
		_tableName = tableName;
		_columnName = columnName;
		_isUnique = isUnique;
	}
	
	/**
	 * 
	 * @param indexName The name of the index
	 * @param tableName The name of the table containing the index column
	 * @param columnName The name of the index column
	 */
	public CreateIndex(String indexName, String tableName, String columnName) {
		this(indexName, tableName, columnName, false);
	}
	
	/**
	 * 
	 * @param indexName The name of the index
	 * @param tableName The name of the table containing the index column
	 * @param column The index column
	 * @param isUnique If duplicated values are allowed
	 */
	public CreateIndex(String indexName, String tableName, Column column, boolean isUnique) {
		this(indexName, tableName, column.getName(), isUnique);
	}
	
	/**
	 * 
	 * @param indexName The name of the index
	 * @param tableName The name of the table containing the index column
	 * @param column The index column
	 */
	public CreateIndex(String indexName, String tableName, Column column) {
		this(indexName, tableName, column.getName(), false);
	}
	
	@Override
	public String getSQL() {
		StringBuilder sb = new StringBuilder("CREATE ");
		if(_isUnique) {
			sb.append("UNIQUE ");
		}
		sb.append("INDEX ");
		sb.append(_iName);
		sb.append(" ON ");
		sb.append(_tableName);
		sb.append(" (");
		sb.append(_columnName);
		sb.append(");");
		return sb.toString();
	}

}
