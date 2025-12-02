

package xcom.utils4j.tasks.demo ;


import static java.awt.BorderLayout.CENTER ;

import java.awt.CardLayout ;
import java.awt.Image ;
import java.io.IOException ;
import java.util.Properties ;

import javax.imageio.ImageIO ;
import javax.swing.ImageIcon ;
import javax.swing.JComponent ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;

import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.tasks.MonitorGUI ;
import xcom.utils4j.tasks.misc.AnnotatedWorkableManager ;


@Log
public class DemoManager extends AnnotatedWorkableManager {

	private static final Logger Logger = LoggerFactory.getLogger(DemoManager.class) ;


	public DemoManager(final String[] args, final Properties props, final MonitorGUI gui) {
		super(args, props, gui) ;
	}


	@Override
	public JComponent createGUIDashboard(final Properties props) {

		final JPanel _dashboard = new JPanel(new CardLayout()) ;

		try {
			final String resSpec = "/" + (this.getClass().getPackage().getName()).replaceAll("\\.", "/") + "/under_construction.png" ;
			Logger.debug("under_construction.png: @{}", resSpec) ;

			final ImageIcon image = new ImageIcon(ImageIO.read(getClass().getResource(resSpec))) ;
			final ImageIcon scaled = new ImageIcon(image.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH)) ;

			_dashboard.add(new JLabel(scaled), CENTER) ;
		}
		catch ( final IOException e ) {
			e.printStackTrace() ;
		}


		return _dashboard ;
	}
}
