

package xcom.utils4j.data ;


import java.io.IOException ;
import java.io.InputStream ;
import java.util.Arrays ;
import java.util.Collection ;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException ;
import org.apache.poi.ss.usermodel.Cell ;
import org.apache.poi.ss.usermodel.Row ;
import org.apache.poi.ss.usermodel.Sheet ;
import org.apache.poi.ss.usermodel.Workbook ;
import org.apache.poi.ss.usermodel.WorkbookFactory ;
import org.junit.After ;
import org.junit.Assert ;
import org.junit.Before ;
import org.junit.Test ;
import org.junit.runner.RunWith ;
import org.junit.runners.Parameterized ;

import xcom.utils4j.JUnits ;


/**
 * Verify the <code>getXxx()</code> methods. Data is provided using a JUnit <code>Parameterized</code> test and the <code>data()</code> method.
 */
@RunWith(Parameterized.class)
public class _Test_Excels$getXxx {

	@Parameterized.Parameters
	public static Collection<Object[]> data() {

		//@formatter:off
        final Object[][] results = {

                // row, column, expected

                { 1, 1, "Perfect" },    // String
                { 2, 1, "123" },        // Integer
                { 3, 1, "123.4" },      // Real
                { 4, 1, "2013-12-02" }, // Date
                { 5, 1, "true" },       // Boolean

                // Formulas

                { 8, 1, "Perfect" },    // String
                { 9, 1, "123" },        // Integer
                { 10, 1, "123.4" },     // Real
                { 11, 1, "2013-12-02" },     // Date
                { 12, 1, "true" },      // Boolean
                { 13, 1, "" },          // Error (divide by 0)


                // Misc ...

                { 99, 99, "" },    // Create cell outside of current defined sheet range.
                { -1, 1, new IllegalArgumentException() },
        } ;
        //@formatter:on

		return (Arrays.asList(results)) ;
	}


	/**
	 * The row of the current test.
	 */
	Integer r ;

	/**
	 * The column of the current test.
	 */
	Integer c ;

	/**
	 * The expected value of the test.
	 */
	String expectedValue ;

	/**
	 * The expected exception of the test.
	 */
	Exception expectedException ;

	/**
	 * The sheet of the current test.
	 */
	Sheet sheet ;

	/**
	 *
	 */
	InputStream stream ;

	/**
	 * The workbook of the current test.
	 */
	Workbook workbook ;


	/**
	 * Instantiate the current test with provided data.
	 *
	 * @param r
	 *            - The row of the test.
	 * @param c
	 *            - The column of the test.
	 * @param expected
	 *            - The expected value of the test.
	 */
	public _Test_Excels$getXxx(final Object r, final Object c, final Object expected) {

		this.r = (Integer) r ;
		this.c = (Integer) c ;

		expectedValue = null ;
		expectedException = null ;

		if ( expected instanceof String )
			expectedValue = (String) expected ;

		else if ( expected instanceof Exception )
			expectedException = (Exception) expected ;
	}


	/**
	 * Opens the Excel spreadsheet used for testing.
	 *
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Before
	public void setupBeforeEachTest() throws InvalidFormatException, IOException {

		stream = getClass().getResourceAsStream("Test_Excels.xlsx") ;
		workbook = WorkbookFactory.create(stream) ;
		sheet = workbook.getSheetAt(0) ;
	}


	@After
	public void teardownAfterEachTest() throws IOException {
		stream.close() ;
	}


	/**
	 * Verify the <code>getRow()<code> method.
	 */
	@Test
	public void getRow() {

		try {
			final Row actual = Excels.getRow(sheet, r) ;

			Assert.assertNotNull("getRow() returned NULL row;", actual) ;

			if ( expectedException != null )
				Assert.fail("Expected exception not thrown: expected " + expectedException.getClass().getSimpleName()) ;
		}
		catch ( final Exception e ) {
			JUnits.assertExceptionEquals("Incorrect exception thrown;", expectedException, e) ;
		}
	}


	/**
	 * Verify the <code>getCell()<code> method.
	 */
	@Test
	public void getCell() {

		try {
			final Row row = Excels.getRow(sheet, r) ;
			final Cell actual = Excels.getCell(row, c) ;

			Assert.assertNotNull("getCell() returned NULL cell;", actual) ;

			if ( expectedException != null )
				Assert.fail("Expected exception not thrown: expected " + expectedException.getClass().getSimpleName()) ;
		}
		catch ( final Exception e ) {
			JUnits.assertExceptionEquals("Incorrect exception thrown;", expectedException, e) ;
		}
	}


	/**
	 * Verify the <code>getCellValueAsString()<code> method.
	 */
	@Test
	public void getCellValueAsString() {

		try {
			final Row row = Excels.getRow(sheet, r) ;
			final Cell cell = Excels.getCell(row, c) ;
			final String actual = Excels.getCellValueAsString(cell) ;
			final String expected = expectedValue ;

			Assert.assertEquals("Incorrect cell value at " + c + ";", expected, actual) ;

			if ( expectedException != null )
				Assert.fail("Expected exception not thrown: expected " + expectedException.getClass().getSimpleName()) ;
		}
		catch ( final Exception e ) {
			JUnits.assertExceptionEquals("Incorrect exception thrown;", expectedException, e) ;
		}
	}
}
