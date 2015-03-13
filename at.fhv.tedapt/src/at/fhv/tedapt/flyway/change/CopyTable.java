package at.fhv.tedapt.flyway.change;

public class CopyTable implements Change {

	private String _goal;
	private String _origin;
	
	public CopyTable(String origin, String goal) {
		_goal = goal;
		_origin = origin;
	}
	
	@Override
	public String getSQL() {
		StringBuilder sb = new StringBuilder("insert into ");
		sb.append(_goal);
		sb.append(" (");
		sb.append(_goal);
		sb.append("_e_id,elt,");
		sb.append(_goal);
		sb.append("_idx) select");
		sb.append(_origin);
		sb.append("_e_id,elt,");
		sb.append(_origin);
		sb.append("_idx from");
		sb.append(_origin);
		
		return sb.toString();
	}

}
