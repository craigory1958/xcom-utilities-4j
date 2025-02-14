

package xcom.utils4j.data.structured.map ;


import xcom.utils4j.data.structured.map.api.interfaces.iKeyBuilder ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;


public class InitialValueBuilder<K> {

	final K key ;


	@Log
	public InitialValueBuilder(final K key) {
		this.key = key ;
	}

	@Log
	public <V> iKeyBuilder<K, V> value(final V value) {
		return new MapBuilder<K, V>().key(key).value(value) ;
	}
}
