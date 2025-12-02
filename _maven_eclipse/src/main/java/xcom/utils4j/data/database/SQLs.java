

package xcom.utils4j.data.database ;


import static xcom.utils4j.data.database.SQLs.PropagateExceptionTypes.PropagateException ;

import java.sql.ResultSet ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.util.ArrayList ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


@Log
public abstract class SQLs {

	public enum PropagateExceptionTypes {
		PropagateException, //
		DoNotPropagateException, //
		;
	}


	private SQLs() {
		throw new UnsupportedOperationException() ;
	}


	public static Result executeQuery(final String sql, final Statement stmt, final RowMapper_ToMap mapper) {

		final Result result = new Result() ;
		ResultSet rs ;

		try {
			if ( !(result.error = !stmt.execute(sql)) ) {

				result.resultSet = new ArrayList<>() ;

				if ( (rs = stmt.getResultSet()) != null )
					while ( rs.next() )
						result.getResultSet().add(mapper.mapRow(rs)) ;
			}
		}
		catch ( final SQLException e ) {
			result.error = true ;
			result.exception = e ;
		}

		return result ;
	}

	public static Result executeQuery(final String sql, final Statement stmt, final RowMapper_ToMap mapper, final PropagateExceptionTypes propogate)
			throws SQLException {

		final Result result = executeQuery(sql, stmt, mapper) ;

		if ( propogate == PropagateException && (result.exception != null) )
			throw result.exception ;

		return result ;
	}


	public static Result executeUpdate(final String sql, final Statement stmt) {

		final Result result = new Result() ;

		try {
			if ( (result.error = stmt.execute(sql)) )
				throw new SQLException("SQL Update returned a ResultSet") ;

			result.updateCount = stmt.getUpdateCount() ;
		}
		catch ( final SQLException e ) {
			result.error = true ;
			result.exception = e ;
		}

		return result ;
	}

	public static Result executeUpdate(final String sql, final Statement stmt, final PropagateExceptionTypes propogate) throws SQLException {

		final Result result = executeUpdate(sql, stmt) ;

		if ( propogate == PropagateException && (result.exception != null) )
			throw result.exception ;

		return result ;
	}

	public static String toString(final String... strings) {

		final StringBuilder _string = new StringBuilder() ;
		for ( final String string : strings )
			_string.append("'").append(string).append("', ") ;

		_string.setLength(_string.length() - 2) ;

		return _string.toString() ;
	}
}
