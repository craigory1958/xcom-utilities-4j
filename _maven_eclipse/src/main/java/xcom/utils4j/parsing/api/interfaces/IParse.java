

package xcom.utils4j.parsing.api.interfaces ;


import org.antlr.v4.runtime.tree.ParseTree ;


public interface IParse {

//	ParseTree parse(String source) ;

	ParseTree parse(String source, String rule) ;
}
