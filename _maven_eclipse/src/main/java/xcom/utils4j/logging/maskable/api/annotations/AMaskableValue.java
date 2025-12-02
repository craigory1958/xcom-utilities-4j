

package xcom.utils4j.logging.maskable.api.annotations ;


import java.lang.annotation.ElementType ;
import java.lang.annotation.Inherited ;
import java.lang.annotation.Retention ;
import java.lang.annotation.RetentionPolicy ;
import java.lang.annotation.Target ;


@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface AMaskableValue {}
