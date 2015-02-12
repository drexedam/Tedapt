package at.fhv.tedapt.test.change;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.fhv.tedapt.flyway.change.DeleteTable;

public class DeleteTableTest {

	public static final String GET_SQL_RESULT = "DROP TABLE tablename;";
	
	@Test
	public void testGetSQL() {
		DeleteTable dt = new DeleteTable("tableName");
		assertEquals(dt.getSQL(), GET_SQL_RESULT);
	}

}
