

package xcom.utils4j.tasks ;


import java.awt.CardLayout ;
import java.awt.Container ;

import javax.swing.JComponent ;
import javax.swing.JPanel ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


@Log
public class MonitorGUI {

	String currentBillboard ;
	String currentDashboard ;

	JPanel billboards ;
	JPanel dashboards ;


	public String getCurrentDashboard() {
		return currentDashboard ;
	}

	public JPanel getBillboards() {
		return billboards ;
	}

	public Container getDashboards() {
		return dashboards ;
	}


	public MonitorGUI() {
		billboards = new JPanel(new CardLayout()) ;
		dashboards = new JPanel(new CardLayout()) ;
	}


	final public void setBillboard(final JComponent billboard) {

		currentBillboard = "monitor" ;
		billboards.add(billboard, currentBillboard) ;
		((CardLayout) billboards.getLayout()).show(billboards, currentBillboard) ;
	}


	final public void setDashboard(final JComponent dashboard) {

		currentDashboard = "default" ;
		dashboards.add(dashboard, currentDashboard) ;
		((CardLayout) dashboards.getLayout()).show(dashboards, currentDashboard) ;
	}


	final public void replaceBillboard(final JComponent billboard) {

		currentBillboard = "monitor" ;
		billboards.add(billboard, currentBillboard) ;
		((CardLayout) billboards.getLayout()).show(billboards, currentBillboard) ;
	}


	final public void replaceDashboard(final JComponent dashboard) {

		currentDashboard = "monitor" ;
		dashboards.add(dashboard, currentDashboard) ;
		((CardLayout) dashboards.getLayout()).show(dashboards, currentDashboard) ;
	}
}
