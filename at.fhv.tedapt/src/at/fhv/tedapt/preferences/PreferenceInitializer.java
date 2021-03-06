package at.fhv.tedapt.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import at.fhv.tedapt.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_UNAME, "root");
		store.setDefault(PreferenceConstants.P_PW, "");
		store.setDefault(PreferenceConstants.DB_ADDRESS, "localhost");
		store.setDefault(PreferenceConstants.DB_NAME, "test");
		
		store.setDefault(PreferenceConstants.FLYWAY_BASE_VERSION, 0);
		store.setDefault(PreferenceConstants.FLYWAY_TARGET_VERSION, 0);
		store.setDefault(PreferenceConstants.FLYWAY_OUT_OF_ORDER, false);
	}

}
