package com.example.safetyalert;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AlertResponseActivity extends Activity {

	private NotificationManager nManager;
	//private final Intent questionnaire = new Intent(this, Questionnaire.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle data = getIntent().getExtras();
		GuardianRequest g = (GuardianRequest) data
				.getParcelable(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST);

		setContentView(R.layout.activity_alert_response);

		TextView t = (TextView)findViewById(R.id.alert_response_body);
		t.append(g.toString());

		nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nManager.cancel(AlertAlarm.ALERT_NOTIFICATION_ID);

		Utils.appendToLog("[ALERT RESPONDED]");
	}

	public void toQuestionnaire(View view) {
		Intent questionnaire = new Intent(this, Questionnaire.class);
		startActivity(questionnaire);
		finish();
	}
}