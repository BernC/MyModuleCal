package com.bernard_05433070.mymodulecal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

public class EditPreferences extends PreferenceActivity { 
	
	//every tutorial i could find advised using this method despite it being deprecated??
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {    

    super.onCreate(savedInstanceState);       

    addPreferencesFromResource(R.xml.preferences);       

}

	
	    
	}