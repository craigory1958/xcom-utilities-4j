

package xcom.utils4j.data.structured.list ;


import java.util.List ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


@Log
public abstract class Lists {


	private Lists() {
		throw new UnsupportedOperationException() ;
	}

	
	public static <T> T last(final List<T> list) {
		return list.get(list.size() - 1) ;
	}
}
