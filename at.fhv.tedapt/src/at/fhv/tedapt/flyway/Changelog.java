package at.fhv.tedapt.flyway;

import java.util.LinkedList;
import java.util.List;

import at.fhv.tedapt.flyway.change.Change;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class Changelog {

	private List<Change> _changes;
	
	public Changelog() {
		_changes = new LinkedList<Change>();
	}
	
	/**
	 * 
	 * @return A list of all changes
	 */
	public List<Change> getChanges() {
		return _changes;
	}
	
	/**
	 * Add a change to the log
	 * @param c The change to be added
	 */
	public void addChange(Change c) {
		if(!_changes.contains(c)) {
			_changes.add(c);
		}
	}
	
	/**
	 * 
	 * @return The SQL representation of all contained changes
	 */
	public String getSQL() {
		StringBuilder sb = new StringBuilder();
		for(Change c : _changes) {
			sb.append(c.getSQL()+System.lineSeparator());
		}
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @return True if the log contains changes false otherwise
	 */
	public boolean isEmpty() {
		return _changes.isEmpty();
	}
	
}
