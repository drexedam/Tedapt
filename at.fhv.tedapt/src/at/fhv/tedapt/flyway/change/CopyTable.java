package at.fhv.tedapt.flyway.change;

/**
 * Copy one reference table into another
 * @author Damian Drexel
 * @version 0.1
 */
public class CopyTable implements Change {

	private String _goal;
	private String _origin;
	
	
	/**
	 * 
	 * @param origin Source for data table
	 * @param goal The table the data has to be copied to
	 */
	public CopyTable(String origin, String goal) {
		_goal = goal.toLowerCase();
		_origin = origin.toLowerCase();
	}
	
	@Override
	public String getSQL() {
		StringBuilder sb = new StringBuilder("insert into ");
		sb.append(_goal);
		sb.append(" (");
		sb.append(_goal);
		sb.append("_e_id,elt,");
		sb.append(_goal);
		sb.append("_idx) select ");
		sb.append(_origin);
		sb.append("_e_id,elt,");
		sb.append(_origin);
		sb.append("_idx from ");
		sb.append(_origin);
		sb.append(";");
		
		return sb.toString();
	}

}
