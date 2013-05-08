package org.northernnerds.projectcrankuptheheat;

import org.northernnerds.enums.CommandTypes;
import org.northernnerds.enums.SettingsNames;
import org.northernnerds.settings.SMSHandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class InfoFragment extends SherlockFragment implements OnClickListener, Observer {

	private final int iDefaultValue = -111;

	private char degreeSign = (char) 0x00B0;

	private int currentTemp = -12;
	private int warningTempLow = -8;
	private int warningTempHigh = -8;
	private int heatingTemp = -24;
	private int coolingTemp = -30;
	private int gsmBat = 34;
	private int gsmSignal = 0;

	private ImageButton refreshImageButton;
	private ImageView gsmSignalImageView, batteryImageView, currentProgramImageView;
	private TextView batteryTextView, currentTemperatureTextView, warningHighTextView, warningLowTextView, currentProgramTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Toast.makeText(getActivity(), "InfoFragment: OnCreate", Toast.LENGTH_SHORT).show();

		View inflatedView = inflater.inflate(R.layout.infoscreen_layout, null);
		inflatedView.setBackgroundResource(R.drawable.bg);

		currentTemperatureTextView = (TextView) inflatedView.findViewById(R.id.currentTemperatureTextView);

		warningHighTextView = (TextView) inflatedView.findViewById(R.id.alarmsHighTextView);
		warningLowTextView = (TextView) inflatedView.findViewById(R.id.alarmsLowTextView);
		currentProgramImageView = (ImageView) inflatedView.findViewById(R.id.currentProgramImageView);
		currentProgramTextView = (TextView) inflatedView.findViewById(R.id.currentProgramTextView);
		gsmSignalImageView = (ImageView) inflatedView.findViewById(R.id.gsmSignalImageView);
		batteryImageView = (ImageView) inflatedView.findViewById(R.id.batteryImageView);
		batteryTextView = (TextView) inflatedView.findViewById(R.id.batteryTextView);

		refreshImageButton = (ImageButton) inflatedView.findViewById(R.id.refreshButtonImageButton);
		refreshImageButton.setOnClickListener(this);

		updateFieldsAndViewFromSharedPreferences();
		
		setProgramImagePlusTextView();
		setBatteryImageAndTextView();
		setGsmSignalImageView();

		return inflatedView;
	}

	@Override
	public void onPause() {
		Toast.makeText(getActivity(), "InfoFragment: OnPause", Toast.LENGTH_SHORT).show();
		super.onPause();
		updateSharedPreferencesfromFields();
	}

	@Override
	public void onResume() {
		Toast.makeText(getActivity(), "InfoFragment: OnResume", Toast.LENGTH_SHORT).show();
		super.onResume();
		updateFieldsAndViewFromSharedPreferences();
	}

	private void setProgramImagePlusTextView() {
		//program = heat
		if(coolingTemp == -1){
			currentProgramImageView.setImageResource(R.drawable.varme_ikon);
			currentProgramTextView.setText(""+heatingTemp+degreeSign);
		}
		//program = cool
		else if(heatingTemp == -1){
			currentProgramImageView.setImageResource(R.drawable.kulde_ikon);
			currentProgramTextView.setText(""+coolingTemp+degreeSign);
		}
		else{
			currentProgramImageView.setImageResource(R.drawable.refresh);//Just to set something
			currentProgramTextView.setText("N/A");
		}
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
			batteryImageView.setImageResource(R.drawable.bat10);
		}
		batteryTextView.setText("" + gsmBat + "%");
	}

	private void setGsmSignalImageView() {
		switch (gsmSignal) {
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

	private void updateSharedPreferencesfromFields() {
		SharedPreferences settings = getSherlockActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		editor.putInt(SettingsNames.CURRENT_TEMP.getName(), currentTemp);
		editor.putInt(SettingsNames.WARNING_TEMP_HIGH.getName(), warningTempHigh);
		editor.putInt(SettingsNames.WARNING_TEMP_LOW.getName(), warningTempLow);
		editor.putInt(SettingsNames.GSM_BATTERY.getName(), gsmBat);
		editor.putInt(SettingsNames.GSM_SIGNAL.getName(), gsmSignal);

		editor.putInt(SettingsNames.COOL_TEMP.getName(), coolingTemp);
		editor.putInt(SettingsNames.HEAT_TEMP.getName(), heatingTemp);

		editor.commit();

	}

	private void updateFieldsAndViewFromSharedPreferences() {
		SharedPreferences settings = getSherlockActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), Context.MODE_PRIVATE);
		currentTemp = settings.getInt(SettingsNames.CURRENT_TEMP.getName(), iDefaultValue);
		warningTempHigh = settings.getInt(SettingsNames.WARNING_TEMP_HIGH.getName(), iDefaultValue);
		warningTempLow = settings.getInt(SettingsNames.WARNING_TEMP_LOW.getName(), iDefaultValue);
		gsmBat = settings.getInt(SettingsNames.GSM_BATTERY.getName(), iDefaultValue);
		gsmSignal = settings.getInt(SettingsNames.GSM_SIGNAL.getName(), iDefaultValue);

		coolingTemp = settings.getInt(SettingsNames.COOL_TEMP.getName(), iDefaultValue);
		heatingTemp = settings.getInt(SettingsNames.HEAT_TEMP.getName(), iDefaultValue);
		if (coolingTemp == -1) {
			// program is heat
			currentProgramImageView.setImageResource(R.drawable.varme_ikon);
			currentProgramTextView.setText("" + heatingTemp + degreeSign);
		} else if (heatingTemp == -1) {
			// program is cool
			currentProgramImageView.setImageResource(R.drawable.kulde_ikon);
			currentProgramTextView.setText("" + coolingTemp + degreeSign);
		}

		currentTemperatureTextView.setText("" + currentTemp + degreeSign);
		warningHighTextView.setText("" + warningTempHigh + degreeSign);
		warningLowTextView.setText("" + warningTempLow + degreeSign);

		setProgramImagePlusTextView();
		setBatteryImageAndTextView();
		setGsmSignalImageView();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.refreshButtonImageButton:
			SMSHandler handler = new SMSHandler(getActivity());
			handler.SendSMS(CommandTypes.STATUS);
			break;
		default:
			break;
		}
	}

	@Override
	public void update() {
		updateFieldsAndViewFromSharedPreferences();
	}
}
