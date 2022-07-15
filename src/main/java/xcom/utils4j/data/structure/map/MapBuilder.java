

package xcom.utils4j.data.structure.map ;


import java.util.AbstractMap ;
import java.util.ArrayList ;
import java.util.Collections ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.Map.Entry ;


public class MapBuilder<K, V> implements IKeyBuilder<K, V>, IValueBuilder<K, V> {

	final List<Entry<K, V>> entries ;
	K key ;

	public MapBuilder() {
		this.entries = new ArrayList<>() ;
	}

	@Override
	public IValueBuilder<K, V> key(final K key) {
		this.key = key ;
		return this ;
	}

	@Override
	public IKeyBuilder<K, V> value(final V value) {
		entries.add(new AbstractMap.SimpleEntry<>(key, value)) ;
		return this ;
	}

	@Override
	public Map<K, V> build() {

		final Map<K, V> map = new HashMap<K, V>() ;

		for ( final Entry<K, V> entry : entries )
			map.put(entry.getKey(), entry.getValue()) ;

		return Collections.unmodifiableMap(map) ;
	}

	public static InitialKeyBuilder builder() {
		return new InitialKeyBuilder() ;
	}
}
