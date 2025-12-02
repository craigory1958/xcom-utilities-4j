

package xcom.utils4j.logging.aspects ;


import java.lang.annotation.Annotation ;
import java.lang.reflect.Constructor ;

import org.apache.log4j.LogManager ;
import org.aspectj.lang.JoinPoint ;
import org.aspectj.lang.annotation.AfterReturning ;
import org.aspectj.lang.annotation.Aspect ;
import org.aspectj.lang.annotation.Before ;
import org.aspectj.lang.annotation.Pointcut ;
import org.aspectj.lang.reflect.CodeSignature ;
import org.aspectj.lang.reflect.ConstructorSignature ;
import org.aspectj.lang.reflect.MethodSignature ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.Signatures ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.logging.aspects.api.annotations.NoLog ;


@Aspect
public class Logging {

	enum JoinTypes {
		Constructor, Method
	}


	@Pointcut("within(@xcom.utils4j.logging.aspects.api.annotations.Log *)")
	public void annotatedLogClass() {}

	@Pointcut("within(@xcom.utils4j.logging.aspects.api.annotations.NoLog *)")
	public void annotatedNoLogClass() {}
	

	@Pointcut("execution(*.new(..))")
	public void anyConstructor() {}

	@Pointcut("execution(@xcom.utils4j.logging.aspects.api.annotations.Log *.new(..))")
	public void annotatedLogConstructor() {}

	@Pointcut("execution(@xcom.utils4j.logging.aspects.api.annotations.NoLog *.new(..))")
	public void annotatedNoLogConstructor() {}
	

	@Pointcut("execution(* *(..))")
	public void anyMethod() {}

	@Pointcut("execution(@xcom.utils4j.logging.aspects.api.annotations.Log * *(..))")
	public void annotatedLogMethod() {}

	@Pointcut("execution(@xcom.utils4j.logging.aspects.api.annotations.NoLog * *(..))")
	public void annotatedNoLogMethod() {}


	@Pointcut("annotatedLogClass() && anyConstructor() && !annotatedNoLogConstructor() || annotatedLogConstructor() && !annotatedNoLogClass()")
	public void logConstructor() {}

	@Pointcut("annotatedLogClass() && anyMethod() && !annotatedNoLogMethod() || annotatedLogMethod() && !annotatedNoLogClass()")
	public void logMethod() {}


	/**
	 * Hook point for logging before a constructor call.
	 *
	 * @param joinPoint
	 *            - Contains method data
	 */
	@Before("logConstructor()")
	public void logBeforeConstructor(final JoinPoint joinPoint) {
		logBefore(joinPoint, JoinTypes.Constructor) ;
	}


	/**
	 * Hook point for logging after a constructor call.
	 *
	 * @param joinPoint
	 *            - Contains method data
	 */
	@AfterReturning(pointcut = "logConstructor()")
	public void logAfterReturningConstructor(final JoinPoint joinPoint) {
		logAfter(joinPoint, JoinTypes.Constructor, null) ;
	}


	/**
	 * Hook point for logging before a method call.
	 *
	 * @param joinPoint
	 *            - Contains method data
	 */
	@Before("logMethod()")
	public void logBeforeMethod(final JoinPoint joinPoint) {
		logBefore(joinPoint, JoinTypes.Method) ;
	}


	/**
	 * Hook point for logging after a method call.
	 *
	 * @param joinPoint
	 *            - Contains method data
	 * @param returned
	 *            - The return value of the method if it has one
	 */
	@AfterReturning(pointcut = "logMethod()", returning = "returned")
	public void logAfterReturningMethod(final JoinPoint joinPoint, final Object returned) {
		logAfter(joinPoint, JoinTypes.Method, returned) ;
	}


