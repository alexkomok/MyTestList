package com.example.mytestlist;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
 
public class ResultActivity extends Activity implements OnClickListener {
	String day;
	Button set_wallpaper;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
 
        Bundle b = getIntent().getExtras();
        day = b.getString(WallpaperChangerHelper.DAY);
        
    	Button mButton=(Button)findViewById(R.id.set_wallpaper);
        if(WallpaperChangerHelper.Weekday.Random.name().equals(day)){
            mButton.setText(getResources().getString(R.string.set_rendom_wallpaper));
        } else {
        	mButton.setText(getResources().getString(R.string.set_wallpaper));
        } 
        
        mButton.setOnClickListener(this);
        
        Map<String, String> selectedWallpapersMap = WallpaperChangerHelper.loadMap(this, day);
        
        ListView lv = (ListView) findViewById(R.id.outputList);
 
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, selectedWallpapersMap.keySet().toArray());
        lv.setAdapter(adapter);
    }

	@Override
	public void onClick(View v) {
		
		if(WallpaperChangerHelper.Weekday.Random.name().equals(day)){
			startActivity( new Intent(getApplicationContext(),  RandomLiveWallpaperActivity.class));
		} else if (WallpaperChangerHelper.Weekday.Monday.name().equals(day)) {
			startActivity( new Intent(getApplicationContext(),  MondayLiveWallpaperActivity.class));
		}
		

		
	}
    
}
