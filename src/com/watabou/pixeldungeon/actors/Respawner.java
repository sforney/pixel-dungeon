package com.watabou.pixeldungeon.actors;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.Statistics;
import com.watabou.pixeldungeon.actors.mobs.Bestiary;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.scenes.GameScene;

public class Respawner {
	private int nextAction = 0;
	
	protected boolean act(int nMobs) {
		if (Dungeon.level.mobs.size() < nMobs) {

			Mob mob = Bestiary.mutable(Dungeon.depth);
			mob.state = mob.WANDERING;
			mob.pos = Dungeon.level.randomRespawnCell();
			if (Dungeon.hero.isAlive() && mob.pos != -1) {
				GameScene.add(mob);
				if (Statistics.amuletObtained) {
					mob.beckon(Dungeon.hero.pos);
				}
			}
		}
		spend(Dungeon.nightMode || Statistics.amuletObtained ? Level.TIME_TO_RESPAWN / 2
				: Level.TIME_TO_RESPAWN);
		return true;
	}
	
	private void spend(int time) {
		setNextAction(getNextAction() + time);
	}

	public int getNextAction() {
		return nextAction;
	}

	public void setNextAction(int nextAction) {
		this.nextAction = nextAction;
	}
}
