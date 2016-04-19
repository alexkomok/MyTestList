package com.example.mytestlist;

import com.example.mytestlist.LiveWallpaperChangerHelper.Weekday;


public class MondayLiveWallpaperActivity extends AbstractLiveWallpaperSetterActivity {

	@Override
	protected LiveWallpaper getLiveWallpaper() {
		return  LiveWallpaperChangerHelper.loadLiveWallpaper(getApplicationContext(), getDay());
	}

	@Override
	protected Weekday getDay() {
		return LiveWallpaperChangerHelper.Weekday.Monday;
	}

}
