package com.example.mytestlist;

public class LiveWallpaper {
	private String packageName;
	private String className;

	public LiveWallpaper(String className, String packageName) {
		super();
		this.packageName = packageName;
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

}
