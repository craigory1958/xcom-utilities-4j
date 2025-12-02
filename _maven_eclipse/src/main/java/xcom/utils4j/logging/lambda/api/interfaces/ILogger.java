

package xcom.utils4j.logging.lambda.api.interfaces ;


import java.util.function.Supplier ;


public interface ILogger extends org.slf4j.Logger {

	org.slf4j.Logger getUnderlyingLogger() ;

	@Override
	default String getName() {
		return getUnderlyingLogger().getName() ;
	}

	@Override
	default boolean isTraceEnabled() {
		return getUnderlyingLogger().isTraceEnabled() ;
	}

	@Override
	default void trace(final String msg) {
		doLog(null, org.slf4j.event.Level.TRACE, msg, null, null) ;
	}

	@Override
	default void trace(final String format, final Object arg) {
		doLog(null, org.slf4j.event.Level.TRACE, format, new Object[] { arg }, null) ;
	}

	@Override
	default void trace(final String format, final Object arg1, final Object arg2) {
		doLog(null, org.slf4j.event.Level.TRACE, format, new Object[] { arg1, arg2 }, null) ;
	}

	@Override
	default void trace(final String format, final Object... arguments) {
		doLog(null, org.slf4j.event.Level.TRACE, format, arguments, null) ;
	}

	@Override
	default void trace(final String msg, final Throwable t) {
		doLog(null, org.slf4j.event.Level.TRACE, msg, null, t) ;
	}

	@Override
	default boolean isTraceEnabled(final org.slf4j.Marker marker) {
		return getUnderlyingLogger().isTraceEnabled(marker) ;
	}

	@Override
	default void trace(final org.slf4j.Marker marker, final String msg) {
		doLog(marker, org.slf4j.event.Level.TRACE, msg, null, null) ;
	}

	@Override
	default void trace(final org.slf4j.Marker marker, final String format, final Object arg) {
		doLog(marker, org.slf4j.event.Level.TRACE, format, new Object[] { arg }, null) ;
	}

	@Override
	default void trace(final org.slf4j.Marker marker, final String format, final Object arg1, final Object arg2) {
		doLog(marker, org.slf4j.event.Level.TRACE, format, new Object[] { arg1, arg2 }, null) ;
	}

	@Override
	default void trace(final org.slf4j.Marker marker, final String format, final Object... arguments) {
		doLog(marker, org.slf4j.event.Level.TRACE, format, arguments, null) ;
	}

	@Override
	default void trace(final org.slf4j.Marker marker, final String msg, final Throwable t) {
		doLog(marker, org.slf4j.event.Level.TRACE, msg, null, t) ;
	}

	@Override
	default boolean isDebugEnabled() {
		return getUnderlyingLogger().isDebugEnabled() ;
	}

	@Override
	default void debug(final String msg) {
		doLog(null, org.slf4j.event.Level.DEBUG, msg, null, null) ;
	}

	@Override
	default void debug(final String format, final Object arg) {
		doLog(null, org.slf4j.event.Level.DEBUG, format, new Object[] { arg }, null) ;
	}

	@Override
	default void debug(final String format, final Object arg1, final Object arg2) {
		doLog(null, org.slf4j.event.Level.DEBUG, format, new Object[] { arg1, arg2 }, null) ;
	}

	@Override
	default void debug(final String format, final Object... arguments) {
		doLog(null, org.slf4j.event.Level.DEBUG, format, arguments, null) ;
	}

	@Override
	default void debug(final String msg, final Throwable t) {
		doLog(null, org.slf4j.event.Level.DEBUG, msg, null, t) ;
	}

	@Override
	default boolean isDebugEnabled(final org.slf4j.Marker marker) {
		return getUnderlyingLogger().isDebugEnabled(marker) ;
	}

	@Override
	default void debug(final org.slf4j.Marker marker, final String msg) {
		doLog(marker, org.slf4j.event.Level.DEBUG, msg, null, null) ;
	}

