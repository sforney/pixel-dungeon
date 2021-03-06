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
package com.watabou.pixeldungeon.items.wands;

import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.BuffOps;
import com.watabou.pixeldungeon.actors.buffs.Poison;
import com.watabou.pixeldungeon.effects.MagicMissile;
import com.watabou.pixeldungeon.levels.LevelState;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.utils.Callback;

public class WandOfPoison extends Wand {
	public WandOfPoison() {
		init();
	}

	public WandOfPoison(WandInfo wandInfo) {
		super(wandInfo);
		init();
	}

	public WandOfPoison(WandInfo wandInfo, StringResolver resolver) {
		super(wandInfo, resolver);
		init();
	}

	private void init() {
		name = resolver.getVar(R.string.WandOfPoison_Name);
	}

	@Override
	protected void onZap(int cell) {
		Char ch = LevelState.findChar(cell);
		if (ch != null) {
			BuffOps.affect(ch, Poison.class).set(
					Poison.durationFactor(ch) * (5 + level()));
		} else {
			GLog.i(resolver.getVar(R.string.WandOfPoison_Info1));
		}
	}

	protected void fx(int cell, Callback callback) {
		MagicMissile.poison(curUser.sprite.parent, curUser.pos, cell, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	@Override
	public String desc() {
		return resolver.getVar(R.string.WandOfPoison_Info);
	}
}
