

package xcom.utils4j.data.database ;


import java.sql.ResultSet ;
import java.sql.SQLException ;

import org.apache.commons.lang3.StringUtils ;

import xcom.utils4j.data.database.api.interfaces.iColumnMapper ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;


/**
 * Retrieve data from a particular column in the current row.
 */
public class ColumnMapper_FromMySQL implements iColumnMapper<String> {

	@Log
	public ColumnMapper_FromMySQL() {}


	/**
	 * @throws SQLException
	 * @see xcom.utils4j.database.iColumnMapper#mapColumn()
	 */
	@Log
	@Override
	public String mapColumn(final ResultSet rs, final int col) throws SQLException {

		String results ;

		try {
			results = rs.getString(col) ;
		}
		catch ( final SQLException e ) {

			switch ( rs.getMetaData().getColumnTypeName(col) ) {

				case "DATE":
					results = "0000-00-00" ;
					break ;

				default:
					results = "" ;
					break ;
			}
		}

		return StringUtils.trimToEmpty(results) ;
	}
}
