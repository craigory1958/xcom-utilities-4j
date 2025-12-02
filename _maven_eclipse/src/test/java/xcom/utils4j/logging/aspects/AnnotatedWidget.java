

package xcom.utils4j.logging.aspects ;


import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.logging.aspects.api.annotations.NoLog ;


@Log
public class AnnotatedWidget {

	public AnnotatedWidget(final Object parm1, @NoLog final Object parm2) {}


	public AnnotatedWidget() {}


	public void methodWParamsWOReturn(final Object parm1, @NoLog final Object parm2) {}


	public void methodWOParamsWOReturn() {}


	public Object methodWParamsWReturn(final Object parm1, @NoLog final Object parm2) {
		return null ;
	}


	@NoLog
	public Object methodWOParamsWReturn() {
		return null ;
	}
}
