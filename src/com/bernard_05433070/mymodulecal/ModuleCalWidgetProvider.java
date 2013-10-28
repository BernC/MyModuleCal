package com.bernard_05433070.mymodulecal;

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

public class ModuleCalWidgetProvider extends AppWidgetProvider {
	

	
	public static final String DEBUG_TAG = "ModCalWidgetProvider";
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		
		try{
			updateWidgetContent(context, appWidgetManager);
		}catch (Exception e){
			Log.e(DEBUG_TAG, "Failed", e);
		}
	}

	private void updateWidgetContent(Context context,
			AppWidgetManager appWidgetManager) {

		RemoteViews remoteView = new RemoteViews(context.getPackageName(),R.layout.modulecal_appwidget_layout);
		
		DBTools dbtools = new DBTools(context);
		

		
		int day;
		
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
		
		ArrayList<HashMap <String, String>> moduleList = dbtools.getTodaysModules(dayString);

		
		remoteView.setTextViewText(R.id.widgetModNameTextView, "ModuleName");
		remoteView.setTextViewText(R.id.widgetStartTimeTextView, "12.00");
		
		Intent launchAppIntent = new Intent(context, MainActivity.class);
		PendingIntent launchAppPendingIntent = PendingIntent.getActivity(context, 0, launchAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.id.full_widget, launchAppPendingIntent);
		
		ComponentName ModuleCalWidget = new ComponentName(context, ModuleCalWidgetProvider.class);
		
		appWidgetManager.updateAppWidget(ModuleCalWidget, remoteView);
		
	}

}
