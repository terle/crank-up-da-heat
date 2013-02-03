package org.northernnerds.projectcrankuptheheat;

import org.northennerds.settings.SMSHandler;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class ThermometerActivity extends SherlockActivity implements
		OnSeekBarChangeListener, OnClickListener {
	private SeekBar seekbarMin, seekbarMax;
	private TextView counterMin, counterMax;
	private int minTemp, maxTemp;
	private Button sendButt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View inflatedView = getLayoutInflater().inflate(R.layout.thermometer, null);

		seekbarMin = (SeekBar) inflatedView.findViewById(R.id.seekBarMin);
		seekbarMin.setOnSeekBarChangeListener(this);

		seekbarMax = (SeekBar) inflatedView.findViewById(R.id.seekBarMax);
		seekbarMax.setOnSeekBarChangeListener(this);

		counterMin = (TextView) inflatedView.findViewById(R.id.textViewMin);
		counterMax = (TextView) inflatedView.findViewById(R.id.textViewMax);

		inflatedView.setRotation(270);
		
		setContentView(inflatedView);

		sendButt = (Button) inflatedView.findViewById(R.id.button1);
		sendButt.setOnClickListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// Toast.makeText(getSherlockActivity(), "Progress changed... " +
		// progress, Toast.LENGTH_LONG).show();
		// Next 2 lines are to make seekbars "follow" eachother.
		if (maxTemp <= minTemp)
			seekbarMin.setProgress(maxTemp);
		if (minTemp >= maxTemp)
			seekbarMax.setProgress(minTemp);

		if (seekBar == seekbarMax) {
			maxTemp = progress;
			counterMax.setText("Max: " + progress);
		}
		if (seekBar == seekbarMin) {
			minTemp = progress;
			counterMin.setText("Min: " + progress);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		Toast.makeText(this, "Started tracking...",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		Toast.makeText(this, "Stopped tracking...",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		SMSHandler handler = new SMSHandler();
		handler.SendSMS();

	}
}
