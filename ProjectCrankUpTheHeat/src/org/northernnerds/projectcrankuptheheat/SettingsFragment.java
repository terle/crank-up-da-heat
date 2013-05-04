package org.northernnerds.projectcrankuptheheat;

import org.northernnerds.enums.Brands;
import org.northernnerds.enums.SettingsNames;

import android.app.Activity;
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

	//Values for SharedPrefs
	private String deviceName = "Sommerfjong i bingbong";
	private Brands brand = Brands.Panasonic;
	private String brandName = "Panasonic";
	private String devicePhoneNum = "+4561319616"; //Dette er tlf nr til device
	private String devicePasswd = "8110"; //Dette er password til device
	private String phNum01 = "+4528921237";
	private String phNum02 = "";
	private String phNum03 = "";
	private String phNum04 = "";
	private ImageButton lockButton;
	private boolean isLocked = false;
	private Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Toast.makeText(getActivity(), "SettingsFragment: OnCreateView", Toast.LENGTH_SHORT).show();

		View inflatedView = inflater.inflate(R.layout.settings_slideout_layout, null);
		
		SharedPreferences settings = getActivity().getSharedPreferences(SettingsNames.prefsName.getName(), 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SettingsNames.deviceName.getName(), deviceName);
		editor.putString(SettingsNames.devicePhoneNum.getName(), devicePhoneNum);
		editor.putString(SettingsNames.devicePassword.getName(), devicePasswd);
		editor.commit();
		
		showWizardCheckBox = (CheckBox) inflatedView.findViewById(R.id.showWizardCheckBox);
		showWizardCheckBox.setOnCheckedChangeListener(this);
		
		unitNameEditText = (EditText) inflatedView.findViewById(R.id.unitNameEditText);
		unitNameEditText.setText(settings.getString(SettingsNames.deviceName.getName(), "N/A"));
		
		phoneNumEditText = (EditText) inflatedView.findViewById(R.id.phonenoEditText);
		phoneNumEditText.setText(settings.getString(SettingsNames.devicePhoneNum.getName(), "N/A"));
		
		passwordEditText = (EditText) inflatedView.findViewById(R.id.unitPasswordEditText);
		passwordEditText.setText(settings.getString(SettingsNames.devicePassword.getName(), "N/A"));
		
		lockButton = (ImageButton) inflatedView.findViewById(R.id.lockButtonImageButton);
		lockButton.setOnClickListener(this);

		//TODO: get phonenumbers
		
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
		
		SharedPreferences sharedPreferences = getSherlockActivity().getSharedPreferences(SettingsNames.prefsName.getName(), Context.MODE_PRIVATE);
	    boolean shouldShowWizard = sharedPreferences.getBoolean("showWizard", false);
	    showWizardCheckBox.setChecked(shouldShowWizard);
	}
	
	private void updateSharedPreferencesfromFields(){
		SharedPreferences settings = getSherlockActivity().getSharedPreferences(SettingsNames.prefsName.getName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString(SettingsNames.deviceName.getName(), deviceName);
		editor.putString(SettingsNames.devicePhoneNum.getName(), devicePhoneNum);
		editor.putString(SettingsNames.devicePassword.getName(), devicePasswd);
		
		editor.putString(SettingsNames.AlarmNum01.getName(), phNum01);
		editor.putString(SettingsNames.AlarmNum02.getName(), phNum02);
		editor.putString(SettingsNames.AlarmNum03.getName(), phNum03);
		editor.putString(SettingsNames.AlarmNum04.getName(), phNum04);
		
		editor.putString(SettingsNames.BrandName.getName(), brand.getName());
		
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
		
		unitNameEditText.setText(deviceName);
		phoneNumEditText.setText(devicePhoneNum);
		passwordEditText.setText(devicePasswd);
		
		//TODO: Set alarmnumbers
		//TODO: Set Brand Logo
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lockButtonImageButton:
			if(isLocked) {
				lockButton.setImageResource(R.drawable.lock_unlocked_icon);
				isLocked = false;
			} else{
				lockButton.setImageResource(R.drawable.lock_locked_icon);
				isLocked = true;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		SharedPreferences sharedPreferences = getSherlockActivity().getSharedPreferences(SettingsNames.prefsName.getName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putBoolean("showWizard", isChecked);
	    editor.commit();
	}
}
