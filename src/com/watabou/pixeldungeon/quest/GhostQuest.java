package com.watabou.pixeldungeon.quest;

import com.watabou.pixeldungeon.Challenges;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.mobs.FetidRat;
import com.watabou.pixeldungeon.actors.mobs.npcs.Ghost;
import com.watabou.pixeldungeon.items.Generator;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.armor.Armor;
import com.watabou.pixeldungeon.items.armor.ClothArmor;
import com.watabou.pixeldungeon.items.quest.DriedRose;
import com.watabou.pixeldungeon.items.weapon.Weapon;
import com.watabou.pixeldungeon.items.weapon.missiles.MissileWeapon;
import com.watabou.pixeldungeon.journal.Feature;
import com.watabou.pixeldungeon.journal.Record;
import com.watabou.pixeldungeon.levels.SewerLevel;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class GhostQuest {

	public boolean spawned;
	public boolean alternative;
	public boolean given;
	public boolean processed;
	public int depth;
	public int left2kill;

	public Weapon weapon;
	public Armor armor;

	public GhostQuest() {
		spawned = false;

		weapon = null;
		armor = null;
	}

	private final String NODE = "sadGhost";

	private final String SPAWNED = "spawned";
	private final String ALTERNATIVE = "alternative";
	private final String LEFT2KILL = "left2kill";
	private final String GIVEN = "given";
	private final String PROCESSED = "processed";
	private final String DEPTH = "depth";
	private final String WEAPON = "weapon";
	private final String ARMOR = "armor";

	public void storeInBundle(Bundle bundle) {
		Bundle node = new Bundle();

		node.put(SPAWNED, spawned);

		if (spawned) {

			node.put(ALTERNATIVE, alternative);
			if (!alternative) {
				node.put(LEFT2KILL, left2kill);
			}

			node.put(GIVEN, given);
			node.put(DEPTH, depth);
			node.put(PROCESSED, processed);

			node.put(WEAPON, weapon);
			node.put(ARMOR, armor);
		}

		bundle.put(NODE, node);
	}

	public void restoreFromBundle(Bundle bundle) {
		Bundle node = bundle.getBundle(NODE);

		if (!node.isNull() && (spawned = node.getBoolean(SPAWNED))) {

			alternative = node.getBoolean(ALTERNATIVE);
			if (!alternative) {
				left2kill = node.getInt(LEFT2KILL);
			}

			given = node.getBoolean(GIVEN);
			depth = node.getInt(DEPTH);
			processed = node.getBoolean(PROCESSED);

			weapon = (Weapon) node.get(WEAPON);
			armor = (Armor) node.get(ARMOR);
		}
	}

	public void spawn(SewerLevel level) {
		if (!spawned && Dungeon.depth > 1 && Random.Int(5 - Dungeon.depth) == 0) {

			Ghost ghost = new Ghost(this);
			do {
				ghost.pos = level.randomRespawnCell();
			} while (ghost.pos == -1);
			level.mobs.add(ghost);
			Actor.occupyCell(ghost);

			spawned = true;
			alternative = Random.Int(2) == 0;
			if (!alternative) {
				left2kill = 8;
			}

			given = false;
			processed = false;
			depth = Dungeon.depth;

			for (int i = 0; i < 4; i++) {
				Item another;
				do {
					another = (Weapon) Generator
							.random(Generator.Category.WEAPON);
				} while (another instanceof MissileWeapon);

				if (weapon == null || another.level > weapon.level) {
					weapon = (Weapon) another;
				}
			}

			if (Dungeon.isChallenged(Challenges.NO_ARMOR)) {
				armor = (Armor) new ClothArmor().degrade();
			} else {
				armor = (Armor) Generator.random(Generator.Category.ARMOR);
				for (int i = 0; i < 3; i++) {
					Item another = Generator.random(Generator.Category.ARMOR);
					if (another.level > armor.level) {
						armor = (Armor) another;
					}
				}
			}

			weapon.identify();
			armor.identify();
		}
	}

	public void process(int pos) {
		if (spawned && given && !processed && (depth == Dungeon.depth)) {
			if (alternative) {

				FetidRat rat = new FetidRat();
				rat.pos = Dungeon.level.randomRespawnCell();
				if (rat.pos != -1) {
					GameScene.add(rat);
					processed = true;
				}

			} else {
				if (Random.Int(left2kill) == 0) {
					Dungeon.level.drop(new DriedRose(), pos).sprite.drop();
					processed = true;
				} else {
					left2kill--;
				}

			}
		}
	}

	public void complete() {
		weapon = null;
		armor = null;

		Dungeon.journal.remove(new Record(Feature.GHOST,
				Dungeon.depth));
	}
}
