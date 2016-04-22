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

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;


public class GalleryWallpaperListAdapter extends BaseAdapter implements ListAdapter {

	public static final int IMAGE_PICK = 5;
	private final LayoutInflater mInflater;
    private final Context mContext;
    private OnClickListener mThumbnailOnClickListener;

    private List<WallpaperTile> galleryWallpapers =
            new ArrayList<WallpaperTile>();
/*
    public static class ThirdPartyWallpaperTile extends WallpaperPickerActivity.WallpaperTileInfo {
        private ResolveInfo mResolveInfo;
        public ThirdPartyWallpaperTile(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
        }
        @Override
        public void onClick(WallpaperPickerActivity a) {
            final ComponentName itemComponentName = new ComponentName(
                    mResolveInfo.activityInfo.packageName, mResolveInfo.activityInfo.name);
            Intent launchIntent = new Intent(Intent.ACTION_SET_WALLPAPER);
            launchIntent.setComponent(itemComponentName);
            a.startActivityForResultSafely(
                    launchIntent, WallpaperPickerActivity.PICK_WALLPAPER_THIRD_PARTY_ACTIVITY);
        }
    }*/

    public GalleryWallpaperListAdapter(Context context) {
    	mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        

        mThumbnailOnClickListener = new OnClickListener() {
            public void onClick(View v) {
            	((WallpaperSelectionActivity)v.getContext()).onClick();
            }
        };
        
        WallpaperTile wallpaperTile = new WallpaperTile(null, WallpaperTile.Type.thirdparty);
        
        
        galleryWallpapers.add(wallpaperTile);

        // Get list of image picker intents
 /*       Intent pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickImageIntent.setType("image/*");
        final List<ResolveInfo> imagePickerActivities =
                pm.queryIntentActivities(pickImageIntent, 0);
        final ComponentName[] imageActivities = new ComponentName[imagePickerActivities.size()];
        for (int i = 0; i < imagePickerActivities.size(); i++) {
            ActivityInfo activityInfo = imagePickerActivities.get(i).activityInfo;
            imageActivities[i] = new ComponentName(activityInfo.packageName, activityInfo.name);
        }

        outerLoop:
        for (ResolveInfo info : apps) {
            final ComponentName itemComponentName =
                    new ComponentName(info.activityInfo.packageName, info.activityInfo.name);
            final String itemPackageName = itemComponentName.getPackageName();
            // Exclude anything from our own package, and the old Launcher,
            // and live wallpaper picker
            if (itemPackageName.equals(context.getPackageName()) ||
                    itemPackageName.equals("com.android.launcher") ||
                    itemPackageName.equals("com.android.wallpaper.livepicker")) {
                continue;
            }
            // Exclude any package that already responds to the image picker intent
            for (ResolveInfo imagePickerActivityInfo : imagePickerActivities) {
                if (itemPackageName.equals(
                        imagePickerActivityInfo.activityInfo.packageName)) {
                    continue outerLoop;
                }
            }
            galleryWallpapers.add(new WallpaperTile(info, WallpaperTile.Type.thirdparty));
        }*/
    }

    public int getCount() {
        return galleryWallpapers.size();
    }

    public WallpaperTile getItem(int position) {
        return galleryWallpapers.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.wallpaper_picker_image_item, parent, false);
        } else {
            view = convertView;
        }

        // Make its background the last photo taken on external storage
        Bitmap lastPhoto = getThumbnailOfLastPhoto();
        if (lastPhoto != null) {
            ImageView galleryThumbnailBg =
                    (ImageView) view.findViewById(R.id.wallpaper_image);
            galleryThumbnailBg.setImageBitmap(getThumbnailOfLastPhoto());
            int colorOverlay = mContext.getResources().getColor(R.color.wallpaper_picker_translucent_gray);
            galleryThumbnailBg.setColorFilter(colorOverlay, PorterDuff.Mode.SRC_ATOP);

        }
        view.setOnClickListener(mThumbnailOnClickListener);
        return view;
    }
    

    
    protected Bitmap getThumbnailOfLastPhoto() {
        Cursor cursor = MediaStore.Images.Media.query(mContext.getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.ImageColumns._ID,
                    MediaStore.Images.ImageColumns.DATE_TAKEN},
                null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC LIMIT 1");

        Bitmap thumb = null;
        if (cursor != null) {
            if (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                thumb = MediaStore.Images.Thumbnails.getThumbnail(mContext.getContentResolver(),
                        id, MediaStore.Images.Thumbnails.MINI_KIND, null);
            }
            cursor.close();
        }
        return thumb;
    }
}
