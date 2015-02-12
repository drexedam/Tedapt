package at.fhv.tedapt.ui;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import at.fhv.tedapt.flyway.FlywayHandler;

public class MigrateDialog extends TitleAreaDialog {

	private Text _txtNsURI;
	private String _nsURI;
	
	public MigrateDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	public void create() {
		super.create();
		setTitle("Migrate changes to Database");
		setMessage("Please enter the nsURI of the model which changes should be migrated to the database", IMessageProvider.INFORMATION);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(new GridLayout(1, false));
		
		Label lblnsURI = new Label(container, SWT.NONE);
		lblnsURI.setText("nsURI");
		
		GridData dNsURI = new GridData();
		dNsURI.grabExcessHorizontalSpace = true;
		dNsURI.horizontalAlignment = GridData.FILL;
		
		_txtNsURI = new Text(container, SWT.BORDER);
		_txtNsURI.setLayoutData(dNsURI);
		_txtNsURI.setText(FlywayHandler.getNSPrefix());
		
		return area;
	}
	
	@Override
	protected void okPressed() {
		_nsURI = _txtNsURI.getText();
		super.okPressed();
	}
	
	public String getNSURI() {
		return _nsURI;
	}

}
