package com.watabou.pixeldungeon.levels.tiles.halls;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.BookshelfTile;

public class HallsBookshelf extends BookshelfTile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Halls_TileDescBookshelf);
	}
}