	@Override
	default void debug(final org.slf4j.Marker marker, final String format, final Object arg) {
		doLog(marker, org.slf4j.event.Level.DEBUG, format, new Object[] { arg }, null) ;
	}

	@Override
	default void debug(final org.slf4j.Marker marker, final String format, final Object arg1, final Object arg2) {
		doLog(marker, org.slf4j.event.Level.DEBUG, format, new Object[] { arg1, arg2 }, null) ;
	}

	@Override
	default void debug(final org.slf4j.Marker marker, final String format, final Object... arguments) {
		doLog(marker, org.slf4j.event.Level.DEBUG, format, arguments, null) ;
	}

	@Override
	default void debug(final org.slf4j.Marker marker, final String msg, final Throwable t) {
		doLog(marker, org.slf4j.event.Level.DEBUG, msg, null, t) ;
	}

	@Override
	default boolean isInfoEnabled() {
		return getUnderlyingLogger().isInfoEnabled() ;
	}

	@Override
	default void info(final String msg) {
		doLog(null, org.slf4j.event.Level.INFO, msg, null, null) ;
	}

	@Override
	default void info(final String format, final Object arg) {
		doLog(null, org.slf4j.event.Level.INFO, format, new Object[] { arg }, null) ;
	}

	@Override
	default void info(final String format, final Object arg1, final Object arg2) {
		doLog(null, org.slf4j.event.Level.INFO, format, new Object[] { arg1, arg2 }, null) ;
	}

	@Override
	default void info(final String format, final Object... arguments) {
		doLog(null, org.slf4j.event.Level.INFO, format, arguments, null) ;
	}

	@Override
	default void info(final String msg, final Throwable t) {
		doLog(null, org.slf4j.event.Level.INFO, msg, null, t) ;
	}

	@Override
	default boolean isInfoEnabled(final org.slf4j.Marker marker) {
		return getUnderlyingLogger().isInfoEnabled(marker) ;
	}

	@Override
	default void info(final org.slf4j.Marker marker, final String msg) {
		doLog(marker, org.slf4j.event.Level.INFO, msg, null, null) ;
	}

	@Override
	default void info(final org.slf4j.Marker marker, final String format, final Object arg) {
		doLog(marker, org.slf4j.event.Level.INFO, format, new Object[] { arg }, null) ;
	}

	@Override
	default void info(final org.slf4j.Marker marker, final String format, final Object arg1, final Object arg2) {
		doLog(marker, org.slf4j.event.Level.INFO, format, new Object[] { arg1, arg2 }, null) ;
	}

	@Override
	default void info(final org.slf4j.Marker marker, final String format, final Object... arguments) {
		doLog(marker, org.slf4j.event.Level.INFO, format, arguments, null) ;
	}

	@Override
	default void info(final org.slf4j.Marker marker, final String msg, final Throwable t) {
		doLog(marker, org.slf4j.event.Level.INFO, msg, null, t) ;
	}

	@Override
	default boolean isWarnEnabled() {
		return getUnderlyingLogger().isWarnEnabled() ;
	}

	@Override
	default void warn(final String msg) {
		doLog(null, org.slf4j.event.Level.WARN, msg, null, null) ;
	}

	@Override
	default void warn(final String format, final Object arg) {
		doLog(null, org.slf4j.event.Level.WARN, format, new Object[] { arg }, null) ;
	}

	@Override
	default void warn(final String format, final Object arg1, final Object arg2) {
		doLog(null, org.slf4j.event.Level.WARN, format, new Object[] { arg1, arg2 }, null) ;
	}

	@Override
	default void warn(final String format, final Object... arguments) {
		doLog(null, org.slf4j.event.Level.WARN, format, arguments, null) ;
	}

	@Override
	default void warn(final String msg, final Throwable t) {
		doLog(null, org.slf4j.event.Level.WARN, msg, null, t) ;
	}

