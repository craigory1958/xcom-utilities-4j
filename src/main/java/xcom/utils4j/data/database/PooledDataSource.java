

package xcom.utils4j.data.database ;


import java.io.PrintWriter ;
import java.sql.Connection ;
import java.sql.SQLException ;
import java.sql.SQLFeatureNotSupportedException ;

import javax.sql.DataSource ;

import org.apache.commons.dbcp.BasicDataSource ;

import xcom.utils4j.logging.annotations.Log ;
import xcom.utils4j.logging.annotations.NoLog ;


/**
 * Wrapper for a <code>DataSource</code>.
 */
public class PooledDataSource implements DataSource {

	/**
	 *
	 */
	DataSource dataSource ;


	/**
	 * Returns <code>BasicDataSource</code> wrapper using the given parameters.
	 *
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 */
	@Log
	public PooledDataSource(final String driver, final String url, final String username, @NoLog final String password) {

		dataSource = new BasicDataSource() ;

		final BasicDataSource ds = (BasicDataSource) dataSource ;
		ds.setDriverClassName(driver) ;
		ds.setUrl(url) ;
		ds.setUsername(username) ;
		ds.setPassword(password) ;

		ds.setDefaultAutoCommit(true) ;
	}


	@Log
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter() ;
	}


	@Log
	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout() ;
	}


	@Log
	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return dataSource.getParentLogger() ;
	}


	@Log
	@Override
	public void setLogWriter(final PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out) ;
	}


	@Log
	@Override
	public void setLoginTimeout(final int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds) ;
	}


	@Log
	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		return dataSource.isWrapperFor(iface) ;
	}


	@Log
	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		return dataSource.unwrap(iface) ;
	}


	@Log
	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection() ;
	}


	@Log
	@Override
	public Connection getConnection(final String username, @NoLog final String password) throws SQLException {
		return dataSource.getConnection(username, password) ;
	}
}
