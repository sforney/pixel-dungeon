package com.watabou.pixeldungeon.levels.tiles;

import java.util.HashSet;
import java.util.Set;

public class TileFactory {
	private static Set<Tile> tiles = new HashSet<Tile>();
	
	public static Tile createTile(Class<? extends Tile> clazz) {
		for(Tile tile : tiles) {
			if(tile.getClass().equals(clazz)) {
				return tile;
			}
		}
		try {
			Tile tile = (Tile)clazz.getConstructor().newInstance();
			tiles.add(tile);
			return tile;
		} catch (Exception e) {
			return null;
		}
	}
}
