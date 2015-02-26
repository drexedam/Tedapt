/**
 */
package example;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link example.ExampleClass#getMyInteger <em>My Integer</em>}</li>
 * </ul>
 * </p>
 *
 * @see example.ExamplePackage#getExampleClass()
 * @model
 * @generated
 */
public interface ExampleClass extends EObject {
	/**
	 * Returns the value of the '<em><b>My Integer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>My Integer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>My Integer</em>' attribute.
	 * @see #setMyInteger(int)
	 * @see example.ExamplePackage#getExampleClass_MyInteger()
	 * @model
	 * @generated
	 */
	int getMyInteger();

	/**
	 * Sets the value of the '{@link example.ExampleClass#getMyInteger <em>My Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>My Integer</em>' attribute.
	 * @see #getMyInteger()
	 * @generated
	 */
	void setMyInteger(int value);

} // ExampleClass
