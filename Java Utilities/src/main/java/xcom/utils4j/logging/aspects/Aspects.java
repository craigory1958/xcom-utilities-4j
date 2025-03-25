

package xcom.utils4j.logging.aspects ;


import java.lang.annotation.Annotation ;
import java.lang.reflect.Method ;
import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.List ;
import java.util.function.Supplier ;

import org.aspectj.lang.JoinPoint ;
import org.aspectj.lang.ProceedingJoinPoint ;
import org.aspectj.lang.annotation.Pointcut ;
import org.aspectj.lang.reflect.MethodSignature ;
import org.springframework.http.ResponseEntity ;
import org.springframework.util.StopWatch ;
import org.springframework.web.bind.annotation.PathVariable ;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestParam ;

import com.fasterxml.jackson.core.util.RequestPayload ;

import xcom.utils4j.logging.lambda.Logger ;
import xcom.utils4j.logging.maskable.Maskable ;


public abstract class Aspects {

	static final List<Class<?>> AnnotatedWithPathVariables ;
	static {
		final Class<?>[] _AnnotatedWithPathVariables = { PathVariable.class } ;
		AnnotatedWithPathVariables = Arrays.asList(_AnnotatedWithPathVariables) ;
	}

	static final List<Class<?>> AnnotatedWithRequestParams ;
	static {
		final Class<?>[] _AnnotatedWithRequestParams = { RequestParam.class } ;
		AnnotatedWithRequestParams = Arrays.asList(_AnnotatedWithRequestParams) ;
	}

	static final List<Class<?>> AnnotatedWithBody ;
	static {
		final Class<?>[] _AnnotatedWithBody = { RequestBody.class, RequestPayload.class } ;
		AnnotatedWithBody = Arrays.asList(_AnnotatedWithBody) ;
	}


	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)" //
			+ "|| @annotation(org.springframework.web.bind.annotation.GetMapping)" //
			+ "|| @annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void webBindAnnotationMappings() {}

	@Pointcut("@annotation(org.springframework.ws.server.endpoint.annotation.PayloadRoot)")
	public void xmlBindAnnotationMappings() {}


	public static void doBefore(final String id, final JoinPoint jp, final Logger log) {

		final Object[] args = jp.getArgs() ;

		log.info("** {} ** @{}.{}() request vars{}, params{}, body{}", //
				new Supplier<Object>() {
					@Override
					public Object get() {
						return id.toUpperCase() ;
					}
				}, //
				new Supplier<Object>() {
					@Override
					public Object get() {
						return jp.getTarget().getClass().getSimpleName() ;
					}
				}, //
				new Supplier<Object>() {
					@Override
					public Object get() {
						return jp.getSignature().getName() ;
					}
				}, //
				new Supplier<Object>() {
					@Override
					public Object get() {
						return Aspects.getPathVariables(args, jp) ;
					}
				}, //
				new Supplier<Object>() {
					@Override
					public Object get() {
						return Aspects.getRequestParams(args, jp) ;
					}
				}, //
				new Supplier<Object>() {
					@Override
					public Object get() {
						return Maskable.toJson.fn(Aspects.getRequestBody(args, jp)) ;
					}
				}) ;
	}


	public static Object doAround(final String id, final ProceedingJoinPoint jp, final Logger log) throws Throwable {

		final StopWatch sw = new StopWatch(Aspects.class.getSimpleName()) ;

		try {
			sw.start() ;

			return jp.proceed() ;
		}
		finally {
			sw.stop() ;

			log.info("** {} ** @{}.{}() execution time {} seconds", //
					new Supplier<Object>() {
						@Override
						public Object get() {
							return id.toUpperCase() ;
						}
					}, //
					new Supplier<Object>() {
						@Override
						public Object get() {
							return jp.getTarget().getClass().getSimpleName() ;
						}
					}, //
					new Supplier<Object>() {
						@Override
						public Object get() {
							return jp.getSignature().getName() ;
						}
					}, //
					new Supplier<Object>() {
						@Override
						public Object get() {
							return sw.getTotalTimeSeconds() ;
						}
					}) ;
		}
	}


	public static void doAfterReturning(final String id, final JoinPoint jp, final Object retVal, final Logger log) {

		final Object _retVal = retVal instanceof ResponseEntity ? ((ResponseEntity<?>) retVal).getBody() : retVal ;

		log.info("** {} ** @{}.{}() response [{}]", //
				new Supplier<Object>() {
					@Override
					public Object get() {
						return id.toUpperCase() ;
					}
				}, //
				new Supplier<Object>() {
					@Override
					public Object get() {
						return jp.getTarget().getClass().getSimpleName() ;
					}
				}, //
				new Supplier<Object>() {
					@Override
					public Object get() {
						return jp.getSignature().getName() ;
					}
				}, //
				new Supplier<Object>() {
					@Override
					public Object get() {
						return Maskable.toJson.fn(_retVal) ;
					}
				}) ; //
	}


	static Object[] getPathVariables(final Object[] args, final JoinPoint jp) {
		return getAnnotatedArgs(args, jp, AnnotatedWithPathVariables) ;
	}


	static Object[] getRequestParams(final Object[] args, final JoinPoint jp) {
		return getAnnotatedArgs(args, jp, AnnotatedWithRequestParams) ;
	}


	static Object[] getRequestBody(final Object[] args, final JoinPoint jp) {
		return getAnnotatedArgs(args, jp, AnnotatedWithBody) ;
	}


	static Object[] getAnnotatedArgs(final Object[] args, final JoinPoint jp, final List<Class<?>> annotatedWith) {

		final List<Object> _args = new ArrayList<>() ;

		final Method m = ((MethodSignature) jp.getSignature()).getMethod() ;
		final Annotation[][] parameterAnnotations = m.getParameterAnnotations() ;

		for ( int i = 0; i < parameterAnnotations.length; i++ )
			for ( final Annotation annotation : parameterAnnotations[i] )
				if ( annotatedWith.contains(annotation.annotationType()) )
					_args.add(args[i]) ;

		return _args.toArray() ;
	}
}
