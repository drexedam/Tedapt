/**
 */
package libraryExample.tests;

import junit.textui.TestRunner;

import libraryExample.LibraryFactory;
import libraryExample.SchoolLibrary;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>School Library</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SchoolLibraryTest extends LibraryTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SchoolLibraryTest.class);
	}

	/**
	 * Constructs a new School Library test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SchoolLibraryTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this School Library test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private SchoolLibrary getFixture() {
		return (SchoolLibrary)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(LibraryFactory.eINSTANCE.createSchoolLibrary());
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

} //SchoolLibraryTest