	@Override
	default boolean isWarnEnabled(final org.slf4j.Marker marker) {
		return getUnderlyingLogger().isWarnEnabled(marker) ;
	}

	@Override
	default void warn(final org.slf4j.Marker marker, final String msg) {
		doLog(marker, org.slf4j.event.Level.WARN, msg, null, null) ;
	}

	@Override
	default void warn(final org.slf4j.Marker marker, final String format, final Object arg) {
		doLog(marker, org.slf4j.event.Level.WARN, format, new Object[] { arg }, null) ;
	}

	@Override
	default void warn(final org.slf4j.Marker marker, final String format, final Object arg1, final Object arg2) {
		doLog(marker, org.slf4j.event.Level.WARN, format, new Object[] { arg1, arg2 }, null) ;
	}

	@Override
	default void warn(final org.slf4j.Marker marker, final String format, final Object... arguments) {
		doLog(marker, org.slf4j.event.Level.WARN, format, arguments, null) ;
	}

	@Override
	default void warn(final org.slf4j.Marker marker, final String msg, final Throwable t) {
		doLog(marker, org.slf4j.event.Level.WARN, msg, null, t) ;
	}

	@Override
	default boolean isErrorEnabled() {
		return getUnderlyingLogger().isErrorEnabled() ;
	}

	@Override
	default void error(final String msg) {
		doLog(null, org.slf4j.event.Level.ERROR, msg, null, null) ;
	}

	@Override
	default void error(final String format, final Object arg) {
		doLog(null, org.slf4j.event.Level.ERROR, format, new Object[] { arg }, null) ;
	}

	@Override
	default void error(final String format, final Object arg1, final Object arg2) {
		doLog(null, org.slf4j.event.Level.ERROR, format, new Object[] { arg1, arg2 }, null) ;
	}

	@Override
	default void error(final String format, final Object... arguments) {
		doLog(null, org.slf4j.event.Level.ERROR, format, arguments, null) ;
	}

	@Override
	default void error(final String msg, final Throwable t) {
		doLog(null, org.slf4j.event.Level.ERROR, msg, null, t) ;
	}

	@Override
	default boolean isErrorEnabled(final org.slf4j.Marker marker) {
		return getUnderlyingLogger().isErrorEnabled(marker) ;
	}

	@Override
	default void error(final org.slf4j.Marker marker, final String msg) {
		doLog(marker, org.slf4j.event.Level.ERROR, msg, null, null) ;
	}

	@Override
	default void error(final org.slf4j.Marker marker, final String format, final Object arg) {
		doLog(marker, org.slf4j.event.Level.ERROR, format, new Object[] { arg }, null) ;
	}

	@Override
	default void error(final org.slf4j.Marker marker, final String format, final Object arg1, final Object arg2) {
		doLog(marker, org.slf4j.event.Level.ERROR, format, new Object[] { arg1, arg2 }, null) ;
	}

	@Override
	default void error(final org.slf4j.Marker marker, final String format, final Object... arguments) {
		doLog(marker, org.slf4j.event.Level.ERROR, format, arguments, null) ;
	}

	@Override
	default void error(final org.slf4j.Marker marker, final String msg, final Throwable t) {
		doLog(marker, org.slf4j.event.Level.ERROR, msg, null, t) ;
	}

	/**
	 * {@link #trace(java.lang.String, java.lang.Object)} with a lambda argument supplier.
	 */
	default void trace(final String format, final Supplier<?> argSupplier1) {
		doLog(null, org.slf4j.event.Level.TRACE, format, new Supplier<?>[] { argSupplier1 }, null) ;
	}

	/**
	 * {@link #trace(java.lang.String, java.lang.Object, java.lang.Object)} with two lambda argument suppliers.
	 */
	default void trace(final String format, final Supplier<?> argSupplier1, final Supplier<?> argSupplier2) {
		doLog(null, org.slf4j.event.Level.TRACE, format, new Supplier<?>[] { argSupplier1, argSupplier2 }, null) ;
	}

