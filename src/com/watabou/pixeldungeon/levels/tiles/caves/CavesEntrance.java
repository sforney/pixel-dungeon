package com.watabou.pixeldungeon.levels.tiles.caves;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.EntranceTile;

public class CavesEntrance extends EntranceTile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Caves_TileDescEntrance);
	}
}
