

package xcom.utils4j.data.columnar ;


import java.io.File ;
import java.io.FileInputStream ;
import java.io.IOException ;
import java.io.InputStreamReader ;
import java.net.URL ;
import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.List ;

import com.csvreader.CsvReader ;

import xcom.utils4j.data.columnar.api.annotations.AColumnarDataReader ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;
import static xcom.utils4j.data.columnar.api.interfaces.IColumnarDataReader.ColumnNamePolicyTypes.* ;


/**
 * Process a CSV file as a columnar data source.
 *
 * @author c.j.gregory
 */
@Log
@AColumnarDataReader(sourceType = "CSV")
public class ColumnarDataReader_CSV extends xcom.utils4j.data.columnar.ColumnarDataReader {

	/**
	 * The stream consumed by <code>CsvReader</code>.
	 */
	InputStreamReader stream ;


	/**
	 * The CSV data source.
	 */
	CsvReader csv ;


	/**
	 * Instantiate a CSV reader using a file as a data source.
	 *
	 * @param file
	 * @param columnNamePolicy
	 * @throws IOException
	 */
	public ColumnarDataReader_CSV(final File file, final ColumnNamePolicyTypes columnNamePolicy) throws IOException {

		this.columnNamePolicy = columnNamePolicy ;
		stream = new InputStreamReader(new FileInputStream(file)) ;

		initialize(stream) ;
	}


	/**
	 * Instantiate a CSV reader using a resource as a data source.
	 *
	 * @param resource
	 * @param columnNamePolicy
	 * @throws IOException
	 */
	public ColumnarDataReader_CSV(final URL resource, final ColumnNamePolicyTypes columnNamePolicy) throws IOException {

		this.columnNamePolicy = columnNamePolicy ;
		stream = new InputStreamReader(resource.openStream()) ;

		initialize(stream) ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#close()
	 */
	@Override
	public void close() {

		csv.close() ; // CvsReader will close stream.

		try {
			stream.close() ;
		}
		catch ( final IOException ex ) {}
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#getColumn(java.lang.String)
	 */
	@Log
	@Override
	public String getColumn(final String columnName) throws IOException {
		return (csv.get(normalizedColumnName(columnName))) ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#getColumns()
	 */
	@Override
	public List<String> getColumns() throws IOException {
		return (getColumns(columnNames)) ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#getColumns(java.util.List)
	 */
	@Override
	public List<String> getColumns(final List<String> columnNames) throws IOException {

		final List<String> results = new ArrayList<String>() ;

		for ( final String columnName : columnNames )
			results.add(csv.get(normalizedColumnName(columnName))) ;

		return (results) ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#getColumns(java.lang.String[])
	 */
	@Override
	public List<String> getColumns(final String... columnNames) throws IOException {
		return (getColumns(Arrays.asList(columnNames))) ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#next()
	 */
	@Override
	public boolean next() throws IOException {
		return (csv.readRecord()) ;
	}


	/**
	 * Initialize instance state:
	 * <ul>
	 * <li>Create columnNames according to column name policy</li>
	 * <li>Create columnNameMappings of simple column names to exact column names only if not Exact Column Name policy.</li>
	 * </ul>
	 *
	 * @param stream
	 * @throws IOException
	 */
	void initialize(final InputStreamReader stream) throws IOException {

		csv = new CsvReader(stream) ;
		csv.readHeaders() ;

		if ( columnNamePolicy == ExactColumnNamePolicy )
			columnNames = Arrays.asList(csv.getHeaders()) ;

		else {
			columnNames = new ArrayList<String>() ;

			// Only create columnNameMappings if simple column name policy.
			columnNameMappings = new HashMap<String, String>() ;
			for ( String columnName : csv.getHeaders() ) {
				final String columnNameMapping = columnName ;
				columnName = normalizeColumnName(columnName) ;
				columnNames.add(columnName) ;
				columnNameMappings.put(columnName, columnNameMapping) ;
			}
		}
	}


	/**
	 * @param columnName
	 * @return The column name according to the column name policy.
	 */
	String normalizedColumnName(final String columnName) {
		return (columnNamePolicy == ExactColumnNamePolicy ? columnName : columnNameMappings.get(columnName.toLowerCase())) ;
	}
}