	/**
	 * {@link #trace(java.lang.String, java.lang.Object...)} with 3 or more lambda argument suppliers.
	 */
	default void trace(final String format, final Supplier<?>... argSuppliers) {
		doLog(null, org.slf4j.event.Level.TRACE, format, argSuppliers, null) ;
	}

	/**
	 * {@link #trace(java.lang.String)} with a lambda message supplier.
	 */
	default void trace(final Supplier<String> msgSupplier) {
		doLog(null, org.slf4j.event.Level.TRACE, msgSupplier, null) ;
	}

	/**
	 * {@link #trace(java.lang.String, java.lang.Throwable)} with a lambda message supplier.
	 */
	default void trace(final Supplier<String> msgSupplier, final Throwable t) {
		doLog(null, org.slf4j.event.Level.TRACE, msgSupplier, t) ;
	}

	/**
	 * {@link #trace(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object)} with a lambda argument supplier.
	 */
	default void trace(final org.slf4j.Marker marker, final String format, final Supplier<?> argSupplier1) {
		doLog(marker, org.slf4j.event.Level.TRACE, format, new Supplier<?>[] { argSupplier1 }, null) ;
	}

	/**
	 * {@link #trace(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)} with two lambda argument suppliers.
	 */
	default void trace(final org.slf4j.Marker marker, final String format, final Supplier<?> argSupplier1, final Supplier<?> argSupplier2) {
		doLog(marker, org.slf4j.event.Level.TRACE, format, new Supplier<?>[] { argSupplier1, argSupplier2 }, null) ;
	}

	/**
	 * {@link #trace(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object...)} with 3 or more lambda argument suppliers.
	 */
	default void trace(final org.slf4j.Marker marker, final String format, final Supplier<?>... argSuppliers) {
		doLog(marker, org.slf4j.event.Level.TRACE, format, argSuppliers, null) ;
	}

	/**
	 * {@link #trace(org.slf4j.org.slf4j.Marker, java.lang.String)} with a lambda message supplier.
	 */
	default void trace(final org.slf4j.Marker marker, final Supplier<String> msgSupplier) {
		doLog(marker, org.slf4j.event.Level.TRACE, msgSupplier, null) ;
	}

	/**
	 * {@link #trace(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Throwable)} with a lambda message supplier.
	 */
	default void trace(final org.slf4j.Marker marker, final Supplier<String> msgSupplier, final Throwable t) {
		doLog(marker, org.slf4j.event.Level.TRACE, msgSupplier, t) ;
	}

	/**
	 * {@link #debug(java.lang.String, java.lang.Object)} with a lambda argument supplier.
	 */
	default void debug(final String format, final Supplier<?> argSupplier1) {
		doLog(null, org.slf4j.event.Level.DEBUG, format, new Supplier<?>[] { argSupplier1 }, null) ;
	}

	/**
	 * {@link #debug(java.lang.String, java.lang.Object, java.lang.Object)} with two lambda argument suppliers.
	 */
	default void debug(final String format, final Supplier<?> argSupplier1, final Supplier<?> argSupplier2) {
		doLog(null, org.slf4j.event.Level.DEBUG, format, new Supplier<?>[] { argSupplier1, argSupplier2 }, null) ;
	}

	/**
	 * {@link #debug(java.lang.String, java.lang.Object...)} with 3 or more lambda argument suppliers.
	 */
	default void debug(final String format, final Supplier<?>... argSuppliers) {
		doLog(null, org.slf4j.event.Level.DEBUG, format, argSuppliers, null) ;
	}

	/**
	 * {@link #debug(java.lang.String)} with a lambda message supplier.
	 */
	default void debug(final Supplier<String> msgSupplier) {
		doLog(null, org.slf4j.event.Level.DEBUG, msgSupplier, null) ;
	}

	/**
	 * {@link #debug(java.lang.String, java.lang.Throwable)} with a lambda message supplier.
	 */
	default void debug(final Supplier<String> msgSupplier, final Throwable t) {
		doLog(null, org.slf4j.event.Level.DEBUG, msgSupplier, t) ;
	}

