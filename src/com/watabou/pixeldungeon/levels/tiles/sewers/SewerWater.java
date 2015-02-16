package com.watabou.pixeldungeon.levels.tiles.sewers;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.WaterTile;

public class SewerWater extends WaterTile {
	@Override
	public String getName() {
		return Game.getVar(R.string.Sewer_TileWater);
	}
}
