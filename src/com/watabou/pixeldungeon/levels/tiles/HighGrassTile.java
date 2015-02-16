package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;

public class HighGrassTile extends Tile {
	public HighGrassTile() {
		losBlocking = true;
	}
	
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Level_TileDescHighGrass);
		
	}

	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileHighGrass);
	}
}
