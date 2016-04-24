package com.example.mytestlist;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;

import com.example.mytestlist.WallpaperChangerHelper.SystemWallpapersStorage;


abstract class AbstractLiveWallpaperSetterActivity extends Activity {
	

	protected abstract LiveWallpaper getLiveWallpaper();
	protected abstract WallpaperChangerHelper.Weekday getDay();
	
    @SuppressLint("ServiceCast") @Override
	protected void onStart() {
		super.onStart();
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
		
		WallpaperManager manager = WallpaperManager.getInstance(this);
		Method method = null;

		try {
			method = WallpaperManager.class.getMethod("getIWallpaperManager", null);

			Object objIWallpaperManager = null;

			objIWallpaperManager = method.invoke(manager, null);

			Class[] param = new Class[1];
			param[0] = ComponentName.class;

			method = objIWallpaperManager.getClass().getMethod("setWallpaperComponent", param);

			Intent intent = new Intent(WallpaperService.SERVICE_INTERFACE);
			LiveWallpaper wallpaper = getLiveWallpaper();
			
			List<WallpaperTile> mallpaperList = new ArrayList<WallpaperTile>();
			for(SystemWallpapersStorage storage : SystemWallpapersStorage.values()){
				
				mallpaperList.addAll(WallpaperChangerHelper.getWallpaperTilesFromSystemWallpapers(this, storage));
			}
			
			boolean isSuccess = false;
			
			if (WallpaperChangerHelper.isLiveWallpaperValid(wallpaper, this)) {
				intent.setClassName(wallpaper.getPackageName(),wallpaper.getClassName());
				method.invoke(objIWallpaperManager, intent.getComponent());
				isSuccess = true;
			} else {
				
				WallpaperTile wallpaperTile = null;
				
				for(WallpaperTile tile : mallpaperList){
					if((tile.getThumbnailResId() + "").equals(wallpaper.getClassName())){
						wallpaperTile = tile;
						break;
					}
				}
				
				if (wallpaperTile != null){
			        try {
			            WallpaperManager wpm = (WallpaperManager) getSystemService(Context.WALLPAPER_SERVICE);
			            wpm.setResource(wallpaperTile.getImageResId());
			            isSuccess = true;
			        } catch (IOException e) {
						e.printStackTrace();
						ExceptionHandler.caughtException(e, this);
			        }
				}
				
				

			}
			
			if (isSuccess) {
				Handler mHandler = new Handler();
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						Intent homeIntent = new Intent(Intent.ACTION_MAIN);
						homeIntent.addCategory(Intent.CATEGORY_HOME);
						homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(homeIntent);
					}

				}, 100L);
			} else {
				Class activityClazz = WallpaperSelectionActivity.class;
				
				if(this instanceof RandomLiveWallpaperActivity){
					activityClazz = WallpaperSelectionActivity.class;
				}
				
				intent = new Intent(getApplicationContext(), activityClazz);
				intent.putExtra("msg", "test");
				startActivity(intent);
			}

			

			
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			ExceptionHandler.caughtException(e, this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			ExceptionHandler.caughtException(e, this);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			ExceptionHandler.caughtException(e, this);
		} /*catch (ClassNotFoundException e) {
			e.printStackTrace();
			ExceptionHandler.caughtException(e, this);
		}*/
		

		
		

	}
    

    	
}
