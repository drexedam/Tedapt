package at.fhv.tedapt.flyway.change;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class DeleteTable implements Change {

	private String _tableName;
	
	/**
	 * 
	 * @param tableName The table to be deleted
	 */
	public DeleteTable(String tableName) {
		_tableName = tableName.toLowerCase();
	}
	
	@Override
	public String getSQL() {
		StringBuilder sb = new StringBuilder("DROP TABLE ");
		sb.append(_tableName);
		sb.append(";");
		return sb.toString();
	}

}
