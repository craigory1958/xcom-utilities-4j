

package xcom.utils4j.logging.maskable.api.interfaces ;


import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import com.fasterxml.jackson.core.JsonProcessingException ;

import xcom.utils4j.logging.maskable.Maskables ;


public interface IMaskable {

	static final Logger log = LoggerFactory.getLogger(IMaskable.class) ;


	static final FMaskableLambdaToJson toJson = new FMaskableLambdaToJson() {

		@Override
		public String fn(final Object o) {

			try {
				return Maskables.Mapper.writeValueAsString(o) ;
			}
			catch ( final JsonProcessingException e ) {
				log.info("Exception while parsing LoggingLambdaToJson: {}", e) ;
			}

			return "{}" ;
		}
	} ;
}
