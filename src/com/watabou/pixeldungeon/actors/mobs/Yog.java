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

import java.util.ArrayList;
import java.util.HashSet;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.Statistics;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.ToxicGas;
import com.watabou.pixeldungeon.actors.buffs.Amok;
import com.watabou.pixeldungeon.actors.buffs.Burning;
import com.watabou.pixeldungeon.actors.buffs.Charm;
import com.watabou.pixeldungeon.actors.buffs.Sleep;
import com.watabou.pixeldungeon.actors.buffs.Terror;
import com.watabou.pixeldungeon.effects.Pushing;
import com.watabou.pixeldungeon.items.keys.SkeletonKey;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.watabou.pixeldungeon.items.weapon.enchantments.Death;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.LevelState;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.YogSprite;
import com.watabou.utils.Random;

public class Yog extends Mob {
	private static final String TXT_DESC = Game.getVar(R.string.Yog_Desc);

	private static int fistsCount = 0;

	public Yog() {
		super();
		name = Dungeon.depth == Statistics.deepestFloor ? Game
				.getVar(R.string.Yog_Name1) : Game.getVar(R.string.Yog_Name2);

		spriteClass = YogSprite.class;
		HP = HT = 300;
		EXP = 50;
		state = PASSIVE;
	}

	public void spawnFists() {
		RottingFist fist1 = new RottingFist(this);
		BurningFist fist2 = new BurningFist(this);

		do {
			fist1.pos = pos + Level.NEIGHBOURS8[Random.Int(8)];
			fist2.pos = pos + Level.NEIGHBOURS8[Random.Int(8)];
		} while (!Level.passable[fist1.pos] || !Level.passable[fist2.pos]
				|| fist1.pos == fist2.pos);

		GameScene.add(fist1);
		GameScene.add(fist2);
	}

	public void fistDied() {
		fistsCount--;
	}

	public void fistAdded() {
		fistsCount++;
	}

	@Override
	public void takeDamage(int dmg, Object src) {
		if (fistsCount > 0) {

			for (Mob mob : Dungeon.level.mobs) {
				if (mob instanceof BurningFist || mob instanceof RottingFist) {
					mob.beckon(pos);
				}
			}

			if (fistsCount == 2) {
				dmg = dmg / 4;
			} else if (fistsCount == 1) {
				dmg = dmg / 2;
			}
		}

		super.takeDamage(dmg, src);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			int p = pos + Level.NEIGHBOURS8[i];
			if (LevelState.findChar(p) == null
					&& (Level.passable[p] || Level.avoid[p])) {
				spawnPoints.add(p);
			}
		}

		if (spawnPoints.size() > 0) {
			Larva larva = new Larva();
			larva.pos = Random.element(spawnPoints);

			GameScene.add(larva);
			LevelState.add(new Pushing(larva, pos, larva.pos), -1);
		}

		return super.defenseProc(enemy, damage);
	}

	@Override
	public void beckon(int cell) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void die(Object cause) {
		for (Mob mob : (Iterable<Mob>) Dungeon.level.mobs.clone()) {
			if (mob instanceof BurningFist || mob instanceof RottingFist) {
				mob.die(cause);
			}
		}

		GameScene.bossSlain();
		Dungeon.level.drop(new SkeletonKey(), pos).sprite.drop();
		super.die(cause);

		yell(Game.getVar(R.string.Yog_Info1));
	}

	@Override
	public void notice() {
		super.notice();
		yell(Game.getVar(R.string.Yog_Info2));
	}

	@Override
	public String description() {
		return TXT_DESC;

	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Death.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Charm.class);
		IMMUNITIES.add(Sleep.class);
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
