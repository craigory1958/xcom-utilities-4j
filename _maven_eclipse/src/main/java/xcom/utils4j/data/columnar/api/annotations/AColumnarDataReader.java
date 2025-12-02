

package xcom.utils4j.data.columnar.api.annotations ;


import java.lang.annotation.ElementType ;
import java.lang.annotation.Retention ;
import java.lang.annotation.RetentionPolicy ;
import java.lang.annotation.Target ;


/**
 * Annotations to mark Data Column Readers.
 *
 * @author c.j.gregory
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface AColumnarDataReader {
	String[] sourceType();
}
