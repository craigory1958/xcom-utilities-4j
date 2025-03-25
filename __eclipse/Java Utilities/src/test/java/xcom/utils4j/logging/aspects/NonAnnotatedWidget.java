

package xcom.utils4j.logging.aspects ;


import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.logging.aspects.api.annotations.NoLog ;


public class NonAnnotatedWidget {

	@Log
	public NonAnnotatedWidget(final Object parm1, @NoLog final Object parm2) {}


	@Log
	public NonAnnotatedWidget() {}


	@Log
	public void methodLogged_WParams_WOReturn(final Object parm1, @NoLog final Object parm2) {}


	@Log
	public void methodLogged_WOParams_WOReturn() {}


	@Log
	public Object methodLogged_WParams_WReturn(final Object parm1, @NoLog final Object parm2) {
		return null ;
	}


	public Object method_WOParams_WReturn() {
		return null ;
	}


	@NoLog
	public Object methodLogged_WOParams_WReturn() {
		return null ;
	}
}
