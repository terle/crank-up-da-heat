package org.northernnerds.projectcrankuptheheat;

import org.northernnerds.enums.CommandTypes;
import org.northernnerds.settings.SMSHandler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.actionbarsherlock.app.SherlockFragment;

public class ThermometerFragment extends SherlockFragment implements OnSeekBarChangeListener, OnClickListener{
	private SeekBar seekbarMin, seekbarMax;
	private TextView counterMin, counterMax;
	private int minTemp, maxTemp;
	private Button sendButt;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View inflatedView = inflater.inflate(R.layout.thermometer, null);
		
		seekbarMin = (SeekBar) inflatedView.findViewById(R.id.seekBarMin);
		seekbarMin.setOnSeekBarChangeListener(this);
		
		seekbarMax = (SeekBar) inflatedView.findViewById(R.id.seekBarMax);
		seekbarMax.setOnSeekBarChangeListener(this);
		
		counterMin = (TextView) inflatedView.findViewById(R.id.textViewMin);
		counterMax = (TextView) inflatedView.findViewById(R.id.textViewMax);
		
		inflatedView.setRotation(270);
		
		sendButt = (Button) inflatedView.findViewById(R.id.button1);
		sendButt.setOnClickListener(this);
		return inflatedView;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		//Toast.makeText(getSherlockActivity(), "Progress changed... " + progress, Toast.LENGTH_LONG).show();
		//Next 2 lines are to make seekbars "follow" eachother.
		if(maxTemp <= minTemp)seekbarMin.setProgress(maxTemp);
		if(minTemp >= maxTemp)seekbarMax.setProgress(minTemp);
		
		if(seekBar == seekbarMax){
			maxTemp = progress;
			counterMax.setText("Max: "+progress);		
		}
		if(seekBar == seekbarMin){
			minTemp = progress;
			counterMin.setText("Min: "+progress);		
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		Toast.makeText(getSherlockActivity(), "Started tracking...", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		Toast.makeText(getSherlockActivity(), "Stopped tracking...", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		SMSHandler handler = new SMSHandler(getActivity());
		handler.SendSMS(CommandTypes.Status);
		
	}
}
