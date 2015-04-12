package at.fhv.tedapt.helper;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class CommonTasks {
	
	/**
	 * 
	 * @param upperBound Upper bound of attribute
	 * @param dataType Data type of attribute
	 * @return Returns true if attribute has to be mapped as a table or false if it can be mapped as column
	 */
	public static boolean mapAsTable(int upperBound, String dataType) {
		return upperBound != 1 || dataType.equals("EByteArray");
	}
	
	/**
	 * 
	 * @param eClass Any EClass
	 * @return Returns the most abstract class the given EClass inheritats from
	 */
	public static EClass getMostAbstract(EClass eClass) {
		return eClass.getEAllSuperTypes().isEmpty() ? eClass : eClass.getEAllSuperTypes().get(0);
	}
	
	/**
	 * 
	 * @param eClass
	 * @return If the class contains a containment reference
	 */
	public static boolean existingContReference(EClass eClass) {
		if(eClass.getEReferences() == null || eClass.getEReferences().isEmpty()) 
			return false;
		
		for(EReference ref : eClass.getEReferences()) {
			if(ref.isContainment()) {
				return true;
			}
		}
			
		return false;
	}
}
