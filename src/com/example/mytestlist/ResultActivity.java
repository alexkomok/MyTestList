package com.example.mytestlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
 
public class ResultActivity extends Activity implements OnClickListener {
	String day;
	Button set_wallpaper;
	ListView listView;
	SelectedWallpaperListAdapter mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        Bundle b = getIntent().getExtras();
        day = b.getString(WallpaperChangerHelper.DAY);
        
    	Button mButton=(Button)findViewById(R.id.set_wallpaper);
        if(WallpaperChangerHelper.Weekday.Random.name().equals(day)){
            mButton.setText(getResources().getString(R.string.set_rendom_wallpaper));
        } else {
        	mButton.setText(getResources().getString(R.string.set_wallpaper));
        } 
        
        mButton.setOnClickListener(this);
        
        listView = (ListView) findViewById(R.id.outputList);
		mAdapter = new SelectedWallpaperListAdapter(this, WallpaperSelectionActivity.selectedTilesList);

		listView.setAdapter(mAdapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
    }

	@Override
	public void onClick(View v) {
		
		if(WallpaperChangerHelper.Weekday.Random.name().equals(day)){
			startActivity( new Intent(this,  RandomLiveWallpaperActivity.class));
		} else if (WallpaperChangerHelper.Weekday.Monday.name().equals(day)) {
			startActivity( new Intent(this,  MondayLiveWallpaperActivity.class));
		}
		

		
	}
    
}
