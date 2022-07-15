

package xcom.utils4j.data.columnar ;


import java.io.File ;
import java.io.FileInputStream ;
import java.io.IOException ;
import java.io.InputStream ;
import java.net.URL ;
import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.List ;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException ;
import org.apache.poi.ss.usermodel.Row ;
import org.apache.poi.ss.usermodel.Sheet ;
import org.apache.poi.ss.usermodel.Workbook ;
import org.apache.poi.ss.usermodel.WorkbookFactory ;

import xcom.utils4j.data.Excels ;
import xcom.utils4j.data.columnar.annotations.ColumnarDataReader ;
import xcom.utils4j.logging.annotations.Log ;


/**
 * Process an Excel workbook as a columnar data source. Only processes the first sheet in the workbook.
 *
 * @author c.j.gregory
 */
@ColumnarDataReader(sourceType = { "XLS", "XLSX" })
public class ColumnarDataReader_Excel extends xcom.utils4j.data.columnar.ColumnarDataReader {

	/**
	 * The current Excel row in the workbook.
	 */
	Row row ;

	/**
	 * The current ordinal row position in the workbook.
	 */
	int rowNum ;

	/**
	 * The number of rows in the workbook.
	 */
	int rowMax ;

	/**
	 * The index of the sheet to read from the workbook.
	 */
	int sheetIndex ;

	/**
	 * The Excel sheet to read from the workbook.
	 */
	Sheet sheet ;

	/**
	 * The stream consumed by <code>Workbook</code>.
	 */
	InputStream stream ;

	/**
	 * The Excel workbook.
	 */
	Workbook workbook ;


	/**
	 * Instantiate an Excel reader using a file as a data source defaulting to the 1st sheet in the workbook.
	 *
	 * @param file
	 * @param exactColumnNames
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Log
	public ColumnarDataReader_Excel(final File file, final boolean exactColumnNames) throws InvalidFormatException, IOException {
		this(file, 0, exactColumnNames) ;
	}


	/**
	 * Instantiate an Excel reader using a file as a data source specifying a sheet to process.
	 *
	 * @param file
	 * @param sheetIndex
	 * @param exactColumnNames
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Log
	public ColumnarDataReader_Excel(final File file, final int sheetIndex, final boolean exactColumnNames) throws InvalidFormatException, IOException {

		this.exactColumnNames = exactColumnNames ;
		this.sheetIndex = sheetIndex ;
		stream = new FileInputStream(file) ;
		initialize(stream) ;
	}


	/**
	 * Instantiate an Excel reader using a resource as a data source defaulting to the 1st sheet in the workbook.
	 *
	 * @param resource
	 * @param exactColumnNames
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Log
	public ColumnarDataReader_Excel(final URL resource, final boolean exactColumnNames) throws InvalidFormatException, IOException {
		this(resource, 0, exactColumnNames) ;
	}


	/**
	 * Instantiate an Excel reader using a resource as a data source specifying a sheet to process.
	 *
	 * @param resource
	 * @param sheetIndex
	 * @param exactColumnNames
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Log
	public ColumnarDataReader_Excel(final URL resource, final int sheetIndex, final boolean exactColumnNames) throws InvalidFormatException, IOException {

		this.exactColumnNames = exactColumnNames ;
		this.sheetIndex = sheetIndex ;
		stream = resource.openStream() ;
		initialize(resource.openStream()) ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#close()
	 */
	@Log
	@Override
	public void close() {

		workbook = null ;

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
	public String getColumn(final String columnName) {

		String results = null ;

		if ( columnIndexes.get(normalizedColumnName(columnName)) != null )
			results = Excels.getCellValueAsString(Excels.getCell(row, columnIndexes.get(normalizedColumnName(columnName)))) ;

		return results ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#getColumns()
	 */
	@Log
	@Override
	public List<String> getColumns() {
		return getColumns(columnNames) ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#getColumns(java.util.List)
	 */
	@Log
	@Override
	public List<String> getColumns(final List<String> columnNames) {

		final List<String> results = new ArrayList<String>() ;

		for ( final String columnName : columnNames ) {
			columnIndexes.get(normalizedColumnName(columnName)) ;
			String columnValue = null ;

			if ( columnIndexes.get(normalizedColumnName(columnName)) != null )
				columnValue = Excels.getCellValueAsString(Excels.getCell(row, columnIndexes.get(normalizedColumnName(columnName)))) ;

			results.add(columnValue) ;
		}

		return results ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#getColumns(java.lang.String[])
	 */
	@Log
	@Override
	public List<String> getColumns(final String... columnNames) throws IOException {
		return getColumns(Arrays.asList(columnNames)) ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see xcom.utils4j.data.columnar.iColumnarDataReader#next()
	 */
	@Log
	@Override
	public boolean next() {

		rowNum++ ;
		row = Excels.getRow(sheet, rowNum) ;

		return (rowNum <= rowMax) ;
	}


	/**
	 * Initialize instance state:
	 * <ul>
	 * <li>Create columnMames according to column name policy</li>
	 * <li>Create columnIndexes of column names to ordinal column position</li>
	 * </ul>
	 *
	 * @param stream
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Log
	void initialize(final InputStream stream) throws InvalidFormatException, IOException {

		workbook = WorkbookFactory.create(stream) ;
		columnNames = new ArrayList<String>() ;
		columnIndexes = new HashMap<String, Integer>() ;
		sheet = workbook.getSheetAt(sheetIndex) ;    // First sheet in notebook.

		final Row currentRow = sheet.getRow(0) ;    // Column names must be in first row.
		final int colMax = currentRow.getLastCellNum() ;

		for ( int colNum = 0; (colNum < colMax); colNum++ ) {
			String columnName = Excels.getCellValueAsString(currentRow.getCell(colNum)) ;

			if ( !exactColumnNames )
				columnName = normalizeColumnName(columnName) ;

			columnNames.add(columnName) ;
			columnIndexes.put(columnName, colNum) ;
		}

		rowNum = 0 ;
		rowMax = sheet.getLastRowNum() ;
	}


	/**
	 * @param columnName
	 * @return The column name according to the column name policy.
	 */
	@Log
	String normalizedColumnName(final String columnName) {
		return (exactColumnNames ? columnName : columnName.toLowerCase()) ;
	}
}
