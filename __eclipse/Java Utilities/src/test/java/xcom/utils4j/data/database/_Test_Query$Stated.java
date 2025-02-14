

package xcom.utils4j.data.database ;


import static xcom.utils4j.data.database.TestQuery.TestQuery_InjectedStatementWhereClause ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_Parms ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_Statement ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_StatementWhereClause ;

import java.sql.Connection ;
import java.sql.ResultSet ;
import java.sql.ResultSetMetaData ;
import java.sql.SQLException ;
import java.sql.Statement ;

import org.junit.Assert ;
import org.junit.Test ;
import org.mockito.Mockito ;

import xcom.utils4j.data.database.Query.Query$Connected ;
import xcom.utils4j.data.database.Query.Query$Mapped ;
import xcom.utils4j.data.database.Query.Query$Stated ;
import xcom.utils4j.data.database.Query.Status ;


public class _Test_Query$Stated {

	/**
	 * Verify that constructor returns the expected type. (Bump code coverage to 100%.)
	 */
	@Test
	public void constructor_Query$$Stated_Returns_CorrectType() {

		final Query$Stated stated = new Query() {}.new Query$Stated(null, TestQuery_StatementWhereClause) ;
		Assert.assertTrue("Constructor did not return expected type.", stated instanceof Query$Stated) ;
	}


	/**
	 * Verify that constructor initializes values.
	 */
	@Test
	public void constructor_Query$$Stated_Initialization() {

		final Query$Connected connected = new Query() {}.new Query$Connected(null) ;

		final Query$Stated stated = new Query() {}.new Query$Stated(connected, TestQuery_StatementWhereClause) ;
		Assert.assertEquals("Incorrect value for member 'connected':", connected, stated.connected) ;
		Assert.assertEquals("Incorrect value for member 'statement':", TestQuery_StatementWhereClause, stated.statement) ;
		Assert.assertEquals("Incorrect value for member '_statement':", TestQuery_StatementWhereClause, stated._statement) ;
	}


	/**
	 * Verify <code>getStatement()</code> method.
	 */
	@Test
	public void method_getStatement_Returns_ValidData() {

		final Query$Stated stated = new Query() {}.new Query$Stated(null, TestQuery_StatementWhereClause) ;
		Assert.assertEquals("Incorrect value for  method 'getStatement()':", TestQuery_StatementWhereClause, stated.getStatement()) ;
	}


	/**
	 * Verify that <code>null</code> statements are caught.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void constructor$arg_Query$$Stated_statement_wNullValue() {
		new Query() {}.new Query$Stated(null, null) ;
	}


	/**
	 * Verify that 'blank' statements are caught.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void constructor$arg_Query$$Stated_statement_wBlankValue() {
		new Query() {}.new Query$Stated(null, "   ") ;    // Testing that argument is trimmed.
	}


	/**
	 * Verify that the <code>inject()</code> method returns the expected type.
	 */
	@Test
	public void method_inject_Returns_CorrectType() {

		final Query$Stated stated = new Query() {}.new Query$Stated(null, TestQuery_StatementWhereClause) ;
		Assert.assertTrue("Method inject() did not return expected type.", stated.inject(null) instanceof Query$Stated) ;
	}


	/**
	 * Verify that
	 */
	@Test
	public void method_inject_Returns_InjectedParameters() {

		final Query$Stated stated = new Query() {}.new Query$Stated(null, TestQuery_StatementWhereClause) ;
		Assert.assertEquals("Method inject() did not inject parameters;", TestQuery_InjectedStatementWhereClause,
				stated.inject(TestQuery_Parms).getStatement()) ;
	}


	/**
	 * Verify that the <code>mapTo()</code> method returns the expected type.
	 */
	@Test
	public void method_mapTo_Returns_CorrectType() {

		final Query$Stated stated = new Query() {}.new Query$Stated(null, TestQuery_StatementWhereClause) ;
		Assert.assertTrue("Method 'mapTo()' did not return expected type.", stated.mapTo(null) instanceof Query$Mapped) ;
	}


	/**
	 * Verify that <code>execute</code> returns data.
	 *
	 * @throws SQLException
	 */
	@Test
	public void method_execute_Returns_ValidData() throws SQLException {

		final Statement statement = Mockito.mock(Statement.class) ;
		Mockito.when(statement.execute(TestQuery_Statement)).thenReturn(true) ;
		Mockito.when(statement.getUpdateCount()).thenReturn(1) ;

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.createStatement()).thenReturn(statement) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;

		final Status status = new Query() {}.new Query$Stated(connected, TestQuery_Statement).execute() ;

		Assert.assertNotNull("", status) ;
		Assert.assertEquals("Incorrect value returned from method 'getResult()':", true, status.result()) ;
		Assert.assertEquals("Incorrect value returned from method 'getNumberOfAffectedRows()':", 1, status.numberOfAffectedRows()) ;
	}


	/**
	 * Verify that <code>execute</code> throws <code>SQLException</code> when error on <code>createStatement()</code>.
	 *
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void method_execute_ThrowsOn_Connecion$$createStatement() throws SQLException {

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.createStatement()).thenThrow(new SQLException()) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;

		new Query() {}.new Query$Stated(connected, TestQuery_Statement).execute() ;
	}


	/**
	 * Verify that <code>getResultSetAsList</code> returns data.
	 *
	 * @throws SQLException
	 */
	@Test
	public void method_getResultSetMetaData_Returns_Data() throws SQLException {

		final ResultSetMetaData md = Mockito.mock(ResultSetMetaData.class) ;

		final ResultSet rs = Mockito.mock(ResultSet.class) ;
		Mockito.when(rs.getMetaData()).thenReturn(md) ;

		final Statement statement = Mockito.mock(Statement.class) ;
		Mockito.when(statement.execute(TestQuery_Statement)).thenReturn(true) ;
		Mockito.when(statement.getResultSet()).thenReturn(rs) ;

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.createStatement()).thenReturn(statement) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;

		Assert.assertNotNull("Method 'getResultSetMetaData()' did not return data.",
				new Query() {}.new Query$Stated(connected, TestQuery_Statement).getResultSetMetaData()) ;
	}


	/**
	 * Verify that <code>getResultSetMetaData</code> throws <code>SQLException</code> when error on <code>createStatement()</code>.
	 *
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void method_getResultSetMetaData_ThrowsOn_Connecion$$createStatement() throws SQLException {

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.createStatement()).thenThrow(new SQLException()) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;

		new Query() {}.new Query$Stated(connected, TestQuery_Statement).getResultSetMetaData() ;
	}
}
