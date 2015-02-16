package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;

public class SignTile extends Tile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Level_TileDescSign);
	}

	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileSign);
	}
}
