package com.watabou.pixeldungeon.levels.tiles.halls;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.HighGrassTile;

public class HallsHighGrass extends HighGrassTile {
	@Override
	public String getName() {
		return Game.getVar(R.string.Halls_TileHighGrass);
	}
}
