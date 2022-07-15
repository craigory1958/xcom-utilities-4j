

package xcom.utils4j.data.columnar ;


import java.io.IOException ;
import java.util.List ;


/**
 * Process a data source as columnar data.
 *
 * <p>
 * Data is virtualized as rows of columns. Exact column names may be used, or columns may be referenced by simple column names. Simple column names are derived
 * from actual (or 'exact') column names by:
 * </p>
 *
 * <ul>
 * <li>truncating at the first \n character</li>
 * <li>removing all whitespace</li>
 * <li>converting to lowercase</li>
 * </ul>
 *
 * @author c.j.gregory
 *
 */
public interface iColumnarDataReader {

	/**
	 * Column name processing policies.
	 */
	public static final boolean SIMPLE_COLUMN_NAME_POLICY = false ;
	public static final boolean EXACT_COLUMN_NAME_POLICY = !SIMPLE_COLUMN_NAME_POLICY ;


	/**
	 * The column name processing policy.
	 */
	boolean isExactColumnNames() ;


	/**
	 * Close the data source.
	 *
	 * @throws IOException
	 */
	void close() throws IOException ;


	/**
	 * @return The column names constructed from the data source.
	 */
	List<String> getColumnNames() ;


	/**
	 * @param columnName
	 *            The requested column.
	 * @return The requested column form the current row. Returns <code>null</code> if the column is not defined in the data source.
	 * @throws IOException
	 */
	String getColumn(String columnName) throws IOException ;


	/**
	 * @return A list of all columns from the current row. The same as <code>getColumns(reader.getColumnNames())</code>.
	 * @throws IOException
	 */
	List<String> getColumns() throws IOException ;


	/**
	 * @param columnNames
	 *            The list of requested columns.
	 * @return A list of requested column s form the current row.
	 * @throws IOException
	 */
	List<String> getColumns(List<String> columnNames) throws IOException ;


	/**
	 * @param columnNames
	 *            The list of requested columns.
	 * @return A list of requested column s form the current row.
	 * @throws IOException
	 */
	List<String> getColumns(String... columnNames) throws IOException ;


	/**
	 * Position the data source at the next row.
	 *
	 * @return <code>true</code> if there are more rows in the data source.
	 * @throws IOException
	 */
	boolean next() throws IOException ;
}
