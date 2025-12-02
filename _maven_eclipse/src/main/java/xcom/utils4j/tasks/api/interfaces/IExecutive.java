

package xcom.utils4j.tasks.api.interfaces ;


import java.util.Properties ;

import javax.swing.JFrame ;


public interface IExecutive {

	public Properties getProps() ;

	public String getSettings() ;

	public IManager getManager() ;

	public JFrame getWindow() ;
}
