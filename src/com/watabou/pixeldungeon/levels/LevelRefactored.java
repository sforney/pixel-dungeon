package com.watabou.pixeldungeon.levels;


import java.util.ArrayList;

import com.watabou.pixeldungeon.Challenges;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.levels.tiles.Tile;
import com.watabou.pixeldungeon.levels.tiles.TileState;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class LevelRefactored implements Bundlable {
	public static enum Feeling {
		NONE, CHASM, WATER, GRASS
	};
	
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	public static final int LENGTH = WIDTH * HEIGHT;
	protected static final float TIME_TO_RESPAWN = 50;
	
	public ArrayList<Tile> map;
	public ArrayList<TileState> mapState;
	public ArrayList<Actor> actors;
	
	public Feeling feeling;
	
	public int viewDistance = Dungeon.isChallenged(Challenges.DARKNESS) ? 3 : 8;
	
	private static final String MAP = "map";
	private static final String VISITED = "visited";
	private static final String MAPPED = "mapped";
	private static final String ENTRANCE = "entrance";
	private static final String EXIT = "exit";
	private static final String HEAPS = "heaps";
	private static final String PLANTS = "plants";
	private static final String MOBS = "mobs";
	private static final String BLOBS = "blobs";
	
	public LevelRefactored() {
		map = new ArrayList<Tile>(LENGTH);
		mapState = new ArrayList<TileState>(LENGTH);
		actors = new ArrayList<Actor>(LENGTH);
		feeling = Feeling.NONE;
	}
	
	public void replaceTile(int pos, Tile tile) {
		map.set(pos, tile);
	}
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeInBundle(Bundle bundle) {
	}
	
	public int distance(int a, int b) {
		int ax = a % WIDTH;
		int ay = a / WIDTH;
		int bx = b % WIDTH;
		int by = b / WIDTH;
		return Math.max(Math.abs(ax - bx), Math.abs(ay - by));
	}
	
	public boolean adjacent(int a, int b) {
		int diff = Math.abs(a - b);
		return diff == 1 || diff == WIDTH || diff == WIDTH + 1
				|| diff == WIDTH - 1;
	}

}
