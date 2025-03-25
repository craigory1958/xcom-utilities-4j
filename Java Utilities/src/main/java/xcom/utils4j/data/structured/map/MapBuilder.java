

package xcom.utils4j.data.structured.map ;


import java.util.AbstractMap ;
import java.util.ArrayList ;
import java.util.Collections ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.Map.Entry ;

import xcom.utils4j.data.structured.map.api.interfaces.iKeyBuilder ;
import xcom.utils4j.data.structured.map.api.interfaces.iValueBuilder ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;


public class MapBuilder<K, V> implements iKeyBuilder<K, V>, iValueBuilder<K, V> {

	final List<Entry<K, V>> entries ;

	K key ;


	@Log
	public MapBuilder() {
		this.entries = new ArrayList<>() ;
	}

	@Log
	@Override
	public iValueBuilder<K, V> key(final K key) {
		this.key = key ;
		return this ;
	}

	@Log
	@Override
	public iKeyBuilder<K, V> value(final V value) {
		entries.add(new AbstractMap.SimpleEntry<>(key, value)) ;
		return this ;
	}

	@Log
	@Override
	public Map<K, V> build() {

		final Map<K, V> map = new HashMap<>() ;

		for ( final Entry<K, V> entry : entries )
			map.put(entry.getKey(), entry.getValue()) ;

		return Collections.unmodifiableMap(map) ;
	}

	@Log
	public static InitialKeyBuilder builder() {
		return new InitialKeyBuilder() ;
	}
}
