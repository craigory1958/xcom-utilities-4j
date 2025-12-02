

package xcom.utils4j.gui ;


import java.time.LocalDateTime ;
import java.time.temporal.ChronoUnit ;
import java.util.ArrayList ;
import java.util.Comparator ;
import java.util.concurrent.TimeUnit ;

import javax.swing.JFrame ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


@Log
public class FrequencyDial extends Dial<LocalDateTime> {

	private static final long serialVersionUID = -6978446364689575009L ;


	public FrequencyDial(final int radius, final String title, final ChronoUnit units) {

		super(radius, title, units) ;

		Initilaize(this) ;
	}

	public FrequencyDial(final int radius, final int begAngle, final int endAngle, final String title, final ChronoUnit units) {

		super(radius, begAngle, endAngle, title, units) ;

		Initilaize(this) ;
	}


	void Initilaize(final FrequencyDial $) {

		$.isPerUnit = true ;
		$.events = new ArrayList<>() ;
	}


	@Override
	void updateDial() {

		if ( dialEventsRecorded >= minEventsToRecord ) {

			final LocalDateTime cBeg = events.get(events.size() - 2) ;
			final LocalDateTime cEnd = events.get(events.size() - 1) ;
			curValue = 1.0f / ChronoUnit.MILLIS.between(cBeg, cEnd) ;

			final LocalDateTime aBeg = events.stream().min(Comparator.naturalOrder()).orElse(null) ;
			final LocalDateTime aEnd = events.stream().max(Comparator.naturalOrder()).orElse(null) ;
			avgValue = (float) (events.size() - 1) / (float) ChronoUnit.MILLIS.between(aBeg, aEnd) ;

			maxValue = (curValue > maxValue ? curValue : maxValue) ;
		}
	}


	public static void main(final String[] args) throws InterruptedException {

		final JFrame f = new JFrame("FrequencyDial") ;
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;

		final FrequencyDial gauge = new FrequencyDial(150, "Frequency", ChronoUnit.SECONDS) ;
		f.add(gauge) ;

		f.setSize(gauge.calcComponentDimension()) ;
		f.setVisible(true) ;

		while ( true ) {
			TimeUnit.MILLISECONDS.sleep((int) (100f + (Math.random() * (1000f - 100f)))) ;
			if ( gauge != null )
				gauge.recordEvent(LocalDateTime.now()) ;
		}
	}
}
