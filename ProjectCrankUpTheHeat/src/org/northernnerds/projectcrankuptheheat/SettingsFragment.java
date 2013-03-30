package org.northernnerds.projectcrankuptheheat;

import org.northennerds.enums.SettingsNames;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class SettingsFragment extends SherlockFragment implements OnClickListener {
	private EditText unitNameEditText, phonenoEditText, passwordEditText;
	private Button thermometerButton, updateStatusButton;
	private CheckBox oldControlCheckBox;
	private ImageView gsmSignalImageView, batteryImageView;
	private TextView batteryTextView;
	
	//Values for SharedPrefs
	private String name = "Sommerfjong i bingbong";
//	private boolean isOldController = true;
//	private String devicePhoneNum = "004528921237";
//	private String passwd = "1234";
	private int minTemp = 8;
	private int maxTemp = 8;
	private int heatingTemp = 24;
	private int coolingTemp = 30;
	private int gsmBat = 34;
	private int gsmSignal = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View inflatedView = inflater.inflate(R.layout.behind, null);
		unitNameEditText = (EditText) inflatedView.findViewById(R.id.unitNameEditText);
		phonenoEditText = (EditText) inflatedView.findViewById(R.id.phonenoEditText);
		passwordEditText = (EditText) inflatedView.findViewById(R.id.passwordEditText);
		
		oldControlCheckBox = (CheckBox) inflatedView.findViewById(R.id.oldControlCheckBox);
		
		gsmSignalImageView = (ImageView) inflatedView.findViewById(R.id.gsmSignalImageView);
		batteryImageView = (ImageView) inflatedView.findViewById(R.id.batteryImageView);
		
		batteryTextView = (TextView) inflatedView.findViewById(R.id.batteryTextView);
		
		thermometerButton = (Button) inflatedView.findViewById(R.id.alarmButton);
		thermometerButton.setOnClickListener(this);
		
		updateStatusButton = (Button) inflatedView.findViewById(R.id.updateStatusButton);
		updateStatusButton.setOnClickListener(this);
		
		return inflatedView;
	}

	@Override
	public void onPause() {
		SharedPreferences settings = getActivity().getSharedPreferences(SettingsNames.prefsName.getName(), 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SettingsNames.deviceName.getName(), name);
		editor.putBoolean(SettingsNames.isOldController.getName(), oldControlCheckBox.isChecked());
		editor.putString(SettingsNames.phoneNum.getName(), phonenoEditText.getText().toString());
		editor.putString(SettingsNames.password.getName(), passwordEditText.getText().toString()); 
		editor.putInt(SettingsNames.minTemp.getName(), minTemp);
		editor.putInt(SettingsNames.maxTemp.getName(), maxTemp);
		editor.putInt(SettingsNames.HeatTemp.getName(), heatingTemp); 
		editor.putInt(SettingsNames.CoolTemp.getName(), coolingTemp); 
		editor.putInt(SettingsNames.GSMBat.getName(), gsmBat);
		editor.putInt(SettingsNames.GSMSignal.getName(), gsmSignal);
		editor.commit();
		super.onPause();
	}
	@Override
	public void onResume() {
		// Restore preferences
		SharedPreferences settings = getActivity().getSharedPreferences(SettingsNames.prefsName.getName(), Context.MODE_PRIVATE);
		unitNameEditText.setText(settings.getString(SettingsNames.deviceName.getName(), "Ingen enhed fundet"));
		phonenoEditText.setText(settings.getString(SettingsNames.phoneNum.getName(), ""));
		passwordEditText.setText(settings.getString(SettingsNames.password.getName(), ""));
		oldControlCheckBox.setChecked(settings.getBoolean(SettingsNames.isOldController.getName(), false));
		
		gsmBat = settings.getInt(SettingsNames.GSMBat.getName(), gsmBat);
		gsmSignal = settings.getInt(SettingsNames.GSMSignal.getName(), gsmSignal);
		
		minTemp = settings.getInt(SettingsNames.minTemp.getName(), 9);
		maxTemp = settings.getInt(SettingsNames.maxTemp.getName(), 9);
		heatingTemp = settings.getInt(SettingsNames.HeatTemp.getName(), 24);
		coolingTemp = settings.getInt(SettingsNames.CoolTemp.getName(), 20);
		
		setBatteryImageAndTextView();
		setGsmSignalImageView();
		
		super.onResume();
	}
	
	private void setBatteryImageAndTextView() {
		if (gsmBat == 0) {
			batteryImageView.setImageResource(R.drawable.bat0);
		} else if (gsmBat > 0 && gsmBat <= 10) {
			batteryImageView.setImageResource(R.drawable.bat1);
		} else if (gsmBat > 10 && gsmBat <= 20) {
			batteryImageView.setImageResource(R.drawable.bat2);
		} else if (gsmBat > 20 && gsmBat <= 30) {
			batteryImageView.setImageResource(R.drawable.bat3);
		} else if (gsmBat > 30 && gsmBat <= 40) {
			batteryImageView.setImageResource(R.drawable.bat4);
		} else if (gsmBat > 40 && gsmBat <= 50) {
			batteryImageView.setImageResource(R.drawable.bat5);
		} else if (gsmBat > 50 && gsmBat <= 60) {
			batteryImageView.setImageResource(R.drawable.bat6);
		} else if (gsmBat > 60 && gsmBat <= 70) {
			batteryImageView.setImageResource(R.drawable.bat7);
		} else if (gsmBat > 70 && gsmBat <= 80) {
			batteryImageView.setImageResource(R.drawable.bat8);
		} else if (gsmBat > 80 && gsmBat <= 90) {
			batteryImageView.setImageResource(R.drawable.bat9);
		} else {
			batteryImageView.setBackgroundResource(R.drawable.bat10);
		}
		batteryTextView.setText("" + gsmBat + "%");
	}
	
	private void setGsmSignalImageView() {
		switch(gsmSignal) {
		case 1:
			gsmSignalImageView.setImageResource(R.drawable.net0);
			break;
		case 2:
			gsmSignalImageView.setImageResource(R.drawable.net1);
			break;
		case 3:
			gsmSignalImageView.setImageResource(R.drawable.net2);
			break;
		case 4:
			gsmSignalImageView.setImageResource(R.drawable.net3);
			break;
		case 5:
			gsmSignalImageView.setImageResource(R.drawable.net4);
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.updateStatusButton:
			Toast.makeText(getActivity(), "Send opdatér status her!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.alarmButton:
			Intent intent = new Intent(getSherlockActivity(), ThermometerActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
