

package xcom.utils4j.data.database ;


import static xcom.utils4j.data.database.TestQuerys.TestQuerys_KeyColumnName ;
import static xcom.utils4j.data.database.TestQuerys.TestQuerys_Statement ;
import static xcom.utils4j.data.database.TestQuerys.TestQuerys_StatementWhereClause ;
import static xcom.utils4j.data.database.TestQuerys.TestQuerys_ValueColumnName ;

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
import xcom.utils4j.data.database.api.interfaces.IRowMapper ;


public class _Test_Query$Mapped<K, V> {

	/**
	 * Verify that constructor returns the expected type. (Bump code coverage to 100%.)
	 */
	@Test
	public void constructor_Query$$Mapped_Returns_CorrectType() {

		final Query$Mapped<K, V> mapped = new Query() {}.new Query$Mapped<>(null, null) ;
		Assert.assertTrue("Constructor did not return expected type.", mapped instanceof Query$Mapped) ;
	}


	/**
	 * Verify that constructor initializes values.
	 */
	@Test
	public void constructor_Query$$Mapped_Initialization() {

		final Query$Stated stated = new Query() {}.new Query$Stated(null, TestQuerys_StatementWhereClause) ;
		final IRowMapper<String, String> rowMapper = new RowMapper_ToMap(new ColumnMapper_FromMySQL()) ;

		final Query$Mapped<String, String> mapped = new Query() {}.new Query$Mapped<>(stated, rowMapper) ;
		Assert.assertEquals("Incorrect value for member 'stated':", stated, mapped.stated) ;
		Assert.assertEquals("Incorrect value for member 'rowMapper':", rowMapper, mapped.rowMapper) ;
	}


	/**
	 * Verify that <code>getResultSetAsList</code> returns data.
	 *
	 * @throws SQLException
	 */
	@Test
	public void method_getResultSetAsList_Returns_Data() throws SQLException {

		final ResultSetMetaData md = Mockito.mock(ResultSetMetaData.class) ;
		Mockito.when(md.getColumnCount()).thenReturn(1) ;
		Mockito.when(md.getColumnLabel(1)).thenReturn(TestQuerys_KeyColumnName) ;

		final ResultSet rs = Mockito.mock(ResultSet.class) ;
		Mockito.when(rs.next()).thenReturn(true).thenReturn(false) ;
		Mockito.when(rs.getMetaData()).thenReturn(md) ;
		Mockito.when(rs.getString(1)).thenReturn(TestQuerys_ValueColumnName) ;

		final Statement statement = Mockito.mock(Statement.class) ;
		Mockito.when(statement.execute(TestQuerys_Statement)).thenReturn(true) ;
		Mockito.when(statement.getResultSet()).thenReturn(rs) ;

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.createStatement()).thenReturn(statement) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;

		final Query$Mapped<String, String> mapped = new Query() {}.new Query$Mapped<>(new Query() {}.new Query$Stated(connected, TestQuerys_Statement),
				new RowMapper_ToMap(new ColumnMapper_FromMySQL())) ;
		Assert.assertNotNull("Method 'getResultSetAsList()' did not return data.", mapped.getResultSetAsList()) ;
	}


	/**
	 * Verify that <code>getResultSetAsList</code> returns <code>null</code>.
	 *
	 * @throws SQLException
	 */
	@Test
	public void method_getResultSetAsList_Returns_Null() throws SQLException {

		final Statement statement = Mockito.mock(Statement.class) ;
		Mockito.when(statement.execute(TestQuerys_StatementWhereClause)).thenReturn(false) ;

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.createStatement()).thenReturn(statement) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;

		final Query$Mapped<String, String> mapped = new Query() {}.new Query$Mapped<>(
				new Query() {}.new Query$Stated(connected, TestQuerys_StatementWhereClause), new RowMapper_ToMap(new ColumnMapper_FromMySQL())) ;
		Assert.assertNull("Method 'getResultSetAsList()' did not return 'null'.", mapped.getResultSetAsList()) ;
	}


	/**
	 * Verify that <code>getResultSetAsList</code> throws <code>SQLException</code> when error on <code>createStetement()</code>.
	 *
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void method_getResultSetAsList_ThrowsOn_Connection$$CreateStatement() throws SQLException {

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.createStatement()).thenThrow(new SQLException()) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;

		new Query() {}.new Query$Mapped<>(new Query() {}.new Query$Stated(connected, TestQuerys_StatementWhereClause),
				new RowMapper_ToMap(new ColumnMapper_FromMySQL())).getResultSetAsList() ;
	}


	/**
	 * Verify that <code>getResultSetAsList</code> throws <code>SQLException</code> when error on <code>getResultsSet()</code>.
	 *
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void method_getResultSetAsList_ThrowsOn_Statement$$getResultSet() throws SQLException {

		final Statement statement = Mockito.mock(Statement.class) ;
		Mockito.when(statement.execute(TestQuerys_StatementWhereClause)).thenReturn(true) ;
		Mockito.when(statement.getResultSet()).thenThrow(new SQLException()) ;

		final Connection connection = Mockito.mock(Connection.class) ;
		Mockito.when(connection.createStatement()).thenReturn(statement) ;

		final Query$Connected connected = new Query() {}.new Query$Connected(connection) ;

		new Query() {}.new Query$Mapped<>(new Query() {}.new Query$Stated(connected, TestQuerys_StatementWhereClause),
				new RowMapper_ToMap(new ColumnMapper_FromMySQL())).getResultSetAsList() ;
	}
}
