package com.watabou.pixeldungeon.levels.tiles.city;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.EntranceTile;

public class CityEntrance extends EntranceTile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.City_TileDescEntrance);
	}
}
