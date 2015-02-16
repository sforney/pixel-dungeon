package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;

public class WaterTile extends Tile {
	public WaterTile() {
		liquid = true;
	}
	
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Level_TileDescWater);
	}

	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileWater);
	}
}
