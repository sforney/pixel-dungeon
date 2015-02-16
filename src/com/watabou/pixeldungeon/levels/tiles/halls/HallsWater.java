package com.watabou.pixeldungeon.levels.tiles.halls;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.WaterTile;

public class HallsWater extends WaterTile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Halls_TileDescStatue);
	}

	@Override
	public String getName() {
		return Game.getVar(R.string.Halls_TileWater);
	}
}
