

package xcom.utils4j.data.database ;


import java.lang.annotation.Annotation ;
import java.lang.reflect.InvocationTargetException ;
import java.util.Properties ;
import java.util.Set ;

import javax.sql.DataSource ;

import org.fest.reflect.core.Reflection ;
import org.reflections.Reflections ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.data.database.api.annotations.DataSourceVariant ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;


public class PooledDataSourceFactory {

	private static final Logger Logger = LoggerFactory.getLogger(PooledDataSourceFactory.class) ;


	@Log
	public PooledDataSourceFactory() {}


	@Log
	public DataSource create(final String scanPath, final String variant, final Properties props) throws Exception {

		Logger.info("Loading pooled datasource for {} @{}", variant, scanPath) ;

		final Set<Class<?>> _variants = new Reflections(scanPath).getTypesAnnotatedWith(DataSourceVariant.class) ;
		Logger.info("found {} annotations in classpath: |{}|", _variants.size(), _variants) ;

		try {
			for ( final Class<?> _variant : _variants )
				for ( final Annotation _annotation : _variant.getAnnotations() )
					if ( _annotation.annotationType().equals(DataSourceVariant.class) )
						if ( _annotation.getClass().getMethod("variant").invoke(_annotation).equals(variant) )

							return Reflection.constructor().withParameterTypes(Properties.class)
									.in(Reflection.type(_variant.getCanonicalName()).loadAs(PooledDataSource.class)).newInstance(props) ;

			throw new Exception("DataSource variant not found.") ;
		}

		catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
			throw e ;
		}
	}
}
