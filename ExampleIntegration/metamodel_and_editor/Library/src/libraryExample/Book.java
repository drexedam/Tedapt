/**
 */
package libraryExample;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Book</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link libraryExample.Book#getTitle <em>Title</em>}</li>
 *   <li>{@link libraryExample.Book#getPages <em>Pages</em>}</li>
 *   <li>{@link libraryExample.Book#getWriter <em>Writer</em>}</li>
 *   <li>{@link libraryExample.Book#getCategory <em>Category</em>}</li>
 * </ul>
 * </p>
 *
 * @see libraryExample.LibraryPackage#getBook()
 * @model
 * @generated
 */
public interface Book extends EObject {
	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see libraryExample.LibraryPackage#getBook_Title()
	 * @model
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link libraryExample.Book#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Pages</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pages</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pages</em>' attribute.
	 * @see #setPages(int)
	 * @see libraryExample.LibraryPackage#getBook_Pages()
	 * @model default="100"
	 * @generated
	 */
	int getPages();

	/**
	 * Sets the value of the '{@link libraryExample.Book#getPages <em>Pages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pages</em>' attribute.
	 * @see #getPages()
	 * @generated
	 */
	void setPages(int value);

	/**
	 * Returns the value of the '<em><b>Writer</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link libraryExample.Writer#getBooks <em>Books</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Writer</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Writer</em>' reference.
	 * @see #setWriter(Writer)
	 * @see libraryExample.LibraryPackage#getBook_Writer()
	 * @see libraryExample.Writer#getBooks
	 * @model opposite="books" required="true"
	 * @generated
	 */
	Writer getWriter();

	/**
	 * Sets the value of the '{@link libraryExample.Book#getWriter <em>Writer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Writer</em>' reference.
	 * @see #getWriter()
	 * @generated
	 */
	void setWriter(Writer value);

	/**
	 * Returns the value of the '<em><b>Category</b></em>' attribute.
	 * The default value is <code>"Mystery"</code>.
	 * The literals are from the enumeration {@link libraryExample.BookCategory}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category</em>' attribute.
	 * @see libraryExample.BookCategory
	 * @see #setCategory(BookCategory)
	 * @see libraryExample.LibraryPackage#getBook_Category()
	 * @model default="Mystery"
	 * @generated
	 */
	BookCategory getCategory();

	/**
	 * Sets the value of the '{@link libraryExample.Book#getCategory <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category</em>' attribute.
	 * @see libraryExample.BookCategory
	 * @see #getCategory()
	 * @generated
	 */
	void setCategory(BookCategory value);

} // Book
