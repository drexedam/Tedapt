/**
 */
package libraryExample.tests;

import junit.textui.TestRunner;

import libraryExample.LibraryFactory;
import libraryExample.SchoolBook;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>School Book</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SchoolBookTest extends BookTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SchoolBookTest.class);
	}

	/**
	 * Constructs a new School Book test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SchoolBookTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this School Book test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private SchoolBook getFixture() {
		return (SchoolBook)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(LibraryFactory.eINSTANCE.createSchoolBook());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //SchoolBookTest
