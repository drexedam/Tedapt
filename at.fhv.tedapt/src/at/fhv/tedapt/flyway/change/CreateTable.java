package at.fhv.tedapt.flyway.change;

import at.fhv.tedapt.exception.PrimaryKeyCanBeNullException;
import at.fhv.tedapt.flyway.DatabaseHandler;
import at.fhv.tedapt.flyway.change.mysql.CreateTableMySQL;
import at.fhv.tedapt.flyway.entity.Column;

public interface CreateTable extends Change {

	public static CreateTable getInstance(String tableName) {
		switch (DatabaseHandler.getDialect()) {
		case MYSQL:
		default:
			return new CreateTableMySQL(tableName);
		}
	}
	
	/**
	 * Adds a simple column to the table.
	 * 
	 * @param c The column to be added
	 */
	public abstract void addColumn(Column c);

	/**
	 * Adds given column to the table and creates a foreign key based on it.
	 * @param c The column containing the foreign key
	 * @param refTable The table containing the column which is referenced
	 * @param refColumn The column which is referenced
	 */
	public abstract void addForeignKey(Column c, String refTable,
			String refColumn);

	/**
	 * Adds given column as primary key. To add multiple primary keys use this function multiple times
	 * @param c The primary key column
	 * @throws PrimaryKeyCanBeNullException 
	 */
	public abstract void addPrimaryKey(Column c)
			throws PrimaryKeyCanBeNullException;

}