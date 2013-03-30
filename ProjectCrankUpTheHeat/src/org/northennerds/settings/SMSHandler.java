package org.northennerds.settings;

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
		System.out.println("DeviceNumber: "); // Add device number...
		SmsManager sm = SmsManager.getDefault();
		
		String msg = ", status"; // Add password
		sm.sendTextMessage("+4528921237", null, msg, null, null);
	}
	

	//this.unregisterReceiver(smsReceiver);
}
