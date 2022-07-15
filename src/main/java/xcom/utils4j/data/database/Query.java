

package xcom.utils4j.data.database ;


import static xcom.utils4j.format.Templator.UnixDelimiters ;

import java.sql.Connection ;
import java.sql.DatabaseMetaData ;
import java.sql.Date ;
import java.sql.ResultSet ;
import java.sql.ResultSetMetaData ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.sql.Timestamp ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Map ;

import org.apache.commons.lang3.StringUtils ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.format.Templator ;
import xcom.utils4j.logging.annotations.Log ;


/**
 * A fluent API for simple JDBC SQL access.
 *
 * <ul>
 * <li>Execute SQL SELECT statement and return selected data:
 *
 * <pre>
 * List&lt;Map&lt;K, V>> = SQLs .connection(connection) .statement(statement) [ .inject(values) ] .mapTo(rowMapper&lt;K, V>()) .getResultSetAsList()
 * </pre>
 *
 * </li>
 *
 * <li>Execute generic SQL statement:
 *
 * <pre>
 * SQLs.Status = SQLs .connection(connection) .statement(statement) [ .inject() ] .execute()
 * </pre>
 *
 * </li>
 *
 * <li>Return metadata of an SQL SELECT statement's result set:
 *
 * <pre>
 * ResultSetMetaData = SQLs .connection(connection) .statement(statement) [ .inject() ] .getResultSetMetaData()
 * </pre>
 *
 * </li>
 *
 * <li>Return table information:
 *
 * <pre>
 * DatabaseMetaData = SQLs .connection(connection) .getDatabaseMetaData()
 *
 * Boolean = SQLs .connection(connection) .tableExists()
 * </pre>
 *
 * </li>
 * </ul>
 *
 */
public abstract class Query {

	private static final Logger Logger = LoggerFactory.getLogger(Query.class) ;


	/**
	 * Status object return when executing generic SQL statement. Will throw <code>NullPointerException</code> if no SQL statement is executed against status.
	 */
	public static class Status {

		/**
		 * Result of SQL statement.
		 */
		Boolean execute ;

		@Log
		public boolean getResult() {
			return execute.booleanValue() ;
		}


		/**
		 * Number of rows affected by SQL statement.
		 */
		Integer updated ;

		@Log
		public int getNumberOfAffectedRows() {
			return updated.intValue() ;
		}
	}


	/**
	 * Various useful constants ...
	 */

	public static final Date Date_MinValue = new Date(0) ;

	public static final Timestamp Timestamp_MinValue = new Timestamp(new Date(0).getTime()) ;


	/**
	 *
	 */
	public class Query$Connected {

		/**
		 *
		 */
		Connection connection ;


		/**
		 * @param mapped
		 * @param connection
		 */
		@Log
		public Query$Connected(final Connection connection) {
			this.connection = connection ;
		}


		/**
		 * @return
		 * @throws SQLException
		 */
		@Log
		public DatabaseMetaData getDatabaseMetaData() throws SQLException {
			return connection.getMetaData() ;
		}


		/**
		 * Instantiate a SQL stated object for the method call chain.
		 *
		 * @param statement
		 *            - The SQL statement to be executed.
		 * @return an instantiated <code>SQLs$Stated</code> object
		 */
		@Log
		public Query$Stated statement(final String statement) {
			return new Query$Stated(this, statement) {} ;
		}


		/**
		 * @param tableName
		 * @return
		 * @throws SQLException
		 */
		@Log
		public boolean tableExists(final String tableName) throws SQLException {

			final Boolean results ;

			final DatabaseMetaData dbmd = connection.getMetaData() ;

			try ( ResultSet rs = dbmd.getTables(null, null, tableName, null) ) {
				results = (rs.next() ? true : false) ;
			}
			catch ( final SQLException ex ) {
				Logger.error("SQL error while checking table existance.") ;
				throw ex ;
			}

			return results ;
		}

	}

	/**
	 * Implements a mapped object for the fluent SQL API method call chain.
	 *
	 * <p>
	 * A 'mapped' SQLs object has a row mapper to convert a SQL result set to the requested return type.
	 * </p>
	 *
	 * @param <T>
	 *            - the requested return type.
	 */
	public class Query$Mapped<K, V> {

		/**
		 *
		 */
		Query$Stated stated ;


		/**
		 *
		 */
		iRowMapper<K, V> rowMapper ;


