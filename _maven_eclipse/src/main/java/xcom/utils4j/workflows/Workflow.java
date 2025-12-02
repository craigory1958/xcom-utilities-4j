

package xcom.utils4j.workflows ;


import edu.uci.ics.jung.graph.DirectedGraph ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.workflows.api.annotations.ITransitionListener ;


/**
 * @param <S>
 * @param <P>
 */
@Log
public class Workflow<S, P> extends StateDiagram<S, P> {

	/**
	 *
	 */
	S state ;


	public void setState(final S state) {
		this.state = state ;
	}


	/**
	 *
	 */
	public Workflow() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param graph
	 * @param state
	 */
	public Workflow(final DirectedGraph<S, P> graph, final S state) {
		this.graph = graph ;
		this.state = state ;
	}


	/**
	 * @param values
	 * @param listener
	 * @return
	 */
	public boolean transition(final Object values, final ITransitionListener<S, P> listener) {

		final boolean results = super.transition(state, values) ;


		if ( results ) {
			state = endState ;
			listener.executeTransition(this) ;
		}


		return results ;
	}


	@Override
	public P getPath() {
		return super.getPath() ;
	}
}
