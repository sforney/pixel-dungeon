package com.watabou.pixeldungeon.actors.hero;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.buffs.Hunger;
import com.watabou.pixeldungeon.scenes.InterlevelScene;

public class DescendAction extends BaseHeroAction {
	public DescendAction(Hero hero, int target) {
		super(hero, target);
	}

	@Override
	public boolean perform() {
		int stairs = target;
		if (hero.pos == stairs && hero.pos == Dungeon.level.exit) {

			hero.curAction = null;

			Hunger hunger = hero.getBuff(Hunger.class);
			if (hunger != null && !hunger.isStarving()) {
				hunger.satisfy(-Hunger.STARVING / 10);
			}

			InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
			Game.switchScene(InterlevelScene.class);

			return false;

		} else if (getCloser(stairs)) {

			return true;

		} else {
			hero.ready();
			return false;
		}
	}
}
