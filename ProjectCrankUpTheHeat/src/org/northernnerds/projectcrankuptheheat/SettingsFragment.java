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
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class SettingsFragment extends SherlockFragment implements OnClickListener, OnCheckedChangeListener {
	private final String strDefaultValue = "N/A";
//	private final int iDefaultValue = -111;

	private EditText unitNameEditText, phoneNumEditText, passwordEditText;
	private CheckBox showWizardCheckBox;
	private View inflatedView;

	// Values for SharedPrefs
	private String deviceName = "Sommerfjong i bingbong";
	private Brands brand = Brands.PANASONIC;
	private String brandName = "Panasonic"; // FIXME - Remove?
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
	private Context context; // FIXME - Remove?
	private ImageView brandImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Toast.makeText(getActivity(), "SettingsFragment: OnCreateView", Toast.LENGTH_SHORT).show();

		inflatedView = inflater.inflate(R.layout.settings_slideout_layout, null);

		SharedPreferences settings = getActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SettingsNames.DEVICE_NAME.getName(), deviceName);
		editor.putString(SettingsNames.DEVICE_PHONENO.getName(), devicePhoneNum);
		editor.putString(SettingsNames.DEVICE_PASSWORD.getName(), devicePasswd);
		editor.putString(SettingsNames.BRAND_NAME.getName(), brand.getName());
		editor.commit();

		showWizardCheckBox = (CheckBox) inflatedView.findViewById(R.id.showWizardCheckBox);
		showWizardCheckBox.setOnCheckedChangeListener(this);

		unitNameEditText = (EditText) inflatedView.findViewById(R.id.unitNameEditText);
		unitNameEditText.setText(settings.getString(SettingsNames.DEVICE_NAME.getName(), strDefaultValue));

		phoneNumEditText = (EditText) inflatedView.findViewById(R.id.phonenoEditText);
		phoneNumEditText.setText(settings.getString(SettingsNames.DEVICE_PHONENO.getName(), strDefaultValue));

		passwordEditText = (EditText) inflatedView.findViewById(R.id.unitPasswordEditText);
		passwordEditText.setText(settings.getString(SettingsNames.DEVICE_PASSWORD.getName(), strDefaultValue));

		brandImageView = (ImageView) inflatedView.findViewById(R.id.brandimageView);
		setBrandImageView(brand);

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

		SharedPreferences sharedPreferences = getSherlockActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(),
						Context.MODE_PRIVATE);
		boolean shouldShowWizard = sharedPreferences.getBoolean(SettingsNames.SHOULD_SHOW_WIZARD.getName(), false);
		showWizardCheckBox.setChecked(shouldShowWizard);
	}

	private void updateSharedPreferencesfromFields() {
		SharedPreferences settings = getSherlockActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), 
				Context.MODE_PRIVATE);
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
		SharedPreferences settings = getSherlockActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), 
				Context.MODE_PRIVATE);
		deviceName = settings.getString(SettingsNames.DEVICE_NAME.getName(), strDefaultValue);
		devicePhoneNum = settings.getString(SettingsNames.DEVICE_PHONENO.getName(), strDefaultValue);
		devicePasswd = settings.getString(SettingsNames.DEVICE_PASSWORD.getName(), strDefaultValue);

		phNum01 = settings.getString(SettingsNames.ALARM_NUMBER_01.getName(), strDefaultValue);
		phNum02 = settings.getString(SettingsNames.ALARM_NUMBER_02.getName(), strDefaultValue);
		phNum03 = settings.getString(SettingsNames.ALARM_NUMBER_03.getName(), strDefaultValue);
		phNum04 = settings.getString(SettingsNames.ALARM_NUMBER_04.getName(), strDefaultValue);

		String temp = settings.getString(SettingsNames.BRAND_NAME.getName(), strDefaultValue);
		brand = getBrandfromString(temp);
		
		shouldShowWizard = settings.getBoolean(SettingsNames.SHOULD_SHOW_WIZARD.getName(), false);

		unitNameEditText.setText(deviceName);
		phoneNumEditText.setText(devicePhoneNum);
		passwordEditText.setText(devicePasswd);
		alarmNum01EditText.setText(phNum01);
		alarmNum02EditText.setText(phNum02);
		alarmNum03EditText.setText(phNum03);
		alarmNum04EditText.setText(phNum04);

		// TODO: Set Brand Logo
		setBrandImageView(brand);
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
				setTableLayoutEnabled(true);
			} else {
				lockButton.setImageResource(R.drawable.lock_locked_icon);
				isLocked = true;
				for(int i = 0; i < parentView.getChildCount(); i++) {
					if(parentView.getChildAt(i).getId() != R.id.lockButtonImageButton) {
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

	// FIXME - Change this, so that we get the name from a list/map instead of from an Enum. This looks bad...
	private Brands getBrandfromString(String name) {
		Brands result = Brands.BOSCH;
		if (name.equals(Brands.BOSCH.getName()))
			return Brands.BOSCH;
		else if (name.equals(Brands.DAIKIN.getName()))
			return Brands.DAIKIN;
		else if (name.equals(Brands.ELECTROLUX_NEW.getName()))
			return Brands.ELECTROLUX_NEW;
		else if (name.equals(Brands.ELECTROLUX_OLD.getName()))
			return Brands.ELECTROLUX_OLD;
		else if (name.equals(Brands.FUJITSU.getName()))
			return Brands.FUJITSU;
		else if (name.equals(Brands.HAIER.getName()))
			return Brands.HAIER;
		else if (name.equals(Brands.IVT.getName()))
			return Brands.IVT;
		else if (name.equals(Brands.LG.getName()))
			return Brands.LG;
		else if (name.equals(Brands.MITSUBISHI.getName()))
			return Brands.MITSUBISHI;
		else if (name.equals(Brands.PANASONIC.getName()))
			return Brands.PANASONIC;
		else if (name.equals(Brands.TOSHIBA.getName()))
			return Brands.TOSHIBA;
		else if (name.equals(Brands.ZIBRA.getName()))
			return Brands.ZIBRA;
		return result;
	}

	// FIXME - See the fixme from above... this looks aweful!
	private void setBrandImageView(Brands brand) {
		switch (brand) {
		case BOSCH: brandImageView.setImageResource(R.drawable.bosch);
			break;
		case DAIKIN:brandImageView.setImageResource(R.drawable.daikin);
			break;
		case ELECTROLUX_NEW:brandImageView.setImageResource(R.drawable.electrolux);
			break;
		case ELECTROLUX_OLD:brandImageView.setImageResource(R.drawable.electrolux);
			break;
		case FUJITSU:brandImageView.setImageResource(R.drawable.fujitsu);
			break;
		case HAIER: brandImageView.setImageResource(R.drawable.haier);
			break;
		case IVT: brandImageView.setImageResource(R.drawable.ivt);
			break;
		case LG:brandImageView.setImageResource(R.drawable.lg);
			break;
		case MITSUBISHI: brandImageView.setImageResource(R.drawable.mitsubishi);
			break;
		case PANASONIC: brandImageView.setImageResource(R.drawable.panasonic);
			break;
		case TOSHIBA:brandImageView.setImageResource(R.drawable.toshiba);
			break;
		case ZIBRA:brandImageView.setImageResource(R.drawable.zibro);
			break;
		default: brandImageView.setImageResource(R.drawable.bosch);
		break;
		}
	}
}
