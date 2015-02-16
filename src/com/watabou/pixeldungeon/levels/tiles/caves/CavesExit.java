package com.watabou.pixeldungeon.levels.tiles.caves;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.levels.tiles.ExitTile;

public class CavesExit extends ExitTile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Caves_TileDescExit);
	}
}
