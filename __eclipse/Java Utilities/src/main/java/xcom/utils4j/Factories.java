

package xcom.utils4j ;


import static xcom.utils4j.format.Templator.UnixDelimiters ;

import java.util.HashMap ;
import java.util.Map ;

import org.apache.commons.lang3.StringUtils ;

import xcom.utils4j.format.Templator ;
import xcom.utils4j.format.Templator.Templator$Delimited ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;


/**
 * Build string classname from input parameters
 */
public class Factories {

	public static final String BuildClassnamePostfixTemplate = "${packageName}${packageSeparator}${classname}${implementationSeparator}${implementation}" ;

	public static final String BuildClassnamePrefixTemplate = "${packageName}${packageSeparator}${implementation}${implementationSeparator}${classname}" ;

	public static final String DefaultBuildClassnameTemplate = BuildClassnamePostfixTemplate ;

	public static final String DefaultImplementationSeparator = "_" ;


	/**
	 *
	 */
	final static Templator$Delimited tt = Templator.delimiters(UnixDelimiters) ;


	/**
	 * Construct an implementation classname from a factory classname.
	 *
	 * <br />
	 * <br />
	 * Given: a factory classname of <code>com.x.ReaderFactory</code> and an implementation of <code>CSV</code>
	 *
	 * <br />
	 * Returns: <code>com.x.Reader_CSV</code>
	 *
	 * @param factoryClassname
	 *            - fully qualified factory classname to base implementation classname upon. (In the form of <code>package.XxxFactory</code>.)
	 * @param implementation
	 *            - implementation to apply to classname
	 * @return - constructed implementation classname
	 */
	@Log
	public static String buildImplementationClassnameFromFactoryClassname(final String factoryClassname, final String implementation,
			final String implementationSeparator, final String buildClassnameTemplate) {

		if ( StringUtils.trimToNull(factoryClassname) == null )
			throw new IllegalArgumentException("Factory Classname cannot be 'null' or 'blank'.") ;


		final Map<String, Object> values = generateBuildImplementationValues(factoryClassname, implementation, implementationSeparator) ;

		values.put("classname", factoryClassname.substring(factoryClassname.lastIndexOf('.') + 1, factoryClassname.length() - 7)) ;

		final String _buildClassnameTemplate =
				(StringUtils.trimToNull(buildClassnameTemplate) == null ? DefaultBuildClassnameTemplate : buildClassnameTemplate) ;

		return tt.template(_buildClassnameTemplate).inject(values).toString() ;
	}


	@Log
	public static String buildImplementationClassnameFromFactoryClassname(final String factoryClassname, final String implementation) {
		return buildImplementationClassnameFromFactoryClassname(factoryClassname, implementation, DefaultImplementationSeparator,
				DefaultBuildClassnameTemplate) ;
	}


	/**
	 * Construct an implementation classname from a interface classname.
	 *
	 * <br />
	 * <br />
	 * Given: a factory classname of <code>com.x.iReader</code> and an implementation of <code>CSV</code>
	 *
	 * <br />
	 * Returns: <code>com.x.Reader_CSV</code>
	 *
	 * @param interfaceClassname
	 *            - fully qualified interface classname to base implementation classname upon. (In the form of <code>package.iXxx</code>.)
	 * @param implementation
	 *            - implementation to apply to classname
	 * @return - constructed implementation classname
	 */
	@Log
	public static String buildImplementationClassnameFromInterfaceClassname(final String interfaceClassname, final String implementation,
			final String implementationSeparator, final String buildClassnameTemplate) {

		if ( StringUtils.trimToNull(interfaceClassname) == null )
			throw new IllegalArgumentException("Interface Classname cannot be 'null' or 'blank'.") ;


		final Map<String, Object> values = generateBuildImplementationValues(interfaceClassname, implementation, implementationSeparator) ;

		values.put("classname", interfaceClassname.substring(interfaceClassname.lastIndexOf('.') + 2)) ;

		final String _buildClassnameTemplate =
				(StringUtils.trimToNull(buildClassnameTemplate) == null ? DefaultBuildClassnameTemplate : buildClassnameTemplate) ;

		return tt.template(_buildClassnameTemplate).inject(values).toString() ;
	}


	@Log
	public static String buildImplementationClassnameFromInterfaceClassname(final String interfaceClassname, final String implementation) {
		return buildImplementationClassnameFromInterfaceClassname(interfaceClassname, implementation, DefaultImplementationSeparator,
				DefaultBuildClassnameTemplate) ;
	}


	/**
	 * @param classname
	 * @param implementation
	 * @param implementationSeparator
	 * @return
	 */
	@Log
	static Map<String, Object> generateBuildImplementationValues(final String classname, final String implementation, final String implementationSeparator) {

		final Map<String, Object> results = new HashMap<String, Object>() ;
		results.put("implementation", implementation) ;


		final int endOfPackageName = classname.lastIndexOf('.') ;

		final String packageName = (endOfPackageName == -1 ? "" : classname.substring(0, endOfPackageName)) ;
		results.put("packageName", packageName) ;

		final String packageSeparator = (endOfPackageName == -1 ? "" : ".") ;
		results.put("packageSeparator", packageSeparator) ;

		String _implementationSeparator = (StringUtils.trimToNull(implementationSeparator) == null ? DefaultImplementationSeparator : implementationSeparator) ;
		_implementationSeparator = (StringUtils.trimToNull(implementation) == null ? null : _implementationSeparator) ;
		results.put("implementationSeparator", _implementationSeparator) ;

		return results ;
	}
}
