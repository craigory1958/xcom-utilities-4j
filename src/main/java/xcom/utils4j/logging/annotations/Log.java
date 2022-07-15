

package xcom.utils4j.logging.annotations ;


import java.lang.annotation.ElementType ;
import java.lang.annotation.Retention ;
import java.lang.annotation.RetentionPolicy ;
import java.lang.annotation.Target ;

import org.slf4j.event.Level ;


@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER })
public @interface Log {

	Level[] value() default { Level.DEBUG, Level.TRACE };
}
