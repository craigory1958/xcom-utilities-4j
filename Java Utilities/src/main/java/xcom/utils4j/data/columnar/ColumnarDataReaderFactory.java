

package xcom.utils4j.data.columnar ;


import static xcom.utils4j.data.columnar.iColumnarDataReader.SIMPLE_COLUMN_NAME_POLICY ;

import java.io.File ;
import java.net.URL ;
import java.util.HashMap ;
import java.util.Map ;
import java.util.Set ;

import org.apache.commons.io.FilenameUtils ;
import org.apache.commons.lang3.StringUtils ;
import org.fest.reflect.core.Reflection ;
import org.reflections.Reflections ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.data.columnar.annotations.ColumnarDataReader ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;


/**
 * Provides a factory for reading various types of columnar data sources.
 *
 * @author c.j.gregory
 */
public class ColumnarDataReaderFactory {

	private static final Logger Logger = LoggerFactory.getLogger(ColumnarDataReaderFactory.class) ;


	/**
	 * Map of readers marked by <code>@ColumnarDataReader</code>.
	 */
	Map<String, String> foundReaders ;


	/**
	 * Instantiate the factory and register known readers.
	 */
	@Log
	public ColumnarDataReaderFactory() {

		foundReaders = new HashMap<String, String>() ;

		final String classpath = "xcom" ;
		Logger.info("Scanning classpath '" + classpath + ".*' for readers ...") ;

		final Set<Class<?>> readers = new Reflections(classpath).getTypesAnnotatedWith(ColumnarDataReader.class) ;
		Logger.info("    found {} readers in classpath '" + classpath + ".*': |{}|", readers.size(), readers) ;

		for ( final Class<?> reader : readers )
			for ( final String sourceType : reader.getAnnotation(ColumnarDataReader.class).sourceType() )
				if ( foundReaders.containsKey(sourceType.toLowerCase()) )
					Logger.warn("Duplicate reader found for Source Type '{}': ", sourceType.toLowerCase(), reader) ;

				else
					foundReaders.put(sourceType.toLowerCase(), reader.getName()) ;
	}


	/**
	 * Create a reader with a derived source type and default column name policy.
	 *
	 * @param dataSource
	 * @return The file columnar data reader
	 */
	@Log
	public iColumnarDataReader create(final File dataSource) {
		return (createReader(dataSource, FilenameUtils.getExtension(dataSource.getAbsolutePath()), SIMPLE_COLUMN_NAME_POLICY)) ;
	}


	/**
	 * Create a reader with a derived source type and specific column name policy.
	 *
	 * @param dataSource
	 * @param excatColumnNamePolicy
	 * @return
	 */
	@Log
	public iColumnarDataReader create(final File dataSource, final boolean excatColumnNamePolicy) {
		return (createReader(dataSource, FilenameUtils.getExtension(dataSource.getAbsolutePath()), excatColumnNamePolicy)) ;
	}


	/**
	 * Create a reader with a derived source type and default column name policy.
	 *
	 * @param dataSource
	 * @return The file columnar data reader
	 */
	@Log
	public iColumnarDataReader create(final URL dataSource) {
		return (createReader(dataSource, FilenameUtils.getExtension(dataSource.getPath()), SIMPLE_COLUMN_NAME_POLICY)) ;
	}


	/**
	 * Create a reader with a derived source type and specific column name policy.
	 *
	 * @param dataSource
	 * @param excatColumnNamePolicy
	 * @return
	 */
	@Log
	public iColumnarDataReader create(final URL dataSource, final boolean excatColumnNamePolicy) {
		return (createReader(dataSource, FilenameUtils.getExtension(dataSource.getPath()), excatColumnNamePolicy)) ;
	}


	/**
	 * Create a reader with a specific source type and default column name policy.
	 *
	 * @param dataSource
	 * @param sourceType
	 * @return The file columnar data reader
	 */
	@Log
	public iColumnarDataReader create(final Object dataSource, final String sourceType) {
		return (createReader(dataSource, sourceType, SIMPLE_COLUMN_NAME_POLICY)) ;
	}


	/**
	 * Create a reader with a specific source type and column name policy.
	 *
	 * @param dataSource
	 * @param sourceType
	 * @param excatColumnNamePolicy
	 * @return The file columnar data reader
	 */
	@Log
	public iColumnarDataReader create(final Object dataSource, final String sourceType, final boolean excatColumnNamePolicy) {
		return (createReader(dataSource, sourceType, excatColumnNamePolicy)) ;
	}


	/**
	 * Instantiate the required reader class.
	 *
	 * @param dataSource
	 * @param sourceType
	 * @param excatColumnNamePolicy
	 * @return The file columnar data reader
	 */
	@Log
	iColumnarDataReader createReader(final Object dataSource, final String sourceType, final boolean excatColumnNamePolicy) {

		if ( dataSource == null )
			throw new IllegalArgumentException("Data Source cannot be 'null'.") ;

		if ( StringUtils.trimToNull(sourceType) == null )
			throw new IllegalArgumentException("Data Source cannot cannot be 'null' or 'blank'.") ;

		if ( !foundReaders.containsKey(sourceType.toLowerCase()) )
			throw new IllegalArgumentException("Source Type not supported by factory.") ;


		final String classname = foundReaders.get(sourceType.toLowerCase()) ;

		return Reflection.constructor().withParameterTypes(dataSource.getClass(), boolean.class)
				.in(Reflection.type(classname).loadAs(iColumnarDataReader.class)).newInstance(dataSource, excatColumnNamePolicy) ;
	}
}
