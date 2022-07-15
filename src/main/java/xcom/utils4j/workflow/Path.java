

package xcom.utils4j.workflow ;


import xcom.utils4j.logging.annotations.Log ;


public class Path implements iPath {

	/**
	 *
	 */
	String condition ;


	@Log
	@Override
	public String getCondition() {
		return condition ;
	}


	/**
	 *
	 */
	String name ;


	@Log
	@Override
	public String getName() {
		return name ;
	}


	/**
	 * @param name
	 * @param condition
	 */
	@Log
	public Path(final String name, final String condition) {
		this.name = name ;
		this.condition = condition ;
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
