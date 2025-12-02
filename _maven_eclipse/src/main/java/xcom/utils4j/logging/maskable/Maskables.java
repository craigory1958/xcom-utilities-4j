

package xcom.utils4j.logging.maskable ;


import java.io.IOException ;

import com.fasterxml.jackson.core.JsonGenerator ;
import com.fasterxml.jackson.databind.AnnotationIntrospector ;
import com.fasterxml.jackson.databind.ObjectMapper ;
import com.fasterxml.jackson.databind.SerializerProvider ;
import com.fasterxml.jackson.databind.introspect.Annotated ;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair ;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector ;
import com.fasterxml.jackson.databind.ser.std.StdSerializer ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.logging.maskable.api.annotations.AMaskableValue ;


@Log
public abstract class Maskables {

	public static final String MASKED_VALUE = "****" ;

	public static final ObjectMapper Mapper = new MaskableObjectMapper() ;


	static {
		final AnnotationIntrospector inspector = Mapper.getSerializationConfig().getAnnotationIntrospector() ;
		final AnnotationIntrospector appendedInspector = AnnotationIntrospectorPair.pair(inspector, new MaskSensitiveDataAnnotationIntrospector()) ;
		Mapper.setAnnotationIntrospector(appendedInspector) ;
	}


	static class MaskSensitiveDataAnnotationIntrospector extends NopAnnotationIntrospector {

		private static final long serialVersionUID = -3804442580342851857L ;


		@Override
		public Object findSerializer(final Annotated am) {

			if ( am.getAnnotation(AMaskableValue.class) != null )
				return MaskSensitiveDataSerializer.class ;

			return null ;
		}
	}

	static class MaskSensitiveDataSerializer extends StdSerializer<Object> {

		private static final long serialVersionUID = 57735653777556753L ;


		public MaskSensitiveDataSerializer() {
			super(Object.class) ;
		}


		@Override
		public void serialize(final Object value, final JsonGenerator gen, final SerializerProvider provider) throws IOException {
			gen.writeString(MASKED_VALUE) ;
		}
	}
}