		/**
		 * @param stated
		 * @param rowMapper
		 */
		public Query$Mapped(final Query$Stated stated, final iRowMapper<K, V> rowMapper) {
			this.stated = stated ;
			this.rowMapper = rowMapper ;
		}


		/**
		 * @return
		 * @throws SQLException
		 */
		@Log
		public List<Map<K, V>> getResultSetAsList() throws SQLException {

			List<Map<K, V>> results = null ;

			Logger.trace("SQL: |{}|", stated._statement) ;

			try ( Statement sqlStatement = stated.connected.connection.createStatement() ) {

				if ( sqlStatement.execute(stated._statement) )
					try ( final ResultSet rs = sqlStatement.getResultSet(); ) {
						results = new ArrayList<Map<K, V>>() ;

						while ( rs.next() ) {
							results.add(rowMapper.mapRow(rs)) ;
							Logger.debug("added row: |{}|", results.get(results.size() - 1)) ;
						}
					}
			}
			catch ( final SQLException ex ) {
				Logger.error("Error executing SQL statement: |{}|", stated._statement) ;
				throw ex ;
			}

			return results ;
		}
	}


	/**
	 * Implements a stated object for the fluent SQL API method call chain.
	 *
	 * <p>
	 * A 'stated' SQLs object has an SQL statement and optional parameters.
	 * </p>
	 */
	public class Query$Stated {

		/**
		*
		*/
		Query$Connected connected ;


		/**
		 * The SQL statement to be executed, and the statement with any injected parameters.
		 */
		String statement ;

		String _statement ;

		@Log
		String getStatement() {
			return _statement ;
		}

		@Log
		Query$Stated setStatement(final String statement) {

			if ( StringUtils.trimToNull(statement) == null )
				throw new IllegalArgumentException("Statement cannot be 'null' or blank.") ;

			this.statement = statement ;
			_statement = new String(this.statement) ;

			return this ;
		}


		/**
		 * Instantiate the SQLs$Stated object.
		 *
		 * @param connected
		 *
		 * @param statement
		 */
		@Log
		public Query$Stated(final Query$Connected connected, final String statement) {
			this.connected = connected ;
			setStatement(statement) ;
		}


		/**
		 * @return
		 * @throws SQLException
		 */
		public Status execute() throws SQLException {

			final Status results = new Status() ;

			Logger.trace("SQL: |{}|", _statement) ;

			try ( Statement sqlStatement = connected.connection.createStatement() ) {
				results.execute = sqlStatement.execute(_statement) ;
				results.updated = sqlStatement.getUpdateCount() ;
			}
			catch ( final SQLException ex ) {
				Logger.error("Error executing SQL statement: |{}|", _statement) ;
				throw ex ;
			}

			return results ;
		}


		/**
		 * @return
		 * @throws SQLException
		 */
		@Log
		public ResultSetMetaData getResultSetMetaData() throws SQLException {

			final ResultSetMetaData results ;

			Logger.trace("SQL: |{}|", _statement) ;

			try ( Statement sqlStatement = connected.connection.createStatement() ) {
				sqlStatement.execute(_statement) ;
				results = sqlStatement.getResultSet().getMetaData() ;
			}
			catch ( final SQLException ex ) {
				Logger.error("Error executing SQL statement: |{}|", _statement) ;
				throw ex ;
			}

			return results ;
		}


		/**
		 * Inject parameters into the SQL statement.
		 *
		 * @param testsqlsParms
		 *            - the parameters to be injected.
		 * @return - the <code>SQLs$Stated</code> object
		 */
		@Log
		public Query$Stated inject(final Map<String, Object> testsqlsParms) {
			_statement = Templator.delimiters(UnixDelimiters).template(statement).inject(testsqlsParms).toString() ;
			return this ;
		}


		/**
		 * Instantiate a SQL mapped object for the method call chain.
		 *
		 * @param rowMapper
		 *            - The mapper of the result set.
		 * @return an instantiated <code>SQLs$Mapped</code> object
		 */
		@Log
		public <K, V> Query$Mapped<K, V> mapTo(final iRowMapper<K, V> rowMapper) {
			return new Query$Mapped<K, V>(this, rowMapper) ;
		}
	}


	/**
	 * Instantiate a SQL non-stated object for the method call chain.
	 *
	 * @param connection
	 *            - The JDBC connection to be used.
	 * @return an instantiated <code>SQLs$NonStated</code> object
	 */
	@Log
	public static Query$Connected connection(final Connection connection) {
		return new Query() {}.new Query$Connected(connection) {} ;
	}
}
