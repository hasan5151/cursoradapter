package com.huzzy.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.huzzy.cursoradapter.mInterface.ClickListener;

/**
 * Created by huzey on 28.08.2016.
 */
public class ListViewAdapter extends ResourceCursorAdapter {
    private ClickListener mClickListener;
    private Context context;
    public ListViewAdapter(Context context, Cursor cursor, int flags, ClickListener mClickListener) {
        super(context,  R.layout.list_items, cursor, flags);
        this.mClickListener=mClickListener;
        this.context=context;
    }

    public ListViewAdapter(Context context, int layout, Cursor cursor, int flags, ClickListener mClickListener) {
        super(context, layout, cursor, flags);
        this.mClickListener=mClickListener;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        setImage(view,cursor.getString(cursor.getColumnIndex(DatabaseAdapter.ITEM_IMAGE_NAME)));
        setText(view,cursor.getString(cursor.getColumnIndex(DatabaseAdapter.ITEM_NAME)));
        mClickListener.onClick(view,cursor.getString(cursor.getColumnIndex(DatabaseAdapter.ITEM_LINK)));
    }

    private void setText(View view, String name) {
        ((TextView) view.findViewById(R.id.item_name)).setText(name);
    }

    private void setImage(View view, String item_image_name) {
        ImageView imageView =  view.findViewById(R.id.item_image);
         if (!TextUtils.isDigitsOnly(item_image_name)){
            int a = context.getResources().getIdentifier(item_image_name, "drawable", context.getPackageName());
            imageView.setImageResource(a);
        }
        else{
            int a = context.getResources().getIdentifier("newsdef", "drawable", context.getPackageName());
            imageView.setImageResource(a);
        }
    }
}
