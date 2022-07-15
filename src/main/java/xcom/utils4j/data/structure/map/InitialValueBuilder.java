

package xcom.utils4j.data.structure.map ;


public class InitialValueBuilder<K> {

	final K key ;

	public InitialValueBuilder(final K key) {
		this.key = key ;
	}

	public <V> IKeyBuilder<K, V> value(final V value) {
		return new MapBuilder<K, V>().key(key).value(value) ;
	}
}
