

package xcom.utils4j.data.structured.map ;


import java.util.HashMap ;
import java.util.Map ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


public class InitialKeyBuilder {

	@Log
	public <K> InitialValueBuilder<K> key(final K key) {
		return new InitialValueBuilder<>(key) ;
	}

	@Log
	public <K, V> Map<K, V> build() {
		return new HashMap<>() ;
	}
}
