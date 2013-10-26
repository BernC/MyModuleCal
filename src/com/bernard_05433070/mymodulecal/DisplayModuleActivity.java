package com.bernard_05433070.mymodulecal;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

public class DisplayModuleActivity extends Activity {
	
	TextView modCodeTextView;
	TextView modNameTextView;
	TextView typeTextView;
	TextView commentTextView;
	TextView startTextView;
	TextView finishTextView;
	TextView locationTextView;
	TextView dayTextView;
	//TextView dbDisplayCodeTextView;
	
	DBTools dbtools = new DBTools(this);
	
	String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_module);
		// Show the Up button in the action bar.
		setupActionBar();
		
		
		//get the intent which started this activity
		Intent myIntent = getIntent();
		
		//possible troublemaker
		id = myIntent.getStringExtra("moduleId");
		
		modCodeTextView = (TextView) findViewById(R.id.modCodeTextView);
		modNameTextView = (TextView) findViewById(R.id.modNameTextView);
		typeTextView = (TextView) findViewById(R.id.typeTextView);
		commentTextView = (TextView) findViewById(R.id.commentTextView);
		startTextView = (TextView) findViewById(R.id.startTextView);
		finishTextView = (TextView) findViewById(R.id.finishTextView);
		locationTextView = (TextView) findViewById(R.id.locationTextView);
		dayTextView = (TextView) findViewById(R.id.dayTextView);
		//dbDisplayCodeTextView = (TextView) findViewByID(R.id.dbDisplayCodeTextView);
		
			
		HashMap <String, String> moduleDetails = dbtools.getModule(id);
		
		modCodeTextView.setText(moduleDetails.get("moduleCode")); 
		modNameTextView.setText(moduleDetails.get("moduleName")); 
		typeTextView.setText(moduleDetails.get("LectureOrPractical")); 
		commentTextView.setText(moduleDetails.get("addComments")); 
		startTextView.setText(moduleDetails.get("startTime")); 
		finishTextView.setText(moduleDetails.get("finishTime")); 
		locationTextView.setText(moduleDetails.get("Location")); 
		dayTextView.setText(moduleDetails.get("day")); 
		
		
		
	}
	
	public void deleteEntry(View v){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.deletion_dialog))
               .setPositiveButton(getString(R.string.confirm_deletion), new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id3) {
                       
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
