package com.bernard_05433070.mymodulecal;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.widget.Toast;

//adapted from examples in android for absolute beginners
public class TimerBroadcastReceiver extends BroadcastReceiver {
	
	MediaPlayer mp = null;
	
	public void onReceive(Context context, Intent intent){
		String moduleName = intent.getStringExtra("moduleName");

		mp = MediaPlayer.create(context,  R.raw.bell_ringing_04);
		mp.start();
		Toast.makeText(context, "Alarm Notification"+ moduleName, Toast.LENGTH_LONG).show();
		
	}

	
}
