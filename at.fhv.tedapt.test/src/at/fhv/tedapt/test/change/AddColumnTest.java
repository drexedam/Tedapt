package at.fhv.tedapt.test.change;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.fhv.tedapt.flyway.change.AddColumn;
import at.fhv.tedapt.flyway.entity.Column;

public class AddColumnTest {

	private static final String GET_SQL_RESULT = "ALTER TABLE tablename ADD coltoadd varchar(255) DEFAULT "
			+ "someDefaultValue NOT NULL AUTO INCREMENT;";
	
	@Test
	public void testGetSQL() {
		Column newCol = new Column("colToAdd", "varchar(255)", true, true);
		newCol.setDefault("someDefaultValue");
		AddColumn ac = new AddColumn("tableName", newCol);
		assertEquals(ac.getSQL(), GET_SQL_RESULT);
	}

}
