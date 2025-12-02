

package xcom.utils4j ;


import org.junit.runner.RunWith ;
import org.junit.runners.Suite ;

import xcom.utils4j.data.__Test_xcom_utils4j_data_ ;
import xcom.utils4j.format.__Test_xcom_utils4j_format_ ;
import xcom.utils4j.gui.__Test_xcom_utils4j_gui_ ;
import xcom.utils4j.logging.__Test_xcom_utils4j_logging_ ;
import xcom.utils4j.parsing.__Test_xcom_utils4j_parsing_ ;
import xcom.utils4j.resources.__Test_xcom_utils4j_resources_ ;
import xcom.utils4j.tasks.__Test_xcom_utils4j_tasks_ ;
import xcom.utils4j.workflows.__Test_xcom_utils4j_workflows_ ;


//@formatter:off

@RunWith(Suite.class)
@Suite.SuiteClasses({
    __Test_xcom_utils4j_data_.class,
    __Test_xcom_utils4j_format_.class,
    __Test_xcom_utils4j_gui_.class,
    __Test_xcom_utils4j_logging_.class,
    __Test_xcom_utils4j_parsing_.class,
    __Test_xcom_utils4j_resources_.class,
    __Test_xcom_utils4j_tasks_.class,
    __Test_xcom_utils4j_workflows_.class,
    _Test_CLArgs_.class,
    _Test_Copyright_.class,
    _Test_Enums_.class,
    _Test_Factories_.class,
    _Test_JSONs_.class,
    _Test_Nuts_.class,
    _Test_Reflects_.class,
    _Test_Signatures_.class,
})

//@formatter:on

public class __Test_xcom_utils4j_ {}
