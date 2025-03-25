

package xcom.utils4j.data.database ;


import java.sql.ResultSet ;
import java.sql.ResultSetMetaData ;
import java.sql.SQLException ;
import java.util.LinkedHashMap ;
import java.util.Map ;

import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.data.database.api.interfaces.iColumnMapper ;
import xcom.utils4j.data.database.api.interfaces.iRowMapper ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;


public class RowMapper_ToMap implements iRowMapper<String, String> {

	private static final Logger Logger = LoggerFactory.getLogger(RowMapper_ToMap.class) ;


	/**
	 *
	 */
	iColumnMapper<String> columnMapper ;

	@Log
	@Override
	public iColumnMapper<String> getColunmMapper() {
		return columnMapper ;
	}


	/**
	 *
	 */
	@Log
	public RowMapper_ToMap(final iColumnMapper<String> columnMapper) {
		this.columnMapper = columnMapper ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.database.iRowMapper#mapRow(java.sql.ResultSet)
	 */
	@Log
	@Override
	public Map<String, String> mapRow(final ResultSet rs) throws SQLException {

		final Map<String, String> results ;

		try {
			final ResultSetMetaData md = rs.getMetaData() ;
			results = new LinkedHashMap<>() ;

			for ( int c = 1; (c <= md.getColumnCount()); c++ )
				results.put(md.getColumnLabel(c), columnMapper.mapColumn(rs, c)) ;
		}
		catch ( final SQLException e ) {
			Logger.error("SQL error while getting ResultSet MetaData") ;
			throw e ;
		}

		return results ;
	}
}
