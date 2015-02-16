package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;

public class BarricadeTile extends Tile {
	public BarricadeTile() {
		passable = false;
	}
	
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Level_TileBarricade);
	}

	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileDescBarricade);
	}
}
