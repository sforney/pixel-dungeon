package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;

public abstract class Tile {
	boolean passable = true;
	boolean secret = false;
	boolean liquid = false;
	boolean pit = false;
	boolean avoid = true;
	boolean empty = true;
	boolean losBlocking = false;
	boolean flammable = false;
	
	public String getDescription() {
		return Game.getVar(R.string.WndInfoCell_Nothing);
	}
	
	public String getName() {
		return Game.getVar(R.string.Level_TileFloor);
	}
	
	public void processEntry(Char ch) {
		
	}
	
	public String toString() {
		return getName() + ": " + getDescription();
	}
	
	/*
	@Override
	public boolean equals(Object obj) {
		if(obj != null && this.getClass().equals(obj.getClass())) {
			return true;
		}
		return false;
	}*/
}
