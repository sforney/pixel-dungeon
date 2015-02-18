package com.watabou.pixeldungeon.actors.hero;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.pixeldungeon.levels.features.Sign;

public class MoveAction extends BaseHeroAction {

	public MoveAction(Hero hero, int target) {
		super(hero, target);
		this.hero = hero;
	}

	@Override
	public boolean perform() {
		if (getCloser(target)) {
			return false;
		} else {
			if (Dungeon.level.map[hero.pos] == Terrain.SIGN) {
				Sign.read(hero.pos);
			}
			Dungeon.observe();
			hero.search(false);
			hero.ready();
			return false;
		}
	}
}
