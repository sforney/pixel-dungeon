package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;

public class SecretDoorTile extends SecretTile {

	public SecretDoorTile(Tile discoveredTile) {
		super(discoveredTile);
	}

	@Override
	public String getDescription() {
		return Game.getVar(R.string.Level_TileWall);
	}
}
