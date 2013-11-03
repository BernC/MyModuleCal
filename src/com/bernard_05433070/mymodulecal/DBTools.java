package com.bernard_05433070.mymodulecal;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTools extends SQLiteOpenHelper{
	
	public DBTools(Context applicationcontext){
		
		super(applicationcontext, "moduleInfo.db", null, 1);

}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		String query = "CREATE TABLE modules ( moduleID INTEGER PRIMARY KEY AUTOINCREMENT, moduleCode TEXT, moduleName TEXT, LectureOrPractical TEXT, day TEXT, startTime TEXT, finishTime TEXT, Location TEXT, addComments TEXT, timeValue INT)";
		
		database.execSQL(query);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query = "DROP TABLE IF EXISTS modules";
		
		database.execSQL(query);
		onCreate(database);

		
	}
	
	public void insertModule(HashMap<String, String> queryValues) {
		
		SQLiteDatabase database = this.getWritableDatabase();
		
		ContentValues values  = new ContentValues();
		
		values.put("moduleCode", queryValues.get("moduleCode"));
		values.put("moduleName", queryValues.get("moduleName"));
		values.put("LectureOrPractical", queryValues.get("LectureOrPractical"));
		values.put("day", queryValues.get("day"));
		values.put("startTime", queryValues.get("startTime"));
		values.put("finishTime", queryValues.get("finishTime"));
		values.put("Location", queryValues.get("Location"));
		values.put("addComments", queryValues.get("addComments"));
		values.put("timeValue", queryValues.get("timeValue"));

		
		database.insert("modules", null, values);
		
		database.close();
		

	}
	
	public int updateContact(HashMap<String, String> queryValues,String key){
		
		SQLiteDatabase database = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("moduleCode", queryValues.get("moduleCode"));
		values.put("moduleName", queryValues.get("moduleName"));
		values.put("LectureOrPractical", queryValues.get("LectureOrPractical"));
		values.put("day", queryValues.get("day"));
		values.put("startTime", queryValues.get("startTime"));
		values.put("finishTime", queryValues.get("finishTime"));
		values.put("Location", queryValues.get("Location"));
		values.put("addComments", queryValues.get("addComments"));
		values.put("timeValue", Integer.parseInt(queryValues.get("timeValue")));

		
		return database.update("modules", values, "moduleID" + " = ?", new String[] { key});

	}
	
	public void deleteContact(String id){
		
		SQLiteDatabase database = this.getWritableDatabase();
		
		String deleteQuery = "DELETE FROM modules WHERE moduleID='" + id + "'";
		
		database.execSQL(deleteQuery);
	}
	
	public ArrayList<HashMap<String, String>> getAllModules(){
		
		ArrayList<HashMap<String, String>> moduleArrayList;
		
		String selectQuery = "SELECT * FROM modules";
		
		moduleArrayList = new ArrayList<HashMap<String, String>>();
		
		SQLiteDatabase database = this.getWritableDatabase();
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				HashMap<String, String> moduleMap = new HashMap<String, String>();
				
				moduleMap.put("moduleId", cursor.getString(0));
				moduleMap.put("moduleCode", cursor.getString(1));
				moduleMap.put("moduleName", cursor.getString(2));
				char Letter = cursor.getString(3).charAt(0);
				String firstLetter = String.valueOf(Letter);
				moduleMap.put("LectureOrPractical", firstLetter);
				String shortDay = cursor.getString(4).substring(0,3);
				moduleMap.put("day", shortDay);
				moduleMap.put("startTime", cursor.getString(5));
				moduleMap.put("finishTime", cursor.getString(6));
				moduleMap.put("Location", cursor.getString(7));
				moduleMap.put("addComments", cursor.getString(8));

				
				moduleArrayList.add(moduleMap);
			}while(cursor.moveToNext());
		}
		
		return moduleArrayList;
		
		
	}
	
public  ArrayList<HashMap<String, String>> getTodaysModules(String day,String time){
		
		SQLiteDatabase database = this.getReadableDatabase();
		
		ArrayList<HashMap<String, String>> moduleArrayList;
		moduleArrayList = new ArrayList<HashMap<String, String>>();

		
		String selectQuery = "Select * FROM modules WHERE day ='" + day + "' AND timeValue > "+ time + "";
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				HashMap<String, String> moduleMap = new HashMap<String, String>();
				
				moduleMap.put("moduleId", cursor.getString(0));
				moduleMap.put("moduleCode", cursor.getString(1));
				moduleMap.put("moduleName", cursor.getString(2));
				moduleMap.put("LectureOrPractical", cursor.getString(3));
				moduleMap.put("day", cursor.getString(4));
				moduleMap.put("startTime", cursor.getString(5));
				moduleMap.put("finishTime", cursor.getString(6));
				moduleMap.put("Location", cursor.getString(7));
				moduleMap.put("addComments", cursor.getString(8));
				
				moduleArrayList.add(moduleMap);

			}while(cursor.moveToNext());
		}
		
		return moduleArrayList;
		
	}
	
	public HashMap <String, String> getModule(String id){
		
		SQLiteDatabase database = this.getReadableDatabase();
		
		HashMap<String, String> moduleMap = new HashMap<String, String>();
		
		String selectQuery = "Select * FROM modules WHERE moduleID ='" + id + "'";
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				
				moduleMap.put("moduleId", cursor.getString(0));
				moduleMap.put("moduleCode", cursor.getString(1));
				moduleMap.put("moduleName", cursor.getString(2));
				moduleMap.put("LectureOrPractical", cursor.getString(3));
				moduleMap.put("day", cursor.getString(4));
				moduleMap.put("startTime", cursor.getString(5));
				moduleMap.put("finishTime", cursor.getString(6));
				moduleMap.put("Location", cursor.getString(7));
				moduleMap.put("addComments", cursor.getString(8));
				moduleMap.put("timeValue", cursor.getString(9));
				
			}while(cursor.moveToNext());
		}
		
		return moduleMap;
		
	}

	}