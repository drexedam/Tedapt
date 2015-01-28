package at.fhv.tedapt.helper;

import org.eclipse.emf.ecore.EClass;

public class CommonTasks {
	public static boolean mapAsTable(int upperBound, String dataType) {
		return upperBound != 1 || dataType.equals("EByteArray");
	}
	
	public static EClass getMostAbstract(EClass eClass) {
		return eClass.getEAllSuperTypes().isEmpty() ? eClass : eClass.getEAllSuperTypes().get(0);
	}
}
