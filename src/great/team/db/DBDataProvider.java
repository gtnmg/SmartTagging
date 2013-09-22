package great.team.db;

import great.team.entity.Catalog;
import great.team.entity.Item;
import great.team.entity.Term;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// TODO: DBDataProvider singleton
public class DBDataProvider extends SQLiteOpenHelper implements IDataProvider {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "smart_tagging_db";

	// Datatables
	private static final String TABLE_CATALOGS = "catalogs";
	private static final String TABLE_ITEMS = "items";
	private static final String TABLE_TERMS = "terms";
	private static final String TABLE_DATA = "data";

	private static final String FIELD_UNIQUE_ID = "_id";

	// Catalogs fields
	private static final String FIELD_CATALOG_PARENT_ID = "parent_id";
	private static final String FIELD_CATALOG_NAME = "name";
	private static final String FIELD_CATALOG_WEIGHT = "weight";

	// Item fields
	private static final String FIELD_ITEM_PATH = "path";
	private static final String FIELD_ITEM_CONTEXT = "context";

	// Term fields
	private static final String FIELD_TERM_NAME = "name";
	private static final String FIELD_TERM_WEIGHT = "weight";

	// Data fields
	private static final String FIELD_DATA_ITEM_ID = "item_id";
	private static final String FIELD_DATA_CATALOG_ID = "catalog_id";
	private static final String FIELD_DATA_TERM_ID = "term_id";

	public DBDataProvider(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//fillTestData();
	}

	@Override
	public Item getItem(Long item_id) {
		String selectQuery = "SELECT  * FROM " + TABLE_ITEMS + " WHERE "
				+ FIELD_UNIQUE_ID + "= ?";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery,
				new String[] { item_id.toString() });

