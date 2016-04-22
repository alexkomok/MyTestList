/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mytestlist;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class WallpaperListAdapter extends BaseAdapter implements ListAdapter {
	private static final String LOG_TAG = "WallpaperListAdapter";
	private LayoutInflater mInflater;

    private Context mContext;
    private List<WallpaperTile> mWallpapers;
    
	WallpaperListAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mWallpapers = new ArrayList<WallpaperTile>();
		findWallpapers();
	}

    public int getCount() {
        if (mWallpapers == null) {
            return 0;
        }
        return mWallpapers.size();
    }

	public WallpaperTile getItem(int position) {
		return mWallpapers.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		if (convertView == null) {
			view = mInflater.inflate(R.layout.wallpaper_picker_wallpaper_item, parent,
					false);
		} else {
			view = convertView;
		}
        
		WallpaperTile wallpaperTile = mWallpapers.get(position);
        ImageView image = (ImageView) view.findViewById(R.id.wallpaper_image);


        if (wallpaperTile.getThumbnail() != null) {
            image.setImageDrawable(wallpaperTile.getThumbnail());
        }
        
        Drawable thumbDrawable = image.getDrawable();
        if (thumbDrawable != null) {
            thumbDrawable.setDither(true);
        } 
        
        String name = wallpaperTile.getFile().getName();
        int dotPos = name.lastIndexOf('.');
        if (dotPos >= -1) {
            name = WallpaperChangerHelper.capitalizeFirstLetter(name.substring(0, dotPos).replace("_small", "").replace("wallpaper_", ""));
        }

        TextView  label = (TextView) view.findViewById(R.id.wallpaper_item_label);
        label.setText(name);
        
		return view;
	}
	
/*    class WallpaperLoader extends AsyncTask<Integer, Void, Bitmap> {
        BitmapFactory.Options mOptions;
        private Context mContext;

        WallpaperLoader() {
            mOptions = new BitmapFactory.Options();
            mOptions.inDither = false;
            mOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            if (isCancelled()) return null;
            try {
                return BitmapFactory.decodeResource(mContext.getResources(),
                        mImages.get(params[0]), mOptions);
            } catch (OutOfMemoryError e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            if (b == null) return;

            if (!isCancelled() && !mOptions.mCancel) {
                // Help the GC
                if (mBitmap != null) {
                    mBitmap.recycle();
                }

                View v = ((Activity) mContext).getView();
                if (v != null) {
                    mBitmap = b;
                    mWallpaperDrawable.setBitmap(b);
                    v.postInvalidate();
                } else {
                    mBitmap = null;
                    mWallpaperDrawable.setBitmap(null);
                }
                mWallpaperDrawable.setBitmap(null);
                mLoader = null;
            } else {
               b.recycle();
            }
        }

        void cancel() {
            mOptions.requestCancelDecode();
            super.cancel(true);
        }
    }*/
    

/*    static class WallpaperDrawable extends Drawable {

        Bitmap mBitmap;
        int mIntrinsicWidth;
        int mIntrinsicHeight;

         package void setBitmap(Bitmap bitmap) {
            mBitmap = bitmap;
            if (mBitmap == null)
                return;
            mIntrinsicWidth = mBitmap.getWidth();
            mIntrinsicHeight = mBitmap.getHeight();
        }

        @Override
        public void draw(Canvas canvas) {
            if (mBitmap == null) return;
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            int x = (width - mIntrinsicWidth) / 2;
            int y = (height - mIntrinsicHeight) / 2;
            canvas.drawBitmap(mBitmap, x, y, null);
        }

        @Override
        public int getOpacity() {
            return android.graphics.PixelFormat.OPAQUE;
        }

        @Override
        public void setAlpha(int alpha) {
            // Ignore
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            // Ignore
        }
    }*/
    
    
    private void findWallpapers() {
        Resources resources = mContext.getResources();
		try {
			resources = mContext.getPackageManager().getResourcesForApplication(WallpaperChangerHelper.LAUNCHER2_PKG_NAME);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        // Context.getPackageName() may return the "original" package name,
        // com.android.launcher2; Resources needs the real package name,
        // com.android.launcher. So we ask Resources for what it thinks the
        // package name should be.
        int listWallpaperId = resources.getIdentifier("wallpapers", "array", WallpaperChangerHelper.LAUNCHER2_PKG_NAME);
        int listExtraWallpaperId = resources.getIdentifier("extra_wallpapers", "array", WallpaperChangerHelper.LAUNCHER2_PKG_NAME);
        addWallpapers(resources, WallpaperChangerHelper.LAUNCHER2_PKG_NAME, listWallpaperId);
        addWallpapers(resources, WallpaperChangerHelper.LAUNCHER2_PKG_NAME, listExtraWallpaperId);

    }


	private void addWallpapers(Resources resources, String packageName, int list) {
		final String[] extras = resources.getStringArray(list);
		for (String extra : extras) {
			int imageRes = resources.getIdentifier(extra, "drawable",
					packageName);
			if (imageRes != 0) {

				final int thumbRes = resources.getIdentifier(extra + "_small", "drawable", packageName);

				if (thumbRes != 0) {
					File thumbnailPath = null;
					Drawable thumbnail = null;
					try {
						thumbnailPath = new File(resources.getString(thumbRes));
						thumbnail = resources.getDrawable( thumbRes );
						WallpaperTile wallpaperTile = new WallpaperTile(thumbnailPath, thumbnail, null, imageRes, thumbRes, WallpaperTile.Type.system);
						mWallpapers.add(wallpaperTile);
						Log.d(LOG_TAG, "add: [" + packageName + "]: " + extra + " (" + imageRes + ")");
					} catch (NotFoundException e) {
						// skip
						e.printStackTrace();
					}

				}
			}
		}
	}
    
}
