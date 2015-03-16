package at.fhv.tedapt.flyway.change;

import at.fhv.tedapt.flyway.DatabaseHandler;
import at.fhv.tedapt.flyway.change.mysql.CreateTableMySQL;

public class ChangeHandler {

	public static CreateTable getCreateTable(String tableName) {
		switch (DatabaseHandler.getDialect()) {
		case MYSQL:
		default:
			return new CreateTableMySQL(tableName);
		}
		
	}
	
}
