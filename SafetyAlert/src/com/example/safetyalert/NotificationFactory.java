package com.example.safetyalert;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.Toast;

public class NotificationFactory {

	private static void setSound(Builder ncb) {
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		ncb.setSound(alarmSound);
	}
	
	private static void setVibrate(Builder ncb) {
		long[] pattern = {0, 100, 1000};
		ncb.setVibrate(pattern);
	}

	public static Notification safetyAppOnNotification(Context context) {
		NotificationCompat.Builder ncb = new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Safety Alert is ON.")
				.setContentText("Running in the background.");

		// User goes back to the screen when they click the notification
		Intent toMainActivity = new Intent(context, MainActivity.class);
		toMainActivity.setClass(context, MainActivity.class);
		toMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		PendingIntent p = PendingIntent.getActivity(context, 0, toMainActivity, 0);
		ncb.setContentIntent(p);

		Notification notification = ncb.build();
		notification.flags |= Notification.FLAG_ONGOING_EVENT;

		return notification;
	}

	public static Notification pendingGuardianRequestNotification(Context context, GuardianRequest g) {
		NotificationCompat.Builder ncb = new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.ic_contact)
				.setContentTitle("Pending Guardian Request!")
				.setContentText("Duration: " + g.guardianshipDuration + " minutes. Tap here to accept.");

		// User goes back to the screen when they click the notification
		Intent toGuardianModeActivity = new Intent(context, GuardianModeActivity.class);
		toGuardianModeActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		toGuardianModeActivity.putExtra(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST, g);

		PendingIntent p = PendingIntent.getActivity(context, 0, toGuardianModeActivity, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT);
		ncb.setContentIntent(p);
		ncb.setDefaults(Notification.DEFAULT_ALL);

		Notification notification = ncb.build();
		notification.flags |= Notification.FLAG_ONGOING_EVENT;

		return notification;
	}

	public static Notification pendingAlertNotification(Context context, GuardianRequest g) {
		NotificationCompat.Builder ncb = new NotificationCompat.Builder(context)
		.setSmallIcon(R.drawable.ic_danger)
		.setContentTitle("Pending Alert!")
		.setContentText("Your friend is in danger! Tap here to respond.");

        // User goes back to the screen when they click the notification
        Intent toAlertResponseActivity = new Intent(context, AlertResponseActivity.class);
        toAlertResponseActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        toAlertResponseActivity.putExtra(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST, g);

        PendingIntent p = PendingIntent.getActivity(context, 0, toAlertResponseActivity, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT);
        ncb.setContentIntent(p);
		ncb.setDefaults(Notification.DEFAULT_ALL);

        Notification notification = ncb.build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;

        return notification;
	}

	public static Notification progressUpdateNotification(Context context, int progress, int outOf) {
		NotificationCompat.Builder ncb = new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Guardian Mode is ON.")
				.setContentText("Running in the background.")
				.setProgress(outOf, progress, false);
		
		Notification notification = ncb.build();
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		
		return notification;
	 }
}