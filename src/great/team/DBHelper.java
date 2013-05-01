package great.team;

import android.database.sqlite.SQLiteDatabase;

public class DBHelper {


	  // Database creation SQL statement
	  private static String getSQL_createCATALOG()
	  {
	     return "create table CATALOG (ID integer primary key autoincrement, NAME text not null, COMMENT text," 
	    		 	+ "PARENT_ID integer not null,WEIGHT  integer not null);"; 
	  }
	  private static String getSQL_createTERMS()
	  {
		  return "CREATE TABLE TERMS (ID integer primary key autoincrement, NAME text not null, WEIGHT  integer not null);"; 
	  }
	  private static String getSQL_createDATA()
	  {
		  return "CREATE TABLE DATA(ID integer primary key autoincrement, ITEM_ID integer not null, CATALOG_ID integer not null, TERM_ID integer not null);"; 
	  }
	  private static String getSQL_createITEMS()
	  {
		  return "CREATE TABLE ITEMS (ID integer primary key autoincrement, PATH text not null, COMMENT text );";
	  }
	  public static void onCreate(SQLiteDatabase database) 
	  {
		  database.execSQL(getSQL_createCATALOG());
		  database.execSQL(getSQL_createTERMS());
		  database.execSQL(getSQL_createITEMS());
		  database.execSQL(getSQL_createDATA());
	  }
	  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	      int newVersion) {
		  /*
	    Log.w(TodoTable.class.getName(), "Upgrading database from version "
	        + oldVersion + " to " + newVersion
	        + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
	    onCreate(database);
	    */
	} 
}
