package com.huzzy.cursoradapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.huzzy.cursoradapter.mInterface.dbInterface;

public class DatabaseAdapter extends SQLiteOpenHelper implements dbInterface {

	private static DatabaseAdapter sSingleton;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "news";
	private static final int SCHEMA_VERSION = 2;
	public static final String ITEM_KEY_ROWID = "_id";
	public static final String ITEM_TABLE = "item_table";
	public static final String ITEM_NAME = "item_name";
	public static final String ITEM_LINK = "item_link";
	public static final String ITEM_POSITION = "item_position";
	public static final String ITEM_IMAGE_NAME = "item_image_name";

	private final String sort_order = "ASC"; // ASC or DESC

	// String to create the initial news database table
	private static final String DATABASE_CREATE_ITEMS = 
			"CREATE TABLE item_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, item_name TEXT, item_link TEXT, item_image_name TEXT, item_position INTEGER);";

	// Methods to setup database singleton and connections
	public synchronized static DatabaseAdapter getInstance(Context ctxt) {
		if (sSingleton == null) {
			sSingleton = new DatabaseAdapter(ctxt);
		}
		return sSingleton;
	}

	public DatabaseAdapter(Context ctxt) {
		super(ctxt, DATABASE_NAME, null, SCHEMA_VERSION);
		//sSingleton = this;
	}

	@Override
	public DatabaseAdapter openConnection() throws SQLException {
		if (mDb == null) {
			mDb = sSingleton.getWritableDatabase();
		}
		return this;
	}

	public synchronized void closeConnection() {
		if (sSingleton != null) {
			sSingleton.close();
			mDb.close();
			sSingleton = null;
			mDb = null;
		}
	}

	// initial database load with dummy records
	
	@Override
	public void onCreate(SQLiteDatabase mDb) {
	/*	try {
			mDb.beginTransaction();
 			mDb.execSQL(DATABASE_CREATE_ITEMS);

			ContentValues cv_items = new ContentValues();

			int i=0;

			cv_items.put(ITEM_NAME, "BBC");
			cv_items.put(ITEM_LINK,"http://www.bbc.co.uk");
			cv_items.put(ITEM_POSITION, i);
 			cv_items.put(ITEM_IMAGE_NAME, "bbc");
			i++;mDb.insert(ITEM_TABLE, ITEM_NAME, cv_items);

			cv_items.put(ITEM_NAME, "CNN");
			cv_items.put(ITEM_LINK,"https://www.cnn.com/");
			cv_items.put(ITEM_POSITION, i);
 			cv_items.put(ITEM_IMAGE_NAME,"cnn");
			i++;mDb.insert(ITEM_TABLE, ITEM_NAME, cv_items);

			cv_items.put(ITEM_NAME, "Washington Post");
			cv_items.put(ITEM_LINK, "https://www.washingtonpost.com/");
			cv_items.put(ITEM_POSITION, i);
 			cv_items.put(ITEM_IMAGE_NAME, "wp");
			mDb.insert(ITEM_TABLE, ITEM_NAME, cv_items);
			mDb.setTransactionSuccessful();

		} finally {
			mDb.endTransaction();
		}*/
	}

	@Override
	public void onUpgrade(SQLiteDatabase mDb, int oldVersion, int newVersion) {
		// You probably shouldn't do the following in a production app,
		// but this is just a demo...
		mDb.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE);
		onCreate(mDb);
	}

	@Override
	public Cursor getAllItemRecords() {
		return mDb.query(ITEM_TABLE, new String[] { ITEM_KEY_ROWID, ITEM_NAME,
						ITEM_LINK, ITEM_POSITION, ITEM_IMAGE_NAME}, null, null, null, null,
				"item_position " + sort_order);
	}

	@Override
	public void changeLink(String name,String newLink) {
		ContentValues ItemArgs = new ContentValues();
		ItemArgs.put(ITEM_LINK, newLink);
		String where = ITEM_NAME +"=?";
		String[] whereArgs = new String[] {String.valueOf(name)};
		mDb.update(ITEM_TABLE, ItemArgs,where, whereArgs);
	}

	@Override
	public void changeName(String name,String newName) {
		ContentValues ItemArgs = new ContentValues();
		ItemArgs.put(ITEM_NAME, newName);
		String where = ITEM_NAME +"=?";
		String[] whereArgs = new String[] {String.valueOf(name)};
		mDb.update(ITEM_TABLE, ItemArgs,where, whereArgs);
	}

	@Override
	public void changeImage(String item_image) {
		ContentValues ItemArgs = new ContentValues();
		ItemArgs.put(ITEM_IMAGE_NAME, item_image);
		String where = ITEM_NAME +"=?";
		String[] whereArgs = new String[] {String.valueOf(item_image)};
		mDb.update(ITEM_TABLE, ItemArgs,where, whereArgs);
	}

	@Override
	public long insertItemRecord(String item_name, String item_details,String item_image, Context context) {
		int item_Position = getMaxColumnData();
		ContentValues initialItemValues = new ContentValues();
		int hoppalaValue = context.getResources().getIdentifier(item_name, "drawable", context.getPackageName());
 		try {
			item_image = context.getResources().getResourceName(hoppalaValue);
		}catch (Resources.NotFoundException e){
			item_image="newsdef";
		}

		initialItemValues.put(ITEM_NAME, item_name);
		initialItemValues.put(ITEM_LINK, item_details);
		initialItemValues.put(ITEM_IMAGE_NAME, item_image);
		initialItemValues.put(ITEM_POSITION, item_Position+1);
		return mDb.insert(ITEM_TABLE, null, initialItemValues);
	}

	@Override
	public boolean deleteItemRecord(long rowId) {
		return mDb.delete(ITEM_TABLE, ITEM_KEY_ROWID + "=" + rowId, null) > 0;
	}

	@Override
	public boolean deleteItemRecord(String itemName) {
		String[] whereArgs = new String[] {String.valueOf(itemName)};
		return mDb.delete(ITEM_TABLE, ITEM_NAME +"=?", whereArgs) > 0;
 	}

	@Override
	public boolean updateItemPosition(long rowId, Integer position) {
		ContentValues ItemArgs = new ContentValues();
		ItemArgs.put(ITEM_POSITION, position);
		return mDb.update(ITEM_TABLE, ItemArgs, ITEM_KEY_ROWID + "=" + rowId,
				null) > 0;
	}

	@Override
	public int getMaxColumnData() {
		final SQLiteStatement stmt = mDb
				.compileStatement("SELECT MAX(item_position) FROM item_table");
		return (int) stmt.simpleQueryForLong();
	}
}