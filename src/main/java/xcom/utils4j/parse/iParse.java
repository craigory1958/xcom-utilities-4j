

package xcom.utils4j.parse ;


import org.antlr.v4.runtime.tree.ParseTree ;


public interface iParse {

//	ParseTree parse(String source) ;

	ParseTree parse(String source, String rule) ;
}
