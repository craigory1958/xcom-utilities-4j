

package xcom.utils4j.logging ;


import org.junit.runner.RunWith ;
import org.junit.runners.Suite ;

import xcom.utils4j.logging.aspects.__Test_xcom_utils4j_logging_aspects_ ;
import xcom.utils4j.logging.lambda.__Test_xcom_utils4j_logging_lambda_ ;
import xcom.utils4j.logging.maskable.__Test_xcom_utils4j_logging_maskable_ ;


//@formatter:off

@RunWith(Suite.class)
@Suite.SuiteClasses({
    __Test_xcom_utils4j_logging_aspects_.class,
    __Test_xcom_utils4j_logging_lambda_.class,
    __Test_xcom_utils4j_logging_maskable_.class,
    _Test_Loggers_.class,
})

//@formatter:on

public class __Test_xcom_utils4j_logging_ {}
