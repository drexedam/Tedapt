package at.fhv.tedapt.flyway.change;

import at.fhv.tedapt.flyway.entity.Column;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 */
public class CopyColumn implements Change {

	private String _table;
	private String _origin;
	private String _goal;
	
	/**
	 * 
	 * @param table The table to operate on
	 * @param origin The column containing the data to be copied
	 * @param goal The column to copy the data to
	 */
	public CopyColumn(String table, String origin, String goal) {
		_table = table.toLowerCase();
		_goal = goal.toLowerCase();
		_origin= origin.toLowerCase();
	}
	
	public CopyColumn(String table, Column origin, Column goal) {
		this(table, origin.getName(), goal.getName());
	}

	@Override
	public String getSQL() {
		StringBuilder sb = new StringBuilder("update ");
		sb.append(_table);
		sb.append(" set ");
		sb.append(_goal);
		sb.append("=");
		sb.append(_origin);
		return sb.toString();
	}

}
