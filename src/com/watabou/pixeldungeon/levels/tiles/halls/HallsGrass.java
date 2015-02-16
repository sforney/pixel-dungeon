package com.watabou.pixeldungeon.levels.tiles.halls;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.GrassTile;

public class HallsGrass extends GrassTile {
	@Override
	public String getName() {
		return Game.getVar(R.string.Halls_TileGrass);
	}
}
