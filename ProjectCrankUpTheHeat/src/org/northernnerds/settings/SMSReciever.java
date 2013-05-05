package org.northernnerds.settings;

import org.northernnerds.enums.ResponseTypes;
import org.northernnerds.enums.SettingsNames;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReciever extends BroadcastReceiver {
	private static final String TAG = "Message received";

	private Context context;;
	private SMSHelper helper;
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		settings = context.getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), Context.MODE_PRIVATE);
		editor = settings.edit();
		helper = new SMSHelper(context);

		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle pudsBundle = intent.getExtras();
			Object[] pdus = (Object[]) pudsBundle.get("pdus");
			SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);

			// Hvis det er den sms vi gerne vil ha fat i... (+4561319616)
			if (messages.getOriginatingAddress().contains(settings.getString(SettingsNames.DEVICE_PHONENO.getName(), ""))) {
				this.abortBroadcast();
				
				Toast.makeText(context, "SMS Received : " + messages.getMessageBody(), Toast.LENGTH_LONG).show();

				// FIXME - Remove logs.
				Log.i(TAG, messages.getMessageBody());
				decodeRespose(messages.getMessageBody());

				// Make notification
				String[] msgLines = messages.getMessageBody().split("\\n");
				ResponseTypes resposeType = helper.getResponseType(msgLines[1]);
				new Notifier(context).makeNotification(resposeType, messages.getMessageBody());
			}
			// FIXME - Remove logs.
			Log.i(TAG, messages.getMessageBody());
		}
	}

	private void decodeRespose(String message) {

		String[] msgLines = message.split("\\n");
		ResponseTypes resposeType = helper.getResponseType(msgLines[1]);

		switch (resposeType) {
		case ALARM_NUMBERS: {
			// Sommerhuset i R¿dhus
			// **Alarmnumre**
			// Ph1:+4561669199
			// Ph2:+4561341592
			// Ph3:+4528921237
			// Ph4:

			// TODO: Make a notification of the numbers
			Toast.makeText(context, ResponseTypes.ALARM_NUMBERS.getText() + " recieved", Toast.LENGTH_SHORT).show();

			editor.putString(SettingsNames.ALARM_NUMBER_01.getName(), helper.getPhoneNum(msgLines[2]));
			editor.putString(SettingsNames.ALARM_NUMBER_02.getName(), helper.getPhoneNum(msgLines[3]));
			editor.putString(SettingsNames.ALARM_NUMBER_03.getName(), helper.getPhoneNum(msgLines[4]));
			editor.putString(SettingsNames.ALARM_NUMBER_04.getName(), helper.getPhoneNum(msgLines[5]));

			break;
		}
		case BRANDS: {
			// Sommerhuset i R¿dhus
			// **Brand status**
			// Aktivt Brand: Panasonic
			// Mulige Brand: Panasonic, Daikin, Haier, LG, Bosch, IVT,
			// Mitsubishi, Toshiba, Elux1, Elux2

			// TODO maybe nothing should be done here
			Toast.makeText(context, ResponseTypes.BRANDS.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case NORMALIZED_NOTIFICATION: {
			// Sommerhuset i R¿dhus
			// **Temp normal igen**
			// Aktuel:11 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:100%
			// GSM signal(1-5):1

			// TODO make the system aware that things have normalized
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.NORMALIZED_NOTIFICATION.getText() + " recieved", Toast.LENGTH_SHORT)
					.show();
			break;
		}
		case PASSWORD_UPDATE: {
			// Sommerhuset i R¿dhus
			// **Din kode er ¾ndret**
			// Din nye kode er:8110

			// TODO Make the system aware that the password has been changed.

			editor.putString(SettingsNames.DEVICE_PASSWORD.getName(), helper.getPassword(msgLines[2]));
			Toast.makeText(context, ResponseTypes.PASSWORD_UPDATE.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case POWER_FAILURE: {
			// Sommerhuset i R¿dhus
			// **Netforsyning afbrudt**
			// Aktuel:14 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:100%
			// GSM signal(1-5):3

			// TODO make the system aware that the power has failed
			extractCommonContent(msgLines);

			Toast.makeText(context, ResponseTypes.POWER_FAILURE.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case POWER_RESTORED: {
			// Sommerhuset i R¿dhus
			// **Netforsyning tilbage**
			// Aktuel:4 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:96%
			// GSM signal(1-5):2

			// TODO make the system aware of that the power has been restored
			extractCommonContent(msgLines);

			Toast.makeText(context, ResponseTypes.POWER_RESTORED.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case SET_UPDATE: {
			// Sommerhuset i R¿dhus
			// **Set opdateret**
			// Aktuel:21 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:100%
			// GSM signal(1-5):4

			// TODO make the system aware of the recieved update
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.SET_UPDATE.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case STATUS: {
			// TODO: Make the system aware of the recieved update
			Toast.makeText(context, ResponseTypes.STATUS.getText() + " recieved", Toast.LENGTH_SHORT).show();
			extractCommonContent(msgLines);
			break;
		}
		case TEMP_HIGH_UPDATE: {
			// Sommerhuset i R¿dhus
			// **H¿j temp opdateret**
			// Aktuel:28 - Set:16_Cool
			// Advarsel Lav:5 - H¿j:45
			// Batteri:65%
			// GSM signal(1-5):3

			// TODO make the system aware of the update
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.TEMP_HIGH_UPDATE.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case TEMP_LOW_UPDATE: {
			// Sommerhuset i R¿dhus
			// **Lav temp opdateret**
			// Aktuel:28 - Set:16_Cool
			// Advarsel Lav:5 - H¿j:20
			// Batteri:64%
			// GSM signal(1-5):3

			// TODO make the system aware of the update
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.TEMP_LOW_UPDATE.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		
		case WARNING_HIGH_TEMP: {
			// Sommerhuset i R¿dhus
			// **ADVARSEL H¿j temp**
			// Aktuel:28 - Set:16_Cool
			// Advarsel Lav:30 - H¿j:20
			// Batteri:63%
			// GSM signal(1-5):3

			// TODO make the system aware of the warning
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.WARNING_HIGH_TEMP.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case WARNING_LOW_TEMP: {
			// Sommerhuset i R¿dhus
			// **ADVARSEL Lav temp**
			// Aktuel:4 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:95%
			// GSM signal(1-5):2

			// TODO make the system aware of the warning
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.WARNING_LOW_TEMP.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case WARNING_UNDER_5_DEGREES: {
			// Sommerhuset i R¿dhus
			// **ADVARSEL under 5gC**
			// Aktuel:4 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:100%
			// GSM signal(1-5):3

			// TODO make the system aware of the warning
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.WARNING_UNDER_5_DEGREES.getText() + " recieved", Toast.LENGTH_SHORT)
					.show();
			break;
		}
		default: {
			Toast.makeText(context, "Unknown case recieved in SMSReciever", Toast.LENGTH_SHORT).show();
			break;
		}
		}
	}

	private void extractCommonContent(String[] msgLines) {
		// Sommerhuset i R¿dhus
		// **Status**
		// Aktuel:25 - Set:24_Heat
		// Advarsel Lav:6 - H¿j:45
		// Batteri:15%
		// GSM signal(1-5):2

		editor.putString(SettingsNames.DEVICE_NAME.getName(), msgLines[0]);

		editor.putInt(SettingsNames.CURRENT_TEMP.getName(), helper.getCurrentTemp(msgLines[2]));

		if (helper.isSetTempHeat(msgLines[2])) {
			editor.putInt(SettingsNames.HEAT_TEMP.getName(), helper.getSetTemp(msgLines[2]));
			editor.putInt(SettingsNames.COOL_TEMP.getName(), -1);
		} else if (helper.isSetTempCool(msgLines[2])) {
			editor.putInt(SettingsNames.HEAT_TEMP.getName(), -1);
			editor.putInt(SettingsNames.COOL_TEMP.getName(), helper.getSetTemp(msgLines[2]));
		}

		editor.putInt(SettingsNames.WARNING_TEMP_LOW.getName(), helper.getWarningLowTemp(msgLines[3]));
		editor.putInt(SettingsNames.WARNING_TEMP_HIGH.getName(), helper.getWarningHighTemp(msgLines[3]));
		editor.putInt(SettingsNames.GSM_BATTERY.getName(), helper.getBatteryStatus(msgLines[4]));
		editor.putInt(SettingsNames.GSM_SIGNAL.getName(), helper.getGsmSignal(msgLines[5]));

		editor.commit();
	}
}
