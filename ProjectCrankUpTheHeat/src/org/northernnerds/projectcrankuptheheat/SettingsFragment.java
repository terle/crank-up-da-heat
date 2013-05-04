package org.northernnerds.projectcrankuptheheat;

import org.northernnerds.enums.Brands;
import org.northernnerds.enums.SettingsNames;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class SettingsFragment extends SherlockFragment implements OnClickListener, OnCheckedChangeListener {
	private final String strDefaultValue = "N/A";
	private final int iDefaultValue = -111;

	private EditText unitNameEditText, phoneNumEditText, passwordEditText;
	private CheckBox showWizardCheckBox;
	private View inflatedView;

	// Values for SharedPrefs
	private String deviceName = "Sommerfjong i bingbong";
	private Brands brand = Brands.Panasonic;
	private String brandName = "Panasonic";
	private String devicePhoneNum = "+4561319616"; // Dette er tlf nr til device
	private String devicePasswd = "8110"; // Dette er password til device
	private String phNum01 = "+4528921237";
	private String phNum02 = "";
	private String phNum03 = "";
	private String phNum04 = "";
	private ImageButton lockButton;
	private EditText alarmNum01EditText, alarmNum02EditText, alarmNum03EditText, alarmNum04EditText;
	private boolean isLocked = false;
	boolean shouldShowWizard = false;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Toast.makeText(getActivity(), "SettingsFragment: OnCreateView", Toast.LENGTH_SHORT).show();

		inflatedView = inflater.inflate(R.layout.settings_slideout_layout, null);

		SharedPreferences settings = getActivity().getSharedPreferences(SettingsNames.prefsName.getName(), 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SettingsNames.deviceName.getName(), deviceName);
		editor.putString(SettingsNames.devicePhoneNum.getName(), devicePhoneNum);
		editor.putString(SettingsNames.devicePassword.getName(), devicePasswd);
		editor.commit();

		showWizardCheckBox = (CheckBox) inflatedView.findViewById(R.id.showWizardCheckBox);
		showWizardCheckBox.setOnCheckedChangeListener(this);

		unitNameEditText = (EditText) inflatedView.findViewById(R.id.unitNameEditText);
		unitNameEditText.setText(settings.getString(SettingsNames.deviceName.getName(), strDefaultValue));

		phoneNumEditText = (EditText) inflatedView.findViewById(R.id.phonenoEditText);
		phoneNumEditText.setText(settings.getString(SettingsNames.devicePhoneNum.getName(), strDefaultValue));

		passwordEditText = (EditText) inflatedView.findViewById(R.id.unitPasswordEditText);
		passwordEditText.setText(settings.getString(SettingsNames.devicePassword.getName(), strDefaultValue));

		alarmNum01EditText = (EditText) inflatedView.findViewById(R.id.alarmNum01EditText);
		alarmNum01EditText.setText(settings.getString(SettingsNames.AlarmNum01.getName(), strDefaultValue));

		alarmNum02EditText = (EditText) inflatedView.findViewById(R.id.alarmNum02EditText);
		alarmNum02EditText.setText(settings.getString(SettingsNames.AlarmNum02.getName(), strDefaultValue));

		alarmNum03EditText = (EditText) inflatedView.findViewById(R.id.alarmNum03EditText);
		alarmNum03EditText.setText(settings.getString(SettingsNames.AlarmNum03.getName(), strDefaultValue));

		alarmNum04EditText = (EditText) inflatedView.findViewById(R.id.alarmNum04EditText);
		alarmNum04EditText.setText(settings.getString(SettingsNames.AlarmNum04.getName(), strDefaultValue));

		lockButton = (ImageButton) inflatedView.findViewById(R.id.lockButtonImageButton);
		lockButton.setOnClickListener(this);

		// TODO: get phonenumbers
		
		return inflatedView;
	}

	@Override
	public void onPause() {
		Toast.makeText(getActivity(), "SettingsFragment: OnPause", Toast.LENGTH_SHORT).show();
		super.onPause();

		updateSharedPreferencesfromFields();
	}

	@Override
	public void onResume() {
		Toast.makeText(getActivity(), "SettingsFragment: OnResume", Toast.LENGTH_SHORT).show();
		super.onResume();
		// Restore preferences
		updateFieldsfromSharedPreferences();

		SharedPreferences sharedPreferences = getSherlockActivity().getSharedPreferences(SettingsNames.prefsName.getName(),
						Context.MODE_PRIVATE);
		boolean shouldShowWizard = sharedPreferences.getBoolean(SettingsNames.ShouldShowWizzard.getName(), false);
		showWizardCheckBox.setChecked(shouldShowWizard);
	}

	private void updateSharedPreferencesfromFields() {
		SharedPreferences settings = getSherlockActivity().getSharedPreferences(SettingsNames.prefsName.getName(), 
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		editor.putString(SettingsNames.deviceName.getName(), deviceName);
		editor.putString(SettingsNames.devicePhoneNum.getName(), devicePhoneNum);
		editor.putString(SettingsNames.devicePassword.getName(), devicePasswd);

		String number = alarmNum01EditText.getText().toString();
		editor.putString(SettingsNames.AlarmNum01.getName(), number);
		number = alarmNum02EditText.getText().toString();
		editor.putString(SettingsNames.AlarmNum02.getName(), number);
		number = alarmNum03EditText.getText().toString();
		editor.putString(SettingsNames.AlarmNum03.getName(), number);
		number = alarmNum04EditText.getText().toString();
		editor.putString(SettingsNames.AlarmNum04.getName(), number);

		editor.putString(SettingsNames.BrandName.getName(), brand.getName());
		editor.putBoolean(SettingsNames.ShouldShowWizzard.getName(), shouldShowWizard);
		
		editor.commit();
	}

	private void updateFieldsfromSharedPreferences() {
		SharedPreferences settings = getSherlockActivity().getSharedPreferences(SettingsNames.prefsName.getName(), 
				Context.MODE_PRIVATE);
		deviceName = settings.getString(SettingsNames.deviceName.getName(), strDefaultValue);
		devicePhoneNum = settings.getString(SettingsNames.devicePhoneNum.getName(), strDefaultValue);
		devicePasswd = settings.getString(SettingsNames.devicePassword.getName(), strDefaultValue);

		phNum01 = settings.getString(SettingsNames.AlarmNum01.getName(), strDefaultValue);
		phNum02 = settings.getString(SettingsNames.AlarmNum02.getName(), strDefaultValue);
		phNum03 = settings.getString(SettingsNames.AlarmNum03.getName(), strDefaultValue);
		phNum04 = settings.getString(SettingsNames.AlarmNum04.getName(), strDefaultValue);

		brandName = settings.getString(SettingsNames.BrandName.getName(), strDefaultValue);
		shouldShowWizard = settings.getBoolean(SettingsNames.ShouldShowWizzard.getName(), false);

		unitNameEditText.setText(deviceName);
		phoneNumEditText.setText(devicePhoneNum);
		passwordEditText.setText(devicePasswd);
		alarmNum01EditText.setText(phNum01);
		alarmNum02EditText.setText(phNum02);
		alarmNum03EditText.setText(phNum03);
		alarmNum04EditText.setText(phNum04);
		
		// TODO: Set Brand Logo
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lockButtonImageButton:
			ViewGroup parentView = (ViewGroup)inflatedView;
			if (isLocked) {
				lockButton.setImageResource(R.drawable.lock_unlocked_icon);
				isLocked = false;
				for(int i = 0; i < parentView.getChildCount(); i++) {
					parentView.getChildAt(i).setEnabled(true);
				}
			} else {
				lockButton.setImageResource(R.drawable.lock_locked_icon);
				isLocked = true;
				for(int i = 0; i < parentView.getChildCount(); i++) {
					if(parentView.getChildAt(i).getId() != R.id.lockButtonImageButton) {
						parentView.getChildAt(i).setEnabled(false);
					}
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		SharedPreferences sharedPreferences = getSherlockActivity().getSharedPreferences(SettingsNames.prefsName.getName(),
						Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(SettingsNames.ShouldShowWizzard.getName(), isChecked);
		editor.commit();
	}
}
