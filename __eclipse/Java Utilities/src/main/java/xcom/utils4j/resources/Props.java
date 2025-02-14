

package xcom.utils4j.resources ;


import static xcom.utils4j.resources.Props.PropagateExceptionTypes.PropagateException ;

import java.io.DataInputStream ;
import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileNotFoundException ;
import java.io.IOException ;
import java.net.URISyntaxException ;
import java.net.URL ;
import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.List ;
import java.util.Map ;
import java.util.Properties ;

import org.apache.commons.io.FileUtils ;
import org.apache.commons.io.FilenameUtils ;
import org.apache.commons.io.filefilter.WildcardFileFilter ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.format.Templates ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;


public class Props {

	public enum PropagateExceptionTypes {
		PropagateException;
	}


	private static final Logger Logger = LoggerFactory.getLogger(Props.class) ;


	@Log
	private Props() {
		throw new UnsupportedOperationException() ;
	}


	@Log
	public static String loadAsString(final Class<?> clazz) {

		try {
			return FileUtils.readFileToString(new File(clazz.getResource(clazz.getSimpleName() + ".properties").toURI()), "UTF-8") ;
		}
		catch ( IOException | URISyntaxException e ) {
			e.printStackTrace() ;
		}

		return null ;
	}

	@Log
	public static void saveFromString(final Class<?> clazz) {

		try {
			System.out.println(new File(clazz.getResource(clazz.getSimpleName() + ".properties").toURI().getPath())) ;
//			FileUtils.writeStringToFile(new File(clazz.getResource(clazz.getSimpleName() + ".properties").toURI()), "UTF-8") ;
		}
		catch ( URISyntaxException e ) {
//			catch ( IOException | URISyntaxException e ) {
			Logger.error("Error writting setting file: |{}|", e) ;
		}
	}


	@Log
	public static Properties load(final String... fileSpecs) {

		try {
			return merge(new Properties(), PropagateException, fileSpecs) ;
		}
		catch ( IOException e ) {}

		return null ;
	}

	@Log
	public static Properties load(PropagateExceptionTypes p, final String... fileSpecs) throws FileNotFoundException, IOException {
		return merge(new Properties(), PropagateException, fileSpecs) ;
	}

	@SuppressWarnings("deprecation")
	@Log
	public static Properties merge(final Properties props, PropagateExceptionTypes p, final String... fileSpecs) throws FileNotFoundException, IOException {

		for ( final String fileSpec : fileSpecs ) {
			Logger.debug("scanning: {} ...", fileSpec) ;

			final String dirSpec = FilenameUtils.getFullPath(fileSpec) ;
			final String fnSpec = FilenameUtils.getName(fileSpec) ;

			final List<String> files = new ArrayList<>(Arrays.asList(new File(dirSpec).list(new WildcardFileFilter(fnSpec)))) ;

			for ( final String file : files ) {
				Logger.debug("Loading properties {} ...", file) ;
				props.load(new DataInputStream(new FileInputStream(new File(file)))) ;
			}
		}

		return props ;
	}


	@Log
	public static Properties load(final Class<?> clazz) {

		try {
			return merge(clazz, new Properties(), PropagateException, clazz.getSimpleName() + ".properties") ;
		}
		catch ( IOException e ) {}

		return null ;
	}

	@Log
	public static Properties load(final Class<?> clazz, final String... rSpecs) {

		try {
			return merge(clazz, new Properties(), PropagateException, rSpecs) ;
		}
		catch ( IOException e ) {}

		return null ;
	}

	@Log
	public static Properties merge(final Class<?> clazz, final Properties props) {

		try {
			return merge(clazz, props, PropagateException, clazz.getSimpleName() + ".properties") ;
		}
		catch ( IOException e ) {}

		return null ;
	}

	@Log
	public static Properties load(final Class<?> clazz, final PropagateExceptionTypes p, final String... rSpecs) throws IOException {
		return merge(clazz, new Properties(), PropagateException, rSpecs) ;
	}

	@Log
	public static Properties merge(final Class<?> clazz, final Properties props, final String... rSpecs) throws IOException {
		return merge(clazz, props, PropagateException, rSpecs) ;
	}

	@Log
	public static Properties merge(final Class<?> clazz, final Properties props, final PropagateExceptionTypes p) throws IOException {
		return merge(clazz, props, PropagateException, clazz.getSimpleName() + ".properties") ;
	}

	@Log
	public static Properties merge(final Class<?> clazz, final Properties props, final PropagateExceptionTypes p, final String... rSpecs) throws IOException {

		final Properties _props = (props == null ? new Properties() : props) ;

		for ( final String rSpec : rSpecs ) {

			Logger.debug("Loading resource {} ...", rSpec) ;
			final URL resource = clazz.getResource(rSpec) ;

			if ( resource != null ) {
				Logger.debug("Loading properties {} ...", resource.getPath()) ;
				_props.load(resource.openStream()) ;
			}
		}

		return _props ;
	}


	@SuppressWarnings("unchecked")
	@Log
	public static String getPropertry(final String key, final Properties props, final String[] tagDelimiters) {
		return Templates.replaceTags(new StringBuilder(props.getProperty(key)), tagDelimiters, (Map) props).toString() ;
	}

	@Log
	public static String getPropertry(final String key, final Properties props) {
		return props.getProperty(key) ;
	}
}
