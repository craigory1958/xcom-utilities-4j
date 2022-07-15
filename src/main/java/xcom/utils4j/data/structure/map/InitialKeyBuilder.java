

package xcom.utils4j.data.structure.map ;


import java.util.HashMap ;
import java.util.Map ;


public class InitialKeyBuilder {

	public <K> InitialValueBuilder<K> key(final K key) {
		return new InitialValueBuilder<>(key) ;
	}

	public <K, V> Map<K, V> build() {
		return new HashMap<>() ;
	}
}
