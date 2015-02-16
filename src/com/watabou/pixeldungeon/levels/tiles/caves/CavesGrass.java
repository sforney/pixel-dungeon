package com.watabou.pixeldungeon.levels.tiles.caves;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.GrassTile;

public class CavesGrass extends GrassTile {
	@Override
	public String getName() {
		return Game.getVar(R.string.Caves_TileGrass);
	}
}
