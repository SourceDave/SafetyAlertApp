package com.example.safetyalert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class SafetyAppService extends Service {

	public static final int SAFETY_APP_NOTIFICATION_ID = 0;

	private NotificationManager notificationManager;
	private GuardianModeAlarm guardianModeAlarm;
	private boolean test;

	@Override
	public void onCreate() {
		super.onCreate();
		this.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		this.guardianModeAlarm = new GuardianModeAlarm();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int starttId) {
		
		if (!Utils.isExternalStorageWritable()) {
			Toast.makeText(this, "THIS APP WON'T WORK WITHOUT EXTERNAL STORAGE!", Toast.LENGTH_LONG).show();
		}

		Utils.lineBreakLogStrong();
		Utils.appendToLog("[ACTIVATED APP]");

		Notification safetyAppOnNotification = NotificationFactory.safetyAppOnNotification(this);
		notificationManager.notify(SAFETY_APP_NOTIFICATION_ID, safetyAppOnNotification);

		test = intent.getBooleanExtra(MainActivity.EXTRA_TEST_BOOLEAN, false);

		if (test) {
			test = false; // turn off test right away.
			guardianModeAlarm.setTestAlarm(SafetyAppService.this);
		} else {
			guardianModeAlarm.setAlarm(SafetyAppService.this);
		}

		return START_REDELIVER_INTENT;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Utils.appendToLog("[DEACTIVATED APP] All scheduled guardian requests/alerts are CANCELED.");
		Utils.lineBreakLogStrong();

		notificationManager.cancel(SAFETY_APP_NOTIFICATION_ID);
		notificationManager.cancel(GuardianModeActivity.GUARDIAN_MODE_NOTIFICATION_ID);
		notificationManager.cancel(AlertAlarm.ALERT_NOTIFICATION_ID);

		guardianModeAlarm.cancelAlarm(this);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}