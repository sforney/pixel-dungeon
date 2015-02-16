package com.watabou.pixeldungeon.levels.tiles.caves;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.HighGrassTile;

public class CavesHighGrass extends HighGrassTile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Caves_TileDescHighGrass);
	}

	@Override
	public String getName() {
		return Game.getVar(R.string.Caves_TileHighGrass);
	}	
}
