/**
 */
package libraryExample;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Asset</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link libraryExample.Asset#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see libraryExample.LibraryPackage#getAsset()
 * @model
 * @generated
 */
public interface Asset extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * The default value is <code>"0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(float)
	 * @see libraryExample.LibraryPackage#getAsset_Value()
	 * @model default="0.0"
	 * @generated
	 */
	float getValue();

	/**
	 * Sets the value of the '{@link libraryExample.Asset#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(float value);

} // Asset
