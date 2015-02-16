package com.watabou.pixeldungeon.levels.tiles.city;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.HighGrassTile;

public class CityHighGrass extends HighGrassTile {
	@Override
	public String getName() {
		return Game.getVar(R.string.City_TileHighGrass);
	}
}
