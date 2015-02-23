/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon.actors.mobs;

import java.util.HashSet;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Badges;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.Statistics;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.ToxicGas;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.Ooze;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.items.LloydsBeacon;
import com.watabou.pixeldungeon.items.keys.SkeletonKey;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.watabou.pixeldungeon.items.weapon.enchantments.Death;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.SewerBossLevel;
import com.watabou.pixeldungeon.mechanics.Ballistica;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.CharSprite;
import com.watabou.pixeldungeon.sprites.GooSprite;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Goo extends Mob implements Bundlable {

	private static final int PUMP_UP_DELAY = 20;
	private static final String GOO_PUMPED = "GOO_PUMPED";

	{
		name = Dungeon.depth == Statistics.deepestFloor ? Game
				.getVar(R.string.Goo_Name1) : Game.getVar(R.string.Goo_Name2);

		HP = HT = 80;
		EXP = 10;
		defenseSkill = 12;
		spriteClass = GooSprite.class;

		loot = new LloydsBeacon();
		lootChance = 0.333f;
	}

	private boolean pumpedUp = false;
	private boolean jumped = false;

	@Override
	public int damageRoll() {
		if (pumpedUp) {
			return Random.NormalIntRange(5, 30);
		} else {
			return Random.NormalIntRange(2, 12);
		}
	}

	@Override
	public int attackSkill(Char target) {
		return pumpedUp && !jumped ? 30 : 15;
	}

	@Override
	public int dr() {
		return 2;
	}

	@Override
	public boolean act() {

		if (Level.water[pos] && HP < HT) {
			sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			HP++;
		}

		return super.act();
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return pumpedUp ? distance(enemy) <= 2 : super.canAttack(enemy);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0) {
			Buff.affect(enemy, Ooze.class);
			enemy.sprite.burst(0x000000, 5);
		}

		return damage;
	}

	@Override
	protected boolean doAttack(final Char enemy) {
		if (pumpedUp) {

			if (Level.adjacent(pos, enemy.pos)) {

				// Pumped up attack WITHOUT accuracy penalty
				jumped = false;
				return super.doAttack(enemy);

			} else {

				// Pumped up attack WITH accuracy penalty
				jumped = true;
				if (Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos) {
					final int dest = Ballistica.trace[Ballistica.distance - 2];

					Callback afterJump = new Callback() {
						@Override
						public void call() {
							move(dest);
							Dungeon.level.mobPress(Goo.this);
							Goo.super.doAttack(enemy);
						}
					};

					if (Dungeon.visible[pos] || Dungeon.visible[dest]) {

						sprite.jump(pos, dest, afterJump);
						return false;

					} else {

						afterJump.call();
						return true;

					}
				} else {

					sprite.idle();
					pumpedUp = false;
					return true;
				}
			}

		} else if (Random.Int(3) > 0) {

			// Normal attack
			return super.doAttack(enemy);

		} else {

			// Pumping up
			pumpedUp = true;
			spend(PUMP_UP_DELAY);

			((GooSprite) sprite).pumpUp();

			if (Dungeon.visible[pos]) {
				sprite.showStatus(CharSprite.NEGATIVE,
						Game.getVar(R.string.Goo_StaInfo1));
				GLog.n(Game.getVar(R.string.Goo_Info1));
			}

			return true;
		}
	}

	@Override
	public boolean attack(Char enemy) {
		boolean result = super.attack(enemy);
		pumpedUp = false;
		return result;
	}

	@Override
	protected boolean getCloser(int target) {
		pumpedUp = false;
		return super.getCloser(target);
	}

	@Override
	public void move(int step) {
		((SewerBossLevel) Dungeon.level).seal();
		super.move(step);
	}

	@Override
	public void die(Object cause) {

		super.die(cause);

		((SewerBossLevel) Dungeon.level).unseal();

		GameScene.bossSlain();
		Dungeon.level.drop(new SkeletonKey(), pos).sprite.drop();

		Badges.validateBossSlain();

		yell(Game.getVar(R.string.Goo_Info2));
	}

	@Override
	public void notice() {
		super.notice();
		yell(Game.getVar(R.string.Goo_Info3));
	}

	@Override
	public String description() {
		return Game.getVar(R.string.Goo_Desc);
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(GOO_PUMPED, pumpedUp);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		pumpedUp = bundle.getBoolean(GOO_PUMPED);
	}
}
