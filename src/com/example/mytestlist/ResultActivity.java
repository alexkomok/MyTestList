package com.example.mytestlist;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 
public class ResultActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
 
        Bundle b = getIntent().getExtras();
        String day = b.getString("day");
        
        Map<String, String> selectedWallpapersMap = LiveWallpaperChangerHelper.loadMap(this, day);
        
        ListView lv = (ListView) findViewById(R.id.outputList);
 
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, selectedWallpapersMap.keySet().toArray());
        lv.setAdapter(adapter);
    }
    
}
