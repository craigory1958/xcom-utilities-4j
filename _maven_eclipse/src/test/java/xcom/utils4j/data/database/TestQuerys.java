

package xcom.utils4j.data.database ;


import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.UUID ;


public abstract class TestQuerys {

	public static final String TestQuerys_UniqueKeyValue = UUID.randomUUID().toString() ;

	public static final String TestQuerys_TestTableName = "TEST_TABLE" ;
	public static final String TestQuerys_KeyColumnName = "KEY" ;
	public static final String TestQuerys_ValueColumnName = "VALUE" ;

	public static final String TestQuerys_Statement = "SELECT KEY, VALUE FROM TEST_TABLE" ;
	public static final String TestQuerys_StatementWhereClause = "SELECT * FROM TEST_TABLE WHERE KEY = '${KEY}'" ;
	public static final String TestQuerys_InjectedStatementWhereClause = "SELECT * FROM TEST_TABLE WHERE KEY = '" + TestQuerys_UniqueKeyValue + "'" ;

	public static final String TestQuerys_KeyValue_15000 = "15000" ;
	public static final String TestQuerys_ValueValue_15000 = "Pending" ;
	public static final String TestQuerys_KeyValue_15001 = "15001" ;
	public static final String TestQuerys_ValueValue_15001 = "Cancelled" ;
	public static final String TestQuerys_KeyValue_15002 = "15002" ;
	public static final String TestQuerys_ValueValue_15002 = "Approved" ;

	public static final Map<String, Object> TestQuerys_Parms = new HashMap<>() ;
	static {
		TestQuerys_Parms.put(TestQuerys_KeyColumnName, TestQuerys_UniqueKeyValue) ;
	}

	public static final List<Map<String, String>> TestQuerys_StatementResults ;
	static {
		TestQuerys_StatementResults = new ArrayList<>() ;

		TestQuerys_StatementResults.add(new LinkedHashMap<String, String>()) ;
		TestQuerys_StatementResults.get(0).put(TestQuerys_KeyColumnName, TestQuerys_KeyValue_15000) ;
		TestQuerys_StatementResults.get(0).put(TestQuerys_ValueColumnName, TestQuerys_ValueValue_15000) ;

		TestQuerys_StatementResults.add(new LinkedHashMap<String, String>()) ;
		TestQuerys_StatementResults.get(1).put(TestQuerys_KeyColumnName, TestQuerys_KeyValue_15001) ;
		TestQuerys_StatementResults.get(1).put(TestQuerys_ValueColumnName, TestQuerys_ValueValue_15001) ;

		TestQuerys_StatementResults.add(new LinkedHashMap<String, String>()) ;
		TestQuerys_StatementResults.get(2).put(TestQuerys_KeyColumnName, TestQuerys_KeyValue_15002) ;
		TestQuerys_StatementResults.get(2).put(TestQuerys_ValueColumnName, TestQuerys_ValueValue_15002) ;
	}

	
	private TestQuerys() {
		throw new UnsupportedOperationException() ;
	}
}
