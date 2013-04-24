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
	private static final String TAG = "Message recieved";

	private Context context;;
	private SMSHelper helper;
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		settings = context.getSharedPreferences(SettingsNames.prefsName.getName(), Context.MODE_PRIVATE);
		editor = settings.edit();
		helper = new SMSHelper(context);

		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {

			Bundle pudsBundle = intent.getExtras();
			Object[] pdus = (Object[]) pudsBundle.get("pdus");
			SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);

			// Hvis det er den sms vi gerne vil ha fat i... (+4561319616)
			if (messages.getOriginatingAddress().contains(
					settings.getString(SettingsNames.devicePhoneNum.getName(), ""))) {
				this.abortBroadcast();
				// Toast.makeText(context, "BroadCastAboardet",
				// Toast.LENGTH_SHORT).show();
				
				Toast.makeText(context, "SMS Received : " + messages.getMessageBody(), Toast.LENGTH_LONG).show();

				Log.i(TAG, messages.getMessageBody());
				decodeRespose(messages.getMessageBody());

				// Make notification
				String[] msgLines = messages.getMessageBody().split("\\n");
				ResponseTypes resposeType = helper.getResponseType(msgLines[1]);
				new Notifier(context).makeNotification(resposeType, messages.getMessageBody());
				
//				Intent notificationIntent = new Intent();
//				PendingIntent pIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
//				// Build notification
//				Notification noti = new Notification.Builder(context).setContentTitle("Status Recieved")
//						.setContentText(messages.getMessageBody()).setSmallIcon(android.R.drawable.ic_menu_camera)
//						.setContentIntent(pIntent).addAction(android.R.drawable.ic_menu_compass, "View", pIntent)
//						.build();
//				NotificationManager notificationManager = (NotificationManager) context
//						.getSystemService(Context.NOTIFICATION_SERVICE);
//				noti.flags |= Notification.FLAG_AUTO_CANCEL;
//				notificationManager.notify(0, noti);

			}

			Log.i(TAG, messages.getMessageBody());

			// Toast.makeText(context, "SMS Received : " +
			// messages.getMessageBody(), Toast.LENGTH_LONG).show();

			// String text = responseTextView.getText().toString();
			// if(text.length() > 0) {
			// text += "\n";
			// }
			// responseTextView.setText(text + messages.getMessageBody());
		}
	}

	private void decodeRespose(String message) {

		String[] msgLines = message.split("\\n");
		ResponseTypes resposeType = helper.getResponseType(msgLines[1]);

		switch (resposeType) {
		case AlarmNumre: {
			// Sommerhuset i R¿dhus
			// **Alarmnumre**
			// Ph1:+4561669199
			// Ph2:+4561341592
			// Ph3:+4528921237
			// Ph4:

			// TODO: Make a notification of the numbers
			Toast.makeText(context, ResponseTypes.AlarmNumre.getText() + " recieved", Toast.LENGTH_SHORT).show();

			editor.putString(SettingsNames.AlarmNum01.getName(), helper.getPhoneNum(msgLines[2]));
			editor.putString(SettingsNames.AlarmNum02.getName(), helper.getPhoneNum(msgLines[3]));
			editor.putString(SettingsNames.AlarmNum03.getName(), helper.getPhoneNum(msgLines[4]));
			editor.putString(SettingsNames.AlarmNum04.getName(), helper.getPhoneNum(msgLines[5]));

			break;
		}
		case Brands: {
			// Sommerhuset i R¿dhus
			// **Brand status**
			// Aktivt Brand: Panasonic
			// Mulige Brand: Panasonic, Daikin, Haier, LG, Bosch, IVT,
			// Mitsubishi, Toshiba, Elux1, Elux2

			// TODO maybe nothing should be done here
			Toast.makeText(context, ResponseTypes.Brands.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case NORMALIZEDNotification: {
			// Sommerhuset i R¿dhus
			// **Temp normal igen**
			// Aktuel:11 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:100%
			// GSM signal(1-5):1

			// TODO make the system aware that things have normalized
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.NORMALIZEDNotification.getText() + " recieved", Toast.LENGTH_SHORT)
					.show();
			break;
		}
		case PASSWORDUpdate: {
			// Sommerhuset i R¿dhus
			// **Din kode er ¾ndret**
			// Din nye kode er:8110

			// TODO Make the system aware that the password has been changed.

			editor.putString(SettingsNames.devicePassword.getName(), helper.getPassword(msgLines[2]));
			Toast.makeText(context, ResponseTypes.PASSWORDUpdate.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case PowerFailure: {
			// Sommerhuset i R¿dhus
			// **Netforsyning afbrudt**
			// Aktuel:14 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:100%
			// GSM signal(1-5):3

			// TODO make the system aware that the power has failed
			extractCommonContent(msgLines);

			Toast.makeText(context, ResponseTypes.PowerFailure.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case PowerRestored: {
			// Sommerhuset i R¿dhus
			// **Netforsyning tilbage**
			// Aktuel:4 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:96%
			// GSM signal(1-5):2

			// TODO make the system aware of that the power has been restored
			extractCommonContent(msgLines);

			Toast.makeText(context, ResponseTypes.PowerRestored.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case SETUpdate: {
			// Sommerhuset i R¿dhus
			// **Set opdateret**
			// Aktuel:21 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:100%
			// GSM signal(1-5):4

			// TODO make the system aware of the recieved update
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.SETUpdate.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case Status: {

			// TODO: Make the system aware of the recieved update
			Toast.makeText(context, ResponseTypes.Status.getText() + " recieved", Toast.LENGTH_SHORT).show();
			extractCommonContent(msgLines);
			break;
		}
		case TempHIGHUpdate: {
			// Sommerhuset i R¿dhus
			// **H¿j temp opdateret**
			// Aktuel:28 - Set:16_Cool
			// Advarsel Lav:5 - H¿j:45
			// Batteri:65%
			// GSM signal(1-5):3

			// TODO make the system aware of the update
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.TempHIGHUpdate.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case TempLOWUpdate: {
			// Sommerhuset i R¿dhus
			// **Lav temp opdateret**
			// Aktuel:28 - Set:16_Cool
			// Advarsel Lav:5 - H¿j:20
			// Batteri:64%
			// GSM signal(1-5):3

			// TODO make the system aware of the update
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.TempLOWUpdate.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		
		case WarningHIGHTemp: {
			// Sommerhuset i R¿dhus
			// **ADVARSEL H¿j temp**
			// Aktuel:28 - Set:16_Cool
			// Advarsel Lav:30 - H¿j:20
			// Batteri:63%
			// GSM signal(1-5):3

			// TODO make the system aware of the warning
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.WarningHIGHTemp.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case WarningLOWTemp: {
			// Sommerhuset i R¿dhus
			// **ADVARSEL Lav temp**
			// Aktuel:4 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:95%
			// GSM signal(1-5):2

			// TODO make the system aware of the warning
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.WarningLOWTemp.getText() + " recieved", Toast.LENGTH_SHORT).show();
			break;
		}
		case WarningUNDER5Degrees: {
			// Sommerhuset i R¿dhus
			// **ADVARSEL under 5gC**
			// Aktuel:4 - Set:18_Heat
			// Advarsel Lav:5 - H¿j:45
			// Batteri:100%
			// GSM signal(1-5):3

			// TODO make the system aware of the warning
			extractCommonContent(msgLines);
			Toast.makeText(context, ResponseTypes.WarningUNDER5Degrees.getText() + " recieved", Toast.LENGTH_SHORT)
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

		editor.putString(SettingsNames.deviceName.getName(), msgLines[0]);

		editor.putInt(SettingsNames.currentTemperatur.getName(), helper.getCurrentTemp(msgLines[2]));

		if (helper.isSetTempHEAT(msgLines[2])) {
			editor.putInt(SettingsNames.HeatTemp.getName(), helper.getSetTemp(msgLines[2]));
			editor.putInt(SettingsNames.CoolTemp.getName(), -1);
		} else if (helper.isSetTempCOOL(msgLines[2])) {
			editor.putInt(SettingsNames.HeatTemp.getName(), -1);
			editor.putInt(SettingsNames.CoolTemp.getName(), helper.getSetTemp(msgLines[2]));
		}

		editor.putInt(SettingsNames.warningTempLOW.getName(), helper.getWarningLOWTemp(msgLines[3]));
		editor.putInt(SettingsNames.warningTempHIGH.getName(), helper.getWarningHIGHTemp(msgLines[3]));

		editor.putInt(SettingsNames.GSMBat.getName(), helper.getBatteryStatus(msgLines[4]));

		editor.putInt(SettingsNames.GSMSignal.getName(), helper.getGSMSignal(msgLines[5]));

		editor.commit();
	}

}
