package com.example.mytestlist;

import java.io.File;

import android.app.WallpaperInfo;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;


// TODO refactor to separate to subclasses
public class WallpaperTile {

	enum Type {system, live, thirdparty, saved}

	private File file;
	private Drawable thumbnail;
	private WallpaperInfo wallpaperInfo;
	private int thumbnailResId;
	private int imageResId;
	private Type type;
	private ResolveInfo resolveInfo;

	public WallpaperTile(Drawable thumbnail, WallpaperInfo info, Intent intent, Type type) {
		super();
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
	
	public WallpaperTile(ResolveInfo resolveInfo, Type type) {
		super();
		this.type = type;
		this.resolveInfo = resolveInfo;
	}
	
	public WallpaperTile(File file, Drawable thumbnail,Type type) {
		super();
		this.thumbnail = thumbnail;
		this.file = file;
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

	public ResolveInfo getResolveInfo() {
		return resolveInfo;
	}
}
