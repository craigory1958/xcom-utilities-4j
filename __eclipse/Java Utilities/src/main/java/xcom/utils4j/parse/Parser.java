

package xcom.utils4j.parse ;


import org.antlr.v4.runtime.ANTLRInputStream ;
import org.antlr.v4.runtime.CharStream ;
import org.antlr.v4.runtime.CommonTokenStream ;
import org.antlr.v4.runtime.Lexer ;
import org.antlr.v4.runtime.TokenStream ;
import org.antlr.v4.runtime.tree.ParseTree ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.Reflects ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;


public class Parser implements iParse {

	private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class) ;


//	String grammar ;

	Lexer lexer ;

//	String packageName ;

	org.antlr.v4.runtime.Parser parser ;

	public org.antlr.v4.runtime.Parser getParser() {
		return parser ;
	}

//	String rule ;


	@Log
	public Parser(final String grammar, final String packageName /* , final String rule */) {

//		this.grammar = grammar ;
//		this.packageName = packageName ;
//		this.rule = rule ;

		// Instantiate lexer ...
		{
			final String classname = (packageName.isEmpty() ? "" : packageName + '.') + grammar + "Lexer" ;
			lexer = (Lexer) Reflects.loadClass(classname).withParameterTypes(CharStream.class).newInstance(new ANTLRInputStream("")) ;
			LOGGER.debug("Instantiated lexer '{}'", lexer.getClass().getName()) ;
		}

		// Instantiate parser ...
		{
			final String classname = (packageName.isEmpty() ? "" : packageName + '.') + grammar + "Parser" ;
			parser = (org.antlr.v4.runtime.Parser) Reflects.loadClass(classname).withParameterTypes(TokenStream.class)
					.newInstance(new CommonTokenStream(lexer)) ;
			LOGGER.debug("Instantiated parser '{}'", getParser().getClass().getName()) ;
		}
	}


	@Log
	public Parser(final org.antlr.v4.runtime.Parser parser) {
		this.parser = parser ;
	}


//	@Log
//	@Override
//	public ParseTree parse(final String source) {
//		return parse(source, rule) ;
//	}


	@Log
	@Override
	public ParseTree parse(final String source, final String rule) {

		lexer.setInputStream(new ANTLRInputStream(source)) ;
		parser.reset() ;

		return (ParseTree) Reflects.method(rule).in(getParser()).invoke() ;
	}
}
