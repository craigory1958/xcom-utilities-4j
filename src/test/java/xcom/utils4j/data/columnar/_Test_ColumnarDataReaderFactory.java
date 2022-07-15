

package xcom.utils4j.data.columnar ;


import java.util.UUID ;

import org.junit.Test ;


/**
 * Verify factory instantiates correct reader class.
 *
 * <br />
 * <br />
 * <code>ColumnarDataTestDriver</code> will drive test with each implementation a the set of readers.
 *
 */
public class _Test_ColumnarDataReaderFactory {

	/**
	 * Verify that exception is thrown when passing <code>null</code> data source.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void createFactoryWithNullDataSource() {

		new ColumnarDataReaderFactory().create(null, "") ;
	}


	/**
	 * Verify that exception is thrown when passing <code>null</code> source type.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void createFactoryWithNullSourceType() {

		new ColumnarDataReaderFactory().create("", null) ;
	}


	/**
	 * Verify that exception is thrown when passing an unknown source type.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void createFactoryWithUnknownSourceType() {

		new ColumnarDataReaderFactory().create("", UUID.randomUUID().toString()) ;
	}
}
