

package xcom.utils4j.data.columnar ;


import static xcom.utils4j.data.columnar.iColumnarDataReader.EXACT_COLUMN_NAME_POLICY ;
import static xcom.utils4j.data.columnar.iColumnarDataReader.SIMPLE_COLUMN_NAME_POLICY ;

import org.junit.Assert ;
import org.junit.Test ;
import org.junit.runner.RunWith ;
import org.junit.runners.Parameterized ;


/**
 * Verify factory instantiates correct reader class.
 *
 * <br />
 * <br />
 * <code>ColumnarDataTestDriver</code> will drive test with each implementation a the set of readers.
 *
 */
@RunWith(Parameterized.class)
public class _Test_ColumnarDataReaderFactory$create extends ColumnarDataReaderTestDriver {

	/**
	 * Instantiate the test with data provided by <code>ColumnarDataTestDriver</code>.
	 *
	 * @param sourceType
	 * @param dataSource
	 * @param expectedClassname
	 */
	public _Test_ColumnarDataReaderFactory$create(final String sourceType, final String dataSource, final String expectedClassname) {

		this.sourceType = sourceType ;
		this.dataSource = dataSource ;
		this.expectedClassname = expectedClassname ;
	}


	/**
	 * Verify that correct reader class in returned when passing a <code>File</code> object.
	 */
	@Test
	public void createReaderWithFile() {

		final iColumnarDataReader reader = factory.create(file, sourceType) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertFalse("Factory not set to process 'SimpleColumnNames'", reader.isExactColumnNames()) ;
	}


	/**
	 * Verify that correct reader class in returned when passing a <code>File</code> object and specifying simple column names.
	 */
	@Test
	public void createReaderWithFileUsingSimpleColumnNames() {

		final iColumnarDataReader reader = factory.create(file, sourceType, SIMPLE_COLUMN_NAME_POLICY) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertFalse("Factory not set to process 'SimpleColumnNames'", reader.isExactColumnNames()) ;
	}


	/**
	 * Verify that correct reader class in returned when passing a <code>File</code> object and specifying exact column names.
	 */
	@Test
	public void createReaderWithFileUsingExactColumnNames() {

		final iColumnarDataReader reader = factory.create(file, sourceType, EXACT_COLUMN_NAME_POLICY) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertTrue("Factory not set to process 'ExactColumnNames'", reader.isExactColumnNames()) ;
	}


	/**
	 * Verify that correct reader class in returned when passing a <code>URL</code> object.
	 */
	@Test
	public void createReaderWithResource() {

		final iColumnarDataReader reader = factory.create(resource, sourceType) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertFalse("Factory not set to process 'SimpleColumnNames'", reader.isExactColumnNames()) ;
	}


	/**
	 * Verify that correct reader class in returned when passing a <code>URL</code> object and specifying simple column names.
	 */
	@Test
	public void createReaderWithResourceUsingSimpleColumnNames() {

		final iColumnarDataReader reader = factory.create(resource, sourceType, SIMPLE_COLUMN_NAME_POLICY) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertFalse("Factory not set to process 'SimpleColumnNames'", reader.isExactColumnNames()) ;
	}


	/**
	 * Verify that correct reader class in returned when passing a <code>URL</code> object and specifying exact column names.
	 */
	@Test
	public void createReaderWithResourceUsingExactColumnNames() {

		final iColumnarDataReader reader = factory.create(resource, sourceType, EXACT_COLUMN_NAME_POLICY) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertTrue("Factory not set to process 'ExactColumnNames'", reader.isExactColumnNames()) ;
	}
}
