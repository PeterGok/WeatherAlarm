package com.mahcks.weatheralarm;

import com.mahcks.weatheralarm.contentprovider.MyAlarmContentProvider;
import com.mahcks.weatheralarm.database.AlarmTable;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class WeatherAlarmOverview extends ListActivity 
	implements LoaderManager.LoaderCallbacks<Cursor> {
	
	// private Cursor cursor;
	private AlarmAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.alarm_list);
	    this.getListView().setDividerHeight(2);
	    fillData();
	    registerForContextMenu(getListView());
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { AlarmTable.COLUMN_ID, AlarmTable.COLUMN_NAME, 
								AlarmTable.COLUMN_TIME, AlarmTable.COLUMN_DAYS,
								AlarmTable.COLUMN_IS_SMART, AlarmTable.COLUMN_IS_CRES,
								AlarmTable.COLUMN_IS_SNOOZE, AlarmTable.COLUMN_VOLUME,
								AlarmTable.COLUMN_IS_ON};
	    CursorLoader cursorLoader = new CursorLoader(this,
	        MyAlarmContentProvider.CONTENT_URI, projection, null, null, null);
	    return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor data) {
		mAdapter.swapCursor(data);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// data is not available anymore, delete reference
	    mAdapter.swapCursor(null);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.alarm_list_menu, menu);
	    return true;
	}
	
	// Reaction to the menu selection
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.create_new_alarm:
	      createAlarm();
	      return true;
	    }
	    return super.onOptionsItemSelected(item);
	  }
	  
	  private void createAlarm() {
		  Intent i = new Intent(this, AlarmEditActivity.class);
		  startActivity(i);
	  }
	  
	// Opens the second activity if an entry is clicked
	  @Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    super.onListItemClick(l, v, position, id);
	    Intent i = new Intent(this, AlarmEditActivity.class);
	    Uri alarmUri = Uri.parse(MyAlarmContentProvider.CONTENT_URI + "/" + id);
	    i.putExtra(MyAlarmContentProvider.CONTENT_ITEM_TYPE, alarmUri);

	    startActivity(i);
	  }
	
	private void fillData() {

	    getLoaderManager().initLoader(0, null, this);
	    mAdapter = new AlarmAdapter(this, null, 0);

	    setListAdapter(mAdapter);
	 }

}
