# Cursor adapter with database

## Screenshot
![alt text](https://firebasestorage.googleapis.com/v0/b/uploadpic-a16bc.appspot.com/o/images%2Fss.png?alt=media&token=dccde96a-1776-4ab8-9c65-b482a0f82da8 "Screen Shot")

## Dependencies 
<pre>  repositories {
     maven { url 'https://jitpack.io' }
  }
  dependencies {
     implementation 'com.github.hasan5151:cursoradapter:2.3'
  }
 </pre>

## Usage
add ListView to your layout
 
       <ListView 
          android:id="@+id/listView"
          android:layout_width="match_parent"
          android:layout_height="match_parent">
        </ListView>
        
        
  
 

Activity
<pre>
  listView = findViewById(R.id.listView);

  mDbHelper = DatabaseAdapter.getInstance(MainActivity.this);
  mDbHelper.openConnection();
  
  // Add your db operations here. For sample look at below
 //  mDbHelper.insertItemRecord("Bbc","http://www.bbc.co.uk","bbc",this);
 
 ListViewAdapter mCursorAdapter = new ListViewAdapter(this, mDbHelper.getAllItemRecords(), 0,this); // last parameter for click listener
 listView.setAdapter(mCursorAdapter);
</pre>

Click Listener
<pre>
  @Override
    public void onClick(View view, String sitelink) {
        view.setOnClickListener(view1 -> 
                Toast.makeText(context,sitelink,Toast.LENGTH_LONG).show());
    }
</pre>

## Sample Db Operations
###### Add New Item
<pre>
         mDbHelper.insertItemRecord("Bbc","http://www.bbc.co.uk","bbc",this);
         // First parameter for name
         // Second parameter for link
         // Third parameter for image path. Put your image in 'drawable' folder.
</pre>

###### Change Item Name
<pre>
         mDbHelper.changeName("Bbc","British Broadcasting Corporation");
</pre>

###### Change Item Link
<pre>
         mDbHelper.changeLink("Bbc","http://www.bbc.com");
</pre>


###### Change Item Image
<pre>
         mDbHelper.changeImage("Bbc","newImage");
</pre>


###### Delete Item Record
<pre>
         mDbHelper.deleteItemRecord("Bbc");
</pre>
 

