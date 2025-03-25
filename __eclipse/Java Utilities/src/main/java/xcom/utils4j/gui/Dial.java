

package xcom.utils4j.gui ;


import static xcom.utils4j.format.Templator.InjectFromArray ;
import static xcom.utils4j.format.Templator.UnixDelimiters ;

import java.awt.BasicStroke ;
import java.awt.Dimension ;
import java.awt.Font ;
import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.time.temporal.ChronoUnit ;
import java.util.ArrayList ;
import java.util.concurrent.Executors ;
import java.util.concurrent.ScheduledFuture ;
import java.util.concurrent.TimeUnit ;

import javax.swing.JComponent ;

import xcom.utils4j.format.Templator ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;


abstract public class Dial<T> extends JComponent {

	private static final long serialVersionUID = 1L ;


	Runnable updateDialListener = new Runnable() {

		@Log
		@Override
		public void run() {

			updateDial() ;

			if ( !events.isEmpty() )
				repaint() ;
		}
	} ;


	static int XOffsetDefault = 50 ;
	static int YOffsetDefault = 30 ;
	static int InitialUpdateDelayInMSDefault = 0 ;
	static int UpdateRateInMSDefault = 1000 ;
	static int MaxEventsToAverageDefault = 15 ;
	static int MinEventsToRecordDefault = 5 ;
	static String FormatDefault = "${precision}%s%s" ;


	int radius ;
	int begAngle ;
	int endAngle ;
	String title ;
	ChronoUnit units ;


	boolean isPerUnit ;
	String unitsTitle ;
	float unitFactor ;
	int xOffset ;
	int yOffset ;
	int initialUpdateDelayInMS ;
	int updateRateInMS ;
	int maxEventsToAverage ;
	int minEventsToRecord ;

	int startAngle ;
	int arcAngle ;
	float scale = 1.0f ;

	String avgFormat ;
	float avgValue = 0.0f ;
	String curFormat ;
	float curValue = 0.0f ;
	String maxFormat ;
	float maxValue = 0.0f ;

	ArrayList<T> events ;
	int dialEventsRecorded = 0 ;
	ScheduledFuture<?> updater ;


	public ScheduledFuture<?> getUpdater() {
		return updater ;
	}


	@Log
	public Dial(final int radius, final String title, final ChronoUnit units) {

		this.radius = radius ;
		begAngle = 45 ;
		endAngle = 315 ;
		this.title = title ;
		this.units = units ;

		Initilize(this) ;
	}


	@Log
	public Dial(final int radius, final int begAngle, final int endAngle, final String title, final ChronoUnit units) {

		this.radius = radius ;
		this.begAngle = begAngle ;
		this.endAngle = endAngle ;
		this.title = title ;
		this.units = units ;

		Initilize(this) ;
	}


	@Log
	void Initilize(final Dial<T> $) {

		$.startAngle = 270 - $.endAngle ;
		$.arcAngle = $.endAngle - $.begAngle ;

		String precision ;

		switch ( $.units ) {

			case MILLIS:
				$.unitsTitle = "ms" ;
				$.unitFactor = 1.0f ;
				precision = "%.0f" ;
				break ;

			case SECONDS:
				$.unitsTitle = "s" ;
				$.unitFactor = 1000.0f ;
				precision = "%.1f" ;
				break ;

			default:
				$.unitsTitle = "ms" ;
				$.unitFactor = 1.0f ;
				precision = "%.0f" ;
				break ;
		}

		$.xOffset = XOffsetDefault ;
		$.yOffset = YOffsetDefault ;
		$.initialUpdateDelayInMS = InitialUpdateDelayInMSDefault ;
		$.updateRateInMS = UpdateRateInMSDefault ;
		$.maxEventsToAverage = MaxEventsToAverageDefault ;
		$.minEventsToRecord = MinEventsToRecordDefault ;

		final Object[] p = { "precision", precision } ;
		$.avgFormat = Templator.delimiters(UnixDelimiters).template("avg " + FormatDefault).inject(InjectFromArray, p).toString() ;
		$.curFormat = Templator.delimiters(UnixDelimiters).template(FormatDefault).inject(InjectFromArray, p).toString() ;
		$.maxFormat = Templator.delimiters(UnixDelimiters).template("max " + FormatDefault).inject(InjectFromArray, p).toString() ;


		$.setPreferredSize($.calcComponentDimension()) ;

		$.updater = Executors.newScheduledThreadPool(1).scheduleWithFixedDelay($.updateDialListener, $.initialUpdateDelayInMS, $.updateRateInMS,
				TimeUnit.MILLISECONDS) ;
	}


	abstract void updateDial() ;

	@Log
	public void recordEvent(final T ts) {

		synchronized ( events ) {

			if ( events.size() >= maxEventsToAverage )
				events.remove(0) ;

			events.add(ts) ;
			dialEventsRecorded++ ;
		}
	}


