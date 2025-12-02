

package xcom.utils4j.logging.aspects ;


import org.junit.Before ;
import org.junit.Test ;
import org.slf4j.LoggerFactory ;

import ch.qos.logback.classic.Logger ;


public class _Test_xcom_utils4j_loging_aspects$AnnotatedWidget {


	private TestLogAppender logAppender ;


	@Before
	public void setupBeforeEachTest() {

		logAppender = new TestLogAppender() ; 
		logAppender.start() ;

		Logger logger = (Logger) LoggerFactory.getLogger(AnnotatedWidget.class) ;
		logger.addAppender(logAppender) ;
	}


	@Test
	public void _tes$AnnotatedWidgett() {

		AnnotatedWidget w = new AnnotatedWidget() ;
		w.methodWOParamsWOReturn() ;

		System.out.println(logAppender.getLogs()) ;
	}
}
