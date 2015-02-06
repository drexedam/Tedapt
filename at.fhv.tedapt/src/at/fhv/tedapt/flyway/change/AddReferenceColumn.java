package at.fhv.tedapt.flyway.change;

import at.fhv.tedapt.flyway.entity.Column;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class AddReferenceColumn implements Change {

	private Column _col;
	private String _table;
	private String _refTable;
	private String _refColumn;
	
	/**
	 * 
	 * @param column The column containing the reference
	 * @param table The table containing the reference
	 * @param refTable The table containing the referenced column
	 * @param refColumn The name of the referenced column
	 */
	public AddReferenceColumn(Column column, String table, String refTable, String refColumn) {
		_col = column;
		_table = table.toLowerCase();
		_refTable = refTable.toLowerCase();
		_refColumn = refColumn.toLowerCase();
	}

	
	@Override
	public String getSQL() {
		StringBuilder sb = new StringBuilder("ALTER TABLE ");
		sb.append(_table);
		sb.append(" ADD ");
		sb.append(_col.getName());
		sb.append(" ");
		sb.append(_col.getType());
		if(_col.notNull()) {
			sb.append(" NOT NULL");
		}
		sb.append(";");
		sb.append(System.lineSeparator());
		sb.append("ALTER TABLE ");
		sb.append(_table);
		sb.append(" ADD FOREIGN KEY(");
		sb.append(_col.getName());
		sb.append(") REFERENCES ");
		sb.append(_refTable);
		sb.append("(");
		sb.append(_refColumn);
		sb.append(");");
		return sb.toString();
	}

}
