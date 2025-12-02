

package xcom.utils4j.data.database ;


import java.io.PrintWriter ;
import java.sql.Connection ;
import java.sql.SQLException ;
import java.sql.SQLFeatureNotSupportedException ;
import java.util.Properties ;

import javax.sql.DataSource ;

import org.apache.commons.dbcp.BasicDataSource ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.logging.aspects.api.annotations.NoLog ;


/**
 * Wrapper for a <code>DataSource</code>.
 */
@Log
public class PooledDataSource implements DataSource {

	/**
	 *
	 */
	DataSource dataSource ;


	public PooledDataSource(final Properties props) {}

	/**
	 * Returns <code>BasicDataSource</code> wrapper using the given parameters.
	 *
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 */
	public PooledDataSource(final String driver, final String url, final String username, @NoLog final String password) {
		instantiatePooledDataSource(driver, url, username, password) ;
	}


	public void instantiatePooledDataSource(final String driver, final String url, final String username, @NoLog final String password) {

		dataSource = new BasicDataSource() ;

		final BasicDataSource ds = (BasicDataSource) dataSource ;
		ds.setDriverClassName(driver) ;
		ds.setUrl(url) ;
		ds.setUsername(username) ;
		ds.setPassword(password) ;

		ds.setDefaultAutoCommit(true) ;

	}


	//
	// DataSource implementation ...
	//

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter() ;
	}


	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout() ;
	}


	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return dataSource.getParentLogger() ;
	}


	@Override
	public void setLogWriter(final PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out) ;
	}


	@Override
	public void setLoginTimeout(final int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds) ;
	}


	@Log
	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		return dataSource.isWrapperFor(iface) ;
	}


	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		return dataSource.unwrap(iface) ;
	}


	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection() ;
	}


	@Override
	public Connection getConnection(final String username, @NoLog final String password) throws SQLException {
		return dataSource.getConnection(username, password) ;
	}
}
