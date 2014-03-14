package com.example.safetyalert;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private Intent safetyAppIntent;
	public static final String EXTRA_TEST_BOOLEAN = "com.example.safetyalert.TEST_BOOLEAN";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		safetyAppIntent = new Intent(this, SafetyAppService.class);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            addTestEntry();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void addTestEntry() {
		stopService(safetyAppIntent);
		safetyAppIntent.putExtra(EXTRA_TEST_BOOLEAN, true);
		startService(safetyAppIntent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void questionnaire(View view) {
		Intent toQuestionnaire = new Intent(this, TimeoutQuestionnaire.class);
		startActivity(toQuestionnaire);
	}

	public void trigger(View view) {
		Intent toSecondary = new Intent(this, DisplayTriggerDetailsActivity.class);
		startActivity(toSecondary);
	}
	public void activateSafetyApp(View view) {
		startService(safetyAppIntent);
	}

	public void deactivateSafetyApp(View view) {
		stopService(safetyAppIntent);
	}

	private boolean safetyAppIsRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (SafetyAppService.class.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}