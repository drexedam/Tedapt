package at.fhv.tedapt.status;

public enum TedaptStatusCode {

	MIGRATION_OK("Migration succesfull", 0),
	MIGRATION_FAILED("Migration failed", 1),
	FILE_SAVE_FAILED("Could not save file", 2),
	FILE_READ_FAILED("Could not read file", 3),
	XML_PARSE_ERROR("Could not parse XML file", 4);
	
	private String _message;
	private int _statusCode;
	
	
	private TedaptStatusCode(String message, int statusCode) {
		_message = message;
		_statusCode = statusCode;
	}
	
	
	public String getMessage() {
		return _message;
	}
	
	public int getStatusCode() {
		return _statusCode;
	}

}
