package at.fhv.tedapt.test.change;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.fhv.tedapt.flyway.change.AddReferenceColumn;
import at.fhv.tedapt.flyway.entity.Column;

public class AddReferenceColumnTest {
	public static final String GET_SQL_RESULT="ALTER TABLE tablename ADD refcol bigint(11);"+
					System.lineSeparator()+
					"ALTER TABLE tablename ADD FOREIGN KEY(refcol) REFERENCES refdtable(refdcolumn);";
	
	@Test
	public void testGetSQL() {
		AddReferenceColumn arc = new AddReferenceColumn(new Column("refCol", "bigint(11)"),"tableName","refdTable", "refdColumn");
		assertEquals(arc.getSQL(), GET_SQL_RESULT);
	}

}
