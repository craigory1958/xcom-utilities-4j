

package xcom.utils4j ;


import org.fest.reflect.core.Reflection ;
import org.fest.reflect.exception.ReflectionError ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


/**
 * A wrapper class for performing reflection
 *
 * @author Craig Gregory
 */
public class Reflects {

	Class<?> clazz ;

	Class<?>[] parameterTypes ;

	String methodName ;

	Object instance ;


	Reflects() {}


	/**
	 * Loads an reference of an existing class name via reflection
	 *
	 * @param classname
	 *            - fully qualified name of class to load reference to
	 * @return - reference to class retrieved via reflection
	 * @throws ReflectionError
	 */
	@Log
	public static Reflects loadClass(final String classname) throws ReflectionError {
		final Reflects results = new Reflects() ;
		results.clazz = Reflection.type(classname).load() ;
		return results ;
	}


	/**
	 * Saves an reference of an existing method name to be invoked via reflection
	 *
	 * @param methodName
	 *            - the exact name of a method
	 * @return - reference to Reflects
	 * @throws ReflectionError
	 */
	@Log
	public static Reflects method(final String methodName) throws ReflectionError {
		final Reflects results = new Reflects() ;
		results.methodName = methodName ;
		return results ;
	}


	/**
	 * Loads a reference to a set of parameters via reflection
	 *
	 * This set of parameters can be used to invoke a class' constructor via reflection
	 *
	 * IE. to create a new ArrayList(10) via reflection, use: new Reflects.loadClass("java.util.ArrayList").withParameterTypes(int.class).newInstance(10))
	 *
	 * @param parameterTypes
	 *            - 1..n references to the classes of each parameter type
	 * @return - reference to Reflects class containing the loaded parameter types
	 */
	@Log
	public Reflects withParameterTypes(final Class<?>... parameterTypes) {
		this.parameterTypes = parameterTypes ;
		return this ;
	}


	/**
	 * Saves a reference to an object created via reflection
	 *
	 * This object can be used to invoke a method via reflection
	 *
	 * IE. to invoke a method on an object via reflection use: Object myArrayList =
	 * Reflects.loadClass("java.util.ArrayList").withParameterTypes(int.class).newInstance(10)) Reflects.method("size").in(myArrayList).invoke()
	 *
	 * @param instance
	 *            - an instance of an object whose method we will be invoking via reflection
	 * @return - reference to Reflects class containing the instance of the object
	 */
	@Log
	public Reflects in(final Object instance) {
		this.instance = instance ;
		return this ;
	}


	/**
	 * Creates an instance of a class via reflection and a no-argument constructor of that class
	 *
	 * @return - Object reference to instance of the class
	 * @throws ReflectionError
	 */
	@Log
	public Object newInstance() throws ReflectionError {
		final Object results = Reflection.constructor().in(clazz).newInstance() ;
		return results ;
	}


	/**
	 * Creates an instance of a class via reflection and a constructor of that class containing at least one argument
	 *
	 * IE. to create a StringBuilder object with a String parameter via reflection: new
	 * Reflects.loadClass("java.lang.StringBuilder").withParameterTypes(String.class).newInstance(10)
	 *
	 * @param parameters
	 *            - 1..n references to the classes of each parameter type
	 * @return - Object reference to instance of the class
	 * @throws ReflectionError
	 */
	@Log
	public Object newInstance(final Object... parameters) throws ReflectionError {
		final Object results = Reflection.constructor().withParameterTypes(parameterTypes).in(clazz).newInstance(parameters) ;
		return results ;
	}


	/**
	 * Invokes a method on an object via reflection. The method does not accept parameters.
	 *
	 * @return - Object result of the method invocation
	 * @throws ReflectionError
	 */
	@Log
	public Object invoke() {
		final Object results = Reflection.method(methodName).in(instance).invoke() ;
		return results ;
	}


	/**
	 * Invokes a method on an object via reflection. The method accepts parameters.
	 *
	 * @param parameters
	 *            - 1..n references to the instances of each parameter type to use in the method invocation
	 * @return - Object result of the method invocation
	 * @throws ReflectionError
	 */
	@Log
	public Object invoke(final Object... parameters) {
		final Object results = Reflection.method(methodName).withParameterTypes(parameterTypes).in(instance).invoke(parameters) ;
		return results ;
	}
}
