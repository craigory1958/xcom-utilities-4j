

package xcom.utils4j.data ;


import org.junit.runner.RunWith ;
import org.junit.runners.Suite ;

import xcom.utils4j.data.columnar.__Test_xcom_utils4j_data_columnar_ ;
import xcom.utils4j.data.database.__Test_xcom_utils4j_data_database_ ;
import xcom.utils4j.data.structured.__Test_xcom_utils4j_data_structured_ ;


//@formatter:off

@RunWith(Suite.class)
@Suite.SuiteClasses({
	__Test_xcom_utils4j_data_columnar_.class,
	__Test_xcom_utils4j_data_database_.class,
	__Test_xcom_utils4j_data_structured_.class,
	_Test_Columns_.class,
	_Test_Excels_.class,
    _Test_Obfuscate_.class,
})

//@formatter:on

public class __Test_xcom_utils4j_data_ {}
