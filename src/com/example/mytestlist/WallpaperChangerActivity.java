package com.example.mytestlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WallpaperChangerActivity extends Activity implements OnClickListener{
	
	Button random_button;
	Button monday_button;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewsById();

		random_button.setOnClickListener(this);
		monday_button.setOnClickListener(this);

	}
    
    private void findViewsById() {
        random_button = (Button) findViewById(R.id.random_button);
        monday_button = (Button) findViewById(R.id.monday_button);
    }

	@Override
	public void onClick(View v) {
		
		WallpaperChangerHelper.Weekday day = WallpaperChangerHelper.Weekday.Random;
		Intent intent = new Intent(this, WallpaperSelectionActivity.class);
		
		switch (v.getId()) {
	      case R.id.random_button:
	    	  day = WallpaperChangerHelper.Weekday.Random;
	        break;
	      case R.id.monday_button:
	    	  day = WallpaperChangerHelper.Weekday.Monday;
	        break;
	      }
		
		
        // Create a bundle object
        Bundle b = new Bundle();
        b.putString(WallpaperChangerHelper.DAY, day.name());
 
        // Add the bundle to the intent.
        intent.putExtras(b);
 
        // start the ResultActivity
        startActivity(intent);
		
	}
}
