

package xcom.utils4j.tasks.demo ;


import java.lang.annotation.ElementType ;
import java.lang.annotation.Retention ;
import java.lang.annotation.RetentionPolicy ;
import java.lang.annotation.Target ;


@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ADemoWorkable {

	boolean disabled() default false;

	String selector() default "Demo";

	String worker() default "xcom.utils4j.tasks.demo.DemoWorker";
}
