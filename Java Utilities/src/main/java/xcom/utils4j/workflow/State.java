

package xcom.utils4j.workflow ;


import xcom.utils4j.logging.aspects.api.annotations.Log ;


public class State implements iState {

	/**
	 *
	 */
	String name ;


	@Log
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null ;
	}


	/**
	 * @param name
	 */
	@Log
	public State(final String name) {
		this.name = name ;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Log
	@Override
	public String toString() {
		return name ;
	}
}