	/**
	 * {@link #debug(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object)} with a lambda argument supplier.
	 */
	default void debug(final org.slf4j.Marker marker, final String format, final Supplier<?> argSupplier1) {
		doLog(marker, org.slf4j.event.Level.DEBUG, format, new Supplier<?>[] { argSupplier1 }, null) ;
	}

	/**
	 * {@link #debug(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)} with two lambda argument suppliers.
	 */
	default void debug(final org.slf4j.Marker marker, final String format, final Supplier<?> argSupplier1, final Supplier<?> argSupplier2) {
		doLog(marker, org.slf4j.event.Level.DEBUG, format, new Supplier<?>[] { argSupplier1, argSupplier2 }, null) ;
	}

	/**
	 * {@link #debug(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object...)} with 3 or more lambda argument suppliers.
	 */
	default void debug(final org.slf4j.Marker marker, final String format, final Supplier<?>... argSuppliers) {
		doLog(marker, org.slf4j.event.Level.DEBUG, format, argSuppliers, null) ;
	}

	/**
	 * {@link #debug(org.slf4j.org.slf4j.Marker, java.lang.String)} with a lambda message supplier.
	 */
	default void debug(final org.slf4j.Marker marker, final Supplier<String> msgSupplier) {
		doLog(marker, org.slf4j.event.Level.DEBUG, msgSupplier, null) ;
	}

	/**
	 * {@link #debug(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Throwable)} with a lambda message supplier.
	 */
	default void debug(final org.slf4j.Marker marker, final Supplier<String> msgSupplier, final Throwable t) {
		doLog(marker, org.slf4j.event.Level.DEBUG, msgSupplier, t) ;
	}


	/**
	 * {@link #info(java.lang.String, java.lang.Object)} with a lambda argument supplier.
	 */
	default void info(final String format, final Supplier<?> argSupplier1) {
		doLog(null, org.slf4j.event.Level.INFO, format, new Supplier<?>[] { argSupplier1 }, null) ;
	}

	/**
	 * {@link #info(java.lang.String, java.lang.Object, java.lang.Object)} with two lambda argument suppliers.
	 */
	default void info(final String format, final Supplier<?> argSupplier1, final Supplier<?> argSupplier2) {
		doLog(null, org.slf4j.event.Level.INFO, format, new Supplier<?>[] { argSupplier1, argSupplier2 }, null) ;
	}

	/**
	 * {@link #info(java.lang.String, java.lang.Object...)} with 3 or more lambda argument suppliers.
	 */
	default void info(final String format, final Supplier<?>... argSuppliers) {
		doLog(null, org.slf4j.event.Level.INFO, format, argSuppliers, null) ;
	}

	/**
	 * {@link #info(java.lang.String)} with a lambda message supplier.
	 */
	default void info(final Supplier<String> msgSupplier) {
		doLog(null, org.slf4j.event.Level.INFO, msgSupplier, null) ;
	}

	/**
	 * {@link #info(java.lang.String, java.lang.Throwable)} with a lambda message supplier.
	 */
	default void info(final Supplier<String> msgSupplier, final Throwable t) {
		doLog(null, org.slf4j.event.Level.INFO, msgSupplier, t) ;
	}

	/**
	 * {@link #info(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object)} with a lambda argument supplier.
	 */
	default void info(final org.slf4j.Marker marker, final String format, final Supplier<?> argSupplier1) {
		doLog(marker, org.slf4j.event.Level.INFO, format, new Supplier<?>[] { argSupplier1 }, null) ;
	}

	/**
	 * {@link #info(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)} with two lambda argument suppliers.
	 */
	default void info(final org.slf4j.Marker marker, final String format, final Supplier<?> argSupplier1, final Supplier<?> argSupplier2) {
		doLog(marker, org.slf4j.event.Level.INFO, format, new Supplier<?>[] { argSupplier1, argSupplier2 }, null) ;
	}

