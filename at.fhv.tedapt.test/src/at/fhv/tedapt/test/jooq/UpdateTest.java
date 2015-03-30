package at.fhv.tedapt.test.jooq;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

import org.jooq.CreateTableAsStep;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.MappedTable;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.NoConnectionProvider;
import org.jooq.impl.SQLDataType;
import org.junit.Test;

public class UpdateTest {

	@Test
	public void test() {
		
		
		
		Settings settings = new Settings();
		settings.withExecuteLogging(false);
		settings.withRenderMapping(new RenderMapping().withSchemata(new MappedSchema().withTables(new MappedTable().withInput("testTable"))));
		
	
		
//		
		
		
		DSLContext context = DSL.using(SQLDialect.MYSQL, settings);
		
		Table<Record> t = DSL.table("testTable");
		
		t.field("myFirstField");
		t.field("mySecondField");
		System.out.println("SQL");
		
		System.out.println(context.updateQuery(t).getSQL());
		
		//context.updateQuery(t).addValue(DSL.field("myFistField"), DSL.field("mySecondField"));
		
		System.out.println(context.update(t).set(t.field("myFirstField"), DSL.select(DSL.field("mySecondField")).from("testTable").asField()).getSQL());
//		System.out.println(",d,,da,s");
//		System.out.println(context.map(t).toString());
		
//		CreateTableAsStep tbl = context.createTable("myTable2");
//		tbl.column("myColumn", SQLDataType.BIGINT);
//		tbl.as(DSL.select(t.field("myFirstField")));
//		System.out.println(tbl.toString());
		
		//fail("Not yet implemented");
		
		Connection con = new NoConnectionProvider().acquire();
//		DatabaseMetaData metaData = com.mysql.jdbc.Driver;...
	}

}
