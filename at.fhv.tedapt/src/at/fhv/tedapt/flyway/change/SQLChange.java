package at.fhv.tedapt.flyway.change;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class SQLChange implements Change {

	private StringBuilder _query;
	
	public SQLChange() {
		_query = new StringBuilder();
	}
	
	public SQLChange(String... queries) {
		
		this();
		
		addQueries(queries);
	}
	
	public void addQueries(String ... queries) {
		for(String query : queries) {
			if(!query.endsWith(";")) {
				query += ";";
			}
			
			//Flyway requires multiple queries to be in separate lines
			query += System.lineSeparator();
			
			_query.append(query);
		}
	}
	
	@Override
	public String getSQL() {
		return _query.toString().toLowerCase();
	}

}
