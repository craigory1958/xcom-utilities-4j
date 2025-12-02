

package xcom.utils4j.data.database ;


import static xcom.utils4j.data.database.TestQuerys.TestQuerys_KeyValue_15000 ;

import java.sql.ResultSet ;
import java.sql.ResultSetMetaData ;
import java.sql.SQLException ;

import org.junit.Assert ;
import org.junit.Test ;
import org.mockito.Mockito ;


public class _Test_ColumnMapper_FromMySQL {

	@Test
	public void method_mapColumn_Retures_ValidString() throws SQLException {

		final ResultSet rs = Mockito.mock(ResultSet.class) ;
		Mockito.when(rs.getString(Mockito.anyInt())).thenReturn(TestQuerys_KeyValue_15000) ;

		final ColumnMapper_FromMySQL columnMapper = new ColumnMapper_FromMySQL() ;
		Assert.assertEquals("Incorrect value for method 'mapColumn':", TestQuerys_KeyValue_15000, columnMapper.mapColumn(rs, 1)) ;
	}

	@Test
	public void method_mapColumn_Retures_ValidDate() throws SQLException {

		final ResultSetMetaData md = Mockito.mock(ResultSetMetaData.class) ;
		Mockito.when(md.getColumnTypeName(Mockito.anyInt())).thenReturn("DATE") ;

		final ResultSet rs = Mockito.mock(ResultSet.class) ;
		Mockito.when(rs.getString(Mockito.anyInt())).thenThrow(new SQLException()) ;
		Mockito.when(rs.getMetaData()).thenReturn(md) ;

		final ColumnMapper_FromMySQL columnMapper = new ColumnMapper_FromMySQL() ;
		Assert.assertEquals("Incorrect value for method 'mapColumn':", "0000-00-00", columnMapper.mapColumn(rs, 1)) ;
	}


	@Test
	public void method_mapRow_Returns_UnknownColumnTypeName() throws SQLException {

		final ResultSetMetaData md = Mockito.mock(ResultSetMetaData.class) ;
		Mockito.when(md.getColumnTypeName(Mockito.anyInt())).thenReturn("XXX") ;

		final ResultSet rs = Mockito.mock(ResultSet.class) ;
		Mockito.when(rs.getString(Mockito.anyInt())).thenThrow(new SQLException()) ;
		Mockito.when(rs.getMetaData()).thenReturn(md) ;

		final ColumnMapper_FromMySQL columnMapper = new ColumnMapper_FromMySQL() ;
		Assert.assertEquals("Incorrect value for method 'mapColumn':", "", columnMapper.mapColumn(rs, 1)) ;
	}


	@Test(expected = SQLException.class)
	public void method_mapRow_ThrowsOn_ResultSet$$getMetaData() throws SQLException {

		final ResultSet rs = Mockito.mock(ResultSet.class) ;
		Mockito.when(rs.getString(Mockito.anyInt())).thenThrow(new SQLException()) ;
		Mockito.when(rs.getMetaData()).thenThrow(new SQLException()) ;

		new ColumnMapper_FromMySQL().mapColumn(rs, 1) ;
	}


	@Test(expected = SQLException.class)
	public void method_mapRow_ThrowsOn_ResultSetMetaData$$getColumnTypeName() throws SQLException {

		final ResultSetMetaData md = Mockito.mock(ResultSetMetaData.class) ;
		Mockito.when(md.getColumnTypeName(Mockito.anyInt())).thenThrow(new SQLException()) ;

		final ResultSet rs = Mockito.mock(ResultSet.class) ;
		Mockito.when(rs.getString(Mockito.anyInt())).thenThrow(new SQLException()) ;
		Mockito.when(rs.getMetaData()).thenReturn(md) ;

		new ColumnMapper_FromMySQL().mapColumn(rs, 1) ;
	}
}
