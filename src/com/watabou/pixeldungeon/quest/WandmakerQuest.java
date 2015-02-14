package com.watabou.pixeldungeon.quest;

import java.util.ArrayList;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.mobs.npcs.Wandmaker;
import com.watabou.pixeldungeon.items.Heap;
import com.watabou.pixeldungeon.items.quest.CorpseDust;
import com.watabou.pixeldungeon.items.seeds.RotberrySeed;
import com.watabou.pixeldungeon.items.wands.Wand;
import com.watabou.pixeldungeon.items.wands.WandOfAmok;
import com.watabou.pixeldungeon.items.wands.WandOfAvalanche;
import com.watabou.pixeldungeon.items.wands.WandOfBlink;
import com.watabou.pixeldungeon.items.wands.WandOfDisintegration;
import com.watabou.pixeldungeon.items.wands.WandOfFirebolt;
import com.watabou.pixeldungeon.items.wands.WandOfLightning;
import com.watabou.pixeldungeon.items.wands.WandOfPoison;
import com.watabou.pixeldungeon.items.wands.WandOfRegrowth;
import com.watabou.pixeldungeon.items.wands.WandOfSlowness;
import com.watabou.pixeldungeon.items.wands.WandOfTelekinesis;
import com.watabou.pixeldungeon.journal.Feature;
import com.watabou.pixeldungeon.journal.Record;
import com.watabou.pixeldungeon.levels.PrisonLevel;
import com.watabou.pixeldungeon.levels.Room;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class WandmakerQuest {

	public boolean spawned;
	public boolean alternative;
	public boolean given;

	public Wand wand1;
	public Wand wand2;

	public WandmakerQuest() {
		spawned = false;

		wand1 = null;
		wand2 = null;
	}

	private static final String NODE = "wandmaker";

	private static final String SPAWNED = "spawned";
	private static final String ALTERNATIVE = "alternative";
	private static final String GIVEN = "given";
	private static final String WAND1 = "wand1";
	private static final String WAND2 = "wand2";

	public void storeInBundle(Bundle bundle) {
		Bundle node = new Bundle();
		node.put(SPAWNED, spawned);
		if (spawned) {
			node.put(ALTERNATIVE, alternative);
			node.put(GIVEN, given);
			node.put(WAND1, wand1);
			node.put(WAND2, wand2);
		}
		bundle.put(NODE, node);
	}

	public void restoreFromBundle(Bundle bundle) {
		Bundle node = bundle.getBundle(NODE);
		if (!node.isNull() && (spawned = node.getBoolean(SPAWNED))) {
			alternative = node.getBoolean(ALTERNATIVE);
			given = node.getBoolean(GIVEN);
			wand1 = (Wand) node.get(WAND1);
			wand2 = (Wand) node.get(WAND2);
		}
	}

	public void spawn(PrisonLevel level, Room room) {
		if (!spawned && Dungeon.depth > 6
				&& Random.Int(10 - Dungeon.depth) == 0) {

			Wandmaker npc = new Wandmaker(this);
			do {
				npc.pos = room.random();
			} while (level.map[npc.pos] == Terrain.ENTRANCE
					|| level.map[npc.pos] == Terrain.SIGN);
			level.mobs.add(npc);
			Actor.occupyCell(npc);

			spawned = true;
			alternative = Random.Int(2) == 0;

			given = false;

			switch (Random.Int(5)) {
			case 0:
				wand1 = new WandOfAvalanche();
				break;
			case 1:
				wand1 = new WandOfDisintegration();
				break;
			case 2:
				wand1 = new WandOfFirebolt();
				break;
			case 3:
				wand1 = new WandOfLightning();
				break;
			case 4:
				wand1 = new WandOfPoison();
				break;
			}
			wand1.random().upgrade();

			switch (Random.Int(5)) {
			case 0:
				wand2 = new WandOfAmok();
				break;
			case 1:
				wand2 = new WandOfBlink();
				break;
			case 2:
				wand2 = new WandOfRegrowth();
				break;
			case 3:
				wand2 = new WandOfSlowness();
				break;
			case 4:
				wand2 = new WandOfTelekinesis();
				break;
			}
			wand2.random().upgrade();
		}
	}

	public void placeItem() {
		if (alternative) {

			ArrayList<Heap> candidates = new ArrayList<Heap>();
			for (Heap heap : Dungeon.level.heaps.values()) {
				if (heap.type == Heap.Type.SKELETON
						&& !Dungeon.visible[heap.pos]) {
					candidates.add(heap);
				}
			}

			if (candidates.size() > 0) {
				Random.element(candidates).drop(new CorpseDust());
			} else {
				int pos = Dungeon.level.randomRespawnCell();
				while (Dungeon.level.heaps.get(pos) != null) {
					pos = Dungeon.level.randomRespawnCell();
				}

				Heap heap = Dungeon.level.drop(new CorpseDust(), pos);
				heap.type = Heap.Type.SKELETON;
				heap.sprite.link();
			}

		} else {

			int shrubPos = Dungeon.level.randomRespawnCell();
			while (Dungeon.level.heaps.get(shrubPos) != null) {
				shrubPos = Dungeon.level.randomRespawnCell();
			}
			Dungeon.level.plant(new RotberrySeed(), shrubPos);

		}
	}

	public void complete() {
		wand1 = null;
		wand2 = null;

		Dungeon.journal.remove(new Record(Feature.WANDMAKER,
				Dungeon.depth));
	}
}
