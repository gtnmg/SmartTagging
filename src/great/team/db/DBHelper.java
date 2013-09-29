package great.team.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "smart_tagging_db";
	
	// Datatables
	public static final String TABLE_CATALOGS = "catalogs";
	public static final String TABLE_ITEMS = "items";
	public static final String TABLE_TERMS = "terms";
	public static final String TABLE_DATA = "data";

	public static final String FIELD_UNIQUE_ID = "_id";

	// Catalogs fields
	public static final String FIELD_CATALOG_PARENT_ID = "parent_id";
	public static final String FIELD_CATALOG_NAME = "name";
	public static final String FIELD_CATALOG_WEIGHT = "weight";

	// Item fields
	public static final String FIELD_ITEM_PATH = "path";
	public static final String FIELD_ITEM_CONTEXT = "context";

	// Term fields
	public static final String FIELD_TERM_NAME = "name";
	public static final String FIELD_TERM_WEIGHT = "weight";

	// Data fields
	public static final String FIELD_DATA_ITEM_ID = "item_id";
	public static final String FIELD_DATA_CATALOG_ID = "catalog_id";
	public static final String FIELD_DATA_TERM_ID = "term_id";
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public Cursor query(String query) {
		return query(query,null);
	}

	public Cursor query(String query, String [] params) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, params);
		return cursor;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CATALOG_TABLE = "CREATE TABLE " + TABLE_CATALOGS + "("
				+ FIELD_UNIQUE_ID + " INTEGER PRIMARY KEY,"
				+ FIELD_CATALOG_PARENT_ID + " INTEGER," + FIELD_CATALOG_NAME
				+ " TEXT, " + FIELD_CATALOG_WEIGHT + " INTEGER" + ")";
		String CREATE_TERM_TABLE = "CREATE TABLE " + TABLE_TERMS + "("
				+ FIELD_UNIQUE_ID + " INTEGER PRIMARY KEY," 
				+ FIELD_TERM_NAME + " TEXT UNIQUE NOT NULL, " 
				+ FIELD_TERM_WEIGHT + " INTEGER" + ")";
		String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
				+ FIELD_UNIQUE_ID + " INTEGER PRIMARY KEY," 
				+ FIELD_ITEM_PATH + " TEXT UNIQUE NOT NULL, " 
				+ FIELD_ITEM_CONTEXT + " TEXT" + ")";
		String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
				+ FIELD_UNIQUE_ID + " INTEGER PRIMARY KEY,"
				+ FIELD_DATA_ITEM_ID + " INTEGER, " 
				+ FIELD_DATA_TERM_ID + " INTEGER, " 
				+ FIELD_DATA_CATALOG_ID + " INTEGER" + ")";

		db.execSQL(CREATE_CATALOG_TABLE);
		db.execSQL(CREATE_TERM_TABLE);
		db.execSQL(CREATE_ITEM_TABLE);
		db.execSQL(CREATE_DATA_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		if (newVersion > oldVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATALOGS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
			// Create tables again
			onCreate(db);
		}	
	}

}
