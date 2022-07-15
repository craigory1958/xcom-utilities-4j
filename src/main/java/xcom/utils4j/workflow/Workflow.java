

package xcom.utils4j.workflow ;


import edu.uci.ics.jung.graph.DirectedGraph ;
import xcom.utils4j.logging.annotations.Log ;


/**
 * @param <S>
 * @param <P>
 */
public class Workflow<S, P> extends StateDiagram<S, P> {

	/**
	 *
	 */
	S state ;


	@Log
	public void setState(final S state) {
		this.state = state ;
	}


	/**
	 *
	 */
	@Log
	public Workflow() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param graph
	 * @param state
	 */
	@Log
	public Workflow(final DirectedGraph<S, P> graph, final S state) {
		this.graph = graph ;
		this.state = state ;
	}


	/**
	 * @param values
	 * @param listener
	 * @return
	 */
	@Log
	public boolean transition(final Object values, final iTransitionListener<S, P> listener) {

		final boolean results = super.transition(state, values) ;


		if ( results ) {
			state = endState ;
			listener.executeTransition(this) ;
		}


		return results ;
	}


	@Log
	@Override
	public P getPath() {
		return super.getPath() ;
	}
}
