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

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.items.Gold;
import com.watabou.pixeldungeon.sprites.GnollSprite;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.utils.Random;

public class Gnoll extends Mob {
	public Gnoll() {
		init();
	}
	
	public Gnoll(StringResolver resolver) {
		super(resolver);
		init();
	}
	
	public void init() {
		name = resolver.getVar(R.string.Gnoll_Name);
		spriteClass = GnollSprite.class;

		HP = HT = 12;
		defenseSkill = 4;

		EXP = 2;
		maxLvl = 8;

		loot = Gold.class;
		lootChance = 0.5f;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(2, 5);
	}

	@Override
	public int attackSkill(Char target) {
		return 11;
	}

	@Override
	public int dr() {
		return 2;
	}

	@Override
	public void die(Object cause) {
		Dungeon.ghostQuest.process(pos);
		super.die(cause);
	}

	@Override
	public String description() {
		return resolver.getVar(R.string.Gnoll_Desc);
	}
}
