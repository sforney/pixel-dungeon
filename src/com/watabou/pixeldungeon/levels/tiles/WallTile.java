package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;

public class WallTile extends Tile {
	public WallTile() {
		passable = false;
		losBlocking = true;
	}
	
	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileWall);
	}
	
	/*
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass().equals(WallTile.class)) {
			return true;
		} else {
			return false;
		}
	}*/
}
