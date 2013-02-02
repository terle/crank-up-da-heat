package org.northennerds.settings;

import org.northernnerds.projectcrankuptheheat.MainActivity;

import android.content.IntentFilter;
import android.telephony.SmsManager;

public class SMSHandler {

	// BroadcastReceiver
	private IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
//	private BroadcastReceiver smsReceiver = new SmsReceiver();

	public SMSHandler(){
		filter.setPriority(Integer.MAX_VALUE);
		//this.registerReceiver(smsReceiver, filter);
	}
	
	public void SendSMS(){
		System.out.println("DeviceNumber: "+MainActivity.devicePhoneNum);
		SmsManager sm = SmsManager.getDefault();
		
		String msg = MainActivity.passwd+", status";
		sm.sendTextMessage("+4528921237", null, msg, null, null);
	}
	

	//this.unregisterReceiver(smsReceiver);
}
