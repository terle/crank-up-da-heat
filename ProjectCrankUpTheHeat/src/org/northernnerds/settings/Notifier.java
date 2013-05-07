package org.northernnerds.settings;

import org.northernnerds.enums.ResponseTypes;
import org.northernnerds.projectcrankuptheheat.MainActivity;
import org.northernnerds.projectcrankuptheheat.R;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

public class Notifier {

	private Context context;

	public Notifier(Context context) {
		this.context = context;
	}

	//FIXME: The way notifications are build are only valid for API >=16
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void makeNotification(ResponseTypes responseType, String message) {
		
		Intent notificationIntent = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Notification noti = new Notification();

		// Build notification
		switch (responseType) {
		case ALARM_NUMBERS: {
//			------------------------------------------
//			8110, StatusPhN
//
//			 Sommerhuset i R¿dhus
//			**Alarmnumre**
//			Ph1:+4561669199
//			Ph2:+4561341592
//			Ph3:
//			Ph4:
//			------------------------------------------
//			8110, phn3, +4528921237
//
//			Sommerhuset i R¿dhus
//			**Alarmnumre**
//			Ph1:+4561669199
//			Ph2:+4561341592
//			Ph3:+4528921237
//			Ph4:
//			------------------------------------------
			noti = new Notification.Builder(context).setContentTitle("Alarmnumre Opdateret").setContentText("")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case BRANDS: {
//			------------------------------------------
//			8110, brand
//
//			 Sommerhuset i R¿dhus
//			**Brand status**
//			Aktivt Brand: Panasonic
//			Mulige Brand: Panasonic, Daikin, Haier, LG, Bosch, IVT, Mitsubishi, Toshiba, Elux1, Elux2
//			------------------------------------------
			noti = new Notification.Builder(context).setContentTitle("Model Opdateret").setContentText("")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case NORMALIZED_NOTIFICATION: {
//			------------------------------------------
//			Sommerhuset i R¿dhus
//			**Temp normal igen**
//			Aktuel:28 - Set:16_Cool
//			Advarsel Lav:5 - H¿j:20
//			Batteri:64%
//			GSM signal(1-5):3
//			------------------------------------------
			noti = new Notification.Builder(context).setContentTitle("Temperatur Normal igen").setContentText("")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case PASSWORD_UPDATE: {
			noti = new Notification.Builder(context).setContentTitle("Password Opdateret").setContentText("")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case POWER_FAILURE: {
			noti = new Notification.Builder(context).setContentTitle("INGEN STR¯M!").setContentText("Netforsyning afbrudt")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case POWER_RESTORED: {
			noti = new Notification.Builder(context).setContentTitle("Str¿m genskabt").setContentText("Str¿mmen er tilbage")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case SET_UPDATE: {
//			------------------------------------------
//			8110, heat, 8
//
//			 Sommerhuset i R¿dhus
//			**Set opdateret**
//			Aktuel:26 - Set:8_Heat
//			Advarsel Lav:5 - H¿j:45
//			Batteri:46%
//			GSM signal(1-5):3
//			------------------------------------------
//			8110, cool, 16
//
//			 Sommerhuset i R¿dhus
//			**Set opdateret**
//			Aktuel:27 - Set:16_Cool
//			Advarsel Lav:5 - H¿j:45
//			Batteri:47%
//			GSM signal(1-5):2
//			------------------------------------------
			noti = new Notification.Builder(context).setContentTitle("Set Opdateret").setContentText("")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case STATUS: {
//			 Sommerhuset i R¿dhus
//			 **Status**
//			 Aktuel:25 - Set:24_Heat
//			 Advarsel Lav:6 - H¿j:45
//			 Batteri:15%
//			 GSM signal(1-5):2
			noti = new Notification.Builder(context).setContentTitle("Status modtaget").setContentText("")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case TEMP_HIGH_UPDATE: {
//			------------------------------------------
//			8110, high, 20
//
//			Sommerhuset i R¿dhus
//			**H¿j temp opdateret**
//			Aktuel:28 - Set:16_Cool
//			Advarsel Lav:30 - H¿j:20
//			Batteri:63%
//			GSM signal(1-5):3
//			------------------------------------------
			noti = new Notification.Builder(context).setContentTitle("Advarsel Opdateret").setContentText("H¿j temperatur")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case TEMP_LOW_UPDATE: {
//			------------------------------------------
//			8110, low, 30
//
//			 Sommerhuset i R¿dhus
//			**Lav temp opdateret**
//			Aktuel:28 - Set:16_Cool
//			Advarsel Lav:30 - H¿j:45
//			Batteri:62%
//			GSM signal(1-5):3
//			------------------------------------------
			noti = new Notification.Builder(context).setContentTitle("Advarsel Opdateret").setContentText("Lav temperatur")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case UNKOWN: {
			noti = new Notification.Builder(context).setContentTitle("Ukendt Type").setContentText(message)
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case WARNING_HIGH_TEMP: {
//			------------------------------------------
//			 Sommerhuset i R¿dhus
//			**ADVARSEL H¿j temp**
//			Aktuel:28 - Set:16_Cool
//			Advarsel Lav:30 - H¿j:20
//			Batteri:63%
//			GSM signal(1-5):3
//			------------------------------------------
			noti = new Notification.Builder(context).setContentTitle("ADVARSEL!").setContentText("H¿j temperatur")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case WARNING_LOW_TEMP: {
//			------------------------------------------
//			ADVARSELS SMS
//
//			Sommerhuset i R¿dhus
//			**ADVARSEL Lav temp**
//			Aktuel:28 - Set:16_Cool
//			Advarsel Lav:30 - H¿j:45
//			Batteri:63%
//			GSM signal(1-5):2
//			------------------------------------------
			noti = new Notification.Builder(context).setContentTitle("ADVARSEL!").setContentText("Lav Temperatur")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}
		case WARNING_UNDER_5_DEGREES: {
			noti = new Notification.Builder(context).setContentTitle("ADVARSEL!").setContentText("Under 5 grader")
					.setSmallIcon(R.drawable.send_ikon)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.current_temperature_icon))
					.setContentIntent(pIntent)
					.build();
			break;
		}

		}
		// noti = new Notification.Builder(context)
		// .setContentTitle("Device yelled at ya'")
		// .setContentText(type.getText())
		// .setSmallIcon(android.R.drawable.ic_menu_camera)
		// .setContentIntent(pIntent)
		// .addAction(android.R.drawable.ic_menu_compass, "View", pIntent)
		// .setStyle(new Notification.BigTextStyle().bigText(message))
		// .build();
		// FIXME - Text needs to changed...

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		noti.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, noti);
	}
}
