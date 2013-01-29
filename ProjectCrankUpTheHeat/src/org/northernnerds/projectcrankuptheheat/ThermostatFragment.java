package org.northernnerds.projectcrankuptheheat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockFragment;

public class ThermostatFragment extends SherlockFragment {
	private String mContent = "???";
	private TextView textViewTempGauge;
	//Integers for the angles of the degrees on thermostat.
	private int temp00 = -120;
	private int temp08 = -90;
	private int temp10 = -60;
	private int temp16 = -20;
	private int temp17 = 0;
	private int temp18 = 20;
	private int temp19  = 40;
	private int temp20 = 60 ;
	private int temp21 = 80;
	private int temp22 = 100;
	private int temp23  = 120;
	private int temp24 = 140;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Rotate rotate;
		rotate = new Rotate(getActivity());

		LinearLayout layout = new LinearLayout(getActivity());
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		layout.setGravity(Gravity.CENTER);
		layout.addView(rotate);
		//TODO: Move this TExtview to the center of the thermostat, and change font.
		textViewTempGauge = new TextView(getActivity());
		textViewTempGauge.setText("Fuck Asier");

		layout.addView(textViewTempGauge);

		return layout;
	}

	public class Rotate extends ImageView {
		Paint paint;
		int direction = 0;
		private float x;
		private float y;
		int thermostatAngle = 0;
		float a, b, c;
		private float centerX;
		private float centerY;
		private float newX;
		private float newY;

		public Rotate(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setStrokeWidth(2);
			paint.setStyle(Style.STROKE);

			this.setImageResource(R.drawable.circular);
		}

		@Override
		protected void onDraw(Canvas canvas) {

			int height = this.getHeight();
			int width = this.getWidth();
			centerX = width / 2;
			centerY = height / 2;
			canvas.rotate(direction, width / 2, height / 2);
			super.onDraw(canvas);

		}

		public void setDirection(int direction) {
			this.direction = direction;
			this.invalidate();
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			System.out
			.println("now i'm in BEFORE calling MotionEvent.ACTION_MOVE ");

			if (event.getAction() == MotionEvent.ACTION_DOWN) {

				x = event.getX();
				y = event.getY();

				newX = centerX - x;
				newY = centerY - y;

				updateRotation(newX, newY);

			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {

				x = event.getX();
				y = event.getY();

				newX = centerX - x;
				newY = centerY - y;

				updateRotation(newX, newY);

			} else if (event.getAction() == MotionEvent.ACTION_UP) {

				x = event.getX();
				y = event.getY();

				newX = centerX - x;
				newY = centerY - y;

				updateRotation(newX, newY);
				snapToDegree(thermostatAngle);

			}
			return true;
		}

		private void updateRotation(float newX2, float newY2) {
			// TODO Auto-generated method stub

			thermostatAngle = (int) Math.toDegrees(Math.atan2(newY, newX)) - 90;

			setDirection(thermostatAngle);
		}
		private void snapToDegree(int currentAngle)
		{
			if(currentAngle > temp00 && currentAngle <temp08){
				if(currentAngle >= (Math.abs(temp00)+Math.abs(temp08))/2){
					setDirection(temp08);
					textViewTempGauge.setText("08°");
				}
				else{
					setDirection(temp00);
					textViewTempGauge.setText("00°");
				}
			}
			if(currentAngle > temp08 && currentAngle <temp10){
				if(currentAngle >= (Math.abs(temp08)+Math.abs(temp10))/2){
					setDirection(temp10);
					textViewTempGauge.setText("10°");
				}
				else{
					setDirection(temp08);
					textViewTempGauge.setText("08°");
				}
			}
			if(currentAngle > temp10 && currentAngle <temp16){
				if(currentAngle >= (Math.abs(temp10)+Math.abs(temp16))/2){
					setDirection(temp16);
					textViewTempGauge.setText("16°");
				}
				else{
					setDirection(temp10);
					textViewTempGauge.setText("10°");
				}
			}
			if(currentAngle > temp16 && currentAngle <temp17){
				if(currentAngle >= (Math.abs(temp16)+Math.abs(temp17))/2){
					setDirection(temp17);
					textViewTempGauge.setText("17°");
				}
				else{
					setDirection(temp16);
					textViewTempGauge.setText("16°");
				}
			}
			if(currentAngle > temp17 && currentAngle <temp18){
				if(currentAngle >= (Math.abs(temp17)+Math.abs(temp18))/2){
					setDirection(temp18);
					textViewTempGauge.setText("18°");
				}
				else{
					setDirection(temp17);
					textViewTempGauge.setText("17°");
				}
			}
			if(currentAngle > temp18 && currentAngle <temp19){
				if(currentAngle >= (Math.abs(temp18)+Math.abs(temp19))/2){
					setDirection(temp19);
					textViewTempGauge.setText("19°");
				}
				else{
					setDirection(temp18);
					textViewTempGauge.setText("18°");
				}
			}
			if(currentAngle > temp19 && currentAngle <temp20){
				if(currentAngle >= (Math.abs(temp19)+Math.abs(temp20))/2){
					setDirection(temp20);
					textViewTempGauge.setText("20°");
				}
				else{
					setDirection(temp19);
					textViewTempGauge.setText("19°");
				}
			}
			if(currentAngle > temp20 && currentAngle <temp21){
				if(currentAngle >= (Math.abs(temp20)+Math.abs(temp21))/2){
					setDirection(temp21);
					textViewTempGauge.setText("21°");
				}
				else{
					setDirection(temp20);
					textViewTempGauge.setText("20°");
				}
			}
			if(currentAngle > temp21 && currentAngle <temp22){
				if(currentAngle >= (Math.abs(temp21)+Math.abs(temp22))/2){
					setDirection(temp22);
					textViewTempGauge.setText("22°");
				}
				else{
					setDirection(temp21);
					textViewTempGauge.setText("21°");
				}
			}
			if(currentAngle > temp22 && currentAngle <temp23){
				if(currentAngle >= (Math.abs(temp22)+Math.abs(temp23))/2){
					setDirection(temp23);
					textViewTempGauge.setText("23°");
				}
				else{
					setDirection(temp22);
					textViewTempGauge.setText("22°");
				}
			}
			if(currentAngle > temp23 && currentAngle <temp24){
				if(currentAngle >= (Math.abs(temp23)+Math.abs(temp24))/2){
					setDirection(temp24);
					textViewTempGauge.setText("24°");
				}
				else setDirection(temp23);
				textViewTempGauge.setText("23°");
			}		
		}
	}
}
