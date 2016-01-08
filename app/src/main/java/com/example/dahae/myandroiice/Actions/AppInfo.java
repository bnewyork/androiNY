package com.example.dahae.myandroiice.Actions;

import java.text.Collator;
import java.util.Comparator;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

public class AppInfo {

	public Drawable mIcon = null;
	public String mAppNaem = null;
	public String mAppPackge = null;

	public static final Comparator<AppInfo> ALPHA_COMPARATOR = new Comparator<AppInfo>() {
		private final Collator sCollator = Collator.getInstance();

		@Override
		public int compare(AppInfo object1, AppInfo object2) {
			return sCollator.compare(object1.mAppNaem, object2.mAppNaem);
		}
	};
}
