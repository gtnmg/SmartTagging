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

public class DBDataProvider implements IDataProvider {

	private DBHelper mDBHelper;
	private Context mContext;
	private SQLiteDatabase mDB;

	public DBDataProvider(Context context) {
		mContext = context;
		mDBHelper = new DBHelper(context);
		openDataBase();
		//fillTestData();
	}
	
	public void openDataBase(){
		if(mDB == null || !mDB.isOpen());
			mDB = mDBHelper.getWritableDatabase();
	}
	
	public void closeDataBase(){
		mDB.close();
		mDBHelper.close();
	}
	
	public SQLiteDatabase getDataBase(){
		return mDB;
	}

	@Override
	public Item getItem(Long item_id) {
		String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_ITEMS + " WHERE "
				+ DBHelper.FIELD_UNIQUE_ID + "= ?";

		Cursor cursor = mDBHelper.query(selectQuery, new String[] { item_id.toString() });
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
		String selectQuery = "SELECT  " + DBHelper.FIELD_DATA_ITEM_ID + " FROM "
				+ DBHelper.TABLE_DATA;

		if (cat != null || term_id != null)
			selectQuery += " WHERE ";
		List<String> params = new ArrayList<String>();
		if (cat != null && cat.getId() != null) {
			selectQuery += DBHelper.FIELD_DATA_CATALOG_ID + " = ? ";
			params.add(cat.getId().toString());
		}
		if (term_id != null) {
			if (params.size() > 0)
				selectQuery += " AND ";
			selectQuery += DBHelper.FIELD_DATA_TERM_ID + " = ? ";
			params.add(term_id.toString());
		}
		String[] pars = params.toArray(new String[params.size()]);
		Cursor cursor = mDBHelper.query(selectQuery, pars);
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
		String selectQuery = "SELECT DISTINCT ITEMS." + DBHelper.FIELD_TERM_NAME
				+ " FROM " + DBHelper.TABLE_TERMS + " ITEMS " + "LEFT JOIN "
				+ DBHelper.TABLE_DATA + " DATA ON " + " ITEMS." + DBHelper.FIELD_UNIQUE_ID
				+ " =  DATA." + DBHelper.FIELD_DATA_TERM_ID;

		String[] params = null;
		if (cat != null) {
			selectQuery += " WHERE " + DBHelper.FIELD_DATA_CATALOG_ID + " = ?";
			params = new String[1];
			params[0] = cat.getId().toString();
		}

		Cursor cursor = mDBHelper.query(selectQuery, params);
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
		String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_TERMS + " WHERE "
				+ DBHelper.FIELD_TERM_NAME + "= ?";

		Cursor cursor = mDBHelper.query(selectQuery, new String[] { term });

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
		ContentValues values = new ContentValues();
		values.put(DBHelper.FIELD_UNIQUE_ID, term.getId()); // Catalog id
		values.put(DBHelper.FIELD_TERM_NAME, term.getName()); // Catalog parent_id
		values.put(DBHelper.FIELD_TERM_WEIGHT, term.getWeight()); // Contact name
		// Inserting Row
		return  mDB.insert(DBHelper.TABLE_TERMS, null, values);
	}

	@Override
	public Long addItem(Item item) {
		ContentValues itemValues = new ContentValues();
		itemValues.put(DBHelper.FIELD_UNIQUE_ID, item.getId());
		itemValues.put(DBHelper.FIELD_ITEM_CONTEXT, item.getComment()); // item context
		itemValues.put(DBHelper.FIELD_ITEM_PATH, item.getPath()); // item path
		return mDB.insert(DBHelper.TABLE_ITEMS, null, itemValues);
	}

	@Override
	public Long addCatalog(Catalog catalog) {
		ContentValues values = new ContentValues();
		values.put(DBHelper.FIELD_UNIQUE_ID, catalog.getId()); // Catalog id
		values.put(DBHelper.FIELD_CATALOG_PARENT_ID, catalog.getParent_id()); // Catalog
																		// parent_id
		values.put(DBHelper.FIELD_CATALOG_NAME, catalog.getName()); // Contact name
		values.put(DBHelper.FIELD_CATALOG_WEIGHT, catalog.getWeight()); // Contact weight
		// Inserting Row
		return mDB.insert(DBHelper.TABLE_CATALOGS, null, values);
	}

	@Override
	public List<Catalog> getRootCatalogs() {
		List<Catalog> catalogList = new ArrayList<Catalog>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_CATALOGS;

		Cursor cursor = mDBHelper.query(selectQuery, null);

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
		openDataBase();
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
		closeDataBase();
	}

	@Override
	public Item findItemByFileURI(String fileUri) {
		String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_ITEMS + " WHERE "
				+ DBHelper.FIELD_ITEM_PATH + "= ?";

		Cursor cursor = mDBHelper.query(selectQuery, new String[] { fileUri });

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
		ContentValues dataValues = new ContentValues();
		String emtyPointer = null;
		dataValues.put(DBHelper.FIELD_UNIQUE_ID, emtyPointer); // Catalog id
		dataValues.put(DBHelper.FIELD_DATA_ITEM_ID, item_id); // Catalog parent_id
		dataValues.put(DBHelper.FIELD_DATA_TERM_ID, term_id); // Contact name
		dataValues.put(DBHelper.FIELD_DATA_CATALOG_ID, catalog_id);
		// Inserting Row
		return mDB.insert(DBHelper.TABLE_DATA, null, dataValues);
	}

	@Override
	public boolean deleteItem(Long item_id) {
		return mDB.delete(DBHelper.TABLE_ITEMS, DBHelper.FIELD_UNIQUE_ID + "=" + item_id, null) > 0;
	}

	@Override
	public boolean deleteTerm(Long term_id) {
		return mDB.delete(DBHelper.TABLE_TERMS, DBHelper.FIELD_UNIQUE_ID + "=" + term_id, null) > 0;
	}

	@Override
	public boolean deleteData(Long data_id) {
		return mDB.delete(DBHelper.TABLE_DATA, DBHelper.FIELD_UNIQUE_ID + "=" + data_id, null) > 0;
	}

	@Override
	public boolean deleteCatalog(Long catalog_id) {
		return mDB.delete(DBHelper.TABLE_CATALOGS, DBHelper.FIELD_UNIQUE_ID + "=" + catalog_id, null) > 0;
	}
}
