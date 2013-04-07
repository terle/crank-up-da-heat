package org.northernnerds.projectcrankuptheheat;

import org.northennerds.enums.CommandTypes;
import org.northennerds.enums.SettingsNames;
import org.northennerds.settings.SMSHandler;

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
	private String devicePhoneNum = "+4561319616";
	private String passwd = "8110";
	private int minTemp = 8;
	private int maxTemp = 8;
	private int heatingTemp = 24;
	private int coolingTemp = 30;
	private int gsmBat = 34;
	private int gsmSignal = 0;
	private int aktuelTemp =18;
	private String phNum01 = "+4528921237";
	private String phNum02 = "";
	private String phNum03 = "";
	private String phNum04 = "";
	private String brandName ="";
	
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
		editor.putString(SettingsNames.DevicePhoneNum.getName(), phonenoEditText.getText().toString());
		editor.putString(SettingsNames.password.getName(), passwordEditText.getText().toString()); 
		editor.putInt(SettingsNames.warningTempLOW.getName(), minTemp);
		editor.putInt(SettingsNames.warningTempHIGH.getName(), maxTemp);
		editor.putInt(SettingsNames.HeatTemp.getName(), heatingTemp); 
		editor.putInt(SettingsNames.CoolTemp.getName(), coolingTemp); 
		editor.putInt(SettingsNames.GSMBat.getName(), gsmBat);
		editor.putInt(SettingsNames.GSMSignal.getName(), gsmSignal);
		
		editor.putInt(SettingsNames.AktuelTemp.getName(), aktuelTemp);
		editor.putString(SettingsNames.AlarmNum01.getName(), phNum01);
		editor.putString(SettingsNames.AlarmNum02.getName(), phNum02);
		editor.putString(SettingsNames.AlarmNum03.getName(), phNum03);
		editor.putString(SettingsNames.AlarmNum04.getName(), phNum04);
		editor.putString(SettingsNames.BrandName.getName(), brandName);
		
		editor.commit();
		super.onPause();
	}
	@Override
	public void onResume() {
		// Restore preferences
		SharedPreferences settings = getActivity().getSharedPreferences(SettingsNames.prefsName.getName(), Context.MODE_PRIVATE);
		unitNameEditText.setText(settings.getString(SettingsNames.deviceName.getName(), "Ingen enhed fundet"));
		phonenoEditText.setText(settings.getString(SettingsNames.DevicePhoneNum.getName(), ""));
		passwordEditText.setText(settings.getString(SettingsNames.password.getName(), ""));
		oldControlCheckBox.setChecked(settings.getBoolean(SettingsNames.isOldController.getName(), false));
		
		gsmBat = settings.getInt(SettingsNames.GSMBat.getName(), gsmBat);
		gsmSignal = settings.getInt(SettingsNames.GSMSignal.getName(), gsmSignal);
		
		minTemp = settings.getInt(SettingsNames.warningTempLOW.getName(), 9);
		maxTemp = settings.getInt(SettingsNames.warningTempHIGH.getName(), 9);
		heatingTemp = settings.getInt(SettingsNames.HeatTemp.getName(), 24);
		coolingTemp = settings.getInt(SettingsNames.CoolTemp.getName(), 20);
		
		aktuelTemp = settings.getInt(SettingsNames.AktuelTemp.getName(), 18);
		
		phNum01 = settings.getString(SettingsNames.AlarmNum01.getName(), "N/A");
		phNum02 = settings.getString(SettingsNames.AlarmNum02.getName(), "N/A");
		phNum03 = settings.getString(SettingsNames.AlarmNum03.getName(), "N/A");
		phNum04 = settings.getString(SettingsNames.AlarmNum04.getName(), "N/A");
		brandName = settings.getString(SettingsNames.BrandName.getName(), "N/A");
		
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
			Toast.makeText(getActivity(), "Sender status SMS! Vent pŒ svar", Toast.LENGTH_SHORT).show();
			SMSHandler handler = new SMSHandler(getActivity());
			handler.SendSMS(CommandTypes.Status);
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
