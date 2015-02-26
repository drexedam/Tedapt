package at.fhv.tedapt.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import at.fhv.tedapt.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class TedaptPreferences
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public TedaptPreferences() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Settings for Tedapt operations.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		StringFieldEditor uName, uPW, dbAdr, dbName;
		RadioGroupFieldEditor dbms;
		
		uName = new StringFieldEditor(PreferenceConstants.P_UNAME, "Database &username", getFieldEditorParent());
		uName.setEmptyStringAllowed(false);
		
		uPW = new StringFieldEditor(PreferenceConstants.P_PW, "Database &password", getFieldEditorParent()) {

			@Override
		    protected void doFillIntoGrid(Composite parent, int numColumns) {
		        super.doFillIntoGrid(parent, numColumns);
		
		        getTextControl().setEchoChar('*');
		    }
		
		};
		
		dbAdr = new StringFieldEditor(PreferenceConstants.DB_ADDRESS, "Database &address", getFieldEditorParent());
		dbAdr.setEmptyStringAllowed(false);
		
		dbName = new StringFieldEditor(PreferenceConstants.DB_NAME, "Database &name", getFieldEditorParent());
		dbName.setEmptyStringAllowed(false);
		
		
		dbms = new RadioGroupFieldEditor(PreferenceConstants.DB_MS, 
				"Database management &system", 1, new String[][] {
					//{"HSQL", PreferenceConstants.DBMS_HSQL},
					{"MySQL", PreferenceConstants.DBMS_MYSQL}//,
					//{"PostgreSQL", PreferenceConstants.DBMS_PSQL},
					//{"SQL Server", PreferenceConstants.DBMS_SQLSERV}
					},
				getFieldEditorParent());
				
		addField(uName);
		addField(uPW);
		addField(dbAdr);
		addField(dbName);
		addField(dbms);
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
	@Override
	public boolean performOk() {
		return super.performOk();
	}
	
}