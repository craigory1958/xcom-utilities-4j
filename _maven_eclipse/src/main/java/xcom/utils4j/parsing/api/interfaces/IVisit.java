

package xcom.utils4j.parsing.api.interfaces ;


public interface IVisit<T> {

	void accept(IVisitor<T> visitor) ;
}
