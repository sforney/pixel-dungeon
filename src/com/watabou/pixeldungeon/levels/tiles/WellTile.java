package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;

public class WellTile extends Tile {
	public WellTile() {
		avoid = true;
	}

	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileWell);
	}
}
