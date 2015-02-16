package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.pixeldungeon.actors.Char;

public interface Trap {
	public void trigger(int position, Char ch);
}
