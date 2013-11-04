package com.bernard_05433070.mymodulecal;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity implements OnSharedPreferenceChangeListener {
	
	Intent myIntent;
	TextView contactId;
	String themechoice;
	
	public static final String DEBUG_TAG = "Main_Activity";

	
	DBTools dbtools = new DBTools(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		themechoice = loadpreference();
		Log.e(DEBUG_TAG, themechoice);
		
		themeUtils.onActivityCreateSetTheme(this,themechoice);
		setContentView(R.layout.activity_main);
		
		
		ArrayList<HashMap <String, String>> moduleList = dbtools.getAllModules();
		
		
		
		if(moduleList.size() != 0){
			//get list view
			ListView listview = getListView();
			listview.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				
					//when item is clicked get its id
					contactId = (TextView) view.findViewById(R.id.dbCodeTextView);
					
					String moduleId = contactId.getText().toString();
					
					Intent addIntent = new Intent(getApplication(), DisplayModuleActivity.class);
					
					//send the mod db id
					addIntent.putExtra("moduleId",moduleId);
					
					//callIntent
					startActivity(addIntent);
				}
			});
			
			
			
			ListAdapter adapter = new SimpleAdapter(MainActivity.this, moduleList, R.layout.module_row, 
					new String[] {"moduleId","moduleCode","LectureOrPractical", "day", "startTime", "Location"}, 
					new int[]{R.id.dbCodeTextView,R.id.modMiniCodeTextView,R.id.firstLetterTextView,R.id.shortDayTextView,R.id.startTimeTextView, R.id.locationMiniTextView});
					
					
					setListAdapter(adapter);
		}
		
	}
	
	public void addModule(View v){
		Intent myIntent = new Intent(getApplication(), Add_module.class);
		startActivity(myIntent);
	}
	

		
		public void setTheme(View v){
			
			Intent prefIntent = new Intent(this, EditPreferences.class);
			startActivity(prefIntent);
			

			
		}
		
		/*
		switch(v.getId()){
		case R.id.lightTheme:
		//handle light
			themeUtils.changeToTheme(this, themeUtils.THEME_BLUE);
		break;
		case R.id.darkTheme:
			//handle dark
			themeUtils.changeToTheme(this, themeUtils.THEME_WHITE);
		break;
		case R.id.largeTheme:
			//handle Large
			themeUtils.changeToTheme(this, themeUtils.THEME_DEFAULT);
		break;
		}
		
		*/
		


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private String loadpreference() {
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		String j = settings.getString("theme_choices", "1");
		
		settings.registerOnSharedPreferenceChangeListener(MainActivity.this);
		
		return j;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		
		recreate();
	}

}