	/**
	 * {@link #info(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object...)} with 3 or more lambda argument suppliers.
	 */
	default void info(final org.slf4j.Marker marker, final String format, final Supplier<?>... argSuppliers) {
		doLog(marker, org.slf4j.event.Level.INFO, format, argSuppliers, null) ;
	}

	/**
	 * {@link #info(org.slf4j.org.slf4j.Marker, java.lang.String)} with a lambda message supplier.
	 */
	default void info(final org.slf4j.Marker marker, final Supplier<String> msgSupplier) {
		doLog(marker, org.slf4j.event.Level.INFO, msgSupplier, null) ;
	}

	/**
	 * {@link #info(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Throwable)} with a lambda message supplier.
	 */
	default void info(final org.slf4j.Marker marker, final Supplier<String> msgSupplier, final Throwable t) {
		doLog(marker, org.slf4j.event.Level.INFO, msgSupplier, t) ;
	}

	/**
	 * {@link #warn(java.lang.String, java.lang.Object)} with a lambda argument supplier.
	 */
	default void warn(final String format, final Supplier<?> argSupplier1) {
		doLog(null, org.slf4j.event.Level.WARN, format, new Supplier<?>[] { argSupplier1 }, null) ;
	}

	/**
	 * {@link #warn(java.lang.String, java.lang.Object, java.lang.Object)} with two lambda argument suppliers.
	 */
	default void warn(final String format, final Supplier<?> argSupplier1, final Supplier<?> argSupplier2) {
		doLog(null, org.slf4j.event.Level.WARN, format, new Supplier<?>[] { argSupplier1, argSupplier2 }, null) ;
	}

	/**
	 * {@link #warn(java.lang.String, java.lang.Object...)} with 3 or more lambda argument suppliers.
	 */
	default void warn(final String format, final Supplier<?>... argSuppliers) {
		doLog(null, org.slf4j.event.Level.WARN, format, argSuppliers, null) ;
	}

	/**
	 * {@link #warn(java.lang.String)} with a lambda message supplier.
	 */
	default void warn(final Supplier<String> msgSupplier) {
		doLog(null, org.slf4j.event.Level.WARN, msgSupplier, null) ;
	}

	/**
	 * {@link #warn(java.lang.String, java.lang.Throwable)} with a lambda message supplier.
	 */
	default void warn(final Supplier<String> msgSupplier, final Throwable t) {
		doLog(null, org.slf4j.event.Level.WARN, msgSupplier, t) ;
	}

	/**
	 * {@link #warn(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object)} with a lambda argument supplier.
	 */
	default void warn(final org.slf4j.Marker marker, final String format, final Supplier<?> argSupplier1) {
		doLog(marker, org.slf4j.event.Level.WARN, format, new Supplier<?>[] { argSupplier1 }, null) ;
	}

	/**
	 * {@link #warn(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)} with two lambda argument suppliers.
	 */
	default void warn(final org.slf4j.Marker marker, final String format, final Supplier<?> argSupplier1, final Supplier<?> argSupplier2) {
		doLog(marker, org.slf4j.event.Level.WARN, format, new Supplier<?>[] { argSupplier1, argSupplier2 }, null) ;
	}

	/**
	 * {@link #warn(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object...)} with 3 or more lambda argument suppliers.
	 */
	default void warn(final org.slf4j.Marker marker, final String format, final Supplier<?>... argSuppliers) {
		doLog(marker, org.slf4j.event.Level.WARN, format, argSuppliers, null) ;
	}

	/**
	 * {@link #warn(org.slf4j.org.slf4j.Marker, java.lang.String)} with a lambda message supplier.
	 */
	default void warn(final org.slf4j.Marker marker, final Supplier<String> msgSupplier) {
		doLog(marker, org.slf4j.event.Level.WARN, msgSupplier, null) ;
	}

