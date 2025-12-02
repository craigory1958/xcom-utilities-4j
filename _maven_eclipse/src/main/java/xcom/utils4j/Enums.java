

package xcom.utils4j ;


import static xcom.utils4j.Enums.PropagateExceptionTypes.DoNotPropagateException ;
import static xcom.utils4j.Enums.PropagateExceptionTypes.PropagateException ;

import java.lang.reflect.Field ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


@Log
public abstract class Enums {

	public enum PropagateExceptionTypes {
		PropagateException, //
		DoNotPropagateException, //
		;
	}


	private Enums() {
		throw new UnsupportedOperationException() ;
	}


	public static <T extends Enum<T>> T valueOfIgnoreCase(final Class<T> enumerations, final String name) {
		return valueOfIgnoreCase(enumerations, name, DoNotPropagateException) ;
	}

	public static <T extends Enum<T>> T valueOfIgnoreCase(final Class<T> enumerations, final String name, final PropagateExceptionTypes propagate) {

		for ( final T enumeration : enumerations.getEnumConstants() )
			if ( enumeration.name().equalsIgnoreCase(name) )
				return enumeration ;

		if ( propagate == PropagateException )
			throw new IllegalArgumentException(String.format("There is no enum with name '%s' in Enum %s", name, enumerations.getName())) ;

		return null ;
	}


	public static <T extends Enum<T>> T attributeOfIgnoreCase(final Class<T> enumerations, final Field attributeRef, final String value) {

		try {
			for ( final T enumeration : enumerations.getEnumConstants() )
				if ( ((String) attributeRef.get(enumeration)).equalsIgnoreCase(value) )
					return enumeration ;
		}
		catch ( final IllegalAccessException e ) {}

		throw new IllegalArgumentException(
				String.format("There is no enum with an attibute '%s' of value '%s' in Enum %s", attributeRef.getName(), value, enumerations.getName())) ;
	}


	public static <T extends Enum<T>> Field fetchAccessibleAttributeReference(final Class<T> enumerations, final String attributeName) {

		try {
			for ( final Field field : enumerations.getDeclaredFields() )
				if ( field.getName().equals(attributeName) ) {
					field.setAccessible(true) ;
					return field ;
				}
		}
		catch ( final SecurityException e ) {}

		throw new IllegalArgumentException(String.format("There is no attibute '%s' in Enum %s", attributeName, enumerations.getName())) ;
	}
}
