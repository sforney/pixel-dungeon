package com.watabou.pixeldungeon.levels.tiles.caves;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.WaterTile;

public class CavesWaterTile extends WaterTile {
	@Override
	public String getName() {
		return Game.getVar(R.string.Caves_TileWater);
	}
}
