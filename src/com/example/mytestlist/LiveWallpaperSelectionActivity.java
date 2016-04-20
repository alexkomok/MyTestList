package com.example.mytestlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
 
public class LiveWallpaperSelectionActivity extends ListActivity implements
        OnClickListener {
	
    Button button;
    ListView listView;
    CheckBox checkBox;
    LiveWallpaperListAdapter adapter;
    String day;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_activity);
		findViewsById();
		
		adapter = new LiveWallpaperListAdapter(this);
		listView.setAdapter(adapter);
		button.setOnClickListener(this);
		
	}
    
    public void onStart(){
    	super.onStart();
        Bundle b = getIntent().getExtras();
        day = b.getString(LiveWallpaperChangerHelper.DAY);
        checkBox.setChecked(false);
        
        if(LiveWallpaperChangerHelper.Weekday.Random.name().equals(day)){
        	listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        	checkBox.setVisibility(View.VISIBLE);
        } else {
        	listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        	checkBox.setVisibility(View.GONE);
        }
        
    }
    
    public void onResume(){
    	super.onResume();
    	
        Map<String, String> selectedWallpapersMap = LiveWallpaperChangerHelper.loadMap(this, day);
		for (int i = 0; i < listView.getAdapter().getCount(); i++) {
			if(selectedWallpapersMap.containsKey(listView.getItemAtPosition(i))){
				listView.setItemChecked(i, true);
			}
			
		}
    }
 
    private void findViewsById() {
        listView = (ListView) findViewById(android.R.id.list);
        button = (Button) findViewById(R.id.testbutton);
        checkBox = (CheckBox) findViewById(R.id.select_all);
    }
 
    public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        Map<String, String> selectedWallpapersMap = new HashMap<String, String>();
        
        if(checked.size() == 0){
        	Toast.makeText(this, "Please, select one.", Toast.LENGTH_LONG).show();
        	return;
        }
        
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            if (checked.valueAt(i)) {
                selectedItems.add(adapter.getItem(position).mInfo.getServiceName());
                selectedWallpapersMap.put(adapter.getItem(position).mInfo.getServiceName(), adapter.getItem(position).mInfo.getPackageName());
            }
        }
        
        LiveWallpaperChangerHelper.saveMap(selectedWallpapersMap, this, day);
 
        Intent intent = new Intent(getApplicationContext(),
                ResultActivity.class);
 
        // Create a bundle object
        Bundle b = new Bundle();
        b.putString(LiveWallpaperChangerHelper.DAY, day);
 
        // Add the bundle to the intent.
        intent.putExtras(b);
 
        // start the ResultActivity
        startActivity(intent);
    }
    
	public void onCheckboxClicked(View view) {
		// Is the view now checked?
		boolean checked = ((CheckBox) view).isChecked();

		// Check which checkbox was clicked
		switch (view.getId()) {
		case R.id.select_all:
			if (checked) {
				for (int i = 0; i < listView.getAdapter().getCount(); i++) {
					listView.setItemChecked(i, true);
				}
			} else {
				for (int i = 0; i < listView.getAdapter().getCount(); i++) {
					listView.setItemChecked(i, false);
				}
			}
			break;

		}
	}
	
}
