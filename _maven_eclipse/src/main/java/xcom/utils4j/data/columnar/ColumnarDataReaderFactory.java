

package xcom.utils4j.data.columnar ;


import static xcom.utils4j.data.columnar.api.interfaces.IColumnarDataReader.ColumnNamePolicyTypes.SimpleColumnNamePolicy ;

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

import xcom.utils4j.data.columnar.api.annotations.AColumnarDataReader ;
import xcom.utils4j.data.columnar.api.interfaces.IColumnarDataReader ;
import xcom.utils4j.data.columnar.api.interfaces.IColumnarDataReader.ColumnNamePolicyTypes ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;


/**
 * Provides a factory for reading various types of columnar data sources.
 *
 * @author c.j.gregory
 */
@Log
public class ColumnarDataReaderFactory {

	private static final Logger Logger = LoggerFactory.getLogger(ColumnarDataReaderFactory.class) ;


	/**
	 * Map of readers marked by <code>@ColumnarDataReader</code>.
	 */
	Map<String, String> foundReaders ;


	/**
	 * Instantiate the factory and register known readers.
	 */
	public ColumnarDataReaderFactory() {

		foundReaders = new HashMap<String, String>() ;

		final String classpath = "xcom" ;
		Logger.info("Scanning classpath '" + classpath + ".*' for readers ...") ;

		final Set<Class<?>> readers = new Reflections(classpath).getTypesAnnotatedWith(AColumnarDataReader.class) ;
		Logger.info("    found {} readers in classpath '" + classpath + ".*': |{}|", readers.size(), readers) ;

		for ( final Class<?> reader : readers )
			for ( final String sourceType : reader.getAnnotation(AColumnarDataReader.class).sourceType() )
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
	public IColumnarDataReader create(final File dataSource) {
		return (createReader(dataSource, FilenameUtils.getExtension(dataSource.getAbsolutePath()), SimpleColumnNamePolicy)) ;
	}


	/**
	 * Create a reader with a derived source type and specific column name policy.
	 *
	 * @param dataSource
	 * @param columnNamePolicy
	 * @return
	 */
	public IColumnarDataReader create(final File dataSource, final ColumnNamePolicyTypes columnNamePolicy) {
		return (createReader(dataSource, FilenameUtils.getExtension(dataSource.getAbsolutePath()), columnNamePolicy)) ;
	}


	/**
	 * Create a reader with a derived source type and default column name policy.
	 *
	 * @param dataSource
	 * @return The file columnar data reader
	 */
	public IColumnarDataReader create(final URL dataSource) {
		return (createReader(dataSource, FilenameUtils.getExtension(dataSource.getPath()), SimpleColumnNamePolicy)) ;
	}


	/**
	 * Create a reader with a derived source type and specific column name policy.
	 *
	 * @param dataSource
	 * @param columnNamePolicy
	 * @return
	 */
	public IColumnarDataReader create(final URL dataSource, final ColumnNamePolicyTypes columnNamePolicy) {
		return (createReader(dataSource, FilenameUtils.getExtension(dataSource.getPath()), columnNamePolicy)) ;
	}


	/**
	 * Create a reader with a specific source type and default column name policy.
	 *
	 * @param dataSource
	 * @param sourceType
	 * @return The file columnar data reader
	 */
	public IColumnarDataReader create(final Object dataSource, final String sourceType) {
		return (createReader(dataSource, sourceType, SimpleColumnNamePolicy)) ;
	}


	/**
	 * Create a reader with a specific source type and column name policy.
	 *
	 * @param dataSource
	 * @param sourceType
	 * @param columnNamePolicy
	 * @return The file columnar data reader
	 */
	public IColumnarDataReader create(final Object dataSource, final String sourceType, final ColumnNamePolicyTypes columnNamePolicy) {
		return (createReader(dataSource, sourceType, columnNamePolicy)) ;
	}


	/**
	 * Instantiate the required reader class.
	 *
	 * @param dataSource
	 * @param sourceType
	 * @param columnNamePolicy
	 * @return The file columnar data reader
	 */
	IColumnarDataReader createReader(final Object dataSource, final String sourceType, final ColumnNamePolicyTypes columnNamePolicy) {

		if ( dataSource == null )
			throw new IllegalArgumentException("Data Source cannot be 'null'.") ;

		if ( StringUtils.trimToNull(sourceType) == null )
			throw new IllegalArgumentException("Data Source cannot cannot be 'null' or 'blank'.") ;

		if ( !foundReaders.containsKey(sourceType.toLowerCase()) )
			throw new IllegalArgumentException("Source Type not supported by factory.") ;


		final String classname = foundReaders.get(sourceType.toLowerCase()) ;

		return Reflection.constructor().withParameterTypes(dataSource.getClass(), ColumnNamePolicyTypes.class)
				.in(Reflection.type(classname).loadAs(IColumnarDataReader.class)).newInstance(dataSource, columnNamePolicy) ;
	}
}
