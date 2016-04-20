package com.example.mytestlist;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
 
public class ResultActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
 
        Bundle b = getIntent().getExtras();
        String day = b.getString(LiveWallpaperChangerHelper.DAY);
        
    	Button mButton=(Button)findViewById(R.id.set_wallpaper);
        if(LiveWallpaperChangerHelper.Weekday.Random.name().equals(day)){
            mButton.setText(getResources().getString(R.string.set_rendom_wallpaper));
        } else {
        	mButton.setText(getResources().getString(R.string.set_wallpaper));
        } 
        
        Map<String, String> selectedWallpapersMap = LiveWallpaperChangerHelper.loadMap(this, day);
        
        ListView lv = (ListView) findViewById(R.id.outputList);
 
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, selectedWallpapersMap.keySet().toArray());
        lv.setAdapter(adapter);
    }
    
}
