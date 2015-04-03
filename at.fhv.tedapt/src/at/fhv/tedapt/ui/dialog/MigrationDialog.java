package at.fhv.tedapt.ui.dialog;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import at.fhv.tedapt.Activator;
import at.fhv.tedapt.helper.MigrationData;
import at.fhv.tedapt.preferences.PreferenceConstants;

public class MigrationDialog extends TitleAreaDialog {
	
	private Text txtUName, txtPW, txtDBAdr, txtDBName, txtBaseVersion, txtMaxVersion;
	private Button btnOOO;
	
	private String _uName, _pw, _dBAdr, _dBName, _baseVersion, _maxVersion;
	private boolean _outOfOrder;

	public MigrationDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	public void create() {
		super.create();
		setTitle("Migrate Database");
		setMessage("Database connection information");
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(2, false);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(layout);
		
		createUserName(container);
		createPassword(container);
		createDBAdr(container);
		createDBName(container);
		createBaseVersion(container);
		createMaxVersion(container);
		createOutOfOrder(container);
		
		
		return area;
	}
	
	private void createOutOfOrder(Composite container) {
//			Label lblOOO = new Label(container, SWT.NONE);
//			lblOOO.setText("Out of order migration");
			
			new Label(container, SWT.NONE).setText("Out of order migration");
			
			GridData dataOOO = new GridData();
			dataOOO.grabExcessHorizontalSpace = true;
			dataOOO.horizontalAlignment = GridData.FILL;
			
			btnOOO = new Button(container, SWT.CHECK);
			btnOOO.setLayoutData(dataOOO);
		
	}

	private void createMaxVersion(Composite container) {
//		Label lblMaxV = new Label(container, SWT.NONE);
//		lblMaxV.setText("Target database version");
		
		new Label(container, SWT.NONE).setText("Target datbase version");
		
		GridData dataMaxV = new GridData();
		dataMaxV.grabExcessHorizontalSpace = true;
		dataMaxV.horizontalAlignment = GridData.FILL;
		
		txtMaxVersion = new Text(container, SWT.BORDER);
		txtMaxVersion.setLayoutData(dataMaxV);
	}

	private void createBaseVersion(Composite container) {
//		Label lblBaseV = new Label(container, SWT.NONE);
//		lblBaseV.setText("Base database version");
		
		new Label(container, SWT.NONE).setText("Base database version");
		
		GridData dataBaseV = new GridData();
		dataBaseV.grabExcessHorizontalSpace = true;
		dataBaseV.horizontalAlignment = GridData.FILL;
		
		txtBaseVersion = new Text(container, SWT.BORDER);
		txtBaseVersion.setLayoutData(dataBaseV);
	}

	private void createUserName(Composite container) {
//		Label lblUName = new Label(container, SWT.NONE);
//		lblUName.setText("Username");
		
		new Label(container, SWT.NONE).setText("Username");
		
		GridData dataUName = new GridData();
		dataUName.grabExcessHorizontalSpace = true;
		dataUName.horizontalAlignment = GridData.FILL;
		
		txtUName = new Text(container, SWT.BORDER);
		txtUName.setLayoutData(dataUName);
		txtUName.setText(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_UNAME));
	}
	
	private void createPassword(Composite container) {
		Label lblPW = new Label(container, SWT.NONE);
		lblPW.setText("Password");
		
		GridData dataPW = new GridData();
		dataPW.grabExcessHorizontalSpace = true;
		dataPW.horizontalAlignment = GridData.FILL;
		
		txtPW = new Text(container, SWT.BORDER);
		txtPW.setLayoutData(dataPW);
		txtPW.setText(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PW));
	}
	
	private void createDBAdr(Composite container) {
		Label lblDBAdr = new Label(container, SWT.NONE);
		lblDBAdr.setText("Database adress");
		
		GridData dataDBAdr = new GridData();
		dataDBAdr.grabExcessHorizontalSpace = true;
		dataDBAdr.horizontalAlignment = GridData.FILL;
		
		txtDBAdr = new Text(container, SWT.BORDER);
		txtDBAdr.setLayoutData(dataDBAdr);
		txtDBAdr.setText(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.DB_ADDRESS));
	}
	
	private void createDBName(Composite container) {
		Label lblDBName = new Label(container, SWT.NONE);
		lblDBName.setText("Database name");
		
		GridData dataDBName = new GridData();
		dataDBName.grabExcessHorizontalSpace = true;
		dataDBName.horizontalAlignment = GridData.FILL;
		
		txtDBName = new Text(container, SWT.BORDER);
		txtDBName.setLayoutData(dataDBName);
		txtDBName.setText(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.DB_NAME));
	}
	
	@Override
	protected void okPressed() {
		save();
		if(!isValidInput()) {
			MessageBox mb = new MessageBox(getParentShell(), SWT.ICON_ERROR | SWT.OK);
			mb.setText("Invalid input!");
			mb.setMessage("Please enter at least: username, database adress and database name");
			mb.open();
		} else {
			super.okPressed();
		}
	}

	
	private void save() {
		_uName = txtUName.getText();
		_pw = txtPW.getText();
		_dBAdr = txtDBAdr.getText();
		_dBName = txtDBName.getText();
		_baseVersion = txtBaseVersion.getText();
		_maxVersion = txtMaxVersion.getText();
		_outOfOrder = btnOOO.getSelection();
	}
	
	public boolean isValidInput() {
		return !_uName.isEmpty() && !_dBAdr.isEmpty() && !_dBName.isEmpty();
	}
	
	
	public MigrationData getData() {
		return new MigrationData(_uName, _pw, _dBAdr, _dBName, _baseVersion, _maxVersion, _outOfOrder);
	}
	
	
}
