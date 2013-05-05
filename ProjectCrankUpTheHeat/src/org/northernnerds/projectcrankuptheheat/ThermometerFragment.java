package org.northernnerds.projectcrankuptheheat;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragment;

public class ThermometerFragment extends SherlockFragment implements OnTouchListener {
	private ImageView maxTempImageView, minTempImageView;
	private ImageView thermoWarm, thermoCold;
	private ViewGroup rootView;
	private int xDelta;
	private int yDelta;
	private float warmMaxValueInDp;// = 50 px
	private float coolMaxValueInDp;// = 200 px
	private float warmMinValueInDp;// = 500 px
	private float coolMinValueInDp;// = 500 px

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View inflatedView = inflater.inflate(R.layout.thermometer_layout, null);
		rootView = (ViewGroup) inflatedView.findViewById(R.id.root);
		
		addThermometer();
		addImages();
		
		return inflatedView;
	}

	private void addThermometer() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int width = displaymetrics.widthPixels;

		// Max thermometer ImageView
		thermoWarm = new ImageView(getActivity());
		thermoWarm.setImageDrawable(getResources().getDrawable(R.drawable.termometer_varm));
		thermoWarm.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(thermoWarm.getMeasuredWidth(), thermoWarm.getMeasuredHeight());
		layoutParams.leftMargin = (width / 2) - layoutParams.width;
		layoutParams.topMargin = 20;

		layoutParams.bottomMargin = -250;
		layoutParams.rightMargin = -250;
		thermoWarm.setLayoutParams(layoutParams);

		rootView.addView(thermoWarm);

		// Min thermometer ImageView
		thermoCold = new ImageView(getActivity());
		thermoCold.setImageDrawable(getResources().getDrawable(R.drawable.termometer_kold));
		thermoCold.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

		RelativeLayout.LayoutParams minLayoutParams = new RelativeLayout.LayoutParams(thermoCold.getMeasuredWidth(), thermoCold.getMeasuredHeight());
		minLayoutParams.leftMargin = width / 2;
		minLayoutParams.topMargin = 20;
		minLayoutParams.bottomMargin = -250;
		minLayoutParams.rightMargin = -250;
		thermoCold.setLayoutParams(minLayoutParams);
		rootView.addView(thermoCold);

		// Getting the relative values for the min and max values.
		warmMaxValueInDp = 50 / (displaymetrics.densityDpi / 160f);
		warmMinValueInDp = 500 / (displaymetrics.densityDpi / 160f);

		coolMaxValueInDp = 200 / (displaymetrics.densityDpi / 160f);
		coolMinValueInDp = warmMinValueInDp;
	}

	private void addImages() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int width = displaymetrics.widthPixels;

		// Max temp ImageView
		maxTempImageView = new ImageView(getActivity());
		maxTempImageView.setId(666); // Creating the ImageView in code, so we need to set the Id on our own.
		maxTempImageView.setImageDrawable(getResources().getDrawable(R.drawable.max));
		maxTempImageView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(maxTempImageView.getMeasuredWidth(), maxTempImageView.getMeasuredHeight());
		layoutParams.leftMargin = (width / 2) - layoutParams.width - (thermoWarm.getMeasuredWidth() / 2);
		layoutParams.topMargin = 50;
		layoutParams.bottomMargin = -250;
		layoutParams.rightMargin = -250;
		maxTempImageView.setLayoutParams(layoutParams);

		maxTempImageView.setOnTouchListener(this);
		rootView.addView(maxTempImageView);

		// Min temp ImageView
		minTempImageView = new ImageView(getActivity());
		minTempImageView.setId(777); // Creating the ImageView in code, so we need to set the Id on our own.
		minTempImageView.setImageDrawable(getResources().getDrawable(R.drawable.min));
		minTempImageView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

		RelativeLayout.LayoutParams minLayoutParams = new RelativeLayout.LayoutParams(minTempImageView.getMeasuredWidth(), minTempImageView.getMeasuredHeight());
		minLayoutParams.leftMargin = width / 2 + (thermoCold.getMeasuredWidth() / 2);
		minLayoutParams.topMargin = 200;
		minLayoutParams.bottomMargin = -250;
		minLayoutParams.rightMargin = -250;
		minTempImageView.setLayoutParams(minLayoutParams);

		minTempImageView.setOnTouchListener(this);

		System.out.println("Width!: " + minTempImageView.getMeasuredWidth());
		rootView.addView(minTempImageView);
	}

	public boolean onTouch(View view, MotionEvent event) {
		final int Y = (int) event.getRawY();
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			xDelta = lParams.leftMargin;
			yDelta = Y - lParams.topMargin;
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			break;
		case MotionEvent.ACTION_POINTER_UP:
			break;
		case MotionEvent.ACTION_MOVE:
			int newVal = Y - yDelta;
			if (view.getId() == 666) { // Max temp image.
				if (newVal < warmMaxValueInDp || newVal > warmMinValueInDp) {
					break;
				}
			} else if (view.getId() == 777) { // Min temp image
				if (newVal < coolMaxValueInDp || newVal > coolMinValueInDp) {
					break;
				}
			}

			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			layoutParams.leftMargin = xDelta;
			layoutParams.topMargin = Y - yDelta;
			layoutParams.rightMargin = -250;
			layoutParams.bottomMargin = -250;
			view.setLayoutParams(layoutParams);
			break;
		}
		rootView.invalidate();
		return true;
	}
}
