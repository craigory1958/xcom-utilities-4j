

package xcom.utils4j.gui ;


import java.time.temporal.ChronoUnit ;
import java.util.ArrayList ;
import java.util.concurrent.TimeUnit ;
import java.util.function.ToLongFunction ;

import javax.swing.JFrame ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


public class DurationDial extends Dial<Long> {

	private static final long serialVersionUID = 1L ;


	@Log
	public DurationDial(final int radius, final String title, final ChronoUnit units) {

		super(radius, title, units) ;

		Initilaize(this) ;
	}

	@Log
	public DurationDial(final int radius, final int begAngle, final int endAngle, final String title, final ChronoUnit units) {

		super(radius, begAngle, endAngle, title, units) ;

		Initilaize(this) ;
	}


	@Log
	void Initilaize(final DurationDial $) {

		$.isPerUnit = false ;
		$.events = new ArrayList<>() ;
	}


	@Log
	@Override
	void updateDial() {

		if ( events.size() >= minEventsToRecord ) {
			curValue = events.get(events.size() - 1) ;
			avgValue = (float) (events.stream().mapToLong(new ToLongFunction<Long>() {
				@Override
				public long applyAsLong(Long f) {
					return (long) f ;
				}
			}).sum()) / (float) events.size() ;
			maxValue = (events.stream().mapToLong(new ToLongFunction<Long>() {
				@Override
				public long applyAsLong(Long f) {
					return (long) f ;
				}
			}).max().orElse((long) curValue)) ;
		}
	}


	@Log
	public static void main(final String[] args) throws InterruptedException {

		final JFrame f = new JFrame("DurationDial") ;
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;

		final DurationDial gauge = new DurationDial(100, 45, 315, "Duration", ChronoUnit.MILLIS) ;
		f.add(gauge) ;

		f.setSize(gauge.calcComponentDimension()) ;
		f.setVisible(true) ;

		while ( true ) {
			TimeUnit.MILLISECONDS.sleep((int) (100f + (Math.random() * (1000f - 100f)))) ;
			if ( gauge != null )
				gauge.recordEvent((long) (100 + (Math.random() * (1000 - 100)))) ;
		}
	}
}
