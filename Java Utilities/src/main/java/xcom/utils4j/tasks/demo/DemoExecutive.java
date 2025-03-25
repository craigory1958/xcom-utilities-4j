

package xcom.utils4j.tasks.demo ;


import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.tasks.ExecutiveScaffold ;


public class DemoExecutive extends ExecutiveScaffold {

	public DemoExecutive(final String[] args) {
		super(args) ;
	}


	@Log
	public static void main(final String[] args) {

		final DemoExecutive $ = new DemoExecutive(args) ;
		$.bootstrapSwing($.getWindow(), $.getProps(), $.getManager(), $.getSettings()) ;
	}
}
