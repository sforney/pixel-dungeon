package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.utils.GLog;

public class SecretTrapTile extends SecretTile {
	public SecretTrapTile(Tile discoveredTile) {
		super(discoveredTile);
	}

	@Override
	public String getDescription() {
		return Game.getVar(R.string.Level_TileFloor);
	}

	public void trigger() {
		GLog.i(Game.getVar(R.string.Level_HiddenPlate));
	}
}
