package at.fhv.tedapt.exception;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class PrimaryKeyCanBeNullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1925404103390754059L;

	public PrimaryKeyCanBeNullException() {
		super();
	}

	public PrimaryKeyCanBeNullException(String message) {
		super(message);
	}

	public PrimaryKeyCanBeNullException(Throwable cause) {
		super(cause);
	}

	public PrimaryKeyCanBeNullException(String message, Throwable cause) {
		super(message, cause);
	}

}
