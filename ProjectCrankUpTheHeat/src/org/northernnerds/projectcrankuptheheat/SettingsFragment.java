package org.northernnerds.projectcrankuptheheat;

import org.northennerds.enums.SettingsNames;

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

import com.actionbarsherlock.app.SherlockFragment;

public class SettingsFragment extends SherlockFragment implements OnClickListener {
	private EditText unitNameEditText, phonenoEditText, passwordEditText;
	private Button thermometerButton;
	private CheckBox oldControlCheckBox;
	private ImageView gsmSignalImageView, batteryImageView;
	private TextView batteryTextView;
	
	//Values for SharedPrefs
	private String name = "Sommerfjong i bingbong";
	private boolean isOldController = false;
	private String devicePhoneNum = "004528921237";
	private String passwd = "1234";
	private int minTemp = 8;
	private int maxTemp = 8;
	private int heatingTemp = 24;
	private int coolingTemp = 30;
	private String gsmBat = "100%";
	private String gsmSignal ="(1-5):3";
	
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
		
		return inflatedView;
	}

	@Override
	public void onPause() {
		SharedPreferences settings = getActivity().getSharedPreferences(SettingsNames.prefsName.getName(), 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SettingsNames.deviceName.getName(), name);
		editor.putBoolean(SettingsNames.isOldController.getName(), isOldController);
		editor.putString(SettingsNames.phoneNum.getName(), devicePhoneNum);
		editor.putString(SettingsNames.password.getName(), passwd); 
		editor.putInt(SettingsNames.minTemp.getName(), minTemp);
		editor.putInt(SettingsNames.maxTemp.getName(), maxTemp);
		editor.putInt(SettingsNames.HeatTemp.getName(), heatingTemp); 
		editor.putInt(SettingsNames.CoolTemp.getName(), coolingTemp); 
		editor.putString(SettingsNames.GSMBat.getName(), gsmBat);
		editor.putString(SettingsNames.GSMSignal.getName(), gsmSignal);
		editor.commit();
		super.onPause();
	}
	@Override
	public void onResume() {
		// Restore preferences
		SharedPreferences settings = getActivity().getSharedPreferences(SettingsNames.prefsName.getName(), 0);
		name = settings.getString(SettingsNames.deviceName.getName(), name);
		isOldController = settings.getBoolean(SettingsNames.isOldController.getName(), false);
		devicePhoneNum = settings.getString(SettingsNames.phoneNum.getName(), "");
		passwd = settings.getString(SettingsNames.password.getName(), "1234");
		minTemp = settings.getInt(SettingsNames.minTemp.getName(), 9);
		maxTemp = settings.getInt(SettingsNames.maxTemp.getName(), 9);
		heatingTemp = settings.getInt(SettingsNames.HeatTemp.getName(), 24);
		coolingTemp = settings.getInt(SettingsNames.CoolTemp.getName(), 20);
		gsmBat = settings.getString(SettingsNames.GSMBat.getName(), "100%");
		gsmSignal = settings.getString(SettingsNames.GSMSignal.getName(), "bla");

		super.onResume();
	}
	
	private void setBatteryImageAndTextView() {
		
	}
	
	private void setGsmSignalImageView() {
		
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getSherlockActivity(), ThermometerActivity.class);
		startActivity(intent);
	}
}
