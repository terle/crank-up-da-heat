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

		return layout;
	}

	public class Rotate extends ImageView {
		Paint paint;
		int direction = 0;
		private float x;
		private float y;
		int degree = 0;
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

			}
			return true;
		}

		private void updateRotation(float newX2, float newY2) {
			// TODO Auto-generated method stub

			degree = (int) Math.toDegrees(Math.atan2(newY, newX)) - 90;

			setDirection(degree);
		}
	}
}