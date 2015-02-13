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
package com.watabou.pixeldungeon.plants;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.Blob;
import com.watabou.pixeldungeon.actors.blobs.ConfusionGas;
import com.watabou.pixeldungeon.scenes.GameScene;

public class Dreamweed extends Plant {
	public Dreamweed() {
		image = 3;
		plantName = Game.getVar(R.string.Dreamweed_Name);
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		if (ch != null) {
			GameScene.add(Blob.seed(pos, 400, ConfusionGas.class));
		}
	}

	@Override
	public String desc() {
		return Game.getVar(R.string.Dreamweed_Desc);
	}
}
