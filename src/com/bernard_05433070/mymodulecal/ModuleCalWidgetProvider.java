package com.bernard_05433070.mymodulecal;

//based off tutorial from here http://mobile.tutsplus.com/tutorials/android/android-essentials-enhancing-your-applications-with-app-widgets/

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TableLayout;

public class ModuleCalWidgetProvider extends AppWidgetProvider {
	
	
	public static final String DEBUG_TAG = "ModCalWidgetProvider";
	
	//this is the code used to update the widget at specified intervals
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
				
		try{
			updateWidgetContent(context, appWidgetManager);
		}catch (Exception e){
			Log.e(DEBUG_TAG, "Failed", e);
		}
	}

	
	private void updateWidgetContent(Context context,
			AppWidgetManager appWidgetManager) {
		
		//remote views are reuired to set content for widgets
		RemoteViews remoteView = new RemoteViews(context.getPackageName(),R.layout.modulecal_appwidget_layout);
		
		DBTools dbtools = new DBTools(context);
		

		
		int day;
		
		//code used to figure out what day of week it is
		Calendar calendar = Calendar.getInstance();
		day = calendar.get(Calendar.DAY_OF_WEEK);
		String dayString;
		
		switch (day){
		
		case 2:  dayString = "Monday";
        break;
		case 3:  dayString = "Tuesday";
        break;
		case 4:  dayString = "Wednesday";
        break;
		case 5:  dayString = "Thursday";
        break;
		case 6:  dayString = "Friday";
		break;
		default: dayString ="Monday";
		break;
		}
		
		
		int time = calendar.get(Calendar.HOUR_OF_DAY);
		String hourString = Integer.toString(time);
		//int checje = Integer.parseInt(hourString);
		
		//search for a module which is due today but has not started yet
		ArrayList<HashMap <String, String>> moduleList = dbtools.getTodaysModules(dayString,hourString);
			
		
		HashMap<String, String> checker = moduleList.get(0);
		
		
		//set the widget text views as appropriate
		remoteView.setTextViewText(R.id.widgetModName, checker.get("moduleName"));
		remoteView.setTextViewText(R.id.widgetStartTime, checker.get("startTime"));
		remoteView.setTextViewText(R.id.widgetLocation, checker.get("Location"));
		
		Intent launchAppIntent = new Intent(context, MainActivity.class);
		PendingIntent launchAppPendingIntent = PendingIntent.getActivity(context, 0, launchAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.id.full_widget, launchAppPendingIntent);
		
		ComponentName ModuleCalWidget = new ComponentName(context, ModuleCalWidgetProvider.class);
		
		appWidgetManager.updateAppWidget(ModuleCalWidget, remoteView);
		
	}


}
