package at.fhv.tedapt.test.change;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.fhv.tedapt.exception.PrimaryKeyCanBeNullException;
import at.fhv.tedapt.flyway.change.CreateTable;
import at.fhv.tedapt.flyway.entity.Column;

public class CreateTableTest {
	
	private static final String GET_SQL_RESULT = "CREATE TABLE testtable (column1 varchar(255),pkcolumn int(11) AUTO_INCREMENT NOT NULL,column2 varChar(255) NOT NULL,fkcolumn bigint(11) NOT NULL,FOREIGN KEY (fkcolumn) REFERENCES reftable(refcolumn),PRIMARY KEY (pkcolumn));";

	@Test(expected=PrimaryKeyCanBeNullException.class)
	public void testAddPrimaryKey() throws PrimaryKeyCanBeNullException {
		CreateTable ct = new CreateTable("hasToFail");
		ct.addPrimaryKey(new Column("wrongPK", "doesNotMatter"));
	}
	
	@Test
	public void testGetSQL() {
		CreateTable ct = new CreateTable("testTable");
		Column c1 = new Column("column1", "varchar(255)");
		ct.addColumn(c1);
		Column c2 = new Column("pkColumn", "int(11)",true, true);
		try {
			ct.addPrimaryKey(c2);
		} catch (PrimaryKeyCanBeNullException e) {
			e.printStackTrace();
		}
		Column c3 = new Column("column2", "varChar(255)", true);
		ct.addColumn(c3);
		
		Column c4 = new Column("fkColumn", "bigint(11)", true);
		ct.addForeignKey(c4, "refTable", "refColumn");
		
		assertEquals(ct.getSQL(), GET_SQL_RESULT);
		
	}

}
