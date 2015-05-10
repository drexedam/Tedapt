package at.fhv.tedapt.operation;

import static at.fhv.tedapt.helper.NamingConstants.FK_SUFFIX;
import static at.fhv.tedapt.helper.NamingConstants.ID;
import static at.fhv.tedapt.helper.NamingConstants.IDX_SUFFIX;
import static at.fhv.tedapt.helper.NamingConstants.ID_SUFFIX;
import static at.fhv.tedapt.helper.NamingConstants.PK_SUFFIX;

import org.eclipse.emf.edapt.declaration.EdaptConstraint;
import org.eclipse.emf.edapt.declaration.EdaptOperation;
import org.eclipse.emf.edapt.history.util.HistoryUtils;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import at.fhv.tedapt.flyway.DatabaseHandler;
import at.fhv.tedapt.flyway.FlywayHandler;
import at.fhv.tedapt.flyway.change.Change;
import at.fhv.tedapt.flyway.change.SQLChange;
import at.fhv.tedapt.helper.CommonTasks;

/**
 * {@description}
 * 
 * @author Damian Drexel
 * @version 0.1
 *
 */

@SuppressWarnings("restriction")
@EdaptOperation(identifier="createAttributeTedapt", label="Create Attribute Tedapt", description="A new attribute and the coresponding column are created.")
public class CreateAttribute extends org.eclipse.emf.edapt.declaration.creation.NewAttribute {

	
	/** {@description} */
	@EdaptConstraint(description="Not nullable attributes need a default value")
	public boolean checkDefault() {
		if(upperBound == 1 && upperBound==lowerBound) {
			return defaultValue != null && !defaultValue.isEmpty();
		}
		
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void execute(Metamodel metamodel, Model model) {
		
		super.execute(metamodel, model);
		
		//DB adaption
		
		//Name of most abstract class
		String superClass = CommonTasks.getMostAbstract(eClass).getName();
		
		
		
		Change change;
		DSLContext context = DatabaseHandler.getContext();
		//Attributes upperBound == 1 are mapped as column otherwise as table
		if(!CommonTasks.mapAsTable(upperBound, type.getName())) {

			String query = context.alterTable(superClass)
			.add(name, DatabaseHandler.mapDataTypeJOOQ(type).nullable(upperBound!=lowerBound)).getSQL();
			change = new SQLChange(query);
		} else {
			String tableName = eClass.getName()+"_"+name;
			
			String createTable = context.createTable(tableName)
					.column(tableName+ID_SUFFIX, SQLDataType.BIGINT)
					.column("elt", DatabaseHandler.mapDataTypeJOOQ(type))
					.column(tableName+IDX_SUFFIX, SQLDataType.INTEGER).getSQL();
			
			String addPK = context.alterTable(tableName)
					.add(DSL.constraint(tableName+ID_SUFFIX+PK_SUFFIX).primaryKey(tableName+ID_SUFFIX, tableName+IDX_SUFFIX)).getSQL();
			String addFK = context.alterTable(tableName)
					.add(DSL.constraint(tableName+ID_SUFFIX+FK_SUFFIX).foreignKey(tableName+ID_SUFFIX).references(superClass, ID)).getSQL();
					
			change = new SQLChange(createTable, addPK, addFK);
					
		}
		
		FlywayHandler.addChange(change);
		
		FlywayHandler.saveChangelog(
				HistoryUtils.getHistoryURI(
						metamodel.getEPackages().get(0).eResource()), "Create Attribute "+name);
	}


}
