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
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.mobs.npcs.Sheep;
import com.watabou.pixeldungeon.effects.CellEmitter;
import com.watabou.pixeldungeon.effects.MagicMissile;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.LevelState;
import com.watabou.pixeldungeon.mechanics.Ballistica;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.utils.BArray;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

public class WandOfFlock extends Wand {
	public WandOfFlock() {
		init();
	}

	public WandOfFlock(WandInfo wandInfo) {
		super(wandInfo);
		init();
	}

	public WandOfFlock(WandInfo wandInfo, StringResolver resolver) {
		super(wandInfo, resolver);
		init();
	}

	private void init() {
		name = resolver.getVar(R.string.WandOfFlock_Name);
	}

	@Override
	protected void onZap(int cell) {
		int level = level();

		int n = level + 2;

		if (LevelState.findChar(cell) != null && Ballistica.distance > 2) {
			cell = Ballistica.trace[Ballistica.distance - 2];
		}

		boolean[] passable = BArray.or(Level.passable, Level.avoid, null);
		for (Actor actor : LevelState.getActors()) {
			if (actor instanceof Char) {
				passable[((Char) actor).pos] = false;
			}
		}

		PathFinder.buildDistanceMap(cell, passable, n);
		int dist = 0;

		if (LevelState.findChar(cell) != null) {
			PathFinder.distance[cell] = Integer.MAX_VALUE;
			dist = 1;
		}

		float lifespan = level + 3;

		sheepLabel: for (int i = 0; i < n; i++) {
			do {
				for (int j = 0; j < Level.LENGTH; j++) {
					if (PathFinder.distance[j] == dist) {

						Sheep sheep = new Sheep(resolver);
						sheep.lifespan = lifespan;
						sheep.pos = j;
						GameScene.add(sheep);
						Dungeon.level.mobPress(sheep);

						CellEmitter.get(j).burst(Speck.factory(Speck.WOOL), 4);

						PathFinder.distance[j] = Integer.MAX_VALUE;

						continue sheepLabel;
					}
				}
				dist++;
			} while (dist < n);
		}
	}

	protected void fx(int cell, Callback callback) {
		MagicMissile.wool(curUser.sprite.parent, curUser.pos, cell, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	@Override
	public String desc() {
		return resolver.getVar(R.string.WandOfFlock_Info);
	}
}
