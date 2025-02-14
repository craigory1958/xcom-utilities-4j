

package xcom.utils4j.workflow ;


public interface iTransitionListener<S, P> {
	void executeTransition(Workflow<S, P> workflow) ;
}
