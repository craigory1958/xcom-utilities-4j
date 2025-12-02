

package xcom.utils4j.data.structured.map ;


import java.util.ArrayList ;
import java.util.Collections ;
import java.util.Comparator ;
import java.util.HashMap ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.TreeMap ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


/**
 * Map Utility class
 */
@Log
public abstract class Maps {

	private Maps() {
		throw new UnsupportedOperationException() ;
	}


	/**
	 * Sorts a Map by Key using a <code>TreeMap</code>.
	 *
	 * @param unsorted
	 *            Map to be sorted by its Values
	 * @return Sorted Map
	 */
	public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(final Map<K, V> unsorted) {
		return new TreeMap<>(unsorted) ;
	}

	/**
	 * Sorts a Map by Value using the Comparable interface.
	 *
	 * @param unsorted
	 *            Map to be sorted by its Values
	 * @return Sorted Map
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> unsorted) {

		final Map<K, V> results = new LinkedHashMap<>() ;

		final List<Map.Entry<K, V>> entries = new ArrayList<>(unsorted.size()) ;
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
	public static <K, V> Map.Entry<K, V> getInstanceOfEntry(final K key, final V value) {

		final Map<K, V> m = new HashMap<>() ;
		m.put(key, value) ;

		return m.entrySet().iterator().next() ;
	}

	public static <K, V> Map.Entry<K, V> firstEntry(final Map<K, V> map) {

		if ( (map == null) || map.isEmpty() )
			return null ;

		return map.entrySet().iterator().next() ;
	}

	public static <K, V> K firstEntryKey(final Map<K, V> map) {

		if ( (map == null) || map.isEmpty() )
			return null ;

		return Maps.firstEntry(map).getKey() ;
	}

	public static <K, V> V firstEntryValue(final Map<K, V> map) {

		if ( (map == null) || map.isEmpty() )
			return null ;

		return Maps.firstEntry(map).getValue() ;
	}

	public static <K, V> Map.Entry<K, V> lastEntry(final Map<K, V> map) {

		if ( (map == null) || map.isEmpty() )
			return null ;

		Map.Entry<K, V> entry = null ;
		for ( final Map.Entry<K, V> _entry : map.entrySet() )
			entry = _entry ;

		return entry ;
	}

	public static <K, V> K lastEntryKey(final Map<K, V> map) {

		if ( (map == null) || map.isEmpty() )
			return null ;

		return Maps.lastEntry(map).getKey() ;
	}

	public static <K, V> V lastEntryValue(final Map<K, V> map) {

		if ( (map == null) || map.isEmpty() )
			return null ;

		return Maps.lastEntry(map).getValue() ;
	}
}
