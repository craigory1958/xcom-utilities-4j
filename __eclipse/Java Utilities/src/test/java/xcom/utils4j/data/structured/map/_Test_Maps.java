

package xcom.utils4j.data.structured.map ;


import java.util.HashMap ;
import java.util.Map ;
import java.util.Map.Entry ;
import java.util.Random ;
import java.util.UUID ;

import org.junit.Assert ;
import org.junit.Test ;


/**
 * JUnit for Maps class
 */
public class _Test_Maps {

	/**
	 * Sort a map of random Integers.
	 */
	@Test
	public void sortRandomIntegers() {

		final int sampleSize = 1000 ;
		final Random r = new Random(System.currentTimeMillis()) ;

		final Map<String, Integer> unsorted = new HashMap<String, Integer>(sampleSize) ;
		for ( int i = 0; (i < 1000); i++ )
			unsorted.put("" + r.nextInt(), r.nextInt()) ;

		final Map<String, Integer> sorted = Maps.sortByValue(unsorted) ;

		Integer prvValue = null ;
		for ( final Entry<String, Integer> ex : sorted.entrySet() ) {

			Assert.assertNotNull("Element is null.", ex.getValue()) ;

			if ( prvValue != null )
				Assert.assertTrue("Element out of order.", ex.getValue() >= prvValue) ;

			prvValue = ex.getValue() ;
		}
	}


	/**
	 * Sort a map of random Strings.
	 */
	@Test
	public void sortRandomStrings() {

		final int sampleSiza = 1000 ;

		final Map<String, String> unsorted = new HashMap<String, String>(sampleSiza) ;
		for ( int i = 0; (i < 1000); i++ )
			unsorted.put(UUID.randomUUID().toString(), UUID.randomUUID().toString()) ;

		final Map<String, String> sorted = Maps.sortByValue(unsorted) ;

		String prvValue = null ;
		for ( final Entry<String, String> ex : sorted.entrySet() ) {

			Assert.assertNotNull("Element is null.", ex.getValue()) ;

			if ( prvValue != null )
				Assert.assertTrue("Element out of order.", ex.getValue().compareTo(prvValue) != -1) ;

			prvValue = ex.getValue() ;
		}
	}


	/**
	 * Test the creation of an map Entry.
	 */
	@Test
	public void getInstanceOfEntry() {

		final String key = UUID.randomUUID().toString() ;
		final String value = UUID.randomUUID().toString() ;
		final Entry<String, String> entry = Maps.getInstanceOfEntry(key, value) ;

		Assert.assertTrue("Entry did not return the expected key", key.equals(entry.getKey())) ;
		Assert.assertTrue("Entry did not return the expected value", value.equals(entry.getValue())) ;
	}


//	/**
//	 * Add code coverage to abstract class instantiation; not really a unit test. Gets code coverage up to 100%.
//	 */
//	@Test
//	public void codeCoverageOfInstantiation() {
//		new Maps() {} ;
//	}
}
