package com.watabou.pixeldungeon.levels.tiles.city;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.WaterTile;

public class CityWater extends WaterTile {
	@Override
	public String getName() {
		return Game.getVar(R.string.City_TileWater);
	}
}
