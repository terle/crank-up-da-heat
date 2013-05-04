package org.northernnerds.projectcrankuptheheat;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class ThermostatFragment extends SherlockFragment implements
		OnClickListener, OnTouchListener, OnSeekBarChangeListener {
	private LinearLayout buttonLayout;
	private Button sendButton, cancelButton;
	private ThermostatView thermostat;
	private TextView tempTextView;
	private SeekBar hotColdSeekBar;
	private RelativeLayout mainLayout;
	private ImageView hotImageView, coldImageView;
	private Drawable hotUnselected, hotSelected, coldUnselected, coldSelected,
			hotBackground, coldBackground;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View inflatedView = inflater.inflate(R.layout.activity_main, null);

		tempTextView = (TextView) inflatedView.findViewById(R.id.temperatureTextView);
		Typeface roboto = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Bold.ttf");
		tempTextView.setTypeface(roboto);
		tempTextView.setTextSize(70);

		thermostat = (ThermostatView) inflatedView
				.findViewById(R.id.thermostatView1);
		thermostat.setTempTextView(tempTextView);
		thermostat.setOnTouchListener(this);

		buttonLayout = (LinearLayout) inflatedView.findViewById(R.id.sendCancelButtonLayout);

		sendButton = (Button) inflatedView.findViewById(R.id.sendButton);
		sendButton.setOnClickListener(this);
		cancelButton = (Button) inflatedView.findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(this);

		hotColdSeekBar = (SeekBar) inflatedView
				.findViewById(R.id.hotColdSeekBar);
		hotColdSeekBar.setOnSeekBarChangeListener(this);

		mainLayout = (RelativeLayout) inflatedView.findViewById(R.id.mainLayout);

		hotImageView = (ImageView) inflatedView.findViewById(R.id.hotImageView);
		coldImageView = (ImageView) inflatedView.findViewById(R.id.coldImageView);

		return inflatedView;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println("X: " + event.getX() + ", Y: " + event.getY());

		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (buttonLayout.getVisibility() == View.INVISIBLE) {
				Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_anim);
				buttonLayout.startAnimation(anim);
				buttonLayout.setVisibility(View.VISIBLE);
				System.out.println("Is this called??!");
			}
		}
		return false;
	}

	private void slideOutButtons() {
		Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_anim);
		buttonLayout.startAnimation(anim);
		buttonLayout.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendButton:
			Toast.makeText(getActivity(), "Sending something!", Toast.LENGTH_SHORT).show();
			// Should call SMSHandler here...
			slideOutButtons();
			break;
		case R.id.cancelButton:
			slideOutButtons();
			break;
		default:
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		// Preloading all background and heat/cold images into drawables - if
		// not doing this, it seems like the first time
		// we need to change background images and such, it takes forever, if we
		// load them from files. Makes sense, since
		// I/O is always performance heavy.
		hotUnselected = getResources().getDrawable(R.drawable.varme_ikon);
		hotSelected = getResources().getDrawable(R.drawable.varme_ikon_selected);
		coldUnselected = getResources().getDrawable(R.drawable.kulde_ikon);
		coldSelected = getResources().getDrawable(R.drawable.kulde_ikon_selected);

		hotBackground = getResources().getDrawable(R.drawable.bg_varm);
		coldBackground = getResources().getDrawable(R.drawable.bg_kold);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (progress <= hotColdSeekBar.getMax() / 2) {
			// TODO: This is deprecated, but since the app is working from API =
			// 8, then we need it.
			// From API = 16, we should use setBackground(drawable) - make a
			// check for the OS version.
			mainLayout.setBackgroundDrawable(hotBackground);
			hotImageView.setImageDrawable(hotSelected);
			coldImageView.setImageDrawable(coldUnselected);
		} else {
			// TODO: This is deprecated, but since the app is working from API =
			// 8, then we need it.
			// From API = 16, we should use setBackground(drawable) - make a
			// check for the OS version.
			mainLayout.setBackgroundDrawable(coldBackground);
			coldImageView.setImageDrawable(coldSelected);
			hotImageView.setImageDrawable(hotUnselected);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (hotColdSeekBar.getProgress() <= hotColdSeekBar.getMax() / 2) {
			hotColdSeekBar.setProgress(0);
		} else {
			hotColdSeekBar.setProgress(100);
		}
	}
}
