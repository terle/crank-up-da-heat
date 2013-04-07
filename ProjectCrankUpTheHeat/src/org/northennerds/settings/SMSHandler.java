package org.northennerds.settings;

import org.northennerds.enums.CommandTypes;
import org.northennerds.enums.SettingsNames;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.telephony.SmsManager;

public class SMSHandler {

	// BroadcastReceiver
	private IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
	private Context context;

	// private BroadcastReceiver smsReceiver = new SmsReceiver();

	public SMSHandler(Context context) {
		filter.setPriority(Integer.MAX_VALUE);
		this.context = context;
		// this.registerReceiver(smsReceiver, filter);
	}

	public void SendSMS(CommandTypes cmdType) {
		// System.out.println("DeviceNumber: "); // Add device number...
		SmsManager sm = SmsManager.getDefault();

		SharedPreferences settings = context.getSharedPreferences(SettingsNames.prefsName.getName(),
				Context.MODE_PRIVATE);
		String passWord = settings.getString(SettingsNames.password.getName(), "1234");

		String deviceNumber = settings.getString(SettingsNames.DevicePhoneNum.getName(), "");

		// String msg = ", status"; // Add password
		// sm.sendTextMessage("+4528921237", null, msg, null, null);

		switch (cmdType) {
		case GetALARMNUMs: {
			String msg = passWord + ", StatusPhN";
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case GetBRANDs: {
			String msg = passWord + ", Brand";
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case SetALARMNUM01: {
			String msg = passWord + ", PhN1, " + settings.getString(SettingsNames.AlarmNum01.getName(), "");
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case SetBRAND: {
			String msg = passWord + ", Brand, " + settings.getString(SettingsNames.BrandName.getName(), "Tomt Navn");
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case SetCOOL: {
			String msg = passWord + ", Cool, " + settings.getInt(SettingsNames.CoolTemp.getName(), 16);
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case SetHEAT: {
			String msg = passWord + ", Heat, " + settings.getInt(SettingsNames.HeatTemp.getName(), 8);
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case SetWarningHIGH: {
			String msg = passWord + ", High, " + settings.getInt(SettingsNames.warningTempHIGH.getName(), 35);
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case SetWarningLOW: {
			String msg = passWord + ", Low, " + settings.getInt(SettingsNames.warningTempLOW.getName(), 10);
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case Status: {
			String msg = passWord + ", Status";
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case setNAME: {
			String msg = passWord + ", Name, " + settings.getString(SettingsNames.deviceName.getName(), "N/A");
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case setPASSWORD: {
			String msg = passWord + ", NewPass, " + settings.getString(SettingsNames.password.getName(), "");
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case SetALARMNUM02: {
			String msg = passWord + ", PhN2, " + settings.getString(SettingsNames.AlarmNum02.getName(), "");
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case SetALARMNUM03: {
			String msg = passWord + ", PhN3, " + settings.getString(SettingsNames.AlarmNum03.getName(), "");
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		case SetALARMNUM04: {
			String msg = passWord + ", PhN4, " + settings.getString(SettingsNames.AlarmNum04.getName(), "");
			sm.sendTextMessage(deviceNumber, null, msg, null, null);
			break;
		}
		default: {
			break;
		}
		}
	}

	// this.unregisterReceiver(smsReceiver);
}
