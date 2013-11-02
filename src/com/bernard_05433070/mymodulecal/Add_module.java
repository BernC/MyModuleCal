package com.bernard_05433070.mymodulecal;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class Add_module extends Activity {
	
	public static final String DEBUG_TAG = "Add_module";

	
	EditText addModCodeEditText;
	EditText addModNameEditText;
	Spinner daySpinner;
	Spinner timeStartSpinner;
	Spinner timeFinishedSpinner;
	EditText addRoomEditText;
	EditText addCommEditText;
	RadioButton buttonPractical;
	RadioButton buttonLecture;
	DBTools dbTools = new DBTools(this);
	int id;
	String module_to_edit;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_module);
		// Show the Up button in the action bar.
		setupActionBar();
		
		addModCodeEditText = (EditText) findViewById(R.id.addModCodeEditText);
		addModNameEditText = (EditText) findViewById(R.id.addModNameEditText);
		addRoomEditText = (EditText) findViewById(R.id.addRoomEditText);
		addCommEditText = (EditText) findViewById(R.id.addCommEditText);
		daySpinner = (Spinner)findViewById(R.id.daySpinner);
		timeStartSpinner = (Spinner)findViewById(R.id.timeStartSpinner);
		timeFinishedSpinner = (Spinner)findViewById(R.id.timeFinishedSpinner);
		buttonPractical = (RadioButton)findViewById(R.id.practicalRadio);
		buttonLecture = (RadioButton)findViewById(R.id.lectureRadio);
		
		Intent myIntent = getIntent();
		module_to_edit = myIntent.getStringExtra("moduleId");
		
		try{
		id = Integer.parseInt(module_to_edit);
		}catch (Exception e){
			id = 0;
		}
		
		Log.e(DEBUG_TAG, "got to just outside if");
    	ArrayList<String> days = new ArrayList<String>();
    	
    	days.add("Monday");
    	days.add("Tuesday");
    	days.add("Wednesday");
    	days.add("Thursday");
    	days.add("Friday");
    	
    	ArrayList<String> hours = new ArrayList<String>();
    	
        hours.add("9.00");
        hours.add("10.00");
        hours.add("11.00");
        hours.add("12.00");
        hours.add("13.00");
        hours.add("14.00");
        hours.add("15.00");
        hours.add("16.00");
        hours.add("17.00");
        hours.add("18.00");
        hours.add("19.00");
		
		if(id != 0){
			Log.e(DEBUG_TAG, "got in here");
			HashMap <String, String> moduleDetails = dbTools.getModule(module_to_edit);
			
			addModCodeEditText.setText(moduleDetails.get("moduleCode")); 
			addModNameEditText.setText(moduleDetails.get("moduleName")); 
			addCommEditText.setText(moduleDetails.get("addComments")); 
			addRoomEditText.setText(moduleDetails.get("Location")); 
			timeStartSpinner.setSelection(hours.indexOf(moduleDetails.get("startTime")));
			timeFinishedSpinner.setSelection(hours.indexOf(moduleDetails.get("finishTime")));
			daySpinner.setSelection(days.indexOf(moduleDetails.get("day")));
			
			if(moduleDetails.get("LectureOrPractical") == "Practical"){
				buttonPractical.setChecked(true);
			}else{
				buttonLecture.setChecked(true);
			}

			
		}
		
		

	}
	
	public void insertModule(View V){
		
		//define local variable to hold the values
		String ModuleType;
		
		//get radio group info
		if(buttonPractical.isChecked()){
			ModuleType = "Practical";
		}else{
			ModuleType = "Lecture";
		}
			
		
				
		//define hashmap for values
		HashMap<String, String> queryValues = new HashMap<String, String>();
		
		
		//put info into the hashmap
		queryValues.put("moduleCode", addModCodeEditText.getText().toString());
		queryValues.put("moduleName", addModNameEditText.getText().toString());
		queryValues.put("LectureOrPractical", ModuleType);
		queryValues.put("day", daySpinner.getItemAtPosition(daySpinner.getSelectedItemPosition()).toString());
		queryValues.put("startTime", timeStartSpinner.getItemAtPosition(timeStartSpinner.getSelectedItemPosition()).toString());
		queryValues.put("finishTime", timeFinishedSpinner.getItemAtPosition(timeFinishedSpinner.getSelectedItemPosition()).toString());
		queryValues.put("Location", addRoomEditText.getText().toString());
		queryValues.put("addComments", addCommEditText.getText().toString());
		queryValues.put("timeValue",  getResources().getStringArray(R.array.hours_values)[timeStartSpinner.getSelectedItemPosition()]);

		
		
		//run the query
		if(id != 0)
		{
			//need to add time value field into update
		dbTools.updateContact(queryValues, module_to_edit);
		}else{
		dbTools.insertModule(queryValues);
		}
		
		//call back the main activity screen
		
		this.callMainActivity(V);
		
	}

	public void callMainActivity(View v) {
		
		Intent myIntent = new Intent(getApplication(), MainActivity.class);
		startActivity(myIntent);
		
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
		getMenuInflater().inflate(R.menu.add_module, menu);
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
