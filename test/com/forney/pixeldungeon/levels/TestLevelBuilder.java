package com.forney.pixeldungeon.levels;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.watabou.pixeldungeon.levels.LevelBuilder;
import com.watabou.pixeldungeon.levels.LevelRefactored;
import com.watabou.pixeldungeon.levels.tiles.Tile;
import com.watabou.pixeldungeon.levels.tiles.WallTile;

@RunWith(JUnit4.class)
public class TestLevelBuilder {
	@Test
	public void testCreateLevel() throws Exception{
		LevelRefactored level = new LevelRefactored();
		LevelBuilder builder = new LevelBuilder();
		//Need to figure out how to call methods that reference R
		//without hitting GLSurfaceView in Game
		level = builder.newLevel(5, true);
		
		Tile currentTile = null;
		Tile previousTile = null;
		for(Tile tile : level.map) {
			currentTile = tile;
			if(previousTile == null) {
				previousTile = tile;
			}
			assertTrue(tile.getClass().equals(WallTile.class));
			assertTrue(currentTile.equals(previousTile));
			previousTile = currentTile;
		}
	}
}
