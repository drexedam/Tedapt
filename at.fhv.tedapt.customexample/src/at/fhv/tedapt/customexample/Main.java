package at.fhv.tedapt.customexample;

import java.util.Properties;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.teneo.PersistenceOptions;
import org.eclipse.emf.teneo.hibernate.HbDataStore;
import org.eclipse.emf.teneo.hibernate.HbHelper;
import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Environment;

import example.ExampleClass;
import example.ExampleFactory;
import example.ExamplePackage;



public class Main {
	
	private static final String DB_NAME = "demo";
	private static final String DB_HOST = "localhost";
	private static final String DB_USER = "root";
	private static final String DB_PW = "damml1991";

	public static void main(String[] args) {
		if(args.length == 0 || args[0].equals("-persist")) {
			persistModel();
		} else if(args[0].equals("-migrate")) {
			migrateChanges();
		}

	}

	
	private static void persistModel() {
		final Properties props = new Properties();
		props.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
		props.setProperty(Environment.USER, DB_USER);
		props.setProperty(Environment.URL, "jdbc:mysql://"+DB_HOST+"/"+DB_NAME);
		props.setProperty(Environment.PASS, DB_PW);
		props.setProperty(Environment.DIALECT, org.hibernate.dialect.MySQL5InnoDBDialect.class.getName());
		props.setProperty(PersistenceOptions.CASCADE_POLICY_ON_NON_CONTAINMENT, "REFRESH,PERSIST,MERGE");
		
		String hbName = "tLibrary";
		
		final HbDataStore hbds = HbHelper.INSTANCE.createRegisterDataStore(hbName);
		
		hbds.setDataStoreProperties(props);
		hbds.setEPackages(new EPackage[] {ExamplePackage.eINSTANCE});
		
		try {
			hbds.initialize();
		} finally {
			System.err.println(hbds.getMappingXML());
		}
		
		SessionFactory sessionFactory = hbds.getSessionFactory();
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		
		tx.begin();
		
		ExampleClass ex1 = ExampleFactory.eINSTANCE.createExampleClass();
		ex1.setMyInteger(0);
		
		session.save(ex1);
		
		
		ExampleClass ex2 = ExampleFactory.eINSTANCE.createExampleClass();
		ex2.setMyInteger(1);
		
		session.save(ex2);
		
		
		ExampleClass ex3 = ExampleFactory.eINSTANCE.createExampleClass();
		ex3.setMyInteger(2);
		
		session.save(ex3);
		
		
		ExampleClass ex4 = ExampleFactory.eINSTANCE.createExampleClass();
		ex4.setMyInteger(3);

		session.save(ex4);
		
		tx.commit();
		
		session.close();
	}
	
	private static void migrateChanges() {
		Flyway flyway = new Flyway();
		flyway.setDataSource("jdbc:mysql://"+DB_HOST+"/"+DB_NAME,
				DB_USER, 
				DB_PW);
		flyway.setBaselineOnMigrate(true);
		flyway.setLocations("classpath:at.fhv.tedapt.customexample.flyway");
		flyway.migrate();
	}
}
