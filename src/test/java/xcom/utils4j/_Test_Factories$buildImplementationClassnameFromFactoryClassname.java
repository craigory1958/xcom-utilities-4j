

package xcom.utils4j ;


import java.util.Arrays ;
import java.util.Collection ;

import org.junit.Assert ;
import org.junit.Test ;
import org.junit.runner.RunWith ;
import org.junit.runners.Parameterized ;


/**
 * Verify the <code>buildImplementationClassnameFromFactoryClassname()</code> method. Data is provided using a JUnit <code>Parameterized</code> test and the
 * <code>data()</code> method.
 */
@RunWith(Parameterized.class)
public class _Test_Factories$buildImplementationClassnameFromFactoryClassname {

	@Parameterized.Parameters
	public static Collection<Object[]> data() {

		//@formatter:off

        final Object[][] results = {

                // (Object factoryClassname, Object implementation, Object expected)

                { "com.x.ReaderFactory", "CSV", "com.x.Reader_CSV" },
                { "com.ReaderFactory", "CSV", "com.Reader_CSV" },
                { "ReaderFactory", "CSV", "Reader_CSV" },
                { "com.x.ReaderFactory", "", "com.x.Reader" },
                { "com.ReaderFactory", "", "com.Reader" },
                { "ReaderFactory", "", "Reader" },
                { null, "", new IllegalArgumentException() },
                { "ReaderFactory", null, "Reader" },
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
	 * The Factory Classname of the current test.
	 */
	String factoryClassname ;


	/**
	 * Instantiate the current test with provided data.
	 *
	 * @param factoryClassname
	 * @param implementation
	 * @param expected
	 */
	public _Test_Factories$buildImplementationClassnameFromFactoryClassname(final Object factoryClassname, final Object implementation, final Object expected) {

		this.factoryClassname = (String) factoryClassname ;
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
	public void buildImplementationClassnameFromFactoryClassname() {

		try {
			final String actual = Factories.buildImplementationClassnameFromFactoryClassname(factoryClassname, implementation) ;

			if ( expectedException != null )
				Assert.fail("Expected exception not thrown; expected: <" + expectedException + ">") ;

			Assert.assertEquals("Incorrect classname;", expectedClassname, actual) ;
		}

		catch ( final Throwable ex ) {
			JUnits.assertExceptionEquals("Incorrect exception thrown;", expectedException, ex) ;
		}
	}
}
