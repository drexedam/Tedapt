/**
 */
package libraryExample;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see libraryExample.LibraryFactory
 * @model kind="package"
 * @generated
 */
public interface LibraryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "libraryExample";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.fhv.at/library/V2";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "libraryExample";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LibraryPackage eINSTANCE = libraryExample.impl.LibraryPackageImpl.init();

	/**
	 * The meta object id for the '{@link libraryExample.impl.LibraryImpl <em>Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see libraryExample.impl.LibraryImpl
	 * @see libraryExample.impl.LibraryPackageImpl#getLibrary()
	 * @generated
	 */
	int LIBRARY = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Books</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__BOOKS = 1;

	/**
	 * The feature id for the '<em><b>Writers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__WRITERS = 2;

	/**
	 * The number of structural features of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link libraryExample.impl.BookImpl <em>Book</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see libraryExample.impl.BookImpl
	 * @see libraryExample.impl.LibraryPackageImpl#getBook()
	 * @generated
	 */
	int BOOK = 1;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__TITLE = 0;

	/**
	 * The feature id for the '<em><b>Pages</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__PAGES = 1;

	/**
	 * The feature id for the '<em><b>Writer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__WRITER = 2;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__CATEGORY = 3;

	/**
	 * The number of structural features of the '<em>Book</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link libraryExample.impl.WriterImpl <em>Writer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see libraryExample.impl.WriterImpl
	 * @see libraryExample.impl.LibraryPackageImpl#getWriter()
	 * @generated
	 */
	int WRITER = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRITER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Books</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRITER__BOOKS = 1;

	/**
	 * The feature id for the '<em><b>Lastname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRITER__LASTNAME = 2;

	/**
	 * The number of structural features of the '<em>Writer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRITER_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link libraryExample.impl.SchoolLibraryImpl <em>School Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see libraryExample.impl.SchoolLibraryImpl
	 * @see libraryExample.impl.LibraryPackageImpl#getSchoolLibrary()
	 * @generated
	 */
	int SCHOOL_LIBRARY = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHOOL_LIBRARY__NAME = LIBRARY__NAME;

	/**
	 * The feature id for the '<em><b>Books</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHOOL_LIBRARY__BOOKS = LIBRARY__BOOKS;

	/**
	 * The feature id for the '<em><b>Writers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHOOL_LIBRARY__WRITERS = LIBRARY__WRITERS;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHOOL_LIBRARY__LOCATION = LIBRARY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>School Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHOOL_LIBRARY_FEATURE_COUNT = LIBRARY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link libraryExample.impl.AssetImpl <em>Asset</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see libraryExample.impl.AssetImpl
	 * @see libraryExample.impl.LibraryPackageImpl#getAsset()
	 * @generated
	 */
	int ASSET = 4;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Asset</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link libraryExample.impl.SchoolBookImpl <em>School Book</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see libraryExample.impl.SchoolBookImpl
	 * @see libraryExample.impl.LibraryPackageImpl#getSchoolBook()
	 * @generated
	 */
	int SCHOOL_BOOK = 5;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHOOL_BOOK__TITLE = BOOK__TITLE;

	/**
	 * The feature id for the '<em><b>Pages</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHOOL_BOOK__PAGES = BOOK__PAGES;

	/**
	 * The feature id for the '<em><b>Writer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHOOL_BOOK__WRITER = BOOK__WRITER;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHOOL_BOOK__CATEGORY = BOOK__CATEGORY;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHOOL_BOOK__VALUE = BOOK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>School Book</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHOOL_BOOK_FEATURE_COUNT = BOOK_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link libraryExample.BookCategory <em>Book Category</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see libraryExample.BookCategory
	 * @see libraryExample.impl.LibraryPackageImpl#getBookCategory()
	 * @generated
	 */
	int BOOK_CATEGORY = 6;


	/**
	 * Returns the meta object for class '{@link libraryExample.Library <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library</em>'.
	 * @see libraryExample.Library
	 * @generated
	 */
	EClass getLibrary();

	/**
	 * Returns the meta object for the attribute '{@link libraryExample.Library#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see libraryExample.Library#getName()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link libraryExample.Library#getBooks <em>Books</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Books</em>'.
	 * @see libraryExample.Library#getBooks()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Books();

	/**
	 * Returns the meta object for the containment reference list '{@link libraryExample.Library#getWriters <em>Writers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Writers</em>'.
	 * @see libraryExample.Library#getWriters()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Writers();

	/**
	 * Returns the meta object for class '{@link libraryExample.Book <em>Book</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Book</em>'.
	 * @see libraryExample.Book
	 * @generated
	 */
	EClass getBook();

	/**
	 * Returns the meta object for the attribute '{@link libraryExample.Book#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see libraryExample.Book#getTitle()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Title();

	/**
	 * Returns the meta object for the attribute '{@link libraryExample.Book#getPages <em>Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pages</em>'.
	 * @see libraryExample.Book#getPages()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Pages();

	/**
	 * Returns the meta object for the reference '{@link libraryExample.Book#getWriter <em>Writer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Writer</em>'.
	 * @see libraryExample.Book#getWriter()
	 * @see #getBook()
	 * @generated
	 */
	EReference getBook_Writer();

	/**
	 * Returns the meta object for the attribute '{@link libraryExample.Book#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category</em>'.
	 * @see libraryExample.Book#getCategory()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Category();

	/**
	 * Returns the meta object for class '{@link libraryExample.Writer <em>Writer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Writer</em>'.
	 * @see libraryExample.Writer
	 * @generated
	 */
	EClass getWriter();

	/**
	 * Returns the meta object for the attribute '{@link libraryExample.Writer#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see libraryExample.Writer#getName()
	 * @see #getWriter()
	 * @generated
	 */
	EAttribute getWriter_Name();

	/**
	 * Returns the meta object for the reference list '{@link libraryExample.Writer#getBooks <em>Books</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Books</em>'.
	 * @see libraryExample.Writer#getBooks()
	 * @see #getWriter()
	 * @generated
	 */
	EReference getWriter_Books();

	/**
	 * Returns the meta object for the attribute '{@link libraryExample.Writer#getLastname <em>Lastname</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lastname</em>'.
	 * @see libraryExample.Writer#getLastname()
	 * @see #getWriter()
	 * @generated
	 */
	EAttribute getWriter_Lastname();

	/**
	 * Returns the meta object for class '{@link libraryExample.SchoolLibrary <em>School Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>School Library</em>'.
	 * @see libraryExample.SchoolLibrary
	 * @generated
	 */
	EClass getSchoolLibrary();

	/**
	 * Returns the meta object for the attribute '{@link libraryExample.SchoolLibrary#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see libraryExample.SchoolLibrary#getLocation()
	 * @see #getSchoolLibrary()
	 * @generated
	 */
	EAttribute getSchoolLibrary_Location();

	/**
	 * Returns the meta object for class '{@link libraryExample.Asset <em>Asset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Asset</em>'.
	 * @see libraryExample.Asset
	 * @generated
	 */
	EClass getAsset();

	/**
	 * Returns the meta object for the attribute '{@link libraryExample.Asset#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see libraryExample.Asset#getValue()
	 * @see #getAsset()
	 * @generated
	 */
	EAttribute getAsset_Value();

	/**
	 * Returns the meta object for class '{@link libraryExample.SchoolBook <em>School Book</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>School Book</em>'.
	 * @see libraryExample.SchoolBook
	 * @generated
	 */
	EClass getSchoolBook();

	/**
	 * Returns the meta object for enum '{@link libraryExample.BookCategory <em>Book Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Book Category</em>'.
	 * @see libraryExample.BookCategory
	 * @generated
	 */
	EEnum getBookCategory();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LibraryFactory getLibraryFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link libraryExample.impl.LibraryImpl <em>Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see libraryExample.impl.LibraryImpl
		 * @see libraryExample.impl.LibraryPackageImpl#getLibrary()
		 * @generated
		 */
		EClass LIBRARY = eINSTANCE.getLibrary();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__NAME = eINSTANCE.getLibrary_Name();

		/**
		 * The meta object literal for the '<em><b>Books</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__BOOKS = eINSTANCE.getLibrary_Books();

		/**
		 * The meta object literal for the '<em><b>Writers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__WRITERS = eINSTANCE.getLibrary_Writers();

		/**
		 * The meta object literal for the '{@link libraryExample.impl.BookImpl <em>Book</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see libraryExample.impl.BookImpl
		 * @see libraryExample.impl.LibraryPackageImpl#getBook()
		 * @generated
		 */
		EClass BOOK = eINSTANCE.getBook();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__TITLE = eINSTANCE.getBook_Title();

		/**
		 * The meta object literal for the '<em><b>Pages</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__PAGES = eINSTANCE.getBook_Pages();

		/**
		 * The meta object literal for the '<em><b>Writer</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOK__WRITER = eINSTANCE.getBook_Writer();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__CATEGORY = eINSTANCE.getBook_Category();

		/**
		 * The meta object literal for the '{@link libraryExample.impl.WriterImpl <em>Writer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see libraryExample.impl.WriterImpl
		 * @see libraryExample.impl.LibraryPackageImpl#getWriter()
		 * @generated
		 */
		EClass WRITER = eINSTANCE.getWriter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WRITER__NAME = eINSTANCE.getWriter_Name();

		/**
		 * The meta object literal for the '<em><b>Books</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WRITER__BOOKS = eINSTANCE.getWriter_Books();

		/**
		 * The meta object literal for the '<em><b>Lastname</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WRITER__LASTNAME = eINSTANCE.getWriter_Lastname();

		/**
		 * The meta object literal for the '{@link libraryExample.impl.SchoolLibraryImpl <em>School Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see libraryExample.impl.SchoolLibraryImpl
		 * @see libraryExample.impl.LibraryPackageImpl#getSchoolLibrary()
		 * @generated
		 */
		EClass SCHOOL_LIBRARY = eINSTANCE.getSchoolLibrary();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHOOL_LIBRARY__LOCATION = eINSTANCE.getSchoolLibrary_Location();

		/**
		 * The meta object literal for the '{@link libraryExample.impl.AssetImpl <em>Asset</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see libraryExample.impl.AssetImpl
		 * @see libraryExample.impl.LibraryPackageImpl#getAsset()
		 * @generated
		 */
		EClass ASSET = eINSTANCE.getAsset();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSET__VALUE = eINSTANCE.getAsset_Value();

		/**
		 * The meta object literal for the '{@link libraryExample.impl.SchoolBookImpl <em>School Book</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see libraryExample.impl.SchoolBookImpl
		 * @see libraryExample.impl.LibraryPackageImpl#getSchoolBook()
		 * @generated
		 */
		EClass SCHOOL_BOOK = eINSTANCE.getSchoolBook();

		/**
		 * The meta object literal for the '{@link libraryExample.BookCategory <em>Book Category</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see libraryExample.BookCategory
		 * @see libraryExample.impl.LibraryPackageImpl#getBookCategory()
		 * @generated
		 */
		EEnum BOOK_CATEGORY = eINSTANCE.getBookCategory();

	}

} //LibraryPackage
