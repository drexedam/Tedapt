package at.fhv.tedapt.test.change;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.fhv.tedapt.flyway.change.CreateIndex;
import at.fhv.tedapt.flyway.entity.Column;

public class CreateIndexTest {

	public static final String GET_SQL_RESULT = "CREATE UNIQUE INDEX icolindex ON indexedtable (indexcolumn);";
	
	@Test
	public void testGetSQL() {
		Column iCol = new Column("indexColumn", "varChar(255)");
		CreateIndex ci = new CreateIndex("iColIndex", "indexedTable", iCol, true);
		assertEquals(ci.getSQL(), GET_SQL_RESULT);
	}

}
