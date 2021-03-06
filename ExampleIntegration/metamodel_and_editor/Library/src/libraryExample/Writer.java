/**
 */
package libraryExample;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Writer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link libraryExample.Writer#getName <em>Name</em>}</li>
 *   <li>{@link libraryExample.Writer#getBooks <em>Books</em>}</li>
 *   <li>{@link libraryExample.Writer#getLastname <em>Lastname</em>}</li>
 * </ul>
 * </p>
 *
 * @see libraryExample.LibraryPackage#getWriter()
 * @model
 * @generated
 */
public interface Writer extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see libraryExample.LibraryPackage#getWriter_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link libraryExample.Writer#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Books</b></em>' reference list.
	 * The list contents are of type {@link libraryExample.Book}.
	 * It is bidirectional and its opposite is '{@link libraryExample.Book#getWriter <em>Writer</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Books</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Books</em>' reference list.
	 * @see libraryExample.LibraryPackage#getWriter_Books()
	 * @see libraryExample.Book#getWriter
	 * @model type="libraryExample.Book" opposite="writer"
	 * @generated
	 */
	EList getBooks();

	/**
	 * Returns the value of the '<em><b>Lastname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lastname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lastname</em>' attribute.
	 * @see #setLastname(String)
	 * @see libraryExample.LibraryPackage#getWriter_Lastname()
	 * @model
	 * @generated
	 */
	String getLastname();

	/**
	 * Sets the value of the '{@link libraryExample.Writer#getLastname <em>Lastname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lastname</em>' attribute.
	 * @see #getLastname()
	 * @generated
	 */
	void setLastname(String value);

} // Writer
