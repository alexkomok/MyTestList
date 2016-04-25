package com.example.mytestlist;

import com.example.mytestlist.WallpaperChangerHelper.Weekday;


public class MondayLiveWallpaperActivity extends AbstractLiveWallpaperSetterActivity {

	@Override
	protected LiveWallpaper getLiveWallpaper() {
		return  WallpaperChangerHelper.loadLiveWallpaper(this, getDay());
	}

	@Override
	protected Weekday getDay() {
		return WallpaperChangerHelper.Weekday.Monday;
	}

}
