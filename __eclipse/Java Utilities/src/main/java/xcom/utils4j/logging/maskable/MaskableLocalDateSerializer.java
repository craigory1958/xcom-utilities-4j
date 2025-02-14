

package xcom.utils4j.logging.maskable ;


import java.io.IOException ;
import java.time.LocalDate ;
import java.time.format.DateTimeFormatter ;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator ;
import com.fasterxml.jackson.databind.SerializerProvider ;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer ;



public class MaskableLocalDateSerializer extends LocalDateSerializer {

	private static final long serialVersionUID = 5884496754236738873L ;


	protected MaskableLocalDateSerializer() {
		super() ;
	}


	protected MaskableLocalDateSerializer(final LocalDateSerializer base, final Boolean useTimestamp, final DateTimeFormatter dtf) {
		super(dtf) ;
	}


	@Override
	protected LocalDateSerializer withFormat(final Boolean useTimestamp, final DateTimeFormatter dtf, JsonFormat.Shape shape) {
		return new MaskableLocalDateSerializer(this, useTimestamp, dtf) ;
	}


	@Override
	public void serialize(final LocalDate date, final JsonGenerator g, final SerializerProvider provider) throws IOException {

		if ( g.getCodec() instanceof MaskableObjectMapper )
			g.writeString(MaskableUtils.MASKED_VALUE) ;
		else
			super.serialize(date, g, provider) ;
	}
}
