package com.watabou.pixeldungeon.levels;

import java.util.ArrayList;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.Statistics;
import com.watabou.pixeldungeon.items.Generator;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.potions.PotionOfStrength;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfEnchantment;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.watabou.pixeldungeon.levels.LevelRefactored.Feeling;
import com.watabou.pixeldungeon.levels.tiles.ChasmTile;
import com.watabou.pixeldungeon.levels.tiles.TileFactory;
import com.watabou.pixeldungeon.levels.tiles.WallTile;
import com.watabou.utils.Random;

public class LevelBuilder {

	public LevelRefactored newLevel(int depth, boolean bossLevel) {
		LevelRefactored level = new LevelRefactored();
		ArrayList<Item> itemsToSpawn = new ArrayList<Item>();

		// Maybe this could be replaced with some sort of event system
		// I don't like calling Statistics directly
		Statistics.setCurrentFloor(depth);

		if (!bossLevel) {
			itemsToSpawn.add(Generator.random(Generator.Category.FOOD));
			if (Dungeon.posNeeded()) {
				itemsToSpawn.add(new PotionOfStrength());
				Dungeon.potionOfStrength++;
			}
			if (Dungeon.souNeeded()) {
				itemsToSpawn.add(new ScrollOfUpgrade());
				Dungeon.scrollsOfUpgrade++;
			}
			if (Dungeon.soeNeeded()) {
				itemsToSpawn.add(new ScrollOfEnchantment());
				Dungeon.scrollsOfEnchantment++;
			}

			if (depth > 1) {
				level.feeling = generateFeeling();
			}
		}
		
		//boolean pitNeeded = Dungeon.depth > 1 && weakFloorCreated;

		for(int i = 0; i < LevelRefactored.LENGTH; i++) {
			if(level.feeling == Feeling.CHASM) {
				level.map.add(TileFactory.createTile(ChasmTile.class));
			} else {
				level.map.add(TileFactory.createTile(WallTile.class));
			}
		}
		
		if(depth > 0 && depth < 6) {
			
		}
		
		layoutRooms(level);

		return level;
	}

	public Feeling generateFeeling() {
		switch (Random.Int(10)) {
		case 0:
			return Feeling.CHASM;
		case 1:
			return Feeling.WATER;
		case 2:
			return Feeling.GRASS;
		default:
			return Feeling.NONE;
		}
	}
	
	private void layoutRooms(LevelRefactored level) {
		int distance;
		int retry = 0;
	}
}
