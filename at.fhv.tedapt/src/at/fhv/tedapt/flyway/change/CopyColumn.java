package at.fhv.tedapt.flyway.change;

public class CopyColumn implements Change {

	private String _table;
	private String _origin;
	private String _goal;
	
	public CopyColumn(String table, String origin, String goal) {
		_table = table.toLowerCase();
		_goal = goal.toLowerCase();
		_origin= origin.toLowerCase();
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
