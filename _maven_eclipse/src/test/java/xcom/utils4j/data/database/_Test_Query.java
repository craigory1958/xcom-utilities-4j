

package xcom.utils4j.data.database ;


import org.junit.Assert ;
import org.junit.Test ;

import xcom.utils4j.data.database.Query.Query$Connected ;
import xcom.utils4j.data.database.Query.Status ;


public class _Test_Query {

	/**
	 * Verify that constructor returns the expected type. (Bump code coverage to 100%.)
	 */
	@Test
	public void constructor_Query_Returns_CorrectType() {

		final Query sqls = new Query() {} ;
		Assert.assertTrue("Constructor did not return expected type.", sqls instanceof Query) ;
	}


	/**
	 * Verify that the <code>connection()</code> method returns the expected type.
	 */
	@Test
	public void method_connection_Returns_CorrectType() {

		Assert.assertTrue("Method 'statement()' did not return expected type.", Query.connection(null) instanceof Query$Connected) ;
	}


	/**
	 * Verify that <code>Status</code> constructor initializes values.
	 */
	@Test
	public void constructor_Status_Initialization() {

		final Status status = new Status() ;
		Assert.assertNull("Incorrect value for member 'execute':", status.execute) ;
		Assert.assertNull("Incorrect value for member 'updated':", status.updated) ;
	}


	/**
	 * Verify <code>getResult()</code> method.
	 */
	@Test
	public void method_Status_getResult_Returns_ValidData() {

		final boolean expected = false ;

		final Status status = new Status() ;
		status.execute = expected ;

		Assert.assertEquals("Incorrect value for member 'execute':", expected, status.result()) ;
	}


	/**
	 * Verify <code>getResult()</code> method throws <code>NullPointerException</code> from constructor.
	 */
	@Test(expected = NullPointerException.class)
	public void method_Status_getResult_Returns_Null() {
		new Status().result() ;
	}


	/**
	 * Verify <code>getNumberOfAffectedRows()</code> method.
	 */
	@Test
	public void method_Query$Status_getNumberOfAffectedRows_Returns_ValidData() {

		final int expected = 0 ;

		final Status statud = new Status() ;
		statud.updated = expected ;

		Assert.assertEquals("Incorrect value for member 'updated':", expected, statud.numberOfAffectedRows()) ;
	}


	/**
	 * Verify <code>getNumberOfAffectedRows()</code> method throws <code>NullPointerException</code> from constructor.
	 */
	@Test(expected = NullPointerException.class)
	public void method_Query$Status_getNumberOfAffectedRows_Returns_Null() {
		new Status().numberOfAffectedRows() ;
	}
}
