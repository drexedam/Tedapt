package at.fhv.tedapt.test.change;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.fhv.tedapt.flyway.change.DeleteColumn;

public class DeleteColumnTest {
	
	private static final String GET_SQL_RESULT = "ALTER TABLE tablename DROP columnname;";
	
	@Test
	public void testGetSQL() {
		DeleteColumn dc = new DeleteColumn("tableName", "columnName");
		assertEquals(dc.getSQL(), GET_SQL_RESULT);
	}

}
