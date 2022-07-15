

package xcom.utils4j.data ;


import java.io.IOException ;
import java.io.InputStream ;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException ;
import org.apache.poi.ss.usermodel.Row ;
import org.apache.poi.ss.usermodel.Sheet ;
import org.apache.poi.ss.usermodel.Workbook ;
import org.apache.poi.ss.usermodel.WorkbookFactory ;
import org.junit.Assert ;
import org.junit.Test ;


/**
 * Misc. tests to complete code coverage of <code>Excels</code>.
 */
public class _Test_Excels {

	@Test
	public void constructor() {

		final Object actual = new Excels() ;

		Assert.assertTrue("Constructor did not return expected type.", actual instanceof Excels) ;
	}


	/**
	 * Verify <code>getCellValueAsString_WithNull()</code> method.
	 */
	@Test
	public void getCellValueAsString_WithNull() {

		final String actual = Excels.getCellValueAsString(null) ;
		final String expected = "" ;

		Assert.assertEquals("Incorrect value for 'null' cell;", expected, actual) ;
	}


	/**
	 * Verify <code>getCell()</code> validates column parameter.
	 *
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getCell() throws InvalidFormatException, IOException {

		try ( InputStream stream = getClass().getResourceAsStream("Test_Excels.xlsx") ) {

			final Workbook workbook = WorkbookFactory.create(stream) ;
			final Sheet sheet = workbook.getSheetAt(0) ;

			final Row r = Excels.getRow(sheet, 1) ;
			Excels.getCell(r, -1) ;

			Assert.fail("Expected exception not thrown: expected " + IllegalArgumentException.class.getSimpleName()) ;
		}
	}
}
