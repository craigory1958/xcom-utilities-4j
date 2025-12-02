

package xcom.utils4j.data ;


import static xcom.utils4j.data.Excels.* ;
import static xcom.utils4j.data.Excels.ABSOLUTE_REFERENCE ;
import static xcom.utils4j.data.Excels.ABSOLUTE_ROW_REFERENCE ;
import static xcom.utils4j.data.Excels.RELATIVE_COLUMN_REFERENCE ;
import static xcom.utils4j.data.Excels.RELATIVE_REFERENCE ;
import static xcom.utils4j.data.Excels.RELATIVE_ROW_REFERENCE ;

import java.util.Arrays ;
import java.util.Collection ;

import org.junit.Assert ;
import org.junit.Test ;
import org.junit.runner.RunWith ;
import org.junit.runners.Parameterized ;

import xcom.utils4j.JUnits ;


/**
 * Verify the <code>cellRef()</code> methods. Data is provided using a JUnit <code>Parameterized</code> test and the <code>data()</code> method.
 */
@RunWith(Parameterized.class)
public class _Test_Excels$cellRef {

	@Parameterized.Parameters
	public static Collection<Object[][]> data() {

		//@formatter:off
        final Object[][][] results = {

                // row, column, expected [, rowRefType ] [, columnRefType ]

                { { 0, 0, "A1" }, {} },
                { { 0, 0, "A1" }, { RELATIVE_REFERENCE } },
                { { 0, 0, "$A$1" }, { ABSOLUTE_REFERENCE } },
                { { 0, 0, "A1" }, { RELATIVE_ROW_REFERENCE, RELATIVE_COLUMN_REFERENCE } },
                { { 0, 0, "$A1" }, { RELATIVE_ROW_REFERENCE, ABSOLUTE_COLUMN_REFERENCE } },
                { { 0, 0, "A$1" }, { ABSOLUTE_ROW_REFERENCE, RELATIVE_COLUMN_REFERENCE } },
                { { 0, 0, "$A$1" }, { ABSOLUTE_ROW_REFERENCE, ABSOLUTE_COLUMN_REFERENCE } },

                { { 0, MaxSupportedColumns, "AZZ1" }, {} },


                // Validate method arguments ...

                { { -1, 0, new IllegalArgumentException() }, {} },
                { { -1, 0, new IllegalArgumentException() }, { RELATIVE_REFERENCE } },
                { { -1, 0, new IllegalArgumentException() }, { RELATIVE_REFERENCE, RELATIVE_REFERENCE } },

                { { 0, -1, new IllegalArgumentException() }, {} },
                { { 0, -1, new IllegalArgumentException() }, { RELATIVE_REFERENCE } },
                { { 0, -1, new IllegalArgumentException() }, { RELATIVE_REFERENCE, RELATIVE_REFERENCE } },

                { { 0, MaxSupportedColumns +1, new IllegalArgumentException() }, {} },
                { { 0, MaxSupportedColumns +1, new IllegalArgumentException() }, { RELATIVE_REFERENCE } },
                { { 0, MaxSupportedColumns +1, new IllegalArgumentException() }, { RELATIVE_REFERENCE, RELATIVE_REFERENCE } },
        } ;
        //@formatter:on

		return (Arrays.asList(results)) ;
	}


	/**
	 * The column of the current test.
	 */
	Integer column ;

	/**
	 * The expected value of the test.
	 */
	String expectedCellRef ;

	/**
	 * The expected exception of the test.
	 */
	Exception expectedException ;

	/**
	 * The refTypes of the current test. The length determines the proper overloaded method.
	 */
	Boolean[] refTypes ;

	/**
	 * The row of the current test.
	 */
	Integer row ;


	/**
	 * Instantiate the current test with provided data.
	 *
	 * @param parms
	 *            - The row, column and expected values of the test
	 * @param refTypes
	 *            - The optional refTypes of the test
	 */
	public _Test_Excels$cellRef(final Object[] parms, final Object[] refTypes) {

		row = (Integer) parms[0] ;
		column = (Integer) parms[1] ;

		expectedCellRef = null ;
		expectedException = null ;

		if ( parms[2] instanceof String )
			expectedCellRef = (String) parms[2] ;
		else
			expectedException = (Exception) parms[2] ;

		switch ( refTypes.length ) {

			case 0: {
				this.refTypes = new Boolean[0] ;
				break ;
			}

			case 1: {
				this.refTypes = new Boolean[1] ;
				this.refTypes[0] = (Boolean) refTypes[0] ;
				break ;
			}

			case 2: {
				this.refTypes = new Boolean[2] ;
				this.refTypes[0] = (Boolean) refTypes[0] ;
				this.refTypes[1] = (Boolean) refTypes[1] ;
				break ;
			}

			default: {
				break ;
			}
		}
	}


	/**
	 * Verify the overloaded <code>CellRef()</code> methods.
	 */
	@Test
	public void cellRef() {

		try {
			final String expected = expectedCellRef ;
			String actual = null ;

			// Determine which method signature to use based on the number of refTypes ...

			switch ( refTypes.length ) {
				case 0:
					actual = Excels.cellRef(row, column) ;
					break ;

				case 1:
					actual = Excels.cellRef(row, column, refTypes[0]) ;
					break ;

				case 2:
					actual = Excels.cellRef(row, column, refTypes[0], refTypes[1]) ;
					break ;

				default:
					break ;
			}

			Assert.assertEquals("Incorrect cell reference;", expected, actual) ;
		}

		catch ( final Exception e ) {
			JUnits.assertExceptionEquals("Incorrect exception thrown;", expectedException, e) ;
		}
	}
}
