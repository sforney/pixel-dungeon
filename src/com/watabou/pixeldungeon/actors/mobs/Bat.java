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
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.items.potions.PotionInfo;
import com.watabou.pixeldungeon.items.potions.PotionOfHealing;
import com.watabou.pixeldungeon.items.weapon.enchantments.Leech;
import com.watabou.pixeldungeon.sprites.BatSprite;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.utils.Random;

public class Bat extends Mob {
	private PotionInfo potionInfo;
	
	public Bat() {
		potionInfo = Dungeon.potionInfo;
		init();
	}
	
	public Bat(PotionInfo potionInfo, StringResolver resolver) {
		super(resolver);
		this.potionInfo = potionInfo;
		init();
	}
	
	private void init() {
		name = resolver.getVar(R.string.Bat_Name);
		spriteClass = BatSprite.class;
		
		HP = HT = 30;
		defenseSkill = 15;
		baseSpeed = 2f;
		
		EXP = 7;
		maxLvl = 15;
		
		flying = true;
		
		loot = new PotionOfHealing(potionInfo, resolver);
		lootChance = 0.125f;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 6, 12 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 16;
	}
	
	@Override
	public int dr() {
		return 4;
	}
	
	@Override
	public String defenseVerb() {
		return resolver.getVar(R.string.Bat_Defense);
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		
		int reg = Math.min( damage, HT - HP );
		
		if (reg > 0) {
			HP += reg;
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
		}
		
		return damage;
	}
	
	@Override
	public String description() {
		return resolver.getVar(R.string.Bat_Desc);
	}
	
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add( Leech.class );
	}
	
	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
