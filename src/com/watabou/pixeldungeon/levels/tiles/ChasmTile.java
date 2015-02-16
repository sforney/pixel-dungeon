package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;

public class ChasmTile extends Tile {
	public ChasmTile() {
		avoid = true;
		pit = true;
	}
	
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Level_TileDescChasm);
	}

	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileChasm);
	}
}
