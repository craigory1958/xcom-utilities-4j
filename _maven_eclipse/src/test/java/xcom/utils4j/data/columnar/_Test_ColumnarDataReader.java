

package xcom.utils4j.data.columnar ;


import static xcom.utils4j.data.columnar.api.interfaces.IColumnarDataReader.ColumnNamePolicyTypes.ExactColumnNamePolicy ;
import static xcom.utils4j.data.columnar.api.interfaces.IColumnarDataReader.ColumnNamePolicyTypes.SimpleColumnNamePolicy ;

import java.io.IOException ;
import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.List ;

import org.junit.Assert ;
import org.junit.Test ;
import org.junit.runner.RunWith ;
import org.junit.runners.Parameterized ;

import xcom.utils4j.data.columnar.api.interfaces.IColumnarDataReader ;


/**
 * Verify basic functionality of <code>ColumnarDataReadable</code>.
 *
 * <br />
 * <br />
 * <code>ColumnarDataTestDriver</code> will drive test with each implementation from a set of readers.
 *
 */
@RunWith(Parameterized.class)
public class _Test_ColumnarDataReader extends ColumnarDataReaderTestDriver {

	/**
	 * Instantiate the test with data provided by <code>ColumnarDataTestDriver</code>.
	 *
	 * @param sourceType
	 * @param dataSource
	 * @param expectedClassname
	 */
	public _Test_ColumnarDataReader(final String sourceType, final String dataSource, final String expectedClassname) {

		this.sourceType = sourceType ;
		this.dataSource = dataSource ;
		this.expectedClassname = expectedClassname ;
	}


