package org.northernnerds.projectcrankuptheheat;

import org.northennerds.enums.Temperatures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class ThermostatView extends ImageView {
	// private int temp00 = 48;
	// private int temp08 = -2;
	// private int temp10 = -25;
	// private int temp16 = -70;
	// private int temp17 = -90;
	// private int temp18 = -108;
	// private int temp19 = -124;
	// private int temp20 = -141;
	// private int temp21 = -157;
	// private int temp22 = -174;
	// private int temp23 = -191;
	// private int temp24 = -206;

	private TextView textViewTempGauge;

	private Paint paint;
	private int degree = 0;
	private float x;
	private float y;
	private int thermostatAngle = 0;
	// private float a, b, c;
	private float centerX;
	private float centerY;
	private float newX;
	private float newY;

	public ThermostatView(Context context) {
		super(context);
	}

	public ThermostatView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

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
		Log.d(this.getClass().getSimpleName(), "Direction is now: " + degree);
		super.onDraw(canvas);
	}

	public void setRotation(int degree) {
		this.degree = degree;
		this.invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out
				.println("now i'm in BEFORE calling MotionEvent.ACTION_MOVE ");

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// x = event.getX();
			// y = event.getY();
			// newX = centerX - x;
			// newY = centerY - y;
			// updateRotation(newX, newY);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			x = event.getX();
			y = event.getY();
			newX = centerX - x;
			newY = centerY - y;
			updateRotation(newX, newY);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			// x = event.getX();
			// y = event.getY();
			// newX = centerX - x;
			// newY = centerY - y;
			// updateRotation(newX, newY);
			snapToDegree(thermostatAngle);
		}
		return true;
	}

	public void setTempTextView(TextView tempTextView) {
		this.textViewTempGauge = tempTextView;
	}

	private void updateRotation(float newX2, float newY2) {
		thermostatAngle = (int) Math.toDegrees(Math.atan2(newY, newX)) - 90;

		if (48 >= thermostatAngle && thermostatAngle >= -207) {
			Log.d("Thermostat angle", "Angle is now: " + thermostatAngle);

			// textViewTempGauge.setText("" + thermostatAngle);

			setRotation(thermostatAngle);
		}
	}

	private void setTextViewAndRotation(int rotation, String text) {
		setRotation(rotation);
		textViewTempGauge.setText(text);

	}

	private Temperatures getClosestTemperature(int angle) {
		Temperatures closestTempSoFar = Temperatures.t16;
		int smallestAngleSoFar = Integer.MAX_VALUE;
		Temperatures[] tempEnums = Temperatures.values();

		if(angle < Temperatures.t00.angle && angle > Temperatures.t08.angle)
		{
			int midValue = (Math.abs(Temperatures.t00.angle)+Math.abs(Temperatures.t08.angle))/2;
			if(angle > midValue) return Temperatures.t00;
			//else
			return Temperatures.t08;
		}

		for (Temperatures t : tempEnums) {
			int v = Math.abs(angle - t.angle);

			if (v < smallestAngleSoFar) {
				smallestAngleSoFar = v;
				closestTempSoFar = t;
			}
		}

		return closestTempSoFar;
	}

	private void snapToDegree(int currentAngle) {

		Temperatures temp = getClosestTemperature(currentAngle);

		switch (temp) {
		case t00:
			setTextViewAndRotation(Temperatures.t00.angle, "0¡");
			break;

		case t08:
			setTextViewAndRotation(Temperatures.t08.angle, "8¡");
			break;

		case t10:
			setTextViewAndRotation(Temperatures.t10.angle, "10¡");
			break;

		case t16:
			setTextViewAndRotation(Temperatures.t16.angle, "16¡");
			break;

		case t17:
			setTextViewAndRotation(Temperatures.t17.angle, "17¡");
			break;

		case t18:
			setTextViewAndRotation(Temperatures.t18.angle, "18¡");
			break;

		case t19:
			setTextViewAndRotation(Temperatures.t19.angle, "19¡");
			break;

		case t20:
			setTextViewAndRotation(Temperatures.t20.angle, "20¡");
			break;

		case t21:
			setTextViewAndRotation(Temperatures.t21.angle, "21¡");
			break;
		case t22:
			setTextViewAndRotation(Temperatures.t22.angle, "22¡");
			break;
		case t23:
			setTextViewAndRotation(Temperatures.t23.angle, "23¡");
			break;
		case t24:
			setTextViewAndRotation(Temperatures.t24.angle, "24¡");
			break;

		default:
			setTextViewAndRotation(Temperatures.t16.angle, "16¡");
			break;
		}
	}
}