package at.fhv.tedapt.test.change;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
		CreateIndexTest.class, CreateTableTest.class,
		CopyColumnTest.class, CopyTableTest.class
		})
public class ChangeTests {

}
