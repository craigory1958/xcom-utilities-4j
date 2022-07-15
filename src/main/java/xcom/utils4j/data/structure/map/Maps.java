

package xcom.utils4j.data.structure.map ;


import java.util.ArrayList ;
import java.util.Collections ;
import java.util.Comparator ;
import java.util.HashMap ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import xcom.utils4j.logging.annotations.Log ;


/**
 * Map Utility class
 */
public abstract class Maps {

	/**
	 * Sorts a Map by Value using the Comparable interface.
	 *
	 * @param unsorted
	 *            Map to be sorted by its Values
	 * @return Sorted Map
	 */
	@Log
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> unsorted) {

		final Map<K, V> results = new LinkedHashMap<K, V>() ;

		final List<Map.Entry<K, V>> entries = new ArrayList<Map.Entry<K, V>>(unsorted.size()) ;
		entries.addAll(unsorted.entrySet()) ;

		Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {

			@Override
			public int compare(final Map.Entry<K, V> entry1, final Map.Entry<K, V> entry2) {
				return entry1.getValue().compareTo(entry2.getValue()) ;
			}
		}) ;

		for ( final Map.Entry<K, V> entry : entries )
			results.put(entry.getKey(), entry.getValue()) ;

		return results ;
	}


	/**
	 * Returns new Map.Entry with the input key and value
	 *
	 * @param key
	 *            Key to be in the Entry
	 * @param value
	 *            Value to be in the Entry
	 * @return Entry with input key and value
	 */
	@Log
	public static <K, V> Map.Entry<K, V> getInstanceOfEntry(final K key, final V value) {

		final Map<K, V> m = new HashMap<K, V>() ;
		m.put(key, value) ;

		return m.entrySet().iterator().next() ;
	}
}