	/**
	 * Verify that the reader reads the correct number of rows from the data source.
	 *
	 * @throws IOException
	 */
	@Test
	public void numberOfRows() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType) ;

		int actual = 0 ;
		while ( reader.next() )
			actual++ ;

		reader.close() ;

		final int expected = NumberOfColumnarDataValues ;

		Assert.assertEquals("Inncorrect number of rows read;", expected, actual) ;
	}


	/**
	 * Verify that the reader returns the correct simple column names.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnNamesUsingSimpleColumnNames_Pass() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, SimpleColumnNamePolicy) ;
		final List<String> columnNames = reader.getColumnNames() ;
		reader.close() ;

		final String[] actual = columnNames.toArray(new String[columnNames.size()]) ;
		final String[] expected = columnarDataValues[SimpleColumnNameValues] ;

		Assert.assertArrayEquals("Incorrect column names returned;", expected, actual) ;
	}


	/**
	 * Perform a negative test verifying the reader distinguishes between simple and exact column names.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnNamesUsingSimpleColumnNames_Fail() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, SimpleColumnNamePolicy) ;
		final List<String> columnNames = reader.getColumnNames() ;
		reader.close() ;

		final String actual = Arrays.toString(columnNames.toArray(new String[columnNames.size()])) ;
		final String expected = Arrays.toString(columnarDataValues[ExactColumnNameValues]) ; // Substitute exact column names to fail test.

		Assert.assertNotSame("Incorrect column names returned;", expected, actual) ;
	}


	/**
	 * Verify that the reader returns the correct exact column names.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnNamesUsingExactColumnNames() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, ExactColumnNamePolicy) ;
		final List<String> columnNames = reader.getColumnNames() ;
		reader.close() ;

		final String[] actual = columnNames.toArray(new String[columnNames.size()]) ;
		final String[] expected = columnarDataValues[ExactColumnNameValues] ;

		Assert.assertArrayEquals("Incorrect column names returned;", expected, actual) ;
	}


	/**
	 * Verify that the reader returns the correct column values using simple column names. (Also tests that column names in uppercase are processed correctly.)
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnByNameUsingSimpleColumnNames_Pass() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, SimpleColumnNamePolicy) ;

		int r = 2 ; // Offset to bypass simple and complex column names in the test data.
		while ( reader.next() ) {
			final List<String> columnValues = new ArrayList<>() ;

			for ( final String columnName : columnarDataValues[SimpleColumnNameValues] )
				columnValues.add(reader.getColumn(columnName.toUpperCase())) ; // Test uppercase compatibility.

			final String actual = Arrays.toString(columnValues.toArray(new String[columnValues.size()])) ;
			final String expected = Arrays.toString(columnarDataValues[r]) ;
			
			Assert.assertEquals("Inncorrect values read;", expected, actual) ;

			r++ ;
		}

		reader.close() ;
	}


	/**
	 * Perform a negative test verifying the reader distinguishes between simple and exact column names.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnByNameUsingSimpleColumnNames_Fail() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, SimpleColumnNamePolicy) ;

		int r = 2 ; // Offset to bypass simple and complex column names in the test data.
		while ( reader.next() ) {
			final List<String> columnValues = new ArrayList<>() ;

			for ( final String columnName : columnarDataValues[ExactColumnNameValues] )
				columnValues.add(reader.getColumn(columnName)) ;    // Substitute exact column names to fail test.

			final String actual = Arrays.toString(columnValues.toArray(new String[columnValues.size()])) ;
			final String expected = Arrays.toString(columnarDataValues[r]) ;

			Assert.assertNotSame("Inncorrect values read;", expected, actual) ;

			r++ ;
		}

		reader.close() ;
	}


	/**
	 * Verify that the reader returns the correct column values using exact column names.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnByNameUsingExactColumnNames() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, ExactColumnNamePolicy) ;

		int r = 2 ; // Offset to bypass simple and complex column names in the test data.
		while ( reader.next() ) {
			final List<String> columnValues = new ArrayList<>() ;

			for ( final String columnName : columnarDataValues[ExactColumnNameValues] )
				columnValues.add(reader.getColumn(columnName)) ;

			final String actual = Arrays.toString(columnValues.toArray(new String[columnValues.size()])) ;
			final String expected = Arrays.toString(columnarDataValues[r]) ;

			Assert.assertEquals("Inncorrect values read;", expected, actual) ;

			r++ ;
		}

		reader.close() ;
	}


	/**
	 * Verify that the reader returns the correct column values when requesting entire row.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumns() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType) ;

		int r = 2 ; // Offset to bypass simple and complex column names in the test data.
		while ( reader.next() ) {
			final List<String> columnValues = reader.getColumns() ;

			final String actual = Arrays.toString(columnValues.toArray(new String[columnValues.size()])) ;
			final String expected = Arrays.toString(columnarDataValues[r]) ;

			Assert.assertEquals("Inncorrect values read;", expected, actual) ;

			r++ ;
		}

		reader.close() ;
	}


	/**
	 * Verify that the reader returns the correct column values when specific column using simple column names.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnsByNamePassedAsArrayUsingSimpleColumnNames_Pass() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, SimpleColumnNamePolicy) ;

		int r = 2 ; // Offset to bypass simple and complex column names in the test data.
		while ( reader.next() ) {
			final List<String> columnValues = reader.getColumns(columnarDataValues[SimpleColumnNameValues]) ;

			final String actual = Arrays.toString(columnValues.toArray(new String[columnValues.size()])) ;
			final String expected = Arrays.toString(columnarDataValues[r]) ;

			Assert.assertEquals("Inncorrect values read;", expected, actual) ;

			r++ ;
		}

		reader.close() ;
	}


	/**
	 * Perform a negative test verifying the reader distinguishes between simple and exact column names.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnsByNamePassedAsArrayUsingSimpleColumnNames_Fail() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, SimpleColumnNamePolicy) ;

		int r = 2 ; // Offset to bypass simple and complex column names in the test data.
		while ( reader.next() ) {
			final List<String> columnValues = reader.getColumns(columnarDataValues[ExactColumnNameValues]) ;

			final String actual = Arrays.toString(columnValues.toArray(new String[columnValues.size()])) ;
			final String expected = Arrays.toString(columnarDataValues[r]) ;

			Assert.assertNotSame("Inncorrect values read;", expected, actual) ;

			r++ ;
		}

		reader.close() ;
	}


	/**
	 * Verify that the reader returns the correct column values when specific column using exact column names.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnsByNamePassedAsArrayUsingExactColumnNames() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, ExactColumnNamePolicy) ;

		int r = 2 ; // Offset to bypass simple and complex column names in the test data.
		while ( reader.next() ) {
			final List<String> columnValues = reader.getColumns(columnarDataValues[ExactColumnNameValues]) ;

			final String actual = Arrays.toString(columnValues.toArray(new String[columnValues.size()])) ;
			final String expected = Arrays.toString(columnarDataValues[r]) ;

			Assert.assertEquals("Inncorrect values read;", expected, actual) ;

			r++ ;
		}

		reader.close() ;
	}


	/**
	 * Verify that the reader returns the correct column values when specific column using simple column names.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnsByNamePassedAsListUsingSimpleColumnNames_Pass() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, SimpleColumnNamePolicy) ;

		int r = 2 ; // Offset to bypass simple and complex column names in the test data.
		while ( reader.next() ) {
			final List<String> columnValues = reader.getColumns(Arrays.asList(columnarDataValues[SimpleColumnNameValues])) ;

			final String actual = Arrays.toString(columnValues.toArray(new String[columnValues.size()])) ;
			final String expected = Arrays.toString(columnarDataValues[r]) ;

			Assert.assertEquals("Inncorrect values read;", expected, actual) ;

			r++ ;
		}

		reader.close() ;
	}


	/**
	 * Perform a negative test verifying the reader distinguishes between simple and exact column names.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnsByNamePassedAsListUsingSimpleColumnNames_Fail() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, SimpleColumnNamePolicy) ;

		int r = 2 ; // Offset to bypass simple and complex column names in the test data.
		while ( reader.next() ) {
			final List<String> columnValues = reader.getColumns(Arrays.asList(columnarDataValues[ExactColumnNameValues])) ;

			final String actual = Arrays.toString(columnValues.toArray(new String[columnValues.size()])) ;
			final String expected = Arrays.toString(columnarDataValues[r]) ;

			Assert.assertNotSame("Inncorrect values read;", expected, actual) ;

			r++ ;
		}

		reader.close() ;
	}


	/**
	 * Verify that the reader returns the correct column values when specific column using exact column names.
	 *
	 * @throws IOException
	 */
	@Test
	public void getColumnsByNamePassedAsListUsingExactColumnNames() throws IOException {

		final IColumnarDataReader reader = factory.create(file, sourceType, ExactColumnNamePolicy) ;

		int r = 2 ; // Offset to bypass simple and complex column names in the test data.
		while ( reader.next() ) {
			final List<String> columnValues = reader.getColumns(Arrays.asList(columnarDataValues[ExactColumnNameValues])) ;

			final String actual = Arrays.toString(columnValues.toArray(new String[columnValues.size()])) ;
			final String expected = Arrays.toString(columnarDataValues[r]) ;

			Assert.assertEquals("Inncorrect values read;", expected, actual) ;

			r++ ;
		}

		reader.close() ;
	}
}
