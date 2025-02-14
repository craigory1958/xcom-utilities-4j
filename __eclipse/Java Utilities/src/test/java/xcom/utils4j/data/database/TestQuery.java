

package xcom.utils4j.data.database ;


import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.UUID ;


public class TestQuery {

	public static final String TestQuery_UniqueKeyValue = UUID.randomUUID().toString() ;

	public static final String TestQuery_TestTableName = "TEST_TABLE" ;
	public static final String TestQuery_KeyColumnName = "KEY" ;
	public static final String TestQuery_ValueColumnName = "VALUE" ;

	public static final String TestQuery_Statement = "SELECT KEY, VALUE FROM TEST_TABLE" ;
	public static final String TestQuery_StatementWhereClause = "SELECT * FROM TEST_TABLE WHERE KEY = '${KEY}'" ;
	public static final String TestQuery_InjectedStatementWhereClause = "SELECT * FROM TEST_TABLE WHERE KEY = '" + TestQuery_UniqueKeyValue + "'" ;

	public static final String TestQuery_KeyValue_15000 = "15000" ;
	public static final String TestQuery_ValueValue_15000 = "Pending" ;
	public static final String TestQuery_KeyValue_15001 = "15001" ;
	public static final String TestQuery_ValueValue_15001 = "Cancelled" ;
	public static final String TestQuery_KeyValue_15002 = "15002" ;
	public static final String TestQuery_ValueValue_15002 = "Approved" ;

	public static final Map<String, Object> TestQuery_Parms = new HashMap<>() ;
	static {
		TestQuery_Parms.put(TestQuery_KeyColumnName, TestQuery_UniqueKeyValue) ;
	}

	public static final List<Map<String, String>> TestQuery_StatementResults ;
	static {
		TestQuery_StatementResults = new ArrayList<>() ;

		TestQuery_StatementResults.add(new LinkedHashMap<String, String>()) ;
		TestQuery_StatementResults.get(0).put(TestQuery_KeyColumnName, TestQuery_KeyValue_15000) ;
		TestQuery_StatementResults.get(0).put(TestQuery_ValueColumnName, TestQuery_ValueValue_15000) ;

		TestQuery_StatementResults.add(new LinkedHashMap<String, String>()) ;
		TestQuery_StatementResults.get(1).put(TestQuery_KeyColumnName, TestQuery_KeyValue_15001) ;
		TestQuery_StatementResults.get(1).put(TestQuery_ValueColumnName, TestQuery_ValueValue_15001) ;

		TestQuery_StatementResults.add(new LinkedHashMap<String, String>()) ;
		TestQuery_StatementResults.get(2).put(TestQuery_KeyColumnName, TestQuery_KeyValue_15002) ;
		TestQuery_StatementResults.get(2).put(TestQuery_ValueColumnName, TestQuery_ValueValue_15002) ;
	}
}
