package org.northennerds.settings;

import org.northennerds.enums.SettingsNames;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReciever extends BroadcastReceiver {
	// private static final String TAG = "Message recieved";

	private Context context;;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED")) {

			Bundle pudsBundle = intent.getExtras();
			Object[] pdus = (Object[]) pudsBundle.get("pdus");
			SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);

			// Hvis det er den sms vi gerne vil ha fat i...
			if (messages.getOriginatingAddress().contains("")) { // Inds�t
																	// nummer
																	// her.
				this.abortBroadcast();
				// TODO: H�ndter svaret
			}

			// Log.i(TAG, messages.getMessageBody());
			Toast.makeText(context,
					"SMS Received : " + messages.getMessageBody(),
					Toast.LENGTH_LONG).show();

			// String text = responseTextView.getText().toString();
			// if(text.length() > 0) {
			// text += "\n";
			// }
			// responseTextView.setText(text + messages.getMessageBody());
		}
	}

	private void parseRespone(String message) {
		String navn = "", aktuel = "", set = "", lav = "", hoej = "", batteri = "", gsm = "";

		// TODO: Hvis navn indeholder nogle af de strenge der bliver spillet på
		// ender det galt, fx "Aktuel"/"Set"/"Advarsel".

		//Indexes til at holde styr på hvor i message vi er
		int indexAktuel = message.indexOf("Aktuel:");
		int indexSet = message.indexOf("Set:");
		int indexAdvarsel = message.indexOf("Advarsel");
		int indexLav = message.indexOf("Lav:");
		int indexHoej = message.indexOf("Høj:");
		int indexBatteri = message.indexOf("Batteri:");
		int indexGSM = message.indexOf("GSM signal");

		//Hiv NAVN ud
		navn = message.substring(0, message.indexOf("*"));
		navn = navn.trim();

		// Hiv AKTUEL temperatur ud
		aktuel = message.substring(indexAktuel + 7, message.indexOf("-"));
		aktuel = aktuel.trim();

		//Hiv SET ud, der bliver lavet mere ved denne substring
		set = message.substring(indexSet + 4, indexAdvarsel);
		set = set.trim();
		
		String heatingTemp, coolingTemp;
		
		if(set.contains("Heat"))
		{
			heatingTemp = set.substring(0, set.indexOf("_"));
			coolingTemp="";
		}
		else
		{
			heatingTemp = "";
			coolingTemp=set.substring(0, set.indexOf("_"));
		}

		//Hiv LAV ud
		lav = message.substring(indexLav + 4,message.indexOf("-", indexLav));
		lav = lav.trim();
		
		//Hiv Høj ud
		hoej = message.substring(indexHoej + 4, indexBatteri);
		hoej = hoej.trim();
		
		//Hiv BATTERI niveauet ud
		batteri = message.substring(indexBatteri + 8, indexGSM);
		batteri = batteri.trim();

		//Hiv GSM signal styrken ud.
		gsm = message.substring(indexGSM + 10, message.length());

		
		
		SharedPreferences settings = context.getSharedPreferences(SettingsNames.prefsName.getName(), 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SettingsNames.deviceName.getName(), navn);
//		editor.putBoolean(SettingsNames.isOldController.getName(),isOldController);
//		editor.putString(SettingsNames.phoneNum.getName(), devicePhoneNum);
//		editor.putString(SettingsNames.password.getName(), passwd);
		editor.putInt(SettingsNames.minTemp.getName(), Integer.parseInt(lav));
		editor.putInt(SettingsNames.maxTemp.getName(), Integer.parseInt(hoej));
		editor.putInt(SettingsNames.HeatTemp.getName(), Integer.parseInt(heatingTemp));
		editor.putInt(SettingsNames.CoolTemp.getName(), Integer.parseInt(coolingTemp));
		editor.putString(SettingsNames.GSMBat.getName(), batteri);
		editor.putString(SettingsNames.GSMSignal.getName(), gsm);
		editor.commit();

	}
}
