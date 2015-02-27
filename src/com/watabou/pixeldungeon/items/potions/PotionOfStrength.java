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
package com.watabou.pixeldungeon.items.potions;

import com.watabou.pixeldungeon.Badges;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.sprites.CharSprite;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.utils.StringResolver;

public class PotionOfStrength extends Potion {
	public PotionOfStrength() {
		name = resolver.getVar(R.string.PotionOfStrength_Name);		
	}
	
	public PotionOfStrength(PotionInfo potionInfo) {
		super(potionInfo);
		name = resolver.getVar(R.string.PotionOfStrength_Name);
	}
	
	public PotionOfStrength(PotionInfo potionInfo, StringResolver resolver) {
		super(potionInfo, resolver);
		name = resolver.getVar(R.string.PotionOfStrength_Name);
	}
	
	@Override
	protected void apply( Hero hero ) {
		setKnown();
		
		hero.STR++;
		hero.sprite.showStatus( CharSprite.POSITIVE, resolver.getVar(R.string.PotionOfStrength_StaApply));
		GLog.p(resolver.getVar(R.string.PotionOfStrength_Apply));
		
		Badges.validateStrengthAttained();
	}
	
	@Override
	public String desc() {
		return resolver.getVar(R.string.PotionOfStrength_Info);
	}
	
	@Override
	public int price() {
		return isKnown() ? 100 * quantity : super.price();
	}
}
