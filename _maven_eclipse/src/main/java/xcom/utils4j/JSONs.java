

package xcom.utils4j ;


import java.lang.reflect.Type ;

import com.google.gson.Gson ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


@Log
public abstract class JSONs {

	private JSONs() {
		throw new UnsupportedOperationException() ;
	}


	/**
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T fromJSON(final String json, final Type type) {
		return new Gson().fromJson(json, type) ;
	}


	/**
	 * @param data
	 * @return
	 */
	public static String toJSON(final Object data) {
		return new Gson().toJson(data) ;
	}
}
