

package xcom.utils4j.data.structure.map ;


import java.util.Map ;


public interface IKeyBuilder<K, V> {

	IValueBuilder<K, V> key(K key) ;

	Map<K, V> build() ;
}
