package com.forney.pixeldungeon.levels.tiles;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.watabou.pixeldungeon.levels.tiles.GrassTile;
import com.watabou.pixeldungeon.levels.tiles.Tile;
import com.watabou.pixeldungeon.levels.tiles.WallTile;
import com.watabou.pixeldungeon.levels.tiles.WaterTile;

@RunWith(JUnit4.class)
public class TileTest {
	@SuppressWarnings("unused")
	@Test
	public void testCreateTile() throws Exception {
		Tile tile = new WaterTile();
		tile = new GrassTile();
		tile = new WallTile();
	}
}
