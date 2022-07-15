

package xcom.utils4j.data.database ;


import static xcom.utils4j.data.database.TestQuery.TestQuery_StatementWhereClause ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_TestTableName ;

import java.sql.Connection ;
import java.sql.DatabaseMetaData ;
import java.sql.ResultSet ;
import java.sql.SQLException ;

import org.junit.Assert ;
import org.junit.Test ;
import org.mockito.Mockito ;

import xcom.utils4j.data.database.Query.Query$Connected ;
import xcom.utils4j.data.database.Query.Query$Stated ;


public class _Test_Query$Connected {

	/**
	 * Verify that constructor returns the expected type. (Bump code coverage to 100%.)
	 */
	@Test
	public void constructor_Query$$Connected_Returns_CorrectType() {

		final Query$Connected connected = new Query() {}.new Query$Connected(null) ;
		Assert.assertTrue("Constructor did not return expected type.", connected instanceof Query$Connected) ;
	}


	/**
	 * Verify that constructor initializes values.
	 */
	@Test
	public void constructor_Query$$Connected_Initializes() {

		final Connection connection = Mockito.mock(Connection.class) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;
		Assert.assertEquals("Incorrect value for member 'connection':", connection, connected.connection) ;
	}


	/**
	 * Verify that the <code>statement()</code> method returns the expected type.
	 */
	@Test
	public void method_statement_Returns_CorrectType() {

		Assert.assertTrue("Method 'statement()' did not return expected type.",
				new Query() {}.new Query$Connected(null).statement(TestQuery_StatementWhereClause) instanceof Query$Stated) ;
	}


	/**
	 * Verify that <code>getResultSetAsList</code> returns data.
	 *
	 * @throws SQLException
	 */
	@Test
	public void method_getDatabaseMetaData_Returns_Data() throws SQLException {

		final DatabaseMetaData dbmd = Mockito.mock(DatabaseMetaData.class) ;

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.getMetaData()).thenReturn(dbmd) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;
		Assert.assertNotNull("Method 'getResultSetAsList()' did not return data.", connected.getDatabaseMetaData()) ;
	}


	/**
	 * Verify that <code>getResultSetAsList</code> returns <code>true</code>.
	 *
	 * @throws SQLException
	 */
	@Test
	public void method_tableExists_Returns_True() throws SQLException {

		final ResultSet rs = Mockito.mock(ResultSet.class) ;
		Mockito.when(rs.next()).thenReturn(true) ;

		final DatabaseMetaData dbmd = Mockito.mock(DatabaseMetaData.class) ;
		Mockito.when(dbmd.getTables(null, null, TestQuery_TestTableName, null)).thenReturn(rs) ;

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.getMetaData()).thenReturn(dbmd) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;
		Assert.assertTrue("Incorrect value for  method 'tableExists()':", connected.tableExists(TestQuery_TestTableName)) ;
	}


	/**
	 * Verify that <code>getResultSetAsList</code> returns <code>false</code>.
	 *
	 * @throws SQLException
	 */
	@Test
	public void method_tableExists_Returns_False() throws SQLException {

		final ResultSet rs = Mockito.mock(ResultSet.class) ;
		Mockito.when(rs.next()).thenReturn(false) ;

		final DatabaseMetaData dbmd = Mockito.mock(DatabaseMetaData.class) ;
		Mockito.when(dbmd.getTables(null, null, TestQuery_TestTableName, null)).thenReturn(rs) ;

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.getMetaData()).thenReturn(dbmd) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;
		Assert.assertFalse("Incorrect value for  method 'tableExists()':", connected.tableExists(TestQuery_TestTableName)) ;
	}


	/**
	 * Verify that <code>tableExists</code> throws <code>SQLException</code> when error on <code>getTables()</code>.
	 *
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void method_tableExists_ThrowsOn_DatabaseMetaData$$getTables() throws SQLException {

		final DatabaseMetaData dbmd = Mockito.mock(DatabaseMetaData.class) ;
		Mockito.when(dbmd.getTables(null, null, TestQuery_TestTableName, null)).thenThrow(new SQLException()) ;

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.getMetaData()).thenReturn(dbmd) ;

		new Query() {}.new Query$Connected(connection).tableExists(TestQuery_TestTableName) ;
	}
}
