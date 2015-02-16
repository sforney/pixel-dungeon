package com.watabou.pixeldungeon.levels.tiles.prison;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.BookshelfTile;

public class PrisonBookshelf extends BookshelfTile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Prison_TileDescBookshelf);
	}
}
