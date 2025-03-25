

package xcom.utils4j.data.structured.list ;


import java.util.List ;


public class Lists {

	public static <T> T last(final List<T> list) {
		return list.get(list.size() - 1) ;
	}
}
