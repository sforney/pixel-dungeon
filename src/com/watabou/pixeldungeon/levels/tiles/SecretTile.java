package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;

/**
 * Used for undetected traps and secret doors
 * @author Shawn
 *
 */
public class SecretTile extends Tile implements Secret {
	Tile discoveredTile; //the tile to replace this tile with when discovered
	
	public SecretTile(Tile discoveredTile) {
		this.discoveredTile = discoveredTile;
		secret = true;
	}

	@Override
	public Tile discover() {
		return discoveredTile;
	}

	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileFloor);
	}
}
