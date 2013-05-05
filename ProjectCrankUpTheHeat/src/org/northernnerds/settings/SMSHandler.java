package org.northernnerds.settings;

import org.northernnerds.enums.CommandTypes;
import org.northernnerds.enums.SettingsNames;

import android.R.string;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMSHandler {

	// BroadcastReceiver
	private IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
	private Context context;

	public SMSHandler(Context context) {
		filter.setPriority(Integer.MAX_VALUE);
		this.context = context;
	}

	public void SendSMS(CommandTypes cmdType) {
		SmsManager sm = SmsManager.getDefault();

		SharedPreferences settings = context.getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), Context.MODE_PRIVATE);
		String passWord = settings.getString(SettingsNames.DEVICE_PASSWORD.getName(), "1234");
		String deviceNumber = settings.getString(SettingsNames.DEVICE_PHONENO.getName(), "");

		if (!(deviceNumber.equals(string.emptyPhoneNumber) || deviceNumber == null || deviceNumber.equals(""))) {
			switch (cmdType) {
			case GET_ALARM_NUMBERS: {
				String msg = passWord + ", StatusPhN";
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case GET_BRANDS: {
				String msg = passWord + ", Brand";
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case SET_ALARM_NO_01: {
				String msg = passWord + ", PhN1, " + settings.getString(SettingsNames.ALARM_NUMBER_01.getName(), "");
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case SET_BRAND: {
				String msg = passWord + ", Brand, " + settings.getString(SettingsNames.BRAND_NAME.getName(), "Tomt Navn");
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case SET_COOL: {
				String msg = passWord + ", Cool, " + settings.getInt(SettingsNames.COOL_TEMP.getName(), 16);
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case SET_HEAT: {
				String msg = passWord + ", Heat, " + settings.getInt(SettingsNames.HEAT_TEMP.getName(), 8);
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case SET_WARNING_HIGH: {
				String msg = passWord + ", High, " + settings.getInt(SettingsNames.WARNING_TEMP_HIGH.getName(), 35);
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case SET_WARNING_LOW: {
				String msg = passWord + ", Low, " + settings.getInt(SettingsNames.WARNING_TEMP_LOW.getName(), 10);
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case STATUS: {
				String msg = passWord + ", Status";
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case SET_NAME: {
				String msg = passWord + ", Name, " + settings.getString(SettingsNames.DEVICE_NAME.getName(), "N/A");
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case SET_PASSWORD: {
				String msg = passWord + ", NewPass, " + settings.getString(SettingsNames.DEVICE_PASSWORD.getName(), "");
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case SET_ALARM_NO_02: {
				String msg = passWord + ", PhN2, " + settings.getString(SettingsNames.ALARM_NUMBER_02.getName(), "");
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case SET_ALARM_NO_03: {
				String msg = passWord + ", PhN3, " + settings.getString(SettingsNames.ALARM_NUMBER_03.getName(), "");
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			case SET_ALARM_NO_04: {
				String msg = passWord + ", PhN4, " + settings.getString(SettingsNames.ALARM_NUMBER_04.getName(), "");
				sm.sendTextMessage(deviceNumber, null, msg, null, null);
				break;
			}
			default: {
				break;
			}
			}
		} else {
			Toast.makeText(context, "Device Phone number is not set",
					Toast.LENGTH_LONG).show();
		}
	}
}
