package com.watabou.pixeldungeon.levels.tiles.sewers;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.BookshelfTile;

public class SewerBookshelf extends BookshelfTile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Sewer_TileDescBookshelf);
	}
}
