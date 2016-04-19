package com.example.mytestlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.mytestlist.LiveWallpaperChangerHelper.Weekday;

public class RandomLiveWallpaperActivity extends AbstractLiveWallpaperSetterActivity {

	@Override
	protected LiveWallpaper getLiveWallpaper() {
		Map<String, String> selectedWallpapersMap = LiveWallpaperChangerHelper.loadMap(getApplicationContext());

		Random random = new Random();
		List<String> keys = new ArrayList<String>(selectedWallpapersMap.keySet());
		String className = keys.get(random.nextInt(keys.size()));
		String packageName = selectedWallpapersMap.get(className);

		return new LiveWallpaper(className, packageName);
	}

	@Override
	protected Weekday getDay() {
		return LiveWallpaperChangerHelper.Weekday.Random;
	}

}
