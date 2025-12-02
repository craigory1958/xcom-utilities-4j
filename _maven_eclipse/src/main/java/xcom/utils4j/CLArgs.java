

package xcom.utils4j ;


import static xcom.utils4j.CLArgs.ExitPolicyTypes.ExitOnHelpOrVersion ;

import java.io.PrintWriter ;
import java.io.StringWriter ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.Map ;
import java.util.Map.Entry ;
import java.util.Properties ;

import org.apache.commons.cli.CommandLine ;
import org.apache.commons.cli.HelpFormatter ;
import org.apache.commons.cli.Option ;
import org.apache.commons.cli.Options ;
import org.apache.commons.cli.ParseException ;
import org.fest.reflect.core.Reflection ;
import org.fest.reflect.exception.ReflectionError ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;
import org.slf4j.event.Level ;
import org.slf4j.spi.LoggingEventBuilder ;
import org.slf4j.spi.NOPLoggingEventBuilder ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


@Log
public abstract class CLArgs {

	public enum ExitPolicyTypes {
		DoNotExitOnHelpOrVersion, //
		ExitOnHelpOrVersion, //
	}


	private static final Logger Logger = LoggerFactory.getLogger(CLArgs.class) ;


	static final String Prop_AppClassname = "AppClassname" ;
	static final String Prop_AppDesc = "AppDesc" ;
	static final String Prop_AppName = "AppName" ;
	static final String Prop_AppSee = "AppSee" ;
	static final String Prop_AppUsage = "AppUsage" ;
	static final String Prop_AppVersion = "AppVersion" ;


	public static final String CLArg_Help = "h" ;
	public static final String CLArgLong_Help = "help" ;
	public static final Option CLArgOption_Help = Option.builder(CLArg_Help).longOpt(CLArgLong_Help).desc("display this info").build() ;

	public static final String CLArgLong_Log = "log" ;
	public static final Option CLArgOption_Log =
			Option.builder(null).longOpt(CLArgLong_Log).hasArg().argName("{event} [, {event} ...]").desc("logging events").build() ;

	public static final String CLArgLong_Version = "version" ;
	public static final Option CLArgOption_Version = Option.builder(null).longOpt(CLArgLong_Version).desc("display version").build() ;


	//@formatter:off

	String[] args ;
	
	CommandLine cmd ;
	public CommandLine cmd() { return cmd ; }
	
	Map<String, Object> decodedArgs ;
	public Map<String, Object> decodedArgs() { return decodedArgs ; }

	Map<String, Level> eventLoggers ;
//	public Map<String, LoggingEventBuilder> events() { return eventLoggers ; }
	
	Logger logger ;
	Properties props ;

	//@formatter:on


	public CLArgs(CommandLine cmd, Properties props, String[] args, Logger logger) {

		this.cmd = cmd ;
		this.args = args ;
		this.props = props ;
		this.logger = logger ;

		this.decodedArgs = new HashMap<>() ;
		this.eventLoggers = new HashMap<>() ;
	}


	public void loadAppProps(final Properties props, final String appClassname, final String appDesc, final String appName, final String appSee,
			final String appUsage, final String appVersion) {

		this.props = props ;

		this.props.put(Prop_AppClassname, appClassname) ;
		this.props.put(Prop_AppDesc, appDesc) ;
		this.props.put(Prop_AppName, appName) ;
		this.props.put(Prop_AppSee, appSee) ;
		this.props.put(Prop_AppUsage, appUsage) ;
		this.props.put(Prop_AppVersion, appVersion) ;

		Logger.debug("props: {}", this.props) ;
	}


	public LoggingEventBuilder getEventLogger(String event) {
		return (eventLoggers.containsKey(event) ? logger.atLevel(eventLoggers.get(event)) : NOPLoggingEventBuilder.singleton()) ;
	}


	public Map<String, Object> decodeCommandLineArgs(CommandLine cmd, Options options, Properties props, ExitPolicyTypes exitPolicy,
			Map<String, Object> decoded) {

		if ( decoded == null )
			decoded = new HashMap<>() ;

		decoded = decodeArg_HelpAndVersion(cmd, options, decoded, props, logger, exitPolicy) ;
		decoded = decodeArg_Log(cmd, options, decoded, props, logger) ;

		return decoded ;
	}


