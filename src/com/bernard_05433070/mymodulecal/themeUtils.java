package com.bernard_05433070.mymodulecal;

import android.app.Activity;
import android.content.Intent;


public class themeUtils
{
	//sets the theme based on the themechoice passed in to it
	public static void onActivityCreateSetTheme(Activity activity, String themechoice)
	{
		int choice = Integer.parseInt(themechoice);
		switch (choice)
		{
		default:
		case 1:
			activity.setTheme(R.style.DarkTheme);
			break;
		case 2:
			activity.setTheme(R.style.LightTheme);
			break;
		case 3:
			activity.setTheme(R.style.AccessibleTheme);
			break;
		}
	}
}