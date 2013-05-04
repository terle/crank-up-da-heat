package org.northernnerds.projectcrankuptheheat;

import org.northernnerds.enums.Temperatures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class ThermostatView extends ImageView {
	private TextView textViewTempGauge;

	private Vibrator vibrator;
	private Temperatures currentTemperature = null;
	boolean shouldIVibrate = false;

	private char degreeSign = (char) 0x00B0;

	private Paint paint;
	private int degree = 0;
	private float x;
	private float y;
	private int thermostatAngle = 0;
	private float centerX;
	private float centerY;
	private float newX;
	private float newY;

	public ThermostatView(Context context) {
		super(context);
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public ThermostatView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(2);
		paint.setStyle(Style.STROKE);

		this.setBackgroundResource(R.drawable.thermo2);
		this.setImageResource(R.drawable.thermo1);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int height = this.getHeight();
		int width = this.getWidth();
		centerX = width / 2;
		centerY = height / 2;
		canvas.rotate(degree, centerX, centerY);
		//Log.d(this.getClass().getSimpleName(), "onDraw(): degree is now: " + degree);
		super.onDraw(canvas);
	}

	public void setRotation(int degree) {
		this.degree = degree;
		this.invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		System.out.println("now i'm in BEFORE calling MotionEvent.ACTION_MOVE ");

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
//			x = event.getX();
//			y = event.getY();
//			newX = centerX - x;
//			newY = centerY - y;
			// updateRotation(newX, newY);
			// if (isXYInCenterRing(event.getX(), event.getY())) {
			// setRotation(thermostatAngle);
			// }
			//Log.d(this.getClass().getSimpleName(), "MotionEvent = ACTION_DOWN. degree:" + degree + " ThermostatAngle:"
					//+ thermostatAngle);

		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			x = event.getX();
			y = event.getY();
			newX = centerX - x;
			newY = centerY - y;
			// Checks if the touch-event is on the "rim" of the thermostat, and
			// only move it, if so.
			if (isXYInCenterRing(event.getX(), event.getY())) {
				updateRotation(newX, newY);
				updateTextView(thermostatAngle);
				//Log.d(this.getClass().getSimpleName(), "MotionEvent = ACTION_MOVE. degree:" + degree + " ThermostatAngle:"
					//	+ thermostatAngle);
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			Log.d(this.getClass().getSimpleName(), "MotionEvent = ACTION_UP.   degree:" + degree + " ThermostatAngle:"
					+ thermostatAngle);
			// x = event.getX();
			// y = event.getY();
			// newX = centerX - x;
			// newY = centerY - y;
			// updateRotation(newX, newY);

			if (thermostatAngle > Temperatures.t00.getAngle() || thermostatAngle < Temperatures.t24.getAngle()) {
				int midValue = (Math.abs(Temperatures.t00.getAngle()) + Math.abs(Temperatures.t24.getAngle())) / 2;
				if (thermostatAngle > midValue) {
					// Snap to 0
					setTextViewAndRotation(Temperatures.t00.getAngle(), "0" + degreeSign);
				} else {
					// Snap to 24
					setTextViewAndRotation(Temperatures.t24.getAngle(), "24" + degreeSign);
				}
				System.out.println("Under 0 eller over 24");
			} else {
				snapToDegree(thermostatAngle);
			}
		}
		return true;
	}

	public void setTempTextView(TextView tempTextView) {
		this.textViewTempGauge = tempTextView;
	}

	private void updateRotation(float newX2, float newY2) {
		thermostatAngle = (int) Math.toDegrees(Math.atan2(newY, newX)) - 90;
		
		// Log.d(this.getClass().getSimpleName(),
				// "updateRotation(): Angle is now: " + thermostatAngle);

		//This block will prevent the view from being updated between 0 & 24
//		if (Temperatures.t00.getAngle() >= thermostatAngle && thermostatAngle >= Temperatures.t24.getAngle()) {
//			// textViewTempGauge.setText("" + thermostatAngle);
			setRotation(thermostatAngle);
//		}
	}

	private void setTextViewAndRotation(int rotation, String text) {
		setRotation(rotation);
		textViewTempGauge.setText(text);

	}

	private Temperatures getClosestTemperature(int angle) {

		Temperatures closestTempSoFar = Temperatures.t16;
		int smallestAngleSoFar = Integer.MAX_VALUE;
		Temperatures[] tempEnums = Temperatures.values();

		if (angle < Temperatures.t00.getAngle() && angle > Temperatures.t08.getAngle()) {
			int midValue = (Math.abs(Temperatures.t00.getAngle()) + Math.abs(Temperatures.t08.getAngle())) / 2;
			if (angle > midValue) {
				makeVibration();
				return Temperatures.t00;
			}
			// else
			makeVibration();
			return Temperatures.t08;
		}

		for (Temperatures t : tempEnums) {
			int v = Math.abs(angle - t.getAngle());

			if (v < smallestAngleSoFar) {
				smallestAngleSoFar = v;
				closestTempSoFar = t;
			}
		}
		makeVibration();
		// for vibrating. If thermostat has changed it value. A vibration should
		// occure
		if (currentTemperature != closestTempSoFar)
			shouldIVibrate = true;

		return closestTempSoFar;
	}

	private void snapToDegree(int currentAngle) {

		Temperatures temp = getClosestTemperature(currentAngle);
		Log.d(this.getClass().getSimpleName(), "----------SNAP to " + temp.getAngle() + "-----------");

		switch (temp) {
		case t00:
			// setTextViewAndRotation(Temperatures.t00.getAngle(), "0"
			// + degreeSign);
			setTextViewAndRotation(Temperatures.t00.getAngle(), "OFF");
			thermostatAngle = Temperatures.t00.getAngle();
			break;
		case t08:
			setTextViewAndRotation(Temperatures.t08.getAngle(), "8" + degreeSign);
			thermostatAngle = Temperatures.t08.getAngle();
			break;
		case t10:
			setTextViewAndRotation(Temperatures.t10.getAngle(), "10" + degreeSign);
			thermostatAngle = Temperatures.t10.getAngle();
			break;
		case t16:
			setTextViewAndRotation(Temperatures.t16.getAngle(), "16" + degreeSign);
			thermostatAngle = Temperatures.t16.getAngle();
			break;
		case t17:
			setTextViewAndRotation(Temperatures.t17.getAngle(), "17" + degreeSign);
			thermostatAngle = Temperatures.t17.getAngle();
			break;
		case t18:
			setTextViewAndRotation(Temperatures.t18.getAngle(), "18" + degreeSign);
			thermostatAngle = Temperatures.t18.getAngle();
			break;
		case t19:
			setTextViewAndRotation(Temperatures.t19.getAngle(), "19" + degreeSign);
			thermostatAngle = Temperatures.t19.getAngle();
			break;
		case t20:
			setTextViewAndRotation(Temperatures.t20.getAngle(), "20" + degreeSign);
			thermostatAngle = Temperatures.t20.getAngle();
			break;
		case t21:
			setTextViewAndRotation(Temperatures.t21.getAngle(), "21" + degreeSign);
			thermostatAngle = Temperatures.t21.getAngle();
			break;
		case t22:
			setTextViewAndRotation(Temperatures.t22.getAngle(), "22" + degreeSign);
			thermostatAngle = Temperatures.t22.getAngle();
			break;
		case t23:
			setTextViewAndRotation(Temperatures.t23.getAngle(), "23" + degreeSign);
			thermostatAngle = Temperatures.t23.getAngle();
			break;
		case t24:
			setTextViewAndRotation(Temperatures.t24.getAngle(), "24" + degreeSign);
			thermostatAngle = Temperatures.t24.getAngle();
			break;
		default:
			setTextViewAndRotation(Temperatures.t16.getAngle(), "16" + degreeSign);
			thermostatAngle = Temperatures.t16.getAngle();
			break;
		}
	}

	private void updateTextView(int currentAngle) {

		Temperatures temp = getClosestTemperature(currentAngle);
		currentTemperature = temp;
		// Log.d(this.getClass().getSimpleName(),
		// "TextView Updated to "+temp.getAngle());

		switch (temp) {
		case t00:
			// textViewTempGauge.setText("0" + degreeSign);
			textViewTempGauge.setText("OFF");
			break;
		case t08:
			textViewTempGauge.setText("8" + degreeSign);
			break;
		case t10:
			textViewTempGauge.setText("10" + degreeSign);
			break;
		case t16:
			textViewTempGauge.setText("16" + degreeSign);
			break;
		case t17:
			textViewTempGauge.setText("17" + degreeSign);
			break;
		case t18:
			textViewTempGauge.setText("18" + degreeSign);
			break;
		case t19:
			textViewTempGauge.setText("19" + degreeSign);
			break;
		case t20:
			textViewTempGauge.setText("20" + degreeSign);
			break;
		case t21:
			textViewTempGauge.setText("21" + degreeSign);
			break;
		case t22:
			textViewTempGauge.setText("22" + degreeSign);
			break;
		case t23:
			textViewTempGauge.setText("23" + degreeSign);
			break;
		case t24:
			textViewTempGauge.setText("24" + degreeSign);
			break;
		default:
			textViewTempGauge.setText("16" + degreeSign);
			break;
		}
	}

	/**
	 * determines if the touch event is relevant to us
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @return true if the (x,y)-coordinates are on the outer rim of the
	 *         thermostat
	 * 
	 * */
	private boolean isXYInCenterRing(float x, float y) {
		float bigRadius = this.getWidth() / 2;
		float smallRadius = this.getWidth() / 2 - 100; // TODO: Verify this
		// number OR Find a
		// better way of
		// estimating that
		// radius.
		// textViewTempGauge.setTextSize(10);
		// textViewTempGauge.setText("x:"+x+"\ny:"+y+"\nBig-r:"+bigRadius+"\nSmall-r:"+smallRadius);

		if (isXYInCircle(x, y, bigRadius) && !isXYInCircle(x, y, smallRadius))
			return true;

		return false;
	}

	/**
	 * used to determine if a (x,y)-coordinate set is within the circle
	 * <p>
	 * The formula for a circle (x-x0)^2+(y-y0)^2 = r
	 * 
	 * @param x
	 *            X-coordinate
	 * @param y
	 *            Y-coordinate
	 * @param radius
	 *            the radius of the circle
	 * @return true if the (x,y) is either ON or within the circle
	 * */
	private boolean isXYInCircle(float x, float y, float radius) {
		/*
		 * Cirklen formel: (x-x0)^2 + (y-y0)^2 = r^2
		 */
		float x0 = centerX;
		float y0 = centerY;
		double result = Math.pow((x - x0), 2) + Math.pow((y - y0), 2);

		if (result <= Math.pow(radius, 2))
			return true;

		return false;
	}

	private void makeVibration() {
		if (shouldIVibrate) {
			vibrator.vibrate(20);
			Log.d(this.getClass().getSimpleName(), "brrrrrrrrm");
			shouldIVibrate = false;
		}
	}
}