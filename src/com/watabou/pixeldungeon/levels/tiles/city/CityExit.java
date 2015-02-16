package com.watabou.pixeldungeon.levels.tiles.city;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.ExitTile;

public class CityExit extends ExitTile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.City_TileDescExit);
	}
}
