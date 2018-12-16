package com.huzzy.cursoradapter.mInterface;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.huzzy.cursoradapter.DatabaseAdapter;

public interface dbInterface{
    DatabaseAdapter openConnection() throws SQLException;
    Cursor getAllItemRecords();
    void changeLink(String name,String newLink);
    void changeName(String name,String newName);
    void changeImage(String item_image);
    long insertItemRecord(String item_name, String item_details,String item_image, Context context);
    boolean deleteItemRecord(long rowId);
    boolean deleteItemRecord(String itemName);
    boolean updateItemPosition(long rowId, Integer position);
    int getMaxColumnData();
}
