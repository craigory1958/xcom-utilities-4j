

package xcom.utils4j.data.database.api.interfaces ;


import java.sql.ResultSet ;
import java.sql.SQLException ;
import java.util.Map ;


/**
 * Provides an API for retrieving data from current row.
 *
 * @param <T>
 */
public interface IRowMapper<K, V> {

	/**
	 * Retrieves the values of the current row of this <code>ResultSet</code> object.
	 *
	 * @param rs
	 *            - <code>ResultSet</code> containing data returned from a query
	 * @return Data in the specified row
	 * @throws <code>SQLException</code>
	 */
	public Map<K, V> mapRow(ResultSet rs) throws SQLException ;


	public IColumnMapper<?> getColunmMapper() ;
}
