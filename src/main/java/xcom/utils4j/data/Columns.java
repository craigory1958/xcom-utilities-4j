

package xcom.utils4j.data ;


import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.logging.annotations.Log ;


/**
 * Describe columns in a columnar data source using enumerations.
 *
 * <br />
 * <br />
 * <span class="strong">Column attributes:</span> <code>[ "{Column Name}" [ , true|false ] [ , true|false ] ] | [ true|false ] [ , true|false ] ]</code> <br />
 * <br />
 * <ul>
 * <li>Mapped - Alternate column used is a CSV output file.</li>
 * <li>Ported - Column is to appear in a CSV output file.</li>
 * <li>NumericAsText - Pre-pend a single quote (') to the column contents on output.</li>
 * </ul>
 *
 * <br />
 *
 * <pre>
 * public enum Columns {
 *     A ("A1", true),        // Output column mapped to "A1", Ported.
 *     B ("B1", true, true),  // Output column mapped to "B1", Ported, NumericAsText.
 *     C ("C1"),              // Output column mapped to "C1".
 *     D (true),              // Ported.
 *     E (true, true),        // Ported, NumericAsText.
 *     F ;
 * </pre>
 *
 * <span class="strong">Usage:</span> <br />
 * <br />
 *
 * <pre>
 * public static class MyColumns extends CSVColumns {
 *
 * 	public MyColumns() {
 * 		super(MyColumns.Columns.values()) ;
 * 	}
 *
 * 	public enum Columns {
 * 		A("A1", true), B("B1", true, true), C, D(true), E(true, true), F;
 *
 * 		Columns(Object... objects) {
 * 			constructor(this, objects) ;
 * 		}
 * 	}
 * }
 * </pre>
 *
 *
 * @author craig.gregory
 *
 */
public class Columns {

	private static final Logger Logger = LoggerFactory.getLogger(Columns.class) ;


	/**
	 * Data store to hold <code>static</code> enum attributes.
	 *
	 * @author craig.gregory
	 *
	 */
	static class CSVColumnsData {
		List<String> columns ;

		Map<String, String> mapped ;

		List<String> numericAsText ;

		List<String> ported ;
	}


	static Map<String, CSVColumnsData> datums = new HashMap<String, CSVColumnsData>() ;


	/**
	 *
	 */
	List<String> columns ;


	@Log
	public List<String> getColumns() {
		return (columns) ;
	}


	/**
	 *
	 */
	Map<String, String> mapped ;


	@Log
	public Map<String, String> getMapped() {
		return (mapped) ;
	}


	@Log
	public boolean isMapped(final String column) {
		return (mapped.containsKey(column)) ;
	}


	@Log
	public String getMapped(final String column) {
		return (mapped.get(column)) ;
	}


	@Log
	public Columns setMapped(final String column, final String mapped) {

		if ( mapped == null )
			this.mapped.remove(column) ;
		else
			this.mapped.put(column, mapped) ;

		return (this) ;
	}


	/**
	 *
	 */
	List<String> numericAsText ;


	@Log
	public List<String> getNumericAsText() {
		return (numericAsText) ;
	}


	@Log
	public boolean isNumericAsText(final String column) {
		return (numericAsText.contains(column)) ;
	}


	@Log
	public Columns setNumericAsText(final String column, final boolean numericAsText) {

		if ( numericAsText )
			this.numericAsText.add(column) ;
		else
			this.numericAsText.remove(column) ;

		return (this) ;
	}


	/**
	 *
	 */
	List<String> ported ;


	@Log
	public List<String> getPorted() {
		return (ported) ;
	}


	@Log
	public boolean isPorted(final String column) {
		return (ported.contains(column)) ;
	}


	@Log
	public Columns setPorted(final String column, final boolean ported) {

		if ( ported )
			this.ported.add(column) ;
		else
			this.ported.remove(column) ;

		return (this) ;
	}


	/**
	 * @param columns
	 */
	@Log
	public Columns(final Columns columns) {

		this.columns = new ArrayList<String>(columns.columns) ;
		mapped = new HashMap<String, String>(columns.mapped) ;
		numericAsText = new ArrayList<String>(columns.numericAsText) ;
		ported = new ArrayList<String>(columns.ported) ;

		logColumns(this) ;
	}


	/**
	 * @param columns
	 */
	@Log
	public Columns(final String[] columns) {

		this.columns = new ArrayList<String>(Arrays.asList(columns)) ;
		ported = new ArrayList<String>(Arrays.asList(columns)) ;
		numericAsText = new ArrayList<String>() ;

		mapped = new HashMap<String, String>() ;
		for ( final String column : columns )
			mapped.put(column, column) ;

		logColumns(this) ;
	}


	/**
	 * @param columns
	 */
	@Log
	public Columns(final List<String> columns) {

		this.columns = new ArrayList<String>(columns) ;
		ported = new ArrayList<String>(columns) ;
		numericAsText = new ArrayList<String>() ;

		mapped = new HashMap<String, String>() ;
		for ( final String column : columns )
			mapped.put(column, column) ;

		logColumns(this) ;
	}


	/**
	 * @param columns
	 */
	@Log
	protected Columns(final Enum<?>[] columns) {

		final String columnClass = columns[0].getClass().getName() ;
		final CSVColumnsData datum = datums.get(columnClass) ;

		this.columns = new ArrayList<String>(datum.columns) ;
		mapped = new HashMap<String, String>(datum.mapped) ;
		numericAsText = new ArrayList<String>(datum.numericAsText) ;
		ported = new ArrayList<String>(datum.ported) ;

		logColumns(this) ;
	}


	/**
	 * @param column
	 * @param objects
	 */
	@Log
	public static void constructor(final Enum<?> column, final Object[] objects) {

		final String columnClass = column.getClass().getName() ;
		CSVColumnsData datum = datums.get(columnClass) ;

		if ( datum == null ) {
			datum = new CSVColumnsData() ;
			datum.columns = new ArrayList<String>() ;
			datum.mapped = new HashMap<String, String>() ;
			datum.numericAsText = new ArrayList<String>() ;
			datum.ported = new ArrayList<String>() ;
		}

		//

		datum.columns.add(column.name()) ;
		datum.mapped.put(column.name(), column.name()) ;

		if ( objects.length > 0 ) {
			int offset = 0 ;

			if ( objects[0] instanceof java.lang.String ) {
				offset = 1 ;
				datum.mapped.put(column.name(), ((String) objects[0])) ;
			}

			if ( (objects.length > (0 + offset)) && ((Boolean) objects[0 + offset]) )
				datum.ported.add(datum.mapped.get(column.name())) ;

			if ( (objects.length > (1 + offset)) && ((Boolean) objects[1 + offset]) )
				datum.numericAsText.add(column.name()) ;
		}

		//

		datums.put(columnClass, datum) ;

		Logger.trace("_columns: |{}|", datum.columns) ;
		Logger.trace("_mapped: |{}|", datum.mapped) ;
		Logger.trace("_numericAsText: |{}|", datum.numericAsText) ;
		Logger.trace("_ported: |{}|", datum.ported) ;
	}


	/**
	 * @param columns
	 */
	@Log
	void logColumns(final Columns columns) {
		Logger.trace("columns: |{}|", columns.columns) ;
		Logger.trace("mapped: |{}|", columns.mapped) ;
		Logger.trace("numericAsText: |{}|", columns.numericAsText) ;
		Logger.trace("ported: |{}|", columns.ported) ;
	}
}
