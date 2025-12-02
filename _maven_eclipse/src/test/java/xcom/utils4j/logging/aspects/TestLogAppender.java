

package xcom.utils4j.logging.aspects ;


import ch.qos.logback.classic.spi.ILoggingEvent ;
import ch.qos.logback.core.AppenderBase ;


public class TestLogAppender extends AppenderBase<ILoggingEvent> {
	private final StringBuilder logs = new StringBuilder() ;

	@Override
    protected void append(ILoggingEvent eventObject) {
        logs.append(eventObject.getFormattedMessage()).append("/n");
    }

	public String getLogs() {
		return logs.toString() ;
	}
}
