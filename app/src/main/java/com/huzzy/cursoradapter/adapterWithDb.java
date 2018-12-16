package com.huzzy.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.huzzy.cursoradapter.mInterface.clickListener;

public class adapterWithDb implements clickListener {

    Context context;
    public adapterWithDb(Context context, ListView listView,DatabaseAdapter mDbHelper){
        this.context=context;
        Cursor cursor = mDbHelper.getAllItemRecords();
        listViewAdapter mCursorAdapter = new listViewAdapter(context, cursor, 0,this);
        listView.setAdapter(mCursorAdapter);
        mCursorAdapter.changeCursor(cursor);
    }

    @Override
    public void onClick(View view, final String sitelink) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, sitelink,Toast.LENGTH_LONG).show();
            }
        });
    }
}
