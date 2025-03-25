

package xcom.utils4j ;


import java.lang.reflect.Constructor ;
import java.lang.reflect.Method ;

import org.aspectj.lang.reflect.ConstructorSignature ;
import org.aspectj.lang.reflect.MethodSignature ;
import org.springframework.web.bind.annotation.RequestMapping ;


/**
 * A wrapper class for performing reflection
 */
public abstract class Signatures {

	public static final boolean FormatSimpleParamenterTypeNames = false ;

	public static final boolean FormatFullParameterTypeNames = !FormatSimpleParamenterTypeNames ;


	/**
	 *
	 */
	Signatures() {}


	/**
	 * @param constructor
	 * @return
	 */
	public static String formatSignature(final ConstructorSignature constructor) {
		return formatSignature(constructor, FormatSimpleParamenterTypeNames) ;
	}


	public static String formatSignature(final ConstructorSignature constructor, final boolean paramaterTypeNamesFormat) {
		return formatSignature(null, constructor.getDeclaringType().getSimpleName(), constructor.getParameterTypes(), paramaterTypeNamesFormat) ;
	}


	public static String formatSignature(final MethodSignature method) {
		return formatSignature(method, FormatSimpleParamenterTypeNames) ;
	}


	public static String formatSignature(final MethodSignature method, final boolean paramaterTypeNamesFormat) {
		return formatSignature(method.getReturnType(), method.getName(), method.getParameterTypes(), paramaterTypeNamesFormat) ;
	}


	/**
	 * @param constructor
	 * @return
	 */
	public static String formatSignature(final Constructor<?> constructor) {
		return formatSignature(constructor, FormatSimpleParamenterTypeNames) ;
	}


	public static String formatSignature(final Constructor<?> constructor, final boolean paramaterTypeNamesFormat) {
		return formatSignature(null, constructor.getName(), constructor.getParameterTypes(), paramaterTypeNamesFormat) ;
	}


	public static String formatSignature(final Method method) {
		return formatSignature(method, FormatSimpleParamenterTypeNames) ;
	}


	public static String formatSignature(final Method method, final boolean paramaterTypeNamesFormat) {
		return formatSignature(method.getReturnType(), method.getName(), method.getParameterTypes(), paramaterTypeNamesFormat) ;
	}


	static String formatSignature(final Class<?> returnType, final String name, final Class<?>[] parameters, final boolean paramaterTypeNamesFormat) {
		return formatReturnType(returnType, paramaterTypeNamesFormat) + name + "(" + formatArguments(parameters, paramaterTypeNamesFormat) + ")" ;
	}


	public static String formatName(final Class<?> name, final boolean paramaterTypeNamesFormat) {
		return paramaterTypeNamesFormat ? name.getName() : name.getSimpleName() ;
	}


	public static String formatArguments(final Class<?>[] args, final boolean argumentTypeNamesFormat) {

		final StringBuilder results = new StringBuilder() ;

		if ( args.length != 0 ) {
			results.append(argumentTypeNamesFormat ? args[0].getName() : args[0].getSimpleName()) ;

			for ( int i = 1; i < args.length; i++ )
				results.append(", ").append(argumentTypeNamesFormat ? args[i].getName() : args[i].getSimpleName()) ;
		}

		return results.toString() ;
	}


	public static String formatReturnType(final Class<?> returnType, final boolean returnTypeNamesFormat) {
		return returnType == null ? "" : (returnTypeNamesFormat ? returnType.getName() : returnType.getSimpleName() + " ") ;
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
