

package xcom.utils4j.data.database ;


import static xcom.utils4j.data.database.TestQuery.TestQuery_InjectedStatementWhereClause ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_KeyColumnName ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_Statement ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_StatementResults ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_TestTableName ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_ValueColumnName ;

import java.sql.Connection ;
import java.sql.DatabaseMetaData ;
import java.sql.DriverManager ;
import java.sql.ResultSetMetaData ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Map ;

import org.junit.Assert ;
import org.junit.BeforeClass ;
import org.junit.Test ;

import xcom.utils4j.data.database.Query.Status ;


public class _Test_Query_E2E {

	public static final String HSQL_DB_DRIVER = "org.hsqldb.jdbc.JDBCDriver" ;
	public static final String HSQL_DB_URL = "jdbc:hsqldb:mem:testDB" ; // mem means it lasts until JVM dies
	public static final String HSQL_DB_USERNAME = "SA" ;
	public static final String HSQL_DB_PASSWORD = "pass" ;


	/**
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@BeforeClass
	public static void setupBeforeClass() throws ClassNotFoundException, SQLException {

		try ( final Connection connection = DriverManager.getConnection(HSQL_DB_URL, HSQL_DB_USERNAME, HSQL_DB_PASSWORD) ) {

			try ( final Statement statement = connection.createStatement() ) {
				// Recreate BNZI database DAW_STATUS table
				statement.execute("CREATE TABLE TEST_TABLE (KEY VARCHAR(100), VALUE VARCHAR(100))") ;
				statement.execute("INSERT INTO TEST_TABLE (KEY, VALUE) VALUES ( 15000, 'Pending')") ;
				statement.execute("INSERT INTO TEST_TABLE (KEY, VALUE) VALUES ( 15001, 'Cancelled')") ;
				statement.execute("INSERT INTO TEST_TABLE (KEY, VALUE) VALUES ( 15002, 'Approved')") ;
			}
		}
	}


	@Test
	public void method_getResultSetAsList_Returns_ValidData() throws SQLException {

		final List<Map<String, String>> actual = Query //
				.connection(DriverManager.getConnection(HSQL_DB_URL, HSQL_DB_USERNAME, HSQL_DB_PASSWORD)) //
				.statement(TestQuery_Statement) //
				.mapTo(new RowMapper_ToMap(new ColumnMapper_FromMySQL())) //
				.getResultSetAsList() ;

		Assert.assertEquals("Incorrect value return from method 'getResultSetAsList()':", TestQuery_StatementResults, actual) ;
	}


	@Test
	public void method_getResultSetAsList_Returns_EmptyData() throws SQLException {

		final List<Map<String, String>> actual = Query //
				.connection(DriverManager.getConnection(HSQL_DB_URL, HSQL_DB_USERNAME, HSQL_DB_PASSWORD)) //
				.statement(TestQuery_InjectedStatementWhereClause) //
				.mapTo(new RowMapper_ToMap(new ColumnMapper_FromMySQL())) //
				.getResultSetAsList() ;

		Assert.assertEquals("Incorrect value return from method 'getResultSetAsList()':", new ArrayList<Map<String, String>>(), actual) ;
	}


	@Test(expected = SQLException.class)
	public void method_getResultSetAsList_ThrowsOn_sql_createStatement() throws SQLException {

		Query //
				.connection(DriverManager.getConnection(HSQL_DB_URL, HSQL_DB_USERNAME, HSQL_DB_PASSWORD)) //
				.statement("SELECT * FROM MY_TABLE") // Invalid table name will cause exception.
				.mapTo(new RowMapper_ToMap(new ColumnMapper_FromMySQL())) //
				.getResultSetAsList() ;
	}


	@Test
	public void method_execute_Returns_ValidData() throws SQLException {

		final Status actual = Query //
				.connection(DriverManager.getConnection(HSQL_DB_URL, HSQL_DB_USERNAME, HSQL_DB_PASSWORD)) //
				.statement(TestQuery_Statement) //
				.execute() ;

		Assert.assertEquals("Incorrect value return from method 'getResult()':", true, actual.result()) ;
		Assert.assertEquals("Incorrect value return from method 'getNumberOfAffectedRows()':", -1, actual.numberOfAffectedRows()) ;
	}


	@Test
	public void method_getResultSetMetaData_Returns_ValidData() throws SQLException {

		final ResultSetMetaData actual = Query //
				.connection(DriverManager.getConnection(HSQL_DB_URL, HSQL_DB_USERNAME, HSQL_DB_PASSWORD)) //
				.statement(TestQuery_Statement) //
				.getResultSetMetaData() ;

		Assert.assertEquals("Incorrect value return from method 'getColumnCount()':", 2, actual.getColumnCount()) ;
		Assert.assertEquals("Incorrect value return from method 'getColumnLabel()':", TestQuery_KeyColumnName, actual.getColumnLabel(1)) ;
		Assert.assertEquals("Incorrect value return from method 'getColumnLabel()':", TestQuery_ValueColumnName, actual.getColumnLabel(2)) ;
	}


	@Test(expected = SQLException.class)
	public void method_getResultSetMetaData_ThrowsOn_Connection$$createStatement() throws SQLException {

		Query //
				.connection(DriverManager.getConnection(HSQL_DB_URL, HSQL_DB_USERNAME, HSQL_DB_PASSWORD)) //
				.statement("SELECT * FROM MY_TABLE") // Invalid table name will cause exception.
				.getResultSetMetaData() ;
	}


	@Test
	public void method_getDatabaseMetaData_Returns_ValidData() throws SQLException {

		final DatabaseMetaData actual = Query //
				.connection(DriverManager.getConnection(HSQL_DB_URL, HSQL_DB_USERNAME, HSQL_DB_PASSWORD)) //
				.getDatabaseMetaData() ;

		Assert.assertEquals("Incorrect value return from method 'getUserName()':", HSQL_DB_USERNAME, actual.getUserName()) ;
	}


	@Test
	public void method_tableExists_Returns_True() throws SQLException {

		final boolean actual = Query //
				.connection(DriverManager.getConnection(HSQL_DB_URL, HSQL_DB_USERNAME, HSQL_DB_PASSWORD)) //
				.tableExists(TestQuery_TestTableName) ;

		Assert.assertEquals("Incorrect value return from method 'getUserName()':", true, actual) ;
	}


	@Test
	public void method_tableExists_Returns_False() throws SQLException {

		final boolean actual = Query //
				.connection(DriverManager.getConnection(HSQL_DB_URL, HSQL_DB_USERNAME, HSQL_DB_PASSWORD)) //
				.tableExists("MY_TABLE") ;

		Assert.assertEquals("Incorrect value return from method 'getUserName()':", false, actual) ;
	}
}
