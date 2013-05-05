package org.northernnerds.projectcrankuptheheat;

import org.northernnerds.enums.Temperatures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Vibrator;
import android.util.AttributeSet;
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
		super.onDraw(canvas);
	}

	public void setRotation(int degree) {
		this.degree = degree;
		this.invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

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
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (thermostatAngle > Temperatures.T00.getAngle() || thermostatAngle < Temperatures.T24.getAngle()) {
				int midValue = (Math.abs(Temperatures.T00.getAngle()) + Math.abs(Temperatures.T24.getAngle())) / 2;
				if (thermostatAngle > midValue) {
					// Snap to 0
					setTextViewAndRotation(Temperatures.T00.getAngle(), "0" + degreeSign);
				} else {
					// Snap to 24
					setTextViewAndRotation(Temperatures.T24.getAngle(), "24" + degreeSign);
				}
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
		setRotation(thermostatAngle);
	}

	private void setTextViewAndRotation(int rotation, String text) {
		setRotation(rotation);
		textViewTempGauge.setText(text);
	}

	private Temperatures getClosestTemperature(int angle) {

		Temperatures closestTempSoFar = Temperatures.T16;
		int smallestAngleSoFar = Integer.MAX_VALUE;
		Temperatures[] tempEnums = Temperatures.values();

		makeVibration();
		
		if (angle < Temperatures.T00.getAngle() && angle > Temperatures.T08.getAngle()) {
			int midValue = (Math.abs(Temperatures.T00.getAngle()) + Math.abs(Temperatures.T08.getAngle())) / 2;
			if (angle > midValue) {
				if(currentTemperature != Temperatures.T00) { 
					shouldIVibrate = true;
				}
				return Temperatures.T00;
			}
			if(currentTemperature != Temperatures.T08) { 
				shouldIVibrate = true;
			}
			return Temperatures.T08;
		}

		for (Temperatures t : tempEnums) {
			int v = Math.abs(angle - t.getAngle());

			if (v < smallestAngleSoFar) {
				smallestAngleSoFar = v;
				closestTempSoFar = t;
			}
		}
		
		// FOR VIBRATING. If thermostat has changed it value. A vibration should occur
		if (currentTemperature != closestTempSoFar) { 
			shouldIVibrate = true;
		}
		return closestTempSoFar;
	}

	private void snapToDegree(int currentAngle) {
		Temperatures temp = getClosestTemperature(currentAngle);

		switch (temp) {
		case T00:
			setTextViewAndRotation(Temperatures.T00.getAngle(), "OFF");
			thermostatAngle = Temperatures.T00.getAngle();
			break;
		case T08:
			setTextViewAndRotation(Temperatures.T08.getAngle(), "8" + degreeSign);
			thermostatAngle = Temperatures.T08.getAngle();
			break;
		case T10:
			setTextViewAndRotation(Temperatures.T10.getAngle(), "10" + degreeSign);
			thermostatAngle = Temperatures.T10.getAngle();
			break;
		case T16:
			setTextViewAndRotation(Temperatures.T16.getAngle(), "16" + degreeSign);
			thermostatAngle = Temperatures.T16.getAngle();
			break;
		case T17:
			setTextViewAndRotation(Temperatures.T17.getAngle(), "17" + degreeSign);
			thermostatAngle = Temperatures.T17.getAngle();
			break;
		case T18:
			setTextViewAndRotation(Temperatures.T18.getAngle(), "18" + degreeSign);
			thermostatAngle = Temperatures.T18.getAngle();
			break;
		case T19:
			setTextViewAndRotation(Temperatures.T19.getAngle(), "19" + degreeSign);
			thermostatAngle = Temperatures.T19.getAngle();
			break;
		case T20:
			setTextViewAndRotation(Temperatures.T20.getAngle(), "20" + degreeSign);
			thermostatAngle = Temperatures.T20.getAngle();
			break;
		case T21:
			setTextViewAndRotation(Temperatures.T21.getAngle(), "21" + degreeSign);
			thermostatAngle = Temperatures.T21.getAngle();
			break;
		case T22:
			setTextViewAndRotation(Temperatures.T22.getAngle(), "22" + degreeSign);
			thermostatAngle = Temperatures.T22.getAngle();
			break;
		case T23:
			setTextViewAndRotation(Temperatures.T23.getAngle(), "23" + degreeSign);
			thermostatAngle = Temperatures.T23.getAngle();
			break;
		case T24:
			setTextViewAndRotation(Temperatures.T24.getAngle(), "24" + degreeSign);
			thermostatAngle = Temperatures.T24.getAngle();
			break;
		default:
			setTextViewAndRotation(Temperatures.T16.getAngle(), "16" + degreeSign);
			thermostatAngle = Temperatures.T16.getAngle();
			break;
		}
	}

	private void updateTextView(int currentAngle) {
		Temperatures temp = getClosestTemperature(currentAngle);
		currentTemperature = temp;

		switch (temp) {
		case T00:
			textViewTempGauge.setText("OFF");
			break;
		case T08:
			textViewTempGauge.setText("8" + degreeSign);
			break;
		case T10:
			textViewTempGauge.setText("10" + degreeSign);
			break;
		case T16:
			textViewTempGauge.setText("16" + degreeSign);
			break;
		case T17:
			textViewTempGauge.setText("17" + degreeSign);
			break;
		case T18:
			textViewTempGauge.setText("18" + degreeSign);
			break;
		case T19:
			textViewTempGauge.setText("19" + degreeSign);
			break;
		case T20:
			textViewTempGauge.setText("20" + degreeSign);
			break;
		case T21:
			textViewTempGauge.setText("21" + degreeSign);
			break;
		case T22:
			textViewTempGauge.setText("22" + degreeSign);
			break;
		case T23:
			textViewTempGauge.setText("23" + degreeSign);
			break;
		case T24:
			textViewTempGauge.setText("24" + degreeSign);
			break;
		default:
			textViewTempGauge.setText("16" + degreeSign);
			break;
		}
	}

	/**
	 * Determines if the touch event is relevant to us
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return true if the (x,y)-coordinates are on the outer rim of the
	 * thermostat
	 */
	private boolean isXYInCenterRing(float x, float y) {
		float bigRadius = this.getWidth() / 2;
		float smallRadius = this.getWidth() / 2 - 100; 
		// TODO: Verify this number OR Find a better way of estimating that radius.

		if (isXYInCircle(x, y, bigRadius) && !isXYInCircle(x, y, smallRadius)) {
			return true;
		}
		return false;
	}

	/**
	 * used to determine if a (x,y)-coordinate set is within the circle
	 * <p>
	 * The formula for a circle (x-x0)^2+(y-y0)^2 = r
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param radius the radius of the circle
	 * @return true if the (x,y) is either ON or within the circle
	 */
	private boolean isXYInCircle(float x, float y, float radius) {
		/*
		 * Cirklen formel: (x-x0)^2 + (y-y0)^2 = r^2
		 */
		float x0 = centerX;
		float y0 = centerY;
		double result = Math.pow((x - x0), 2) + Math.pow((y - y0), 2);

		if (result <= Math.pow(radius, 2)) {
			return true;
		}
		return false;
	}

	private void makeVibration() {
		if (shouldIVibrate) {
			vibrator.vibrate(20);
			shouldIVibrate = false;
		}
	}
}