

package xcom.utils4j.data.structure.map ;


public interface IValueBuilder<K, V> {

	IKeyBuilder<K, V> value(V value) ;
}
