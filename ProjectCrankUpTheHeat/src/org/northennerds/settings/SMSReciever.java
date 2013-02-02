package org.northennerds.settings;

import org.northernnerds.projectcrankuptheheat.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReciever extends BroadcastReceiver {
//	private static final String TAG = "Message recieved";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {

			Bundle pudsBundle = intent.getExtras();
			Object[] pdus = (Object[]) pudsBundle.get("pdus");
			SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);

			//Hvis det er den sms vi gerne vil ha fat i...
			if(messages.getOriginatingAddress().contains(MainActivity.devicePhoneNum)){
				this.abortBroadcast();
				//TODO: Håndtér svaret
			}

			//		Log.i(TAG, messages.getMessageBody());
			Toast.makeText(context,	"SMS Received : " + messages.getMessageBody(), Toast.LENGTH_LONG).show();

			//		String text = responseTextView.getText().toString();
			//		if(text.length() > 0) {
			//			text += "\n";
			//		}
			//		responseTextView.setText(text + messages.getMessageBody());
		}
	}
}
