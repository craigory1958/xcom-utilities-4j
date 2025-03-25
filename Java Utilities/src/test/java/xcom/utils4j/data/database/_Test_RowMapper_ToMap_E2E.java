

package xcom.utils4j.data.database ;


import static xcom.utils4j.data.database.TestQuery.TestQuery_Statement ;
import static xcom.utils4j.data.database.TestQuery.TestQuery_StatementResults ;

import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.ResultSet ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.util.ArrayList ;
import java.util.Map ;

import org.junit.Assert ;
import org.junit.BeforeClass ;
import org.junit.Test ;


public class _Test_RowMapper_ToMap_E2E {

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
	public void method_mapRow_Returns_ValidData() throws SQLException {

		ArrayList<Map<String, String>> actual = null ;
		final RowMapper_ToMap rowMapper = new RowMapper_ToMap(new ColumnMapper_FromMySQL()) ;

		try ( Statement sqlStatement = DriverManager.getConnection(HSQL_DB_URL, HSQL_DB_USERNAME, HSQL_DB_PASSWORD).createStatement() ) {

			if ( sqlStatement.execute(TestQuery_Statement) )
				try ( final ResultSet rs = sqlStatement.getResultSet(); ) {
					actual = new ArrayList<>() ;

					while ( rs.next() )
						actual.add(rowMapper.mapRow(rs)) ;
				}
		}

		Assert.assertEquals("Incorrect value return from method 'mapRow()':", TestQuery_StatementResults, actual) ;
	}
}