	/**
	 * Executes before the annotated method or constructor.
	 *
	 * <pre>
	 *      Type0 method(Type1 arg1, Type2 arg2)
	 *
	 *      LOGGER.debug("[ENTER] Type0 method(Type1, Type2)")
	 *      LOGGER.trace("[  ...] arg1: |{}|", arg1);
	 *      LOGGER.trace("[  ...] arg2: |{}|", arg2);
	 * </pre>
	 *
	 * @param joinPoint
	 *            - Contains method data
	 * @param isMethod
	 *            - true if method, false if constructor
	 */
	void logBefore(final JoinPoint joinPoint, final JoinTypes joinType) {

		final org.slf4j.event.Level classLoggingLevel = classLoggingLevel(joinPoint) ;
		final org.slf4j.event.Level annotatedMethodLoggingLevel = annotatedMethodLoggingLevel(joinPoint, joinType) ;
		final org.slf4j.event.Level annotatedParametersLoggingLevel = annotatedParameterLoggingLevel(joinPoint, joinType) ;

		final CodeSignature signature = (CodeSignature) joinPoint.getStaticPart().getSignature() ;


		final org.apache.log4j.Logger log4jLogger = LogManager.getLogger(joinPoint.getSourceLocation().getWithinType()) ;
		final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(joinPoint.getSourceLocation().getWithinType()) ;

		if ( classLoggingLevel.ordinal() >= annotatedMethodLoggingLevel.ordinal() ) {

			String formatedSignature = null ;

			switch ( joinType ) {

				case Constructor:
					formatedSignature = Signatures.formatSignature((ConstructorSignature) signature) ;
					break ;

				case Method:
					formatedSignature = Signatures.formatSignature((MethodSignature) signature) ;
					break ;
			}

			log(classLoggingLevel, annotatedMethodLoggingLevel, slf4jLogger, log4jLogger, "[ENTER] {}", formatedSignature) ;
		}


		final String[] parmNames = signature.getParameterNames() ;
		final Object[] parmArgs = joinPoint.getArgs() ;
		Annotation[][] parmAnnotations = null ;

		switch ( joinType ) {

			case Constructor:
				final ConstructorSignature x = (ConstructorSignature) signature ;
				parmAnnotations = x.getConstructor().getParameterAnnotations() ;

				break ;

			case Method:
				final MethodSignature y = (MethodSignature) signature ;
				parmAnnotations = y.getMethod().getParameterAnnotations() ;
				break ;
		}

		for ( int p = 0; (p < parmNames.length); p++ ) {
			boolean isNoLog = false ;
			org.slf4j.event.Level annotatedSingleParameterLoggingLevel = annotatedParametersLoggingLevel ;

			for ( final Annotation annotation : parmAnnotations[p] ) {

				if ( annotation instanceof NoLog )
					isNoLog = true ;

				if ( annotation instanceof Log )
					annotatedSingleParameterLoggingLevel = ((Log) annotation).value()[0] ;
			}

			if ( !isNoLog && (classLoggingLevel.ordinal() >= annotatedSingleParameterLoggingLevel.ordinal()) )
				log(classLoggingLevel, annotatedSingleParameterLoggingLevel, slf4jLogger, log4jLogger, "[  ...] {}: |{}|", parmNames[p], parmArgs[p]) ;
		}
	}


