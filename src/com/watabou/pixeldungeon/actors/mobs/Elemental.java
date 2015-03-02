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

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.BuffOps;
import com.watabou.pixeldungeon.actors.buffs.Burning;
import com.watabou.pixeldungeon.actors.buffs.Frost;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.items.potions.PotionInfo;
import com.watabou.pixeldungeon.items.potions.PotionOfLiquidFlame;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.watabou.pixeldungeon.items.wands.WandOfFirebolt;
import com.watabou.pixeldungeon.items.weapon.enchantments.Fire;
import com.watabou.pixeldungeon.sprites.ElementalSprite;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.utils.Random;

public class Elemental extends Mob {
	private PotionInfo potionInfo;
	
	public Elemental() {
		potionInfo = Dungeon.potionInfo;
		init();
	}

	public Elemental(StringResolver resolver, PotionInfo potionInfo) {
		super(resolver);
		this.potionInfo = potionInfo;
		init();
	}

	public void init() {
		name = resolver.getVar(R.string.Elemental_Name);
		spriteClass = ElementalSprite.class;

		HP = HT = 65;
		defenseSkill = 20;

		EXP = 10;
		maxLvl = 20;

		flying = true;

		loot = new PotionOfLiquidFlame(potionInfo, resolver);
		lootChance = 0.1f;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(16, 20);
	}

	@Override
	public int attackSkill(Char target) {
		return 25;
	}

	@Override
	public int dr() {
		return 5;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(2) == 0) {
			BuffOps.affect(enemy, Burning.class).reignite(enemy);
		}

		return damage;
	}

	@Override
	public void add(Buff buff) {
		if (buff instanceof Burning) {
			if (HP < HT) {
				HP++;
				sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			}
		} else {
			if (buff instanceof Frost) {
				takeDamage(Random.NormalIntRange(1, HT * 2 / 3), buff);
			}
			super.add(buff);
		}
	}

	@Override
	public String description() {
		return resolver.getVar(R.string.Elemental_Desc);
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(Fire.class);
		IMMUNITIES.add(WandOfFirebolt.class);
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
