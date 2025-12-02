

package xcom.utils4j.data.database ;


import java.sql.SQLException ;
import java.util.List ;
import java.util.Map ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


@Log
public class Result {

	boolean error ;
	SQLException exception ;
	int updateCount ;
	List<Map<String, String>> resultSet ;


	public Result() {
		error = false ;
	}

	public Result(final boolean error) {
		this.error = error ;
	}

	public Result(final boolean error, final Result r) {
		this.error = error ;
		exception = r.exception ;
		updateCount = r.updateCount ;
		resultSet = r.resultSet ;
	}


	public boolean isError() {
		return error ;
	}


	public int getUpdateCount() {
		return updateCount ;
	}


	public List<Map<String, String>> getResultSet() {
		return resultSet ;
	}


	@Override
	public String toString() {
		return super.toString() + ", error: " + error //
				+ (exception != null ? ", exception: " + exception.getMessage() : "") //
				+ (updateCount != 0 ? ", updateCount: " + updateCount : "") //
				+ (resultSet != null ? ", resultSet.size(): " + resultSet.size() : "") //
		;
	}
}
