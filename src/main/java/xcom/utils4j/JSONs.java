

package xcom.utils4j ;


import java.lang.reflect.Type ;

import com.google.gson.Gson ;

import xcom.utils4j.logging.annotations.Log ;


public class JSONs {

	/**
	 * @param json
	 * @param type
	 * @return
	 */
	@Log
	public static <T> T fromJSON(final String json, final Type type) {
		return new Gson().fromJson(json, type) ;
	}


	/**
	 * @param data
	 * @return
	 */
	@Log
	public static String toJSON(final Object data) {
		return new Gson().toJson(data) ;
	}
}
