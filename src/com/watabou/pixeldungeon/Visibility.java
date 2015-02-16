package com.watabou.pixeldungeon;

import java.util.Arrays;

import com.watabou.pixeldungeon.levels.Level;

public class Visibility {
	private boolean[] visible = new boolean[Level.LENGTH];
	
	public void clearVisibility() {
		Arrays.fill(visible, false);
	}
	
	public boolean isVisible(int pos) {
		return visible[pos];
	}
}
