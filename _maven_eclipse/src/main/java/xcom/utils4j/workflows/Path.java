

package xcom.utils4j.workflows ;


import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.workflows.api.annotations.IPath ;


@Log
public class Path implements IPath {

	/**
	 *
	 */
	String condition ;


	@Override
	public String getCondition() {
		return condition ;
	}


	/**
	 *
	 */
	String name ;


	@Override
	public String getName() {
		return name ;
	}


	/**
	 * @param name
	 * @param condition
	 */
	public Path(final String name, final String condition) {
		this.name = name ;
		this.condition = condition ;
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
