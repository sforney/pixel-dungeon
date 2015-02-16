package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;

public class BookshelfTile extends Tile {
	@Override
	public String getDescription() {
		return "This string needs to be replaced";
	}

	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileBookshelf);
	}
}
