

package xcom.utils4j.resources ;


import java.io.IOException ;
import java.net.URL ;

import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.logging.annotations.Log ;


public class Properties {

	private static final Logger Logger = LoggerFactory.getLogger(Properties.class) ;


	/**
	 * @param clazz
	 * @param props
	 * @param propFiles
	 * @return
	 */
	@Log
	public static java.util.Properties mergeAssociatedProperties(final Class<?> clazz, java.util.Properties props, final String... propFiles) {

		if ( props == null )
			props = new java.util.Properties() ;

		for ( final String propFile : propFiles )
			try {
				final URL resource = clazz.getResource(propFile) ;

				if ( resource != null ) {
					Logger.debug("Loading properties {} ...", resource.getPath()) ;
					props.load(resource.openStream()) ;
				}
			}
			catch ( final IOException ex ) {}

		return props ;
	}
}