	@Log
	public Dimension calcComponentDimension() {

		final int w = (radius * 2) + (xOffset * 2) + (xOffset / 2) ;
		final int h = (radius * 2) + (yOffset * 2) ;
		return new Dimension(w, h) ;
	}


	@Log
	@Override
	public void paintComponent(final Graphics g) {

		final float range = arcAngle ;

		if ( curValue > 0 ) {
			scale = ((maxValue * scale) > range ? (range * 0.95f) / maxValue : scale) ;
			scale = ((maxValue * scale) < (range * 0.85f) ? (range * 0.85f) / maxValue : scale) ;
		}

		final float _avg = avgValue * scale ;
		final float _cur = curValue * scale ;
		final float _max = maxValue * scale ;

		final float delta = ((range * 0.90f) - (maxValue * scale)) / 10 ;
		scale += delta / range ;


		final int x = xOffset ;
		final int y = yOffset ;
		final int xC = radius + xOffset ;
		final int yC = radius + yOffset ;
		final int w = 2 * radius ;
		final int h = 2 * radius ;


		super.paintComponent(g) ;
		final Graphics2D g2 = (Graphics2D) g ;

		{
			g2.setStroke(new BasicStroke(1)) ;
			g2.drawArc(xC - 2, yC - 2, 4, 4, 0, 360) ;    // Dial center
			g2.drawArc(x, y, w, h, startAngle, arcAngle) ;    // Dial

			g2.setStroke(new BasicStroke(3)) ;
			drawTickMark(-begAngle, xC, yC, radius, 14, g2, new BasicStroke(3)) ;    // Start tick
			drawTickMark(arcAngle - begAngle, xC, yC, radius, 14, g2, new BasicStroke(3)) ;    // End tick
		}

		{
			g2.setFont(g2.getFont().deriveFont(14.0f)) ;
			final float x1 = xC - (g2.getFontMetrics().stringWidth(title) * .5f) ;
			g2.drawString(title, x1, yC + 70) ;
		}


		if ( events.size() >= minEventsToRecord ) {

			// Value
			{
				final int x2 = (int) ((radius + 7) * Math.cos(Math.toRadians((int) _cur - begAngle))) ;
				final int y2 = (int) ((radius + 7) * Math.sin(Math.toRadians((int) _cur - begAngle))) ;

				g2.setStroke(new BasicStroke(1)) ;
				g2.drawLine(xC, yC, xC - x2, yC - y2) ;

				g2.setFont(g2.getFont().deriveFont(14.0f)) ;
				final String text = String.format(curFormat, curValue * unitFactor, (isPerUnit ? "/" : " "), unitsTitle) ;
				final float x1 = xC - (g2.getFontMetrics().stringWidth(text) * .5f) ;

				g2.setStroke(new BasicStroke(1)) ;
				g2.drawString(text, x1, yC + 50) ;
			}

			// Average
			{
				drawTickMark((int) _avg - begAngle, xC, yC, radius, 14, g2, new BasicStroke(2)) ;

				final String text = String.format(avgFormat, avgValue * unitFactor, (isPerUnit ? "/" : " "), unitsTitle) ;
				drawTickText(text, xC, yC, avgValue, _avg, g2, new BasicStroke(1), g2.getFont().deriveFont(12.0f)) ;
			}

			// Max
			{
				drawTickMark((int) _max - begAngle, xC, yC, radius, 14, g2, new BasicStroke(2)) ;

				final String text = String.format(maxFormat, maxValue * unitFactor, (isPerUnit ? "/" : " "), unitsTitle) ;
				drawTickText(text, xC, yC, maxValue, _max, g2, new BasicStroke(1), g2.getFont().deriveFont(12.0f)) ;
			}
		}
	}


	@Log
	void drawTickMark(final int a, final int x, final int y, final int r, final int tickLength, final Graphics2D g2, final BasicStroke stroke) {

		g2.setStroke(stroke) ;

		final int x1 = (int) ((r - (tickLength / 2)) * Math.cos(Math.toRadians(a))) ;
		final int y1 = (int) ((r - (tickLength / 2)) * Math.sin(Math.toRadians(a))) ;

		final int x2 = (int) ((r + (tickLength / 2)) * Math.cos(Math.toRadians(a))) ;
		final int y2 = (int) ((r + (tickLength / 2)) * Math.sin(Math.toRadians(a))) ;

		g2.drawLine(x - x1, y - y1, x - x2, y - y2) ;
	}


	@Log
	void drawTickText(final String text, final int xC, final int yC, final float maxValue, final float _max, final Graphics2D g2, final BasicStroke stroke,
			final Font font) {

		g2.setFont(font) ;
		g2.setStroke(stroke) ;

		float x1 = (float) (xC - ((radius + 15) * Math.cos(Math.toRadians((int) _max - begAngle)))) ;
		x1 -= (x1 < xC ? g2.getFontMetrics().stringWidth(text) : 0.0f) ;
		final float y1 = (float) (yC - ((radius + 15) * Math.sin(Math.toRadians((int) _max - begAngle)))) ;

		g2.drawString(text, x1, y1) ;
	}
}