	/**
	 * Executes after the annotated constructor or method is finished executing
	 *
	 * If a constructor it will execute a statement like: LOGGER.debug("[ EXIT] parseList(|{}|)", this)
	 *
	 * If a method it will execute a statement like: LOGGER.debug("[ EXIT] parseList(|{}|)", results) if there is a returnVal or: LOGGER.debug( "[ EXIT]
	 * parseList()"; if there is a void return value
	 *
	 * It will use the Logger associated with the class that is annotated.
	 *
	 * @param joinPoint
	 *            - Contains method data
	 * @param joinType
	 *            - Either JOIN_TYPE_CONSTRUCTOR or JOIN_TYPE_METHOD
	 * @param returnVal
	 *            - The return value of the method if it has one
	 */
	void logAfter(final JoinPoint joinPoint, final JoinTypes joinType, final Object returnVal) {

		final org.slf4j.event.Level classLoggingLevel = classLoggingLevel(joinPoint) ;
		final org.slf4j.event.Level annotatedMethodLoggingLevel = annotatedMethodLoggingLevel(joinPoint, joinType) ;

		final org.apache.log4j.Logger log4jLogger = LogManager.getLogger(joinPoint.getSourceLocation().getWithinType()) ;
		final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(joinPoint.getSourceLocation().getWithinType()) ;

		if ( classLoggingLevel.ordinal() >= annotatedMethodLoggingLevel.ordinal() )
			switch ( joinType ) {

				case Constructor:
					log(classLoggingLevel, annotatedMethodLoggingLevel, slf4jLogger, log4jLogger, "[ EXIT] {}({})",
							joinPoint.getStaticPart().getSignature().getDeclaringType().getSimpleName(), joinPoint.getThis()) ;
					break ;

				case Method:
					final MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature() ;
					final String returnType = methodSignature.getReturnType().toString() ;

					if ( returnType.equals("void") )
						log(classLoggingLevel, annotatedMethodLoggingLevel, slf4jLogger, log4jLogger, "[ EXIT] {}()", joinPoint.getSignature().getName()) ;
					else
						log(classLoggingLevel, annotatedMethodLoggingLevel, slf4jLogger, log4jLogger, "[ EXIT] {}({})", joinPoint.getSignature().getName(),
								returnVal) ;

					break ;
			}
	}


	org.slf4j.event.Level classLoggingLevel(final JoinPoint joinPoint) {
		return convertLog4jToSlf4j(LogManager.getLogger(joinPoint.getSourceLocation().getWithinType()).getEffectiveLevel()) ;
	}


	org.slf4j.event.Level annotatedMethodLoggingLevel(final JoinPoint joinPoint, final JoinTypes joinType) {

		final Log loggingAnnotation ;

		switch ( joinType ) {

			case Constructor:
				final Constructor<?> constructor = ((ConstructorSignature) joinPoint.getSignature()).getConstructor() ;
				loggingAnnotation = constructor.getAnnotation(Log.class) ;
				break ;

			case Method:
				loggingAnnotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Log.class) ;
				break ;

			default:
				loggingAnnotation = null ;
				break ;
		}

		return (loggingAnnotation != null ? loggingAnnotation.value()[0] : org.slf4j.event.Level.DEBUG) ;
	}


	org.slf4j.event.Level annotatedParameterLoggingLevel(final JoinPoint joinPoint, final JoinTypes joinType) {

		Log log = null ;

		switch ( joinType ) {

			case Constructor:
				final Constructor<?> constructor = ((ConstructorSignature) joinPoint.getSignature()).getConstructor() ;
				log = ((constructor.getAnnotation(Log.class))) ;
				break ;

			case Method:
				log = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Log.class) ;
				break ;
		}

		return (log != null && log.value().length > 1 ? log.value()[1] : org.slf4j.event.Level.TRACE) ;
	}


	void log(final org.slf4j.event.Level classLoggingLevel, final org.slf4j.event.Level loggingLevel, final org.slf4j.Logger slf4jLogger,
			final org.apache.log4j.Logger log4jLogger, final String msg, final Object... objects) {

		log4jLogger.setLevel(convertSlf4jLevelToLog4j(loggingLevel)) ;

		switch ( loggingLevel ) {

			case TRACE:
				slf4jLogger.trace(msg, objects) ;
				break ;

			case DEBUG:
				slf4jLogger.debug(msg, objects) ;
				break ;

			case INFO:
				slf4jLogger.info(msg, objects) ;
				break ;

			case WARN:
				slf4jLogger.warn(msg, objects) ;
				break ;

			case ERROR:
				slf4jLogger.error(msg, objects) ;
				break ;
		}

		log4jLogger.setLevel(convertSlf4jLevelToLog4j(classLoggingLevel)) ;
	}


	org.apache.log4j.Level convertSlf4jLevelToLog4j(final org.slf4j.event.Level slf4jLevel) {
		return org.apache.log4j.Level.toLevel(slf4jLevel.toString()) ;
	}


	org.slf4j.event.Level convertLog4jToSlf4j(final org.apache.log4j.Level log4jLevel) {
		return org.slf4j.event.Level.valueOf(log4jLevel.toString()) ;
	}
}
