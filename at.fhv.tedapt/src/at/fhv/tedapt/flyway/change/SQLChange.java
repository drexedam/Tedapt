package at.fhv.tedapt.flyway.change;


public class SQLChange implements Change {

	private StringBuilder _query;
	
	
	public SQLChange(String... queries) {
		_query = new StringBuilder();

		for(String query : queries) {
			if(!query.endsWith(";")) {
				query += ";";
			}
			query += System.lineSeparator();
			
			_query.append(query);
		}
	}
	
	@Override
	public String getSQL() {
		return _query.toString().toLowerCase();
	}

}