		// looping through all rows and adding to list
		Item item = new Item();
		if (cursor.moveToFirst()) {
			item.setId(Long.parseLong(cursor.getString(0)));
			item.setPath(cursor.getString(1));
			item.setComment(cursor.getString(2));
		}
		return item;
	}

	@Override
	public List<Item> getItems(Catalog cat, Long term_id) {
		List<Item> items = new ArrayList<Item>();
		// TODO: make cool query to database from several tables
		String selectQuery = "SELECT  " + FIELD_DATA_ITEM_ID + " FROM "
				+ TABLE_DATA;

		if (cat != null || term_id != null)
			selectQuery += " WHERE ";
		List<String> params = new ArrayList<String>();
		if (cat != null && cat.getId() != null) {
			selectQuery += FIELD_DATA_CATALOG_ID + " = ? ";
			params.add(cat.getId().toString());
		}
		if (term_id != null) {
			if (params.size() > 0)
				selectQuery += " AND ";
			selectQuery += FIELD_DATA_TERM_ID + " = ? ";
			params.add(term_id.toString());
		}
		SQLiteDatabase db = this.getReadableDatabase();
		String[] pars = params.toArray(new String[params.size()]);
		Cursor cursor = db.rawQuery(selectQuery, pars);
		if (cursor.moveToFirst()) {
			do {
				Long item_id = Long.parseLong(cursor.getString(0));
				Item item = getItem(item_id);
				items.add(item);
			} while (cursor.moveToNext());
		}
		return items;
	}

	@Override
	public String[] getTerms(Catalog cat) {

		// Select All Query
		String selectQuery = "SELECT DISTINCT ITEMS." + FIELD_TERM_NAME
				+ " FROM " + TABLE_TERMS + " ITEMS " + "LEFT JOIN "
				+ TABLE_DATA + " DATA ON " + " ITEMS." + FIELD_UNIQUE_ID
				+ " =  DATA." + FIELD_DATA_TERM_ID;

		String[] params = null;
		if (cat != null) {
			selectQuery += " WHERE " + FIELD_DATA_CATALOG_ID + " = ?";
			params = new String[1];
			params[0] = cat.getId().toString();
		}

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, params);
		String[] result = new String[cursor.getCount()];
		int i = 0;
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				result[i++] = cursor.getString(0);
			} while (cursor.moveToNext());
		}
		return result;
	}

	@Override
	public Term findTermByName(String term) {
		String selectQuery = "SELECT  * FROM " + TABLE_TERMS + " WHERE "
				+ FIELD_TERM_NAME + "= ?";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { term });

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			Term termEntry = new Term();
			termEntry.setId(Long.parseLong(cursor.getString(0)));
			termEntry.setName(cursor.getString(1));
			if (cursor.getString(2) != null && !cursor.getString(2).equals(""))
				termEntry.setWeight(Long.parseLong(cursor.getString(3)));
			return termEntry;
		}
		// return contact list
		return null;
	}

	@Override
	public Long addTerm(Term term) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FIELD_UNIQUE_ID, term.getId()); // Catalog id
		values.put(FIELD_TERM_NAME, term.getName()); // Catalog parent_id
		values.put(FIELD_TERM_WEIGHT, term.getWeight()); // Contact name
		// Inserting Row
		Long id = db.insert(TABLE_TERMS, null, values);
		db.close(); // Closing database connection
		return id;
	}

	@Override
	public Long addItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues itemValues = new ContentValues();
		itemValues.put(FIELD_UNIQUE_ID, item.getId());
		itemValues.put(FIELD_ITEM_CONTEXT, item.getComment()); // item context
		itemValues.put(FIELD_ITEM_PATH, item.getPath()); // item path
		Long id = db.insert(TABLE_ITEMS, null, itemValues);
		db.close();
		return id;
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
		// db.close(); --- doesn't work
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
//			fillTestData();
		}
	}

	@Override
	public Long addCatalog(Catalog catalog) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FIELD_UNIQUE_ID, catalog.getId()); // Catalog id
		values.put(FIELD_CATALOG_PARENT_ID, catalog.getParent_id()); // Catalog
																		// parent_id
		values.put(FIELD_CATALOG_NAME, catalog.getName()); // Contact name
		values.put(FIELD_CATALOG_WEIGHT, catalog.getWeight()); // Contact weight
		// Inserting Row
		Long id = db.insert(TABLE_CATALOGS, null, values);
		db.close(); // Closing database connection
		return id;
	}

	@Override
	public List<Catalog> getRootCatalogs() {
		List<Catalog> catalogList = new ArrayList<Catalog>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CATALOGS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Catalog catalog = new Catalog();
				catalog.setId(Long.parseLong(cursor.getString(0)));
				if (cursor.getString(1) != null
						&& !cursor.getString(1).equals(""))
					catalog.setParent_id(Long.parseLong(cursor.getString(1)));
				catalog.setName(cursor.getString(2));
				if (cursor.getString(3) != null
						&& !cursor.getString(3).equals(""))
					catalog.setWeight(Long.parseLong(cursor.getString(3)));
				// Adding contact to list
				catalogList.add(catalog);
			} while (cursor.moveToNext());
		}
		// return contact list
		
		return catalogList;
	}

	public void fillTestData() {
		addCatalog(new Catalog(Long.valueOf(1), null, Long.parseLong("40"),
				"foto"));
		addCatalog(new Catalog(Long.valueOf(2), null, Long.parseLong("40"),
				"video"));
		addCatalog(new Catalog(Long.valueOf(3), null, Long.parseLong("40"),
				"documents"));
		addCatalog(new Catalog(Long.valueOf(4), null, Long.parseLong("40"),
				"maps"));
		addCatalog(new Catalog(Long.valueOf(5), null, Long.parseLong("40"),
				"other")); // can place id = null
		addCatalog(new Catalog(Long.valueOf(6), null, Long.parseLong("40"),
				"add new"));

		addTerm(new Term(Long.valueOf(1), "test", null));
		addTerm(new Term(Long.valueOf(2), "testing", null));
		addTerm(new Term(Long.valueOf(3), "tost", null));
		addTerm(new Term(Long.valueOf(4), "ttt", null));
		addTerm(new Term(Long.valueOf(5), "123", null));
		addTerm(new Term(Long.valueOf(6), "1", null));

		addItem(new Item(Long.valueOf(1), "path1", "item1"));
		addItem(new Item(Long.valueOf(2), "path2", "item2"));
		addItem(new Item(Long.valueOf(3), "path3", "item3"));
		addItem(new Item(Long.valueOf(4), "path4", "item4"));
		addItem(new Item(Long.valueOf(5), "path5", "item5"));
		addItem(new Item(Long.valueOf(6), "path6", "item6"));
		
		addData(Long.valueOf(1), Long.valueOf(1), Long.valueOf(1));
		addData(Long.valueOf(2), Long.valueOf(1), Long.valueOf(1));
		addData(Long.valueOf(3), Long.valueOf(2), Long.valueOf(1));
		addData(Long.valueOf(4), Long.valueOf(2), Long.valueOf(1));		
		addData(Long.valueOf(5), Long.valueOf(4), Long.valueOf(2));		
		addData(Long.valueOf(6), Long.valueOf(6), Long.valueOf(2));		
	}

	@Override
	public Item findItemByFileURI(String fileUri) {
		String selectQuery = "SELECT  * FROM " + TABLE_ITEMS + " WHERE "
				+ FIELD_ITEM_PATH + "= ?";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[] { fileUri });

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			Item itemEntry = new Item();
			itemEntry.setId(Long.parseLong(cursor.getString(0)));
			itemEntry.setPath(cursor.getString(1));
			if (cursor.getString(2) != null && !cursor.getString(2).equals(""))
				itemEntry.setComment(cursor.getString(2));
			return itemEntry;
		}
		return null;
	}

	@Override
	public Long addData(Long item_id, Long term_id, Long catalog_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues dataValues = new ContentValues();
		String emtyPointer = null;
		dataValues.put(FIELD_UNIQUE_ID, emtyPointer); // Catalog id
		dataValues.put(FIELD_DATA_ITEM_ID, item_id); // Catalog parent_id
		dataValues.put(FIELD_DATA_TERM_ID, term_id); // Contact name
		dataValues.put(FIELD_DATA_CATALOG_ID, catalog_id);
		// Inserting Row
		Long id = db.insert(TABLE_DATA, null, dataValues);
		db.close(); // Closing database connection
		return id;
	}
}
