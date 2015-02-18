package com.watabou.pixeldungeon.actors.hero;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.levels.features.AlchemyPot;

public class CookAction extends BaseHeroAction {

	public CookAction(Hero hero, int target) {
		super(hero, target);
	}

	@Override
	public boolean perform() {
		if (Dungeon.visible[target]) {
			hero.ready();
			AlchemyPot.operate(hero, target);
			return true;
		} else if (getCloser(target)) {
			return true;
		} else {
			hero.ready();
			return false;
		}
	}
}
