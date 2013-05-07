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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class SettingsFragment extends SherlockFragment implements OnClickListener, OnCheckedChangeListener, OnItemSelectedListener {
	private final String strDefaultValue = "N/A";
	// private final int iDefaultValue = -111;

	private EditText unitNameEditText, phoneNumEditText, passwordEditText;
	private CheckBox showWizardCheckBox;
	private View inflatedView;

	// Values for SharedPrefs
	private String deviceName = "Sommerfjong i bingbong";
	private Brands brand = Brands.PANASONIC;
	private String devicePhoneNum = "+4561319616"; // Dette er tlf nr til device
	private String devicePasswd = "8110"; // Dette er password til device
	private String phNum01 = "+4528921237";
	private String phNum02 = "";
	private String phNum03 = "";
	private String phNum04 = "";
	private ImageButton lockButton;
	private EditText alarmNum01EditText, alarmNum02EditText, alarmNum03EditText, alarmNum04EditText;
	private boolean isLocked = false;
	private boolean shouldShowWizard = false;
	private Spinner brandSpinner;

	private Brands[] brandList = { Brands.BOSCH, Brands.DAIKIN, Brands.ELECTROLUX_NEW, Brands.ELECTROLUX_OLD, Brands.FUJITSU, Brands.HAIER,
			Brands.IVT, Brands.LG, Brands.MITSUBISHI, Brands.PANASONIC, Brands.TOSHIBA, Brands.ZIBRA };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Toast.makeText(getActivity(), "SettingsFragment: OnCreateView", Toast.LENGTH_SHORT).show();

		inflatedView = inflater.inflate(R.layout.settings_slideout_layout, null);

		SharedPreferences settings = getActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), 0);
		// --FOR DEVELOPEMNT----
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SettingsNames.DEVICE_NAME.getName(), deviceName);
		editor.putString(SettingsNames.DEVICE_PHONENO.getName(), devicePhoneNum);
		editor.putString(SettingsNames.DEVICE_PASSWORD.getName(), devicePasswd);
		editor.putString(SettingsNames.BRAND_NAME.getName(), brand.getName());
		editor.commit();
		// ---------------------

		showWizardCheckBox = (CheckBox) inflatedView.findViewById(R.id.showWizardCheckBox);
		showWizardCheckBox.setOnCheckedChangeListener(this);

		unitNameEditText = (EditText) inflatedView.findViewById(R.id.unitNameEditText);
		unitNameEditText.setText(settings.getString(SettingsNames.DEVICE_NAME.getName(), strDefaultValue));

		phoneNumEditText = (EditText) inflatedView.findViewById(R.id.phonenoEditText);
		phoneNumEditText.setText(settings.getString(SettingsNames.DEVICE_PHONENO.getName(), strDefaultValue));

		passwordEditText = (EditText) inflatedView.findViewById(R.id.unitPasswordEditText);
		passwordEditText.setText(settings.getString(SettingsNames.DEVICE_PASSWORD.getName(), strDefaultValue));

		alarmNum01EditText = (EditText) inflatedView.findViewById(R.id.alarmNum01EditText);
		alarmNum01EditText.setText(settings.getString(SettingsNames.ALARM_NUMBER_01.getName(), strDefaultValue));

		alarmNum02EditText = (EditText) inflatedView.findViewById(R.id.alarmNum02EditText);
		alarmNum02EditText.setText(settings.getString(SettingsNames.ALARM_NUMBER_02.getName(), strDefaultValue));

		alarmNum03EditText = (EditText) inflatedView.findViewById(R.id.alarmNum03EditText);
		alarmNum03EditText.setText(settings.getString(SettingsNames.ALARM_NUMBER_03.getName(), strDefaultValue));

		alarmNum04EditText = (EditText) inflatedView.findViewById(R.id.alarmNum04EditText);
		alarmNum04EditText.setText(settings.getString(SettingsNames.ALARM_NUMBER_04.getName(), strDefaultValue));

		lockButton = (ImageButton) inflatedView.findViewById(R.id.lockButtonImageButton);
		lockButton.setOnClickListener(this);

		brandSpinner = (Spinner) inflatedView.findViewById(R.id.brandSpinner);
		brandSpinner.setAdapter(new CustomAdapter(getActivity(), R.layout.row, brandList));

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

		SharedPreferences sharedPreferences = getSherlockActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(),
				Context.MODE_PRIVATE);
		boolean shouldShowWizard = sharedPreferences.getBoolean(SettingsNames.SHOULD_SHOW_WIZARD.getName(), false);
		showWizardCheckBox.setChecked(shouldShowWizard);
	}

	private void updateSharedPreferencesfromFields() {
		SharedPreferences settings = getSherlockActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		editor.putString(SettingsNames.DEVICE_NAME.getName(), deviceName);
		editor.putString(SettingsNames.DEVICE_PHONENO.getName(), devicePhoneNum);
		editor.putString(SettingsNames.DEVICE_PASSWORD.getName(), devicePasswd);

		String number = alarmNum01EditText.getText().toString();
		editor.putString(SettingsNames.ALARM_NUMBER_01.getName(), number);
		number = alarmNum02EditText.getText().toString();
		editor.putString(SettingsNames.ALARM_NUMBER_02.getName(), number);
		number = alarmNum03EditText.getText().toString();
		editor.putString(SettingsNames.ALARM_NUMBER_03.getName(), number);
		number = alarmNum04EditText.getText().toString();
		editor.putString(SettingsNames.ALARM_NUMBER_04.getName(), number);

		editor.putString(SettingsNames.BRAND_NAME.getName(), brand.getName());
		editor.putBoolean(SettingsNames.SHOULD_SHOW_WIZARD.getName(), shouldShowWizard);

		editor.commit();
	}

	private void updateFieldsfromSharedPreferences() {
		SharedPreferences settings = getSherlockActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), Context.MODE_PRIVATE);
		deviceName = settings.getString(SettingsNames.DEVICE_NAME.getName(), strDefaultValue);
		devicePhoneNum = settings.getString(SettingsNames.DEVICE_PHONENO.getName(), strDefaultValue);
		devicePasswd = settings.getString(SettingsNames.DEVICE_PASSWORD.getName(), strDefaultValue);

		phNum01 = settings.getString(SettingsNames.ALARM_NUMBER_01.getName(), strDefaultValue);
		phNum02 = settings.getString(SettingsNames.ALARM_NUMBER_02.getName(), strDefaultValue);
		phNum03 = settings.getString(SettingsNames.ALARM_NUMBER_03.getName(), strDefaultValue);
		phNum04 = settings.getString(SettingsNames.ALARM_NUMBER_04.getName(), strDefaultValue);

		String temp = settings.getString(SettingsNames.BRAND_NAME.getName(), strDefaultValue);
		brand = brand.getBrand(temp);
		// TODO: SetSelection on brandspinner when loading values from
		// SharedPrefs.
		// brandSpinner.setSelection(CustomAdapter.getPosition("Category 2"));

		shouldShowWizard = settings.getBoolean(SettingsNames.SHOULD_SHOW_WIZARD.getName(), false);

		unitNameEditText.setText(deviceName);
		phoneNumEditText.setText(devicePhoneNum);
		passwordEditText.setText(devicePasswd);
		alarmNum01EditText.setText(phNum01);
		alarmNum02EditText.setText(phNum02);
		alarmNum03EditText.setText(phNum03);
		alarmNum04EditText.setText(phNum04);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lockButtonImageButton:
			ViewGroup parentView = (ViewGroup) inflatedView;
			if (isLocked) {
				lockButton.setImageResource(R.drawable.lock_unlocked_icon);
				isLocked = false;
				for (int i = 0; i < parentView.getChildCount(); i++) {
					parentView.getChildAt(i).setEnabled(true);
				}
				setTableLayoutEnabled(true);
			} else {
				lockButton.setImageResource(R.drawable.lock_locked_icon);
				isLocked = true;
				for (int i = 0; i < parentView.getChildCount(); i++) {
					if (parentView.getChildAt(i).getId() != R.id.lockButtonImageButton) {
						parentView.getChildAt(i).setEnabled(false);
					}
				}
				setTableLayoutEnabled(false);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Keep it DRY...
	 * 
	 * @param enabled
	 */
	private void setTableLayoutEnabled(boolean enabled) {
		alarmNum01EditText.setEnabled(enabled);
		alarmNum02EditText.setEnabled(enabled);
		alarmNum03EditText.setEnabled(enabled);
		alarmNum04EditText.setEnabled(enabled);
		showWizardCheckBox.setEnabled(enabled);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		SharedPreferences sharedPreferences = getSherlockActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(),
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(SettingsNames.SHOULD_SHOW_WIZARD.getName(), isChecked);
		editor.commit();
	}

	public class CustomAdapter extends ArrayAdapter<Brands> {

		public CustomAdapter(Context context, int textViewResourceId, Brands[] objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = getLayoutInflater(getArguments());
			View row = inflater.inflate(R.layout.row, parent, false);
			ImageView icon = (ImageView) row.findViewById(R.id.icon);

			if (brandList[position] == Brands.BOSCH) {
				icon.setImageResource(R.drawable.bosch);
				brand = Brands.BOSCH;
			} else if (brandList[position] == Brands.DAIKIN) {
				icon.setImageResource(R.drawable.daikin);
				brand = Brands.DAIKIN;
			} else if (brandList[position] == Brands.ELECTROLUX_NEW) {
				icon.setImageResource(R.drawable.electrolux);
				brand = Brands.ELECTROLUX_NEW;
			} else if (brandList[position] == Brands.ELECTROLUX_OLD) {
				icon.setImageResource(R.drawable.electrolux);
				brand = Brands.ELECTROLUX_OLD;
			} else if (brandList[position] == Brands.FUJITSU) {
				icon.setImageResource(R.drawable.fujitsu);
				brand = Brands.FUJITSU;
			} else if (brandList[position] == Brands.HAIER) {
				icon.setImageResource(R.drawable.haier);
				brand = Brands.HAIER;
			} else if (brandList[position] == Brands.IVT) {
				icon.setImageResource(R.drawable.ivt);
				brand = Brands.IVT;
			} else if (brandList[position] == Brands.LG) {
				icon.setImageResource(R.drawable.lg);
				brand = Brands.LG;
			} else if (brandList[position] == Brands.MITSUBISHI) {
				icon.setImageResource(R.drawable.mitsubishi);
				brand = Brands.MITSUBISHI;
			} else if (brandList[position] == Brands.PANASONIC) {
				icon.setImageResource(R.drawable.panasonic);
				brand = Brands.PANASONIC;
			} else if (brandList[position] == Brands.TOSHIBA) {
				icon.setImageResource(R.drawable.toshiba);
				brand = Brands.TOSHIBA;
			} else if (brandList[position] == Brands.ZIBRA) {
				icon.setImageResource(R.drawable.zibro);
				brand = Brands.ZIBRA;
			} else {
				icon.setImageResource(R.drawable.refresh);
			}
			return row;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		brand = (Brands) arg0.getItemAtPosition(arg2);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
}