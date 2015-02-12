package com.watabou.pixeldungeon;

import android.content.res.Resources;

public class TextStrings {
	public static String TXT_ROSE1;
	public static String TXT_ROSE2;
	public static String TXT_RAT1;
	public static String TXT_RAT2;
	
	public static void init(Resources res) {
		TXT_ROSE1 = res.getString(R.string.txt_rose1);
		TXT_ROSE2 = res.getString(R.string.txt_rose2);
		TXT_RAT1 = res.getString(R.string.txt_rat1);
		TXT_RAT2 = res.getString(R.string.txt_rat2);
	}
}
