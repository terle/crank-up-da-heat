package org.northernnerds.projectcrankuptheheat;

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
	private int temp00 = 48;
	private int temp08 = -2;
	private int temp10 = -25;
	private int temp16 = -70;
	private int temp17 = -90;
	private int temp18 = -108;
	private int temp19 = -124;
	private int temp20 = -141;
	private int temp21 = -157;
	private int temp22 = -174;
	private int temp23 = -191;
	private int temp24 = -206;

	private TextView textViewTempGauge;
	
	private Paint paint;
	private int degree = 0;
	private float x;
	private float y;
	private int thermostatAngle = 0;
	private float a, b, c;
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
		System.out.println("now i'm in BEFORE calling MotionEvent.ACTION_MOVE ");
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
//			x = event.getX();
//			y = event.getY();
//			newX = centerX - x;
//			newY = centerY - y;
//			updateRotation(newX, newY);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			x = event.getX();
			y = event.getY();
			newX = centerX - x;
			newY = centerY - y;
			updateRotation(newX, newY);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
//			x = event.getX();
//			y = event.getY();
//			newX = centerX - x;
//			newY = centerY - y;
//			updateRotation(newX, newY);
//			snapToDegree(thermostatAngle);
		}
		return true;
	}
	
	public void setTempTextView(TextView tempTextView) {
		this.textViewTempGauge = tempTextView;
	}

	private void updateRotation(float newX2, float newY2) {
		thermostatAngle = (int) Math.toDegrees(Math.atan2(newY, newX)) - 90;
		
		if(48 >= thermostatAngle && thermostatAngle >= -207) {
			Log.d("Thermostat angle", "Angle is now: " + thermostatAngle);
			
			textViewTempGauge.setText("" + thermostatAngle);
			
			setRotation(thermostatAngle);
		}
	}

	private void snapToDegree(int currentAngle) {
		if (currentAngle > temp00 && currentAngle < temp08) {
			if (currentAngle >= (Math.abs(temp00) + Math.abs(temp08)) / 2) {
				setRotation(temp08);
				textViewTempGauge.setText("08");
			} else {
				setRotation(temp00);
				textViewTempGauge.setText("00");
			}
		}
		if (currentAngle > temp08 && currentAngle < temp10) {
			if (currentAngle >= (Math.abs(temp08) + Math.abs(temp10)) / 2) {
				setRotation(temp10);
				textViewTempGauge.setText("10");
			} else {
				setRotation(temp08);
				textViewTempGauge.setText("08");
			}
		}
		if (currentAngle > temp10 && currentAngle < temp16) {
			if (currentAngle >= (Math.abs(temp10) + Math.abs(temp16)) / 2) {
				setRotation(temp16);
				textViewTempGauge.setText("16");
			} else {
				setRotation(temp10);
				textViewTempGauge.setText("10");
			}
		}
		if (currentAngle > temp16 && currentAngle < temp17) {
			if (currentAngle >= (Math.abs(temp16) + Math.abs(temp17)) / 2) {
				setRotation(temp17);
				textViewTempGauge.setText("17");
			} else {
				setRotation(temp16);
				textViewTempGauge.setText("16");
			}
		}
		if (currentAngle > temp17 && currentAngle < temp18) {
			if (currentAngle >= (Math.abs(temp17) + Math.abs(temp18)) / 2) {
				setRotation(temp18);
				textViewTempGauge.setText("18");
			} else {
				setRotation(temp17);
				textViewTempGauge.setText("17");
			}
		}
		if (currentAngle > temp18 && currentAngle < temp19) {
			if (currentAngle >= (Math.abs(temp18) + Math.abs(temp19)) / 2) {
				setRotation(temp19);
				textViewTempGauge.setText("19");
			} else {
				setRotation(temp18);
				textViewTempGauge.setText("18");
			}
		}
		if (currentAngle > temp19 && currentAngle < temp20) {
			if (currentAngle >= (Math.abs(temp19) + Math.abs(temp20)) / 2) {
				setRotation(temp20);
				textViewTempGauge.setText("20");
			} else {
				setRotation(temp19);
				textViewTempGauge.setText("19");
			}
		}
		if (currentAngle > temp20 && currentAngle < temp21) {
			if (currentAngle >= (Math.abs(temp20) + Math.abs(temp21)) / 2) {
				setRotation(temp21);
				textViewTempGauge.setText("21");
			} else {
				setRotation(temp20);
				textViewTempGauge.setText("20");
			}
		}
		if (currentAngle > temp21 && currentAngle < temp22) {
			if (currentAngle >= (Math.abs(temp21) + Math.abs(temp22)) / 2) {
				setRotation(temp22);
				textViewTempGauge.setText("22");
			} else {
				setRotation(temp21);
				textViewTempGauge.setText("21");
			}
		}
		if (currentAngle > temp22 && currentAngle < temp23) {
			if (currentAngle >= (Math.abs(temp22) + Math.abs(temp23)) / 2) {
				setRotation(temp23);
				textViewTempGauge.setText("23");
			} else {
				setRotation(temp22);
				textViewTempGauge.setText("22");
			}
		}
		if (currentAngle > temp23 && currentAngle < temp24) {
			if (currentAngle >= (Math.abs(temp23) + Math.abs(temp24)) / 2) {
				setRotation(temp24);
				textViewTempGauge.setText("24");
			} else
				setRotation(temp23);
			textViewTempGauge.setText("23");
		}
	}
}