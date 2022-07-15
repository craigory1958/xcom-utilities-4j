

package xcom.utils4j.data.database ;


import static xcom.utils4j.data.database.TestQuery.TestQuery_KeyColumnName ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_KeyValue_15000 ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_StatementResults ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_ValueColumnName ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_ValueValue_15000 ;

import java.sql.ResultSet ;
import java.sql.ResultSetMetaData ;
import java.sql.SQLException ;

import org.junit.Assert ;
import org.junit.Test ;
import org.mockito.Mockito ;


public class _Test_RowMapper_ToMap {

	/**
	 * Verify that constructor returns the expected type. (Bump code coverage to 100%.)
	 */
	@Test
	public void constructor_RowMapper_ToMap_Returns_CorrectType() {

		final RowMapper_ToMap rowMapper = new RowMapper_ToMap(null) ;
		Assert.assertTrue("Constructor did not return expected type.", rowMapper instanceof RowMapper_ToMap) ;
	}


	/**
	 * Verify that constructor initializes values.
	 */
	@Test
	public void constructor_Status_Initialization() {

		final ColumnMapper_FromMySQL expected = new ColumnMapper_FromMySQL() ;

		final RowMapper_ToMap rowMapper = new RowMapper_ToMap(expected) ;
		Assert.assertEquals("Incorrect value for member 'columnMapper':", expected, rowMapper.columnMapper) ;
	}


	/**
	 * Verify <code>getColunmMapper()</code> method.
	 */
	@Test
	public void method_getColunmMapper_Returns_ValidData() {

		final ColumnMapper_FromMySQL expected = new ColumnMapper_FromMySQL() ;

		final RowMapper_ToMap rowMapper = new RowMapper_ToMap(expected) ;
		Assert.assertEquals("Incorrect value for method 'getColunmMapper':", expected, rowMapper.getColunmMapper()) ;
	}


	@Test
	public void method_mapRow_Retures_ValidData() throws SQLException {

		final ResultSetMetaData md = Mockito.mock(ResultSetMetaData.class) ;
		Mockito.when(md.getColumnCount()).thenReturn(2) ;
		Mockito.when(md.getColumnLabel(Mockito.anyInt())).thenReturn(TestQuery_KeyColumnName).thenReturn(TestQuery_ValueColumnName) ;

		final ResultSet rs = Mockito.mock(ResultSet.class) ;
		Mockito.when(rs.getMetaData()).thenReturn(md) ;
		Mockito.when(rs.getString(Mockito.anyInt())).thenReturn(TestQuery_KeyValue_15000).thenReturn(TestQuery_ValueValue_15000) ;

		final RowMapper_ToMap rowMapper = new RowMapper_ToMap(new ColumnMapper_FromMySQL()) ;
		Assert.assertEquals("Incorrect value for method 'mapRow':", TestQuery_StatementResults.get(0), rowMapper.mapRow(rs)) ;
	}


	@Test(expected = SQLException.class)
	public void method_mapRow_ThrowsOn_ResultSet$$getMetaData() throws SQLException {

		final ResultSet rs = Mockito.mock(ResultSet.class) ;
		Mockito.when(rs.getMetaData()).thenThrow(new SQLException()) ;

		new RowMapper_ToMap(new ColumnMapper_FromMySQL()).mapRow(rs) ;
	}
}
