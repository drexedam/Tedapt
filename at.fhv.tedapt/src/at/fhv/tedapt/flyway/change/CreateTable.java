package at.fhv.tedapt.flyway.change;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import at.fhv.tedapt.exception.PrimaryKeyCanBeNullException;
import at.fhv.tedapt.flyway.entity.Column;
import at.fhv.tedapt.flyway.entity.ForeignKey;

/**
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */
public class CreateTable implements Change {
	
	
	private String _tableName;
	private List<String> _primaryKeys;
	private List<ForeignKey> _foreignKeys; 
	private List<Column> _columns;
	
	/**
	 * 
	 * @param tableName The name of the table to be created
	 */
	public CreateTable(String tableName) {
		_tableName = tableName;
		_columns = new LinkedList<Column>();
		_foreignKeys = new LinkedList<ForeignKey>();
		_primaryKeys = new LinkedList<String>();
	}
	
	/**
	 * Adds a simple column to the table.
	 * 
	 * @param c The column to be added
	 */
	public void addColumn(Column c) {
		if(!_columns.contains(c) && !_primaryKeys.contains(c.getName())) {
			_columns.add(c);
		}
	}

	/**
	 * Adds a foreign key to the table.
	 * @deprecated Use addForeignKey(Column c, String refTable, String refColumn). It will automatically
	 * add the column to the table
	 * @param fk
	 */
	@Deprecated
	public void addForeignKey(ForeignKey fk) {
		if(!_foreignKeys.contains(fk)) {
			_foreignKeys.add(fk);
		}
	}
	
	/**
	 * Adds given column to the table and creates a foreign key based on it.
	 * @param c The column containing the foreign key
	 * @param refTable The table containing the column which is referenced
	 * @param refColumn The column which is referenced
	 */
	public void addForeignKey(Column c, String refTable, String refColumn) {
		addColumn(c);
		_foreignKeys.add(new ForeignKey(c.getName(), refTable, refColumn));
	}
	
	/**
	 * Adds given column as primary key. To add multiple primary keys use this function multiple times
	 * @param c The primary key column
	 * @throws PrimaryKeyCanBeNullException 
	 */
	public void addPrimaryKey(Column c) throws PrimaryKeyCanBeNullException {
		if(!c.notNull()) {
			//SEE http://dev.mysql.com/doc/refman/5.1/en/create-table.html
			throw new PrimaryKeyCanBeNullException("A primary key column may not be null.");
		}
		addColumn(c);
		_primaryKeys.add(c.getName());
	}
	
	
	@Override
	public String getSQL() {
		StringBuilder sb = new StringBuilder("CREATE TABLE "+_tableName.toLowerCase());
		if(!_columns.isEmpty()) {
			sb.append(" (");
			Iterator<Column> it = _columns.iterator();
			Column c;
			while(it.hasNext()) {
				c = it.next();
				sb.append(c.getName().toLowerCase()+" "+c.getType());
				
				if(c.getDefault() != null) {
					sb.append(" DEFAULT "+c.getDefault());
				}
				
				if(c.doesAutoIncrement()) {
					sb.append(" AUTO_INCREMENT");
				}
				
				if(c.notNull()) {
					sb.append(" NOT NULL");
				}
				
				if(it.hasNext()) {
					sb.append(",");
				}
			}
			
			for(ForeignKey fk : _foreignKeys) {
				sb.append(",FOREIGN KEY ("+fk.getColumn().toLowerCase()+") REFERENCES "+fk.getRefTable().toLowerCase()+"("+fk.getRefColumn().toLowerCase()+")");
			}
			
			Iterator<String> itPK = _primaryKeys.iterator();
			sb.append(",PRIMARY KEY (");
			while(itPK.hasNext()) {
				sb.append(itPK.next().toLowerCase());
				if(itPK.hasNext()) {
					sb.append(",");
				}
			}
			
			sb.append("));");
		}
		return sb.toString();
	}
	
	
}
