package org.northernnerds.settings;

import org.northernnerds.enums.ResponseTypes;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class Notifier {

	private Context context;

	public Notifier(Context context) {
		this.context = context;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void makeNotification(ResponseTypes type, String message) {
		Intent notificationIntent = new Intent();
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		// Build notification
		Notification noti = new Notification.Builder(context).setContentTitle("Device yelled at ya'")
				.setContentText(type.getText()).setSmallIcon(android.R.drawable.ic_menu_camera)
				.setContentIntent(pIntent).addAction(android.R.drawable.ic_menu_compass, "View", pIntent)
				.setStyle(new Notification.BigTextStyle().bigText(message)).build();
		// FIXME - Text needs to changed...

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		noti.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, noti);
	}
}
