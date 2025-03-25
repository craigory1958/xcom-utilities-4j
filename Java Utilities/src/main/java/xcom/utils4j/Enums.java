

package xcom.utils4j ;


import java.lang.reflect.Field ;


public abstract class Enums {

	public static final boolean PropagateException = true ;


	public static <T extends Enum<T>> T valueOfIgnoreCase(final Class<T> enumerations, final String name) {
		return valueOfIgnoreCase(enumerations, name, false) ;
	}

	public static <T extends Enum<T>> T valueOfIgnoreCase(final Class<T> enumerations, final String name, final boolean propagateException) {

		for ( final T enumeration : enumerations.getEnumConstants() )
			if ( enumeration.name().equalsIgnoreCase(name) )
				return enumeration ;

		if ( propagateException )
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