	public Map<String, Object> decodeCommandLine(final CLArgs $args, final Options options, final Properties props, Map<String, Object> decoded,
			final Logger logger) throws ParseException {

		if ( decoded == null )
			decoded = new HashMap<>() ;

		for ( final Option _option : $args.cmd.getOptions() ) {
			final String optName = _option.getLongOpt() ;

			try {
				decoded.put( //
						optName, //
						Reflection //
								.method("decodeArg_" + optName).withReturnType(String.class) //
								.withParameterTypes(CommandLine.class, Options.class, Map.class, Properties.class, Logger.class) //
								.in($args) //
								.invoke($args.cmd, options, decoded, props, logger) //
				) ;
			}
			catch ( final ReflectionError | IllegalArgumentException e ) {
				if ( !optName.equals(CLArgLong_Help) && !optName.equals(CLArgLong_Version) )
					logger.error("Could not find decoder for -{}", optName) ;
			}
		}

		validateCommandLine(cmd, options) ;

		return decoded ;
	}


	public abstract void validateCommandLine(CommandLine cmd, final Options options) throws ParseException ;


	public Map<String, Object> decodeArg_HelpAndVersion(final CommandLine cmd, final Options options, Map<String, Object> decoded, final Properties props,
			final Logger logger, final ExitPolicyTypes exitPolicy) {

		if ( decoded == null )
			decoded = new HashMap<>() ;

		if ( cmd.hasOption(CLArgLong_Help) )
			decoded = decodeArg_help(cmd, options, decoded, props, logger) ;

		if ( cmd.hasOption(CLArgLong_Version) )
			decoded = decodeArg_version(cmd, options, decoded, props, logger) ;

		if ( (exitPolicy == ExitOnHelpOrVersion) && (cmd.hasOption(CLArgLong_Help) || cmd.hasOption(CLArgLong_Version)) )
			System.exit(0) ;

		return decoded ;
	}


	public Map<String, Object> decodeArg_Log(final CommandLine cmd, final Options options, Map<String, Object> decoded, final Properties props,
			final Logger logger) {

		if ( decoded == null )
			decoded = new HashMap<>() ;

		if ( cmd.hasOption(CLArgLong_Log) )
			decoded = decodeArg_log(cmd, options, decoded, props, logger) ;

		return decoded ;
	}


	Map<String, Object> decodeArg_help(final CommandLine cmd, final Options options, final Map<String, Object> decoded, final Properties props,
			final Logger logger) {

		printToolHelp(props.getProperty(Prop_AppUsage), props.getProperty(Prop_AppDesc), props.getProperty(Prop_AppSee), options, logger) ;

		decoded.put(CLArgLong_Help, null) ;

		return decoded ;
	}


	Map<String, Object> decodeArg_log(final CommandLine cmd, final Options options, final Map<String, Object> decoded, final Properties props,
			final Logger logger) {

		Logger.debug("Logging events: {}", cmd.getOptionValue(CLArgLong_Log)) ;

		for ( String eventSpec : cmd.getOptionValue(CLArgLong_Log).split("[,]") ) {

			String[] eventTokens = eventSpec.split("[=]") ;
			if ( eventTokens.length < 2 )
				eventTokens = (eventSpec + "=INFO").split("[=]") ;

			Logger.debug("eventTokens: {}", Arrays.asList(eventTokens)) ;

			eventLoggers.put(eventTokens[0], Enums.valueOfIgnoreCase(Level.class, eventTokens[1])) ;
		}

		Logger.debug("eventLoggers: {}", eventLoggers) ;

		decoded.put(CLArgLong_Log, cmd.getOptionValue(CLArgLong_Log)) ;

		return decoded ;
	}


	static Map<String, Object> decodeArg_version(final CommandLine cmd, final Options options, final Map<String, Object> decoded, final Properties props,
			final Logger logger) {

		logger.info("--version: " + props.getProperty(Prop_AppVersion)) ;

		decoded.put(CLArgLong_Version, props.getProperty(Prop_AppVersion)) ;

		return decoded ;
	}


	//
	//
	//

	public static void printToolHelp(final String appUsage, final String appDesc, final String appSee, final Options options, final Logger logger) {

		final StringWriter out = new StringWriter() ;
		final String desc = "\n" + appDesc + "\n\nOptions:" ;
		final String see = "\n" + appSee ;

		new HelpFormatter().printHelp(new PrintWriter(out), 200, appUsage, desc, options, 4, 2, see, false) ;

		logger.info(out.toString()) ;
	}


	public static void printToolUsage(final String appUsage, final String appDesc, final Map<String, Object> decoded, final Logger logger) {

		logger.info("usage: {}", appUsage) ;
		logger.info("\n{}\n\nOptions:", appDesc) ;

		for ( final Entry<String, Object> _eArg : decoded.entrySet() )
			logger.info("    -" + _eArg.getKey() + " " + _eArg.getValue()) ;
	}
}
