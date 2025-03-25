

package xcom.utils4j.resources ;


import java.io.File ;
import java.io.IOException ;
import java.net.URISyntaxException ;

import org.apache.commons.io.FileUtils ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


public class Resources {

	@Log
	private Resources() {
		throw new UnsupportedOperationException() ;
	}


	@Log
	public static String readAsString(final String rSpec, final Class<?> clazz) {

		try {
			return FileUtils.readFileToString(new File(clazz.getResource(rSpec).toURI()), "UTF-8") ;
		}
		catch ( IOException | URISyntaxException e ) {
			e.printStackTrace() ;
		}

		return null ;
	}
}
