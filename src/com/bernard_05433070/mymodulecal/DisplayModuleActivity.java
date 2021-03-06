package com.bernard_05433070.mymodulecal;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

public class DisplayModuleActivity extends Activity {
	
	public static final String DEBUG_TAG = "AlarmDebug";
	
	TextView modCodeTextView;
	TextView modNameTextView;
	TextView typeTextView;
	TextView commentTextView;
	TextView startTextView;
	TextView finishTextView;
	TextView locationTextView;
	TextView dayTextView;
	TextView timeValue;
	RadioButton atTime;
	RadioButton five_before;
	RadioButton ten_before;
	RadioButton fifteen_before;
	String moduleName;
	
	String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
	

	
	//TextView dbDisplayCodeTextView;
	
	DBTools dbtools = new DBTools(this);
	
	String id;
	//alarm id used to give a unique id to each alarm intent set up
	int alarmId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());		
		String themechoice = settings.getString("theme_choices", "1");
		themeUtils.onActivityCreateSetTheme(this,themechoice);
		setContentView(R.layout.activity_display_module);
		// Show the Up button in the action bar.
		setupActionBar();
		
		
		//get the intent which started this activity
		Intent myIntent = getIntent();
		
		//get module id for db lookup
		id = myIntent.getStringExtra("moduleId");
		
		Log.e(DEBUG_TAG, id);
		
		modCodeTextView = (TextView) findViewById(R.id.modCodeTextView);
		modNameTextView = (TextView) findViewById(R.id.modNameTextView);
		typeTextView = (TextView) findViewById(R.id.typeTextView);
		commentTextView = (TextView) findViewById(R.id.commentTextView);
		startTextView = (TextView) findViewById(R.id.startTextView);
		finishTextView = (TextView) findViewById(R.id.finishTextView);
		locationTextView = (TextView) findViewById(R.id.locationTextView);
		dayTextView = (TextView) findViewById(R.id.dayTextView);
		timeValue = (TextView) findViewById(R.id.timeValue);
		atTime = (RadioButton) findViewById(R.id.atTimeAlarm);
		five_before = (RadioButton) findViewById(R.id.fiveBeforeAlarm);
		ten_before = (RadioButton) findViewById(R.id.tenBeforeAlarm);
		fifteen_before = (RadioButton) findViewById(R.id.fifteenBeforeAlarm);

		
			
		HashMap <String, String> moduleDetails = dbtools.getModule(id);
		moduleName = moduleDetails.get("moduleName");
		
		alarmId = Integer.parseInt(id);
		
		modCodeTextView.setText(moduleDetails.get("moduleCode")); 
		modNameTextView.setText(moduleDetails.get("moduleName")); 
		typeTextView.setText(moduleDetails.get("LectureOrPractical")); 
		commentTextView.setText(moduleDetails.get("addComments")); 
		startTextView.setText(moduleDetails.get("startTime")); 
		finishTextView.setText(moduleDetails.get("finishTime")); 
		locationTextView.setText(moduleDetails.get("Location")); 
		dayTextView.setText(moduleDetails.get("day")); 
		timeValue.setText(moduleDetails.get("timeValue"));
		
		
		
	}
	
	public void deleteEntry(View v){
		
		//alert dialog code adapted from official google tutorials
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.deletion_dialog))
               .setPositiveButton(getString(R.string.confirm_deletion), new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id3) {
                       
                	   //delete module and return to main activity
                  		dbtools.deleteContact(id);
                		Intent myIntent = new Intent(getApplication(), MainActivity.class);
                		startActivity(myIntent);
                	   
                   }
               })
               .setNegativeButton(R.string.cancel_deletion, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
		
	}
	
	public void updateEntry(View v){
		
		Intent editIntent = new Intent(getApplication(), Add_module.class);
		
		//send the mod db id
		editIntent.putExtra("moduleId",id);
		
		//callIntent
		startActivity(editIntent);
		
		
	}
	
	
	public void setAlarm(View v){
		timerAlert(v);
		
	}

