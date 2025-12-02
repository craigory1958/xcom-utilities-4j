

package xcom.utils4j.logging.maskable ;


import com.fasterxml.jackson.databind.ObjectMapper ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


@Log
public class MaskableObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -4175722432784544760L ;
}
