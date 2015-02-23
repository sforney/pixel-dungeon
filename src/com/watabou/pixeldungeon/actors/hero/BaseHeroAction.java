package com.watabou.pixeldungeon.actors.hero;

import com.watabou.noosa.Camera;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.features.Chasm;

public abstract class BaseHeroAction implements HeroAction {
	protected Hero hero;
	protected int target;

	public BaseHeroAction(Hero hero, int target) {
		this.hero = hero;
		this.target = target;
	}

	protected boolean getCloser(final int target) {
		if (hero.rooted) {
			Camera.main.shake(1, 1f);
			return false;
		}

		int step = -1;

		if (Level.adjacent(hero.pos, target)) {
			if (hero.findCharacter(target) == null) {
				if (Level.pit[target] && !hero.flying && !Chasm.jumpConfirmed) {
					Chasm.heroJump(hero);
					hero.interrupt();
					return false;
				}
				if (Level.passable[target] || Level.avoid[target]) {
					step = target;
				}
			}
		} else {
			int len = Level.LENGTH;
			boolean[] p = Level.passable;
			boolean[] v = Dungeon.level.visited;
			boolean[] m = Dungeon.level.mapped;
			boolean[] passable = new boolean[len];
			for (int i = 0; i < len; i++) {
				passable[i] = p[i] && (v[i] || m[i]);
			}

			step = Dungeon.findPath(hero, hero.pos, target, passable,
					Level.fieldOfView);
		}
		if (step != -1) {
			int oldPos = hero.pos;
			hero.move(step);
			hero.sprite.move(oldPos, hero.pos);
			hero.spend(1 / hero.speed());
			Dungeon.observe();
			return true;
		} else {
			return false;
		}
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}
}