public void timerAlert(View v) {

		int i = 10;
		
		//alarm code adapted from examples in Android for absolute beginners p284 to p297
		
		Intent timerIntent = new Intent(this,TimerBroadcastReceiver.class);
		timerIntent.putExtra("moduleName", moduleName);
		
		PendingIntent myPendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), alarmId, timerIntent, 0);
		
		//alarm manager used to set future alert
		AlarmManager myAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		
		//Calendar instance will be used to determine the time alarm will go off at
		Calendar now = Calendar.getInstance();
        int weekday = now.get(Calendar.DAY_OF_WEEK);
		
		int alarm_min;
		
		// date retrieval code adapted from posts on http://www.coderanch.com/t/385117/java/java/date-Monday
		int alarm_hour = Integer.parseInt(timeValue.getText().toString());
		Log.e(DEBUG_TAG,Integer.toString(alarm_hour));
		if(atTime.isChecked()){
			alarm_min = 0;
		}else if(five_before.isChecked()){
			alarm_min = 55;
			alarm_hour = alarm_hour - 1;
		}else if(ten_before.isChecked()){
			alarm_min = 50;
			alarm_hour = alarm_hour - 1;
		}else{
			alarm_min = 45;
			alarm_hour = alarm_hour - 1;
		}

		int offset = 0;
		int required_day = 0;
		String fromDB = dayTextView.getText().toString();
        
		Log.e(DEBUG_TAG, "Just before value of day");
		Log.e(DEBUG_TAG, fromDB);
		Log.e(DEBUG_TAG, "Just After Value of day");
		
        if(fromDB.equals("Monday")){
        required_day = Calendar.MONDAY;
        offset = 2;
        }else if(fromDB.equals("Tuesday")){     
    		Log.e(DEBUG_TAG, "Got into tuesday debug setter");

            required_day = Calendar.TUESDAY;
            offset = 3;
        }else if(fromDB.equals("Wednesday")){       
            required_day = Calendar.WEDNESDAY;
            offset = 4;
        }else if(fromDB.equals("Thursday")){       
            required_day = Calendar.THURSDAY;
            offset = 5;
        }else if(fromDB.equals("Friday")){
        	required_day = Calendar.FRIDAY;
            offset = 6;
        }
        //required to prevent alarms from being set 4 hours off
        now.setTimeZone(TimeZone.getTimeZone("GMT"));
		
        if (weekday != required_day)
        {
            // calculate how much to add
            // the 2 is the difference between Saturday and Monday
            int dayes = (Calendar.SATURDAY - weekday + offset) % 7;
            now.add(Calendar.DAY_OF_YEAR, dayes);
            Log.e(DEBUG_TAG, "Check Alarm Hour At this point");
            Log.e(DEBUG_TAG, Integer.toString(alarm_hour));
            now.set(Calendar.HOUR_OF_DAY, alarm_hour);
            now.set(Calendar.MINUTE, alarm_min);
        }else{
        	if((Calendar.HOUR_OF_DAY) < alarm_hour ){
        		now.set(Calendar.HOUR_OF_DAY, alarm_hour);
            	now.set(Calendar.MINUTE, alarm_min);
        	}else{
        	now.add(Calendar.DAY_OF_YEAR, 7);
        	now.set(Calendar.HOUR_OF_DAY, alarm_hour);
        	now.set(Calendar.MINUTE, alarm_min);
        	}
        }
		
		
		
		//setting alarm , time off from milliseconds is required
		myAlarmManager.set(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), myPendingIntent);
		//myAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(i*1000), myPendingIntent);

		
		//user confirmation popup
		Toast.makeText(this, "Alarm has been Set", Toast.LENGTH_LONG).show();
	}

public void cancelAlarm(View v) {
	
	//to cancel an alarm a duplicate of it must be created. This is why the module id is used as a unique identifier
	int i = 10;
	
	Intent timerIntent = new Intent(this,TimerBroadcastReceiver.class);
	timerIntent.putExtra("moduleName", moduleName);
	//cancel alarm and display popup to user
	PendingIntent myPendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), alarmId, timerIntent, PendingIntent.FLAG_CANCEL_CURRENT);
	Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_LONG).show();
	
	
}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_module, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
