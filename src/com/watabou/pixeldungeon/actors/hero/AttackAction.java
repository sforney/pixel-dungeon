package com.watabou.pixeldungeon.actors.hero;

import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Invisibility;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.ui.AttackIndicator;

public class AttackAction extends BaseHeroAction {
	private Char enemy;
	
	public AttackAction(Hero hero, int target, Char enemy) {
		super(hero, target);
		this.enemy = enemy;
	}

	@Override
	public boolean perform() {
		hero.setEnemy(enemy);

		if (Level.adjacent(hero.pos, enemy.pos) && hero.getEnemy().isAlive()
				&& !hero.isCharmedBy(enemy)) {
			hero.spend(hero.attackDelay());
			hero.sprite.attack(enemy.pos);
			AttackIndicator.target(enemy);
			hero.attack(enemy);		
			Invisibility.dispel();
			hero.curAction = null;
			return false;
		} else {
			if (Level.fieldOfView[enemy.pos] && getCloser(enemy.pos)) {
				return true;
			} else {
				hero.ready();
				return false;
			}
		}
	}
}
