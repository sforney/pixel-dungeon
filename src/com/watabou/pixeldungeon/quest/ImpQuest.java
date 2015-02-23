package com.watabou.pixeldungeon.quest;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.mobs.Golem;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.actors.mobs.Monk;
import com.watabou.pixeldungeon.actors.mobs.npcs.Imp;
import com.watabou.pixeldungeon.items.Generator;
import com.watabou.pixeldungeon.items.quest.DwarfToken;
import com.watabou.pixeldungeon.items.rings.Ring;
import com.watabou.pixeldungeon.journal.Feature;
import com.watabou.pixeldungeon.journal.Record;
import com.watabou.pixeldungeon.levels.CityLevel;
import com.watabou.pixeldungeon.levels.Room;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class ImpQuest {
	private boolean alternative;
	private boolean spawned;
	private boolean given;
	private boolean completed;

	public Ring reward;

	public ImpQuest() {
		spawned = false;
		reward = null;
	}

	private static final String NODE = "demon";
	private static final String ALTERNATIVE = "alternative";
	private static final String SPAWNED = "spawned";
	private static final String GIVEN = "given";
	private static final String COMPLETED = "completed";
	private static final String REWARD = "reward";

	public void storeInBundle(Bundle bundle) {
		Bundle node = new Bundle();

		node.put(SPAWNED, spawned);

		if (spawned) {
			node.put(ALTERNATIVE, alternative);

			node.put(GIVEN, given);
			node.put(COMPLETED, completed);
			node.put(REWARD, reward);
		}

		bundle.put(NODE, node);
	}

	public void restoreFromBundle(Bundle bundle) {
		Bundle node = bundle.getBundle(NODE);

		if (!node.isNull() && (spawned = node.getBoolean(SPAWNED))) {
			alternative = node.getBoolean(ALTERNATIVE);

			given = node.getBoolean(GIVEN);
			completed = node.getBoolean(COMPLETED);
			reward = (Ring) node.get(REWARD);
		}
	}

	public void spawn(CityLevel level, Room room) {
		if (!spawned && Dungeon.depth > 16
				&& Random.Int(20 - Dungeon.depth) == 0) {

			Imp npc = new Imp(this);
			do {
				npc.pos = level.randomRespawnCell();
			} while (npc.pos == -1 || level.heaps.get(npc.pos) != null);
			level.mobs.add(npc);
			npc.occupyCell();

			spawned = true;
			alternative = Random.Int(2) == 0;

			given = false;

			do {
				reward = (Ring) Generator.random(Generator.Category.RING);
			} while (reward.cursed);
			reward.upgrade(2);
			reward.cursed = true;
		}
	}

	public void process(Mob mob) {
		if (spawned && given && !completed) {
			if ((alternative && mob instanceof Monk)
					|| (!alternative && mob instanceof Golem)) {

				Dungeon.level.drop(new DwarfToken(), mob.pos).sprite.drop();
			}
		}
	}

	public void complete() {
		reward = null;
		completed = true;

		Dungeon.journal.remove(new Record(Feature.IMP, Dungeon.depth));
	}

	public boolean isCompleted() {
		return completed;
	}
	
	public boolean isAlternative() {
		return alternative;
	}

	public void setAlternative(boolean alternative) {
		this.alternative = alternative;
	}

	public boolean isSpawned() {
		return spawned;
	}

	public void setSpawned(boolean spawned) {
		this.spawned = spawned;
	}

	public boolean isGiven() {
		return given;
	}

	public void setGiven(boolean given) {
		this.given = given;
	}
}
