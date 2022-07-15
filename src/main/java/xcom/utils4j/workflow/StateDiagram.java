

package xcom.utils4j.workflow ;


import java.util.Iterator ;

import org.apache.commons.lang3.StringUtils ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import edu.uci.ics.jung.graph.DirectedGraph ;
import edu.uci.ics.jung.graph.Graph ;
import ognl.Ognl ;
import ognl.OgnlContext ;
import ognl.OgnlException ;
import xcom.utils4j.logging.annotations.Log ;


/**
 * A <code>StateDiagram</code> is a collection of states and paths between states represented as a directed graph. States are nodes within the graph and paths
 * are the edges between nodes. Each path may have an associated condition expression that must evaluate to <code>true</code> to transition between the
 * <code>begin-state</code> and <code>end-state</code> as identified by the graph edge.
 *
 * <br/>
 * <br/>
 * During transition, path conditions for all out-flowing paths from the <code>begin-state</code> are evaluated until the first condition evaluating to
 * <code>true</code> is found. If a validated path is found, an optional callback listener is invoked. The callback listener may override the
 * <code>end-state</code>.
 *
 * @param <S>
 *            - the <code>State</code> type.
 * @param <P>
 *            - the <code>Path</code> type.
 */
public class StateDiagram<S, P> {

	private static final Logger Logger = LoggerFactory.getLogger(StateDiagram.class) ;

	final static OgnlContext ctx = new OgnlContext() ;


	/**
	 * The directed graph representing states and paths between states of the state diagram.
	 */
	DirectedGraph<S, P> graph ;


	@Log
	public Graph<S, P> getGraph() {
		return graph ;
	}


	@Log
	public StateDiagram<S, P> setGraph(final DirectedGraph<S, P> graph) {
		this.graph = graph ;
		return this ;
	}


	/**
	 *
	 */
	S beginState ;


	@Log
	public S getBeginState() {
		return beginState ;
	}


	/**
	 *
	 */
	S endState ;


	@Log
	public S getEndState() {
		return endState ;
	}


	/**
	 *
	 */
	P path ;


	@Log
	public P getPath() {
		return path ;
	}


	/**
	 *
	 */
	@Log
	public StateDiagram() {}


	/**
	 * @param graph
	 */
	@Log
	public StateDiagram(final DirectedGraph<S, P> graph) {
		this.graph = graph ;
	}


	/**
	 * Process all out-flowing edges from a begin-state and transition to the end-state with the first path condition evaluating to <code>true</code>.
	 *
	 * <br/>
	 * <br/>
	 * When evaluating conditions:
	 *
	 * <ul>
	 * <li>path evaluation order is not guaranteed</li>
	 * <li><code>null</code> or empty path condition evaluate to <code>true</code></li>
	 * </ul>
	 *
	 * @param state
	 *            - begin-state within graph
	 * @param values
	 *            - expression values
	 * @return - transitioned to end-state (may be <code>null</code>)
	 */
	@Log
	public boolean transition(final S state, final Object values) {

		boolean results = false ;


		// Iterate all paths out-flowing from state and evaluate path condition until a valid condition is found ...

		P _path = null ;
		for ( final Iterator<P> iter = graph.getOutEdges(state).iterator(); !results && iter.hasNext(); /* no inc */ ) {
			_path = iter.next() ;
			Logger.trace("Evaluating path: {} ...", _path) ;

			// Evaluate path condition against parameter values ...
			// 'true' is substituted for a path condition of 'null'.
			try {
				final String expr = (StringUtils.trimToNull(((iPath) _path).getCondition()) == null ? "true" : ((iPath) _path).getCondition()) ;
				Logger.trace("Evaluating condition: {} ...", expr) ;
				results = (boolean) Ognl.getValue(Ognl.parseExpression(expr), ctx, values) ;
			}
			catch ( final OgnlException ex ) {
				ex.printStackTrace() ;
			}
		}


		// Save end-state and invoke callback if valid path condition found ...

		if ( results ) {
			beginState = state ;
			endState = graph.getDest(_path) ;
			path = _path ;

			Logger.trace("Valid condition found; transitioning to: {} ...", endState) ;
		}

		return results ;
	}
}
