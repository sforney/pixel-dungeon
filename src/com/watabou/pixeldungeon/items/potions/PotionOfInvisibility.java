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

import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.BuffOps;
import com.watabou.pixeldungeon.actors.buffs.Invisibility;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.utils.StringResolver;

public class PotionOfInvisibility extends Potion {

	private static final float ALPHA = 0.4f;

	public PotionOfInvisibility(PotionInfo potionInfo) {
		super(potionInfo);
		name = resolver.getVar(R.string.PotionOfInvisibility_Name);
	}

	public PotionOfInvisibility(PotionInfo potionInfo, StringResolver resolver) {
		super(potionInfo, resolver);
		name = resolver.getVar(R.string.PotionOfInvisibility_Name);
	}

	@Override
	protected void apply(Hero hero) {
		setKnown();
		BuffOps.affect(hero, Invisibility.class, Invisibility.DURATION);
		GLog.i(resolver.getVar(R.string.PotionOfInvisibility_Apply));
		Sample.INSTANCE.play(Assets.SND_MELD);
	}

	@Override
	public String desc() {
		return resolver.getVar(R.string.PotionOfInvisibility_Info);
	}

	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}

	public static void melt(Char ch) {
		if (ch.sprite.parent != null) {
			ch.sprite.parent.add(new AlphaTweener(ch.sprite, ALPHA, 0.4f));
		} else {
			ch.sprite.alpha(ALPHA);
		}
	}
}
