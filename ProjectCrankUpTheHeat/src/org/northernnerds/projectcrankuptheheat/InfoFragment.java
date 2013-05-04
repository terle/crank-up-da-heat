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

public class InfoFragment extends SherlockFragment implements OnClickListener {

	private final String strDefaultValue = "N/A";
	private final int iDefaultValue = -111;
	
	private char degreeSign = (char) 0x00B0;

	private int currentTemp = -12;
	private int warningTempLow = -8;
	private int warningTempHigh = -8;
	private int heatingTemp = -24;
	private int coolingTemp = -30;
	private int gsmBat = 34;
	private int gsmSignal = 0;

	private String unitName = "Sommerfjong i bingbong";
	private String currentProgram;
	// private Button thermometerButton, updateStatusButton;
	private ImageButton refreshImageButton;
	private ImageView gsmSignalImageView, batteryImageView, currentProgramImageView, warnningHighImageView, warningLowImageView;
	private TextView batteryTextView, currentTemperatureTextView, warningHighTextView, warningLowTextView,
			currentProgramTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Toast.makeText(getActivity(), "InfoFragment: OnCreate", Toast.LENGTH_SHORT).show();

		View inflatedView = inflater.inflate(R.layout.infoscreen_layout, null);

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

		setProgramImagePlusTextView();
		setBatteryImageAndTextView();
		setGsmSignalImageView();

		// thermometerButton = (Button)
		// inflatedView.findViewById(R.id.alarmButton);
		// thermometerButton.setOnClickListener(this);

		// updateStatusButton = (Button)
		// inflatedView.findViewById(R.id.updateStatusButton);
		// updateStatusButton.setOnClickListener(this);

		return inflatedView;
	}

	@Override
	public void onPause() {
		Toast.makeText(getActivity(), "InfoFragment: OnPause", Toast.LENGTH_SHORT).show();
		super.onPause();
		// SharedPreferences settings =
		// getActivity().getSharedPreferences(SettingsNames.prefsName.getName(),
		// Context.MODE_PRIVATE);
		// SharedPreferences.Editor editor = settings.edit();
		//
		// editor.commit();
		updateSharedPreferencesfromFields();
	}

	@Override
	public void onResume() {
		Toast.makeText(getActivity(), "InfoFragment: OnResume", Toast.LENGTH_SHORT).show();
		super.onResume();
		// SharedPreferences settings =
		// getSherlockActivity().getSharedPreferences(SettingsNames.prefsName.getName(),
		// Context.MODE_PRIVATE);
		//
		// currentTemperatureTextView.setText(settings.getString(SettingsNames.AktuelTemp.getName(),
		// "N/A"));
		// warningHighTextView.setText(settings.getString(SettingsNames.warningTempHIGH.getName(),
		// "N/A"));
		// warningLowTextView.setText(settings.getString(SettingsNames.warningTempLOW.getName(),
		// "N/A"));
		//
		// currentProgram =
		// settings.getString(SettingsNames.AktuelTemp.getName(), "N/A");
		// gsmSignal = settings.getInt(SettingsNames.GSMSignal.getName(), 0);
		// setGsmSignalImageView();
		// gsmBat = settings.getInt(SettingsNames.GSMBat.getName(), 0);
		// setBatteryImageAndTextView();

		updateFieldsfromSharedPreferences();
	}

	private void setProgramImagePlusTextView() {
		// TODO: S¾t billede alt efter heat/Cool + textView til temperatur (fx
		// heat_16)
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
	
	private void updateSharedPreferencesfromFields(){
		SharedPreferences settings = getSherlockActivity().getSharedPreferences(SettingsNames.prefsName.getName(),
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putInt(SettingsNames.currentTemperatur.getName(), currentTemp);
		editor.putInt(SettingsNames.warningTempHIGH.getName(), warningTempHigh);
		editor.putInt(SettingsNames.warningTempLOW.getName(), warningTempLow);
		editor.putInt(SettingsNames.GSMBat.getName(), gsmBat);
		editor.putInt(SettingsNames.GSMSignal.getName(), gsmSignal);
		
		editor.putInt(SettingsNames.CoolTemp.getName(), coolingTemp);
		editor.putInt(SettingsNames.HeatTemp.getName(), heatingTemp);
		
		editor.commit();
		
	}

	private void updateFieldsfromSharedPreferences() {
		SharedPreferences settings = getSherlockActivity().getSharedPreferences(SettingsNames.prefsName.getName(),
				Context.MODE_PRIVATE);
		currentTemp = settings.getInt(SettingsNames.currentTemperatur.getName(), iDefaultValue);
		warningTempHigh = settings.getInt(SettingsNames.warningTempHIGH.getName(), iDefaultValue);
		warningTempLow= settings.getInt(SettingsNames.warningTempLOW.getName(), iDefaultValue);
		gsmBat = settings.getInt(SettingsNames.GSMBat.getName(), iDefaultValue);
		gsmSignal = settings.getInt(SettingsNames.GSMSignal.getName(), iDefaultValue);

		// String strCProgram = settings.getString(SettingsNames.,
		// strDefaultValue);
		
		coolingTemp = settings.getInt(SettingsNames.CoolTemp.getName(), iDefaultValue);
		heatingTemp = settings.getInt(SettingsNames.HeatTemp.getName(), iDefaultValue);
		if (coolingTemp == -1) {
			// program is heat
			currentProgramImageView.setImageResource(R.drawable.varme_ikon);
			currentProgramTextView.setText("" + heatingTemp+degreeSign);
		}
		else if (heatingTemp == -1) {
			//program is cool
			currentProgramImageView.setImageResource(R.drawable.kulde_ikon);
			currentProgramTextView.setText(""+coolingTemp+degreeSign);
		}

		currentTemperatureTextView.setText("" + currentTemp+degreeSign);
		warningHighTextView.setText("" + warningTempHigh+degreeSign);
		warningLowTextView.setText("" + warningTempLow+degreeSign);

		setBatteryImageAndTextView();
		setGsmSignalImageView();

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.refreshButtonImageButton:
			// Toast.makeText(getActivity(), "Sender status SMS! Vent pŒ svar",
			// Toast.LENGTH_SHORT).show();
			SMSHandler handler = new SMSHandler(getActivity());
			handler.SendSMS(CommandTypes.Status);
			break;

		default:
			break;
		}
	}
}
