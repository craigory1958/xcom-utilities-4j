

package xcom.utils4j.workflows.api.annotations ;

import xcom.utils4j.workflows.Workflow ;

public interface ITransitionListener<S, P> {
	void executeTransition(Workflow<S, P> workflow) ;
}
