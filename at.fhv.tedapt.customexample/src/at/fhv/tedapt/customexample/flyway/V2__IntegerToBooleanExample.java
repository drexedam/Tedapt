package at.fhv.tedapt.customexample.flyway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

public class V2__IntegerToBooleanExample implements JdbcMigration{

	@Override
	public void migrate(Connection connection) throws Exception {
		PreparedStatement getIntValues = connection.prepareStatement("SELECT e_id, myinteger FROM exampleclass");
		ResultSet intValues = getIntValues.executeQuery();
		
		PreparedStatement createBooleanColumn1 = connection.prepareStatement("ALTER TABLE exampleclass ADD boolean1 BOOLEAN");
		PreparedStatement createBooleanColumn2 = connection.prepareStatement("ALTER TABLE exampleclass ADD boolean2 BOOLEAN");
		createBooleanColumn1.execute();
		createBooleanColumn2.execute();
		
		PreparedStatement insertBoolean;
		while(intValues.next()) {
			switch (intValues.getInt("myinteger")) {
			case 0:
				insertBoolean = connection.prepareStatement("UPDATE exampleclass SET boolean1 = 0, boolean2 = 0 WHERE e_ID="+intValues.getInt("e_id"));
				break;
			case 1:
				insertBoolean = connection.prepareStatement("UPDATE exampleclass SET boolean1 = 0, boolean2 = 1 WHERE e_ID="+intValues.getInt("e_id"));
				break;

			case 2:
				insertBoolean = connection.prepareStatement("UPDATE exampleclass SET boolean1 = 1, boolean2 = 0 WHERE e_ID="+intValues.getInt("e_id"));
				break;
			
			case 3:
			default:
				insertBoolean = connection.prepareStatement("UPDATE exampleclass SET boolean1 = 1, boolean2 = 1 WHERE e_ID="+intValues.getInt("e_id"));
				break;
			}
			
			insertBoolean.execute();
			
			connection.prepareStatement("ALTER TABLE exampleclass DROP COLUMn myinteger").execute();
			
		}
		
		
	}

}
