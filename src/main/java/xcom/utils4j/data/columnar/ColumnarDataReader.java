

package xcom.utils4j.data.columnar ;


import java.util.List ;
import java.util.Map ;

import org.apache.commons.lang3.StringUtils ;

import xcom.utils4j.logging.annotations.Log ;


/**
 * Abstracts reading various types of columnar data sources.
 *
 * @author c.j.gregory
 */
public abstract class ColumnarDataReader implements iColumnarDataReader {

	/**
	 * The exact column names.
	 */
	List<String> columnNames ;


	/**
	 * The mappings of column names to their ordinal index. Not initialized for CSV data sources.
	 */
	Map<String, Integer> columnIndexes ;


	/**
	 * The mappings of simple column names to exact column names. Only initialized when using simple column name policy.
	 */
	Map<String, String> columnNameMappings ;


	/**
	 * The column name processing policy.
	 */
	boolean exactColumnNames ;


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#getColumnNames()
	 */
	@Log
	@Override
	public List<String> getColumnNames() {
		return (columnNames) ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#isExactColumnNames()
	 */
	@Log
	@Override
	public boolean isExactColumnNames() {
		return (exactColumnNames) ;
	}


	/**
	 * Convert a complex column name to a simple column name:
	 * <ul>
	 * <li>truncating at first \n character</li>
	 * <li>removing all spaces</li>
	 * <li>converting to lowercase</li>
	 * </ul>
	 *
	 * @param columnName
	 * @return The columnName processed according to the simple column name policy.
	 */
	@Log
	String normalizeColumnName(final String columnName) {

		String results = columnName ;

		results += "\n" ;
		results = results.substring(0, results.indexOf('\n')) ;
		results = StringUtils.deleteWhitespace(results) ;
		results = results.toLowerCase() ;

		return results ;
	}
}
