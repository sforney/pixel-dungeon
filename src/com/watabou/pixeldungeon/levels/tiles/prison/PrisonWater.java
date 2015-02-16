package com.watabou.pixeldungeon.levels.tiles.prison;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.WaterTile;

public class PrisonWater extends WaterTile {
	@Override
	public String getName() {
		return Game.getVar(R.string.Prison_TileWater);
	}
}
