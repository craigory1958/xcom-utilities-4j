

package xcom.utils4j.data.structure.visitor ;


public interface iVisit<T> {

	void accept(iVisitor<T> visitor) ;
}
