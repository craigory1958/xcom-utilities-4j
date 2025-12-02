

package xcom.utils4j.format ;


import static xcom.utils4j.format.Templator.DefaultDelimiters ;

import java.util.Arrays ;
import java.util.Collection ;
import java.util.HashMap ;
import java.util.Map ;

import org.junit.Assert ;
import org.junit.Test ;
import org.junit.runner.RunWith ;
import org.junit.runners.Parameterized ;

import xcom.utils4j.JUnits ;


/**
 * Verify the <code>replaceTags()</code> methods.
 *
 * <br/>
 * <br/>
 * Data is provided using a JUnit <code>Parameterized</code> test and the <code>data()</code> method.
 */
@RunWith(Parameterized.class)
public class _Test_Templates$replaceTags {

	@Parameterized.Parameters
	public static Collection<Object[][]> data() {

		//@formatter:off
        final Object[][][] results = {

                // (String[1] template, String[6] delimiters, String[*] values, String[1] expected)

                { {""}, DefaultDelimiters, {}, {""} },
                { {""}, DefaultDelimiters, null, {""} },
                { {""}, DefaultDelimiters, {null}, {""} },

                { {""}, DefaultDelimiters, {null, null}, {""} },
                { {""}, DefaultDelimiters, {null, ""}, {""} },
                { {""}, DefaultDelimiters, {"", null}, {""} },
                { {""}, DefaultDelimiters, {"", ""}, {""} },

                { {""}, null, {}, {new IllegalArgumentException()} },
                { {""}, {null, null}, {}, {new IllegalArgumentException()} },
                { {""}, {"", ""}, {}, {new IllegalArgumentException()} },

                { {""}, DefaultDelimiters, {null, '.'}, {""} },
                { {""}, DefaultDelimiters, {'.', null}, {""} },
                { {""}, DefaultDelimiters, {'.', '.'}, {""} },

                { {""}, DefaultDelimiters, {"Value1", "Value2"}, {""} },
                { {"Nothing tagged"}, DefaultDelimiters, {"Value1", "Value2"}, {"Nothing tagged"} },
                { {"<@1@> <@2@>"}, DefaultDelimiters, {"Something", "tagged"}, {"Something tagged"} },
                { {"<@1@>"}, DefaultDelimiters, {"Something", "extra"}, {"Something"} },
                { {"<@1@> <@2@>"}, DefaultDelimiters, {"Something"}, {"Something "} },
                { {"<@1@> <@2@>"}, DefaultDelimiters, {"Something", null}, {"Something "} },

                { {""}, {"<", ">"}, {"Value1", "Value2"}, {""} },
                { {"Nothing tagged"}, {"<", ">"}, {"Value1", "Value2"}, {"Nothing tagged"} },
                { {"<1> <2>"}, {"<", ">"}, {"Something", "tagged"}, {"Something tagged"} },
                { {"<1>"}, {"<", ">"}, {"Something", "extra"}, {"Something"} },
                { {"<1> <2>"}, {"<", ">"}, {"Something"}, {"Something "} },
                { {"<1> <2>"}, {"<", ">"}, {"Something", null}, {"Something "} },
                { {"<1> <2>"}, {"<", ">"}, {}, {" "} },
        } ;
        //@formatter:on

		return (Arrays.asList(results)) ;
	}

	/**
	 *
	 */
	String template ;

	String[] delimiters ;

	Map<String, Object> values ;

	String expected ;

	Exception expectedException ;


	/**
	 * Instantiate the current test with provided data.
	 *
	 * @param template
	 * @param delimiters
	 * @param values
	 * @param expected
	 */
	public _Test_Templates$replaceTags(final Object[] template, final Object[] delimiters, final Object[] values, final Object[] expected) {

		this.template = null ;
		this.delimiters = null ;
		this.values = null ;
		this.expected = null ;
		expectedException = null ;

		if ( template != null )
			this.template = ((String) template[0]) ;

		if ( delimiters != null )
			this.delimiters = Arrays.asList(delimiters).toArray(new String[delimiters.length]) ;

		if ( values != null ) {
			this.values = new HashMap<>() ;
			for ( Integer i = 1; (i <= values.length); i++ )
				this.values.put(i.toString(), values[i - 1]) ;
		}

		if ( expected[0] instanceof String )
			this.expected = ((String) expected[0]) ;

		else if ( expected[0] instanceof Exception )
			expectedException = ((Exception) expected[0]) ;
	}


	@Test
	public void replaceTags_FromString() {

		try {
			final String original = template ;
			final String actual = Templates.replaceTags(new StringBuilder(template), delimiters, values).toString() ;

			Assert.assertEquals("Replacement error;", expected, actual) ;
			Assert.assertEquals("Orginal template altered;", original, template) ;

			if ( expectedException != null )
				Assert.fail("Expected exception not thrown; expected: <" + expectedException + ">") ;
		}
		catch ( final Throwable e ) {
			JUnits.assertExceptionEquals("Incorrect exception thrown;", expectedException, e) ;
		}
	}
}
