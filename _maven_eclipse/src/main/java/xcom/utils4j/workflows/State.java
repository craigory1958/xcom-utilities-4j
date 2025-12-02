

package xcom.utils4j.workflows ;


import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.workflows.api.annotations.IState ;


@Log
public class State implements IState {

	/**
	 *
	 */
	String name ;


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null ;
	}


	/**
	 * @param name
	 */
	public State(final String name) {
		this.name = name ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name ;
	}
}
