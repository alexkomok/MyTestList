package com.example.mytestlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.app.WallpaperInfo;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
 
public class WallpaperSelectionActivity extends ListActivity implements
        OnClickListener {
	
    Button button;
    ListView listView;
    CheckBox checkBox;
    WallpaperListAdapter wallpaperListAdapter;
    LiveWallpaperListAdapter liveWallpaperListAdapter;
    ThirdPartyWallpaperListAdapter thirdPartyWallpaperListAdapter;
    GalleryWallpaperListAdapter galleryWallpaperListAdapter;
    MergeAdapter mergeAdapter;
    String day;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_activity);
		findViewsById();
		
		wallpaperListAdapter = new WallpaperListAdapter(this);
		liveWallpaperListAdapter = new LiveWallpaperListAdapter(this);
		thirdPartyWallpaperListAdapter = new ThirdPartyWallpaperListAdapter(this);
		galleryWallpaperListAdapter = new GalleryWallpaperListAdapter(this);
		mergeAdapter = new MergeAdapter();

		mergeAdapter.addAdapter(galleryWallpaperListAdapter);
		mergeAdapter.addAdapter(new ListTitleAdapter(this, getString(R.string.wallpapers), wallpaperListAdapter));
		mergeAdapter.addAdapter(wallpaperListAdapter);		
		mergeAdapter.addAdapter(new ListTitleAdapter(this, getString(R.string.live_wallpapers), liveWallpaperListAdapter));
		mergeAdapter.addAdapter(liveWallpaperListAdapter);
		//mergeAdapter.addAdapter(new ListTitleAdapter(this, getString(R.string.live_wallpapers), liveWallpaperListAdapter));
		//mergeAdapter.addAdapter(thirdPartyWallpaperListAdapter);
		
		
		
		listView.setAdapter(mergeAdapter);
		button.setOnClickListener(this);
		
	}
    
    public void onStart(){
    	super.onStart();
        Bundle b = getIntent().getExtras();
        day = b.getString(WallpaperChangerHelper.DAY);
        checkBox.setChecked(false);
        
        if(WallpaperChangerHelper.Weekday.Random.name().equals(day)){
        	listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        	checkBox.setVisibility(View.VISIBLE);
        } else {
        	listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        	checkBox.setVisibility(View.GONE);
        }
        
    }
    
    public void setItemChecked(){
    	
        Map<String, String> selectedWallpapersMap = WallpaperChangerHelper.loadMap(this, day);
		for (int i = 0; i < listView.getAdapter().getCount(); i++) {
			if (listView.getItemAtPosition(i) instanceof WallpaperTile) {
				WallpaperTile lwp = (WallpaperTile) listView.getItemAtPosition(i);
				if (WallpaperTile.Type.live.equals(lwp.getType()) && selectedWallpapersMap.containsKey(lwp.getWallpaperInfo().getServiceName())) {
					listView.setItemChecked(i, true);
				} else if (WallpaperTile.Type.system.equals(lwp.getType()) && selectedWallpapersMap.containsKey("" + lwp.getThumbnailResId())) {
					listView.setItemChecked(i, true);
				}
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
        	Toast.makeText(this,  getString(R.string.select_one), Toast.LENGTH_LONG).show();
        	return;
        }
        
        int wallpaperListShift = galleryWallpaperListAdapter.getCount() + 1;
        int liveWallpaperListShift = wallpaperListAdapter.getCount() + 1 + wallpaperListShift;
        
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            Object item = mergeAdapter.getItem(position);
            if (checked.valueAt(i) && item instanceof WallpaperTile) {
            	
            	if(WallpaperTile.Type.live.equals(((WallpaperTile)item).getType())){
                	WallpaperInfo info = liveWallpaperListAdapter.getItem(position - liveWallpaperListShift).getWallpaperInfo();
                    selectedItems.add(info.getServiceName());
                    selectedWallpapersMap.put(info.getServiceName(), info.getPackageName());
            	} else  if((WallpaperTile.Type.system.equals(((WallpaperTile)item).getType()))){
            		WallpaperTile wallpaperTile = wallpaperListAdapter.getItem(position - wallpaperListShift);
            		selectedWallpapersMap.put("" + wallpaperTile.getThumbnailResId(), "" + wallpaperTile.getImageResId());
            	}
            	

            }
        }
        
        WallpaperChangerHelper.saveMap(selectedWallpapersMap, this, day);
 
        Intent intent = new Intent(getApplicationContext(),
                ResultActivity.class);
 
        // Create a bundle object
        Bundle b = new Bundle();
        b.putString(WallpaperChangerHelper.DAY, day);
 
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
	
    public void onClick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        this.startActivityForResult(intent, 5);
    }
	
}
