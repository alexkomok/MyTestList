package com.example.mytestlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LiveWallpaperChangerActivity extends Activity implements OnClickListener{
	
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
		
		
		LiveWallpaperChangerHelper.Weekday day = LiveWallpaperChangerHelper.Weekday.Random;
		
		switch (v.getId()) {
	      case R.id.random_button:
	    	  day = LiveWallpaperChangerHelper.Weekday.Random;
	        break;
	      case R.id.monday_button:
	    	  day = LiveWallpaperChangerHelper.Weekday.Monday;
	        break;
	      }
		
		Intent intent = new Intent(this, LiveWallpaperSelectionActivity.class);
		 
        // Create a bundle object
        Bundle b = new Bundle();
        b.putString("day", day.name());
 
        // Add the bundle to the intent.
        intent.putExtras(b);
 
        // start the ResultActivity
        startActivity(intent);
		
	}
}
