package at.fhv.tedapt.test.change;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddColumnTest.class, AddReferenceColumnTest.class,
		CreateIndexTest.class, CreateTableTest.class, DeleteColumnTest.class,
		DeleteTableTest.class })
public class ChangeTests {

}