	/**
	 * {@link #warn(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Throwable)} with a lambda message supplier.
	 */
	default void warn(final org.slf4j.Marker marker, final Supplier<String> msgSupplier, final Throwable t) {
		doLog(marker, org.slf4j.event.Level.WARN, msgSupplier, t) ;
	}

	/**
	 * {@link #error(java.lang.String, java.lang.Object)} with a lambda argument supplier.
	 */
	default void error(final String format, final Supplier<?> argSupplier1) {
		doLog(null, org.slf4j.event.Level.ERROR, format, new Supplier<?>[] { argSupplier1 }, null) ;
	}

	/**
	 * {@link #error(java.lang.String, java.lang.Object, java.lang.Object)} with two lambda argument suppliers.
	 */
	default void error(final String format, final Supplier<?> argSupplier1, final Supplier<?> argSupplier2) {
		doLog(null, org.slf4j.event.Level.ERROR, format, new Supplier<?>[] { argSupplier1, argSupplier2 }, null) ;
	}

	/**
	 * {@link #error(java.lang.String, java.lang.Object...)} with 3 or more lambda argument suppliers.
	 */
	default void error(final String format, final Supplier<?>... argSuppliers) {
		doLog(null, org.slf4j.event.Level.ERROR, format, argSuppliers, null) ;
	}

	/**
	 * {@link #error(java.lang.String)} with a lambda message supplier.
	 */
	default void error(final Supplier<String> msgSupplier) {
		doLog(null, org.slf4j.event.Level.ERROR, msgSupplier, null) ;
	}

	/**
	 * {@link #error(java.lang.String, java.lang.Throwable)} with a lambda message supplier.
	 */
	default void error(final Supplier<String> msgSupplier, final Throwable t) {
		doLog(null, org.slf4j.event.Level.ERROR, msgSupplier, t) ;
	}

	/**
	 * {@link #error(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object)} with a lambda argument supplier.
	 */
	default void error(final org.slf4j.Marker marker, final String format, final Supplier<?> argSupplier1) {
		doLog(marker, org.slf4j.event.Level.ERROR, format, new Supplier<?>[] { argSupplier1 }, null) ;
	}

	/**
	 * {@link #error(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)} with two lambda argument suppliers.
	 */
	default void error(final org.slf4j.Marker marker, final String format, final Supplier<?> argSupplier1, final Supplier<?> argSupplier2) {
		doLog(marker, org.slf4j.event.Level.ERROR, format, new Supplier<?>[] { argSupplier1, argSupplier2 }, null) ;
	}

	/**
	 * {@link #error(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Object...)} with 3 or more lambda argument suppliers.
	 */
	default void error(final org.slf4j.Marker marker, final String format, final Supplier<?>... argSuppliers) {
		doLog(marker, org.slf4j.event.Level.ERROR, format, argSuppliers, null) ;
	}

	/**
	 * {@link #error(org.slf4j.org.slf4j.Marker, java.lang.String)} with a lambda message supplier.
	 */
	default void error(final org.slf4j.Marker marker, final Supplier<String> msgSupplier) {
		doLog(marker, org.slf4j.event.Level.ERROR, msgSupplier, null) ;
	}

	/**
	 * {@link #error(org.slf4j.org.slf4j.Marker, java.lang.String, java.lang.Throwable)} with a lambda message supplier.
	 */
	default void error(final org.slf4j.Marker marker, final Supplier<String> msgSupplier, final Throwable t) {
		doLog(marker, org.slf4j.event.Level.ERROR, msgSupplier, t) ;
	}

	void doLog(org.slf4j.Marker marker, org.slf4j.event.Level level, Supplier<String> msgSupplier, Throwable t) ;

	void doLog(org.slf4j.Marker marker, org.slf4j.event.Level level, String format, Supplier<?>[] argSuppliers, Throwable t) ;

	void doLog(org.slf4j.Marker marker, org.slf4j.event.Level level, String format, Object[] arguments, Throwable t) ;
}
