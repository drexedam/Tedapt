package at.fhv.tedapt.test.change;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.fhv.tedapt.flyway.change.CopyTable;

public class CopyTableTest {

	public static final String GET_SQL_RESULT = "insert into goaltable (goaltable_e_id,elt,goaltable_idx) "
			+ "select orgtable_e_id,elt,orgtable_idx from orgtable;";
	
	@Test
	public void testGetSQL() {
		assertEquals(GET_SQL_RESULT, new CopyTable("orgTable", "goalTable").getSQL());
	}

}
