package at.fhv.tedapt.flyway.change;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public interface Change {

	/**
	 * 
	 * @return The change represented as SQL query
	 */
	public String getSQL();
}
