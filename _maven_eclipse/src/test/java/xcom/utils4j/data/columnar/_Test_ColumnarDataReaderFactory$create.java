

package xcom.utils4j.data.columnar ;


import static xcom.utils4j.data.columnar.api.interfaces.IColumnarDataReader.ColumnNamePolicyTypes.ExactColumnNamePolicy ;
import static xcom.utils4j.data.columnar.api.interfaces.IColumnarDataReader.ColumnNamePolicyTypes.SimpleColumnNamePolicy ;

import org.junit.Assert ;
import org.junit.Test ;
import org.junit.runner.RunWith ;
import org.junit.runners.Parameterized ;

import xcom.utils4j.data.columnar.api.interfaces.IColumnarDataReader ;


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

		final IColumnarDataReader reader = factory.create(file, sourceType) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertTrue("Factory not set to 'SimpleColumnNamePolicy'", reader.getColumnNamePolicy() == SimpleColumnNamePolicy) ;
	}


	/**
	 * Verify that correct reader class in returned when passing a <code>File</code> object and specifying simple column names.
	 */
	@Test
	public void createReaderWithFileUsingSimpleColumnNames() {

		final IColumnarDataReader reader = factory.create(file, sourceType, SimpleColumnNamePolicy) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertTrue("Factory not set to 'SimpleColumnNamePolicy'", reader.getColumnNamePolicy() == SimpleColumnNamePolicy) ;
	}


	/**
	 * Verify that correct reader class in returned when passing a <code>File</code> object and specifying exact column names.
	 */
	@Test
	public void createReaderWithFileUsingExactColumnNames() {

		final IColumnarDataReader reader = factory.create(file, sourceType, ExactColumnNamePolicy) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertTrue("Factory not set to 'ExactColumnNamePolicy'", reader.getColumnNamePolicy() == ExactColumnNamePolicy) ;
	}


	/**
	 * Verify that correct reader class in returned when passing a <code>URL</code> object.
	 */
	@Test
	public void createReaderWithResource() {

		final IColumnarDataReader reader = factory.create(resource, sourceType) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertTrue("Factory not set to 'SimpleColumnNamePolicy'", reader.getColumnNamePolicy() == SimpleColumnNamePolicy) ;
	}


	/**
	 * Verify that correct reader class in returned when passing a <code>URL</code> object and specifying simple column names.
	 */
	@Test
	public void createReaderWithResourceUsingSimpleColumnNames() {

		final IColumnarDataReader reader = factory.create(resource, sourceType, SimpleColumnNamePolicy) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertTrue("Factory not set to 'SimpleColumnNamePolicy'", reader.getColumnNamePolicy() == SimpleColumnNamePolicy) ;
	}


	/**
	 * Verify that correct reader class in returned when passing a <code>URL</code> object and specifying exact column names.
	 */
	@Test
	public void createReaderWithResourceUsingExactColumnNames() {

		final IColumnarDataReader reader = factory.create(resource, sourceType, ExactColumnNamePolicy) ;

		Assert.assertEquals("Incorrect factory class returned;", reader.getClass().getName(), expectedClassname) ;
		Assert.assertTrue("Factory not set to 'ExactColumnNamePolicy'", reader.getColumnNamePolicy() == ExactColumnNamePolicy) ;
	}
}
