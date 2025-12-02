

package xcom.utils4j ;


import static xcom.utils4j.Signatures.NameFormatTypes.FullNameFormat ;
import static xcom.utils4j.Signatures.NameFormatTypes.SimpleNameFormat ;

import java.lang.reflect.Constructor ;
import java.lang.reflect.Method ;

import org.aspectj.lang.reflect.ConstructorSignature ;
import org.aspectj.lang.reflect.MethodSignature ;
import org.springframework.web.bind.annotation.RequestMapping ;


public abstract class Signatures {

	public enum NameFormatTypes {
		SimpleNameFormat, //
		FullNameFormat, //
		;
	}


	private Signatures() {
		throw new UnsupportedOperationException() ;
	}


	/**
	 * @param constructor
	 * @return
	 */
	public static String formatSignature(final ConstructorSignature constructor) {
		return formatSignature(constructor, SimpleNameFormat) ;
	}


	public static String formatSignature(final ConstructorSignature constructor, final NameFormatTypes format) {
		return formatSignature(null, constructor.getDeclaringType().getSimpleName(), constructor.getParameterTypes(), format) ;
	}


	public static String formatSignature(final MethodSignature method) {
		return formatSignature(method, SimpleNameFormat) ;
	}


	public static String formatSignature(final MethodSignature method, final NameFormatTypes format) {
		return formatSignature(method.getReturnType(), method.getName(), method.getParameterTypes(), format) ;
	}


	/**
	 * @param constructor
	 * @return
	 */
	public static String formatSignature(final Constructor<?> constructor) {
		return formatSignature(constructor, SimpleNameFormat) ;
	}


	public static String formatSignature(final Constructor<?> constructor, final NameFormatTypes format) {
		return formatSignature(null, constructor.getName(), constructor.getParameterTypes(), format) ;
	}


	public static String formatSignature(final Method method) {
		return formatSignature(method, SimpleNameFormat) ;
	}


	public static String formatSignature(final Method method, final NameFormatTypes format) {
		return formatSignature(method.getReturnType(), method.getName(), method.getParameterTypes(), format) ;
	}


	static String formatSignature(final Class<?> returnType, final String name, final Class<?>[] parameters, final NameFormatTypes format) {
		return formatReturnType(returnType, format) + name + "(" + formatArguments(parameters, format) + ")" ;
	}


	public static String formatName(final Class<?> name, final NameFormatTypes format) {
		return format == FullNameFormat ? name.getName() : name.getSimpleName() ;
	}


	public static String formatArguments(final Class<?>[] args, final NameFormatTypes format) {

		final StringBuilder results = new StringBuilder() ;

		if ( args.length != 0 ) {
			results.append(format == FullNameFormat ? args[0].getName() : args[0].getSimpleName()) ;

			for ( int i = 1; i < args.length; i++ )
				results.append(", ").append(format == FullNameFormat ? args[i].getName() : args[i].getSimpleName()) ;
		}

		return results.toString() ;
	}


	public static String formatReturnType(final Class<?> returnType, final NameFormatTypes format) {
		return returnType == null ? "" : (format == FullNameFormat ? returnType.getName() : returnType.getSimpleName() + " ") ;
	}


	/**
	 * @param object
	 * @param clazz
	 * @return
	 */
	public static String formatGrailsControllerSignature(final Object object, final Class<?> clazz) {
		return clazz.getSimpleName() + " @" + Signatures.formatSignature(object.getClass().getEnclosingMethod()) ;
	}


	/**
	 * @param object
	 * @param clazz
	 * @return
	 */
	public static String formatSpringControllerSignature(final Object object, final Class<?> clazz) {

		String results = clazz.getSimpleName() + " @" + Signatures.formatSignature(object.getClass().getEnclosingMethod()) ;

		String annotation = object.getClass().getEnclosingMethod().getAnnotation(RequestMapping.class).toString() ;
		annotation = annotation.substring(annotation.lastIndexOf('.') + 1) ;

		results += " w/" + annotation ;

		return results ;
	}
}
