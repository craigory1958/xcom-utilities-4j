

package xcom.utils4j.data.columnar ;


import java.io.File ;
import java.net.MalformedURLException ;
import java.net.URL ;
import java.util.Arrays ;
import java.util.Collection ;

import org.apache.commons.io.FileUtils ;
import org.junit.After ;
import org.junit.Assert ;
import org.junit.Before ;
import org.junit.BeforeClass ;
import org.junit.runners.Parameterized.Parameters ;


/**
 * Provides a wrapper to drive testing of columnar data factories.
 *
 * <br>
 * <br>
 * Test classes that extend <code>ColumnarDataReaderTestDriver</code> are invoked with implementation of <code>ColumnarDataReadable</code> as returned by the
 * method <code>data()</code>.
 */
public class ColumnarDataReaderTestDriver {

	/**
	 * Test data to compare against <code>TestColumnarData.csv</code>, <code>TestColumnarData.xls</code> and <code>TestColumnarData.xlsx</code> columnar data
	 * sources.
	 */

	//@formatter:off

    public static final String[][] columnarDataValues = {
        { "type", "simplecolumnname", "complexcolumn", "4" },
        { "Type", "SimpleColumnName", "Complex Column\nName", "4" },
        { "Formulas", "r2c2", "r2c3", "r2c4" },
        { "Character", "r3c2", "r3c3", "r3c4" },
        { "Numeric", "123", "456", "678" }
    } ;

    //@formatter:on


	/**
	 * Reference to the simple column names in <code>columnarDataValues</code>.
	 */
	public static final int SimpleColumnNameValues = 0 ;

	/**
	 * Reference to the exact column names in <code>columnarDataValues</code>.
	 */
	public static final int ExactColumnNameValues = 1 ;

	/**
	 * Reference to the number of actual data rows in <code>columnarDataValues</code>.
	 */
	public static final int NumberOfColumnarDataValues = columnarDataValues.length - 2 ;

	/**
	 * The data source of the current test.
	 */
	String dataSource ;

	/**
	 * The expect column data reader classname produced by the factory of the current test.
	 */
	String expectedClassname ;

	/**
	 * The data source type of the current test.
	 */
	String sourceType ;

	/**
	 * The factory used for each test.
	 */
	static ColumnarDataReaderFactory factory ;

	/**
	 * A file object created for each test.
	 */
	File file ;

	/**
	 * A resource object created for each test.
	 */
	URL resource ;


	/**
	 * @return The columnar data type, data source and expected reader classname for each test.
	 */
	@Parameters
	public static Collection<String[]> data() {

		//@formatter:off

        final String[][] results = {

                // (String sourceType, String dataSource, String expectedClassname)

                { "CSV", "/xcom/utils4j/data/Test_ColumnarDataReader.csv", ColumnarDataReader_CSV.class.getName() },
                { "XLS", "/xcom/utils4j/data/Test_ColumnarDataReader.xls", ColumnarDataReader_Excel.class.getName() },
                { "XLSX", "/xcom/utils4j/data/Test_ColumnarDataReader.xlsx", ColumnarDataReader_Excel.class.getName() },
        } ;

        //@formatter:on

		return Arrays.asList(results) ;
	}


	/**
	 * Setup the factory for each test class and verify that the number of readers implemented by the factory matches the number of readers defined in the
	 * method <code>data()</code>.
	 */
	@BeforeClass
	public static void setupBeforeClass() {

		factory = new ColumnarDataReaderFactory() ;

		final int actual = factory.foundReaders.size() ;
		final int expected = data().size() ;

		Assert.assertEquals("Incorrect number of factories to test;", expected, actual) ;
	}


	/**
	 * Create a file and resource object from the data source for each test.
	 *
	 * @throws MalformedURLException
	 */
	@Before
	public void setupBeforeEachTest() throws MalformedURLException {

		file = FileUtils.toFile(getClass().getResource(dataSource)) ;
		resource = getClass().getResource(dataSource) ;
	}


	@After
	public void teardownAfterEachTest() {

		file = null ;
		resource = null ;
	}
}
