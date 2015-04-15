package at.fhv.tedapt.status;

import org.eclipse.core.runtime.Status;

import at.fhv.tedapt.Activator;

public class TedaptStatus extends Status {
	
	public TedaptStatus(int severity, TedaptStatusCode code, Throwable exception) {
		super(severity, Activator.PLUGIN_ID, code.getStatusCode(), code.getMessage(), exception);
	}
	
	public TedaptStatus(int severity, TedaptStatusCode code, String additionalMessage, Throwable exception) {
		super(severity, Activator.PLUGIN_ID, code.getStatusCode(), code.getMessage()+" "+additionalMessage, exception);
	}
	
	public TedaptStatus(int severity, TedaptStatusCode code) {
		this(severity, code, null);
	}
}
