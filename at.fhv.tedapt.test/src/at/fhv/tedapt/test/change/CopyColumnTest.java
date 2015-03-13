package at.fhv.tedapt.test.change;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.fhv.tedapt.flyway.change.CopyColumn;

public class CopyColumnTest {

	public static final String GET_SQL_RESULT = "update atable set column2=column1;";
	
	@Test
	public void testGetSQL() {
		assertEquals(GET_SQL_RESULT, new CopyColumn("aTable", "column1", "column2").getSQL());
	}

}
