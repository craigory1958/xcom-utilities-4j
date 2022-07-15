

package xcom.utils4j ;


import java.util.Arrays ;
import java.util.Collection ;

import org.junit.Assert ;
import org.junit.Test ;
import org.junit.runner.RunWith ;
import org.junit.runners.Parameterized ;


/**
 * Verify the <code>buildImplementationClassnameFromInterfaceClassname()</code> method. Data is provided using a JUnit <code>Parameterized</code> test and the
 * <code>data()</code> method.
 */
@RunWith(Parameterized.class)
public class _Test_Factories$buildImplementationClassnameFromInterfaceClassname {

	@Parameterized.Parameters
	public static Collection<Object[]> data() {

		//@formatter:off

        final Object[][] results = {

                // (Object interfaceClassname, Object implementation, Object expected)

                { "com.x.iReader", "CSV", "com.x.Reader_CSV" },
                { "com.iReader", "CSV", "com.Reader_CSV" },
                { "iReader", "CSV", "Reader_CSV" },
                { "com.x.iReader", "", "com.x.Reader" },
                { "com.iReader", "", "com.Reader" },
                { "iReader", "", "Reader" },
                { null, "", new IllegalArgumentException() },
                { "iReader", null, "Reader" },
                { "", "", new IllegalArgumentException() },
        } ;

        //@formatter:on

		return (Arrays.asList(results)) ;
	}


	/**
	 * The expected constructed classname of the current test.
	 */
	String expectedClassname ;

	/**
	 * The expected exception of the current test.
	 */
	Exception expectedException ;

	/**
	 * The Factory Prefix of the current test.
	 */
	String implementation ;

	/**
	 * The Interface Class name of the current test.
	 */
	String interfaceClassname ;


	/**
	 * Instantiate the current test with provided data.
	 *
	 * @param interfaceClassname
	 * @param implementation
	 * @param expected
	 */
	public _Test_Factories$buildImplementationClassnameFromInterfaceClassname(final Object interfaceClassname, final Object implementation,
			final Object expected) {
		this.interfaceClassname = (String) interfaceClassname ;
		this.implementation = (String) implementation ;

		expectedClassname = null ;
		expectedException = null ;

		if ( expected instanceof String )
			expectedClassname = (String) expected ;
		else if ( expected instanceof Exception )
			expectedException = (Exception) expected ;
	}


	/**
	 * Verify constructed classname.
	 */
	@Test
	public void buildImplementationClassnameFromInterfaceClassname() {
		try {
			final String actual = Factories.buildImplementationClassnameFromInterfaceClassname(interfaceClassname, implementation) ;

			if ( expectedException != null )
				Assert.fail("Expected exception not thrown; expected: <" + expectedException + ">") ;

			Assert.assertEquals("Incorrect classname;", expectedClassname, actual) ;
		}

		catch ( final Throwable ex ) {
			JUnits.assertExceptionEquals("Incorrect exception thrown;", expectedException, ex) ;
		}
	}
}
