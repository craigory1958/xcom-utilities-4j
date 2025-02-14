

package xcom.utils4j ;


import java.io.PrintWriter ;
import java.io.StringWriter ;
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

import xcom.utils4j.logging.aspects.api.annotations.Log ;


public abstract class CLArgs {

	public static final String CLArg_Help = "h" ;
	public static final String CLArgLong_Help = "help" ;
	public static final Option CLArgOption_Help = Option.builder(CLArg_Help).longOpt(CLArgLong_Help).desc("display this message").build() ;


	CommandLine cmd ;
	Map<String, Object> cmdArgs ;
	Properties props ;


	public CommandLine cmd() {
		return cmd ;
	}


	public Map<String, Object> cmdArgs() {
		return cmdArgs ;
	}


	public CLArgs(CommandLine cmd, Properties props) {
		this.cmd = cmd ;
		this.props = props ;
	}


	@Log
	public void decodeCommandLine(final CommandLine cmd, final Options options, final Properties props, CLArgs args, Logger logger) throws ParseException {

		if ( args.cmdArgs == null )
			args.cmdArgs = new HashMap<>() ;


		if ( !cmd.hasOption(CLArg_Help) )
			for ( final Option _option : options.getOptions() ) {
				final String optName = _option.getOpt() ;

				try {
					args.cmdArgs.put(optName, Reflection //
							.method("decodeCommandLineArg_" + optName).withReturnType(String.class) //
							.withParameterTypes(CommandLine.class, Properties.class, Map.class) //
							.in(args) //
							.invoke(cmd, props, args.cmdArgs) //
					) ;
				}
				catch ( final ReflectionError | IllegalArgumentException e ) {
					if ( !optName.equals(CLArg_Help) ) {
						logger.error("Could not find argument decoder for -{}", optName) ;
					}
				}
			}

		validateCommandLine(cmd, options) ;
	}

	@Log
	public abstract void validateCommandLine(CommandLine cmd, final Options options) throws ParseException;


	@Log
	public static void printToolHelp(final org.slf4j.Logger log, final String appName, final Options options, final String appDesc, final String appSee) {

		final StringWriter out = new StringWriter() ;
		final String usage = "java -cp dmt.jar " + appName + " [options]" ;
		final String desc = "\n" + appDesc + "\n\nOptions:" ;
		final String see = "\n" + appSee ;

		new HelpFormatter().printHelp(new PrintWriter(out), 200, usage, desc, options, 4, 2, see, false) ;

		log.info(out.toString()) ;
	}


	@Log
	public static void printToolUsage(final org.slf4j.Logger log, final String appName, final CLArgs _args, final String appDesc) {

		log.info("usage: java -cp dmt.jar " + appName + " [options]") ;
		log.info("\n" + appDesc + "\n\nOptions:") ;

		for ( final Entry<String, Object> _eArg : _args.cmdArgs().entrySet() )
			log.info("    -" + _eArg.getKey() + " " + _eArg.getValue()) ;
	}
}
