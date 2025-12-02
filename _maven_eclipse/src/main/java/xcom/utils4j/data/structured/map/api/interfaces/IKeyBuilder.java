

package xcom.utils4j.data.structured.map.api.interfaces ;


import java.util.Map ;


public interface IKeyBuilder<K, V> {

	IValueBuilder<K, V> key(K key) ;

	Map<K, V> build() ;
}
