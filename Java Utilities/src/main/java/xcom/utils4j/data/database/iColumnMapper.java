

package xcom.utils4j.data.database ;


import java.sql.ResultSet ;
import java.sql.SQLException ;


/**
 * Provides an API for retrieving data from a particular column in the current row.
 *
 * @param <T>
 */
public interface iColumnMapper<T> {

	/**
	 * Retrieves the value of the designated column in the current row of this ResultSet object.
	 *
	 * @param rs
	 *            - <code>ResultSet</code> containing data returned from a query
	 * @param col
	 *            - Column number containing data to return (1 for first, 2 for second, etc.)
	 * @return Data in the specified column of the current row
	 * @throws <code>SQLException</code>
	 */
	public T mapColumn(ResultSet rs, int col) throws SQLException ;
}
