

package xcom.utils4j.logging.maskable ;


import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import com.fasterxml.jackson.core.JsonProcessingException ;


public interface Maskable {

	static final Logger log = LoggerFactory.getLogger(Maskable.class) ;


	static final MaskableLambdaToJson toJson = new MaskableLambdaToJson() {
		@Override
		public String fn(final Object o) {

			try {
				return MaskableUtils.Mapper.writeValueAsString(o) ;
			}
			catch ( final JsonProcessingException e ) {
				log.info("Exception while parsing LoggingLambdaToJson: {}", e) ;
			}

			return "{}" ;
		}
	} ;
}
