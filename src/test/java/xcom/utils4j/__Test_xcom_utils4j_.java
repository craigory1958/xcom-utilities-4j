

package xcom.utils4j ;


import org.junit.runner.RunWith ;
import org.junit.runners.Suite ;

import xcom.utils4j.data._Test_Excels_;
import xcom.utils4j.data.__Test_xcom_utils4j_data_ ;
import xcom.utils4j.format.__Test_xcom_utils4j_format_ ;


//@formatter:off
@RunWith(Suite.class)
@Suite.SuiteClasses({
    __Test_xcom_utils4j_data_.class,
    __Test_xcom_utils4j_format_.class,
    _Test_Excels_.class,
    _Test_Factories_.class,
})
//@formatter:on

public class __Test_xcom_utils4j_ {}
