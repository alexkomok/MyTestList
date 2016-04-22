package com.example.mytestlist;

import java.io.File;

import android.app.WallpaperInfo;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class WallpaperTile {

	enum Type {system, live}

	private File file;
	private Drawable thumbnail;
	private WallpaperInfo wallpaperInfo;
	private int thumbnailResId;
	private int imageResId;
	private Type type;

	public WallpaperTile(Drawable thumbnail, WallpaperInfo info, Intent intent, Type type) {
		this.thumbnail = thumbnail;
		this.wallpaperInfo = info;
		this.type = type;
	}
	
	public WallpaperTile(File file, Drawable thumbnail,
			WallpaperInfo wallpaperInfo, int thumbnailResId, int imageResId, Type type) {
		super();
		this.file = file;
		this.thumbnail = thumbnail;
		this.wallpaperInfo = wallpaperInfo;
		this.thumbnailResId = thumbnailResId;
		this.imageResId = imageResId;
		this.type = type;
	}

	public File getFile() {
		return file;
	}

	public Drawable getThumbnail() {
		return thumbnail;
	}

	public WallpaperInfo getWallpaperInfo() {
		return wallpaperInfo;
	}

	public int getThumbnailResId() {
		return thumbnailResId;
	}

	public int getImageResId() {
		return imageResId;
	}

	public Type getType() {
		return type;
	}
}
