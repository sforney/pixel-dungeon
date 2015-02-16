package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.damagetypes.Electricity;
import com.watabou.pixeldungeon.effects.CellEmitter;
import com.watabou.pixeldungeon.effects.Lightning;
import com.watabou.pixeldungeon.effects.particles.SparkParticle;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.utils.Random;

public class LightningTrapTile extends Tile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Level_TileDescTrap);
	}
	
	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileLightningTrap);
	}
	
	public void trigger(int pos, Char ch) {
		if (ch != null) {
			ch.damage(Math.max(1, Random.Int(ch.HP / 3, 2 * ch.HP / 3)),
					new Electricity());
			if (ch == Dungeon.hero) {

				Camera.main.shake(2, 0.3f);

				if (!ch.isAlive()) {
					Dungeon.fail(Utils.format(
							Game.getVar(R.string.ResultDescriptions_Trap),
							Game.getVar(R.string.LightningTrap_Name), Dungeon.depth));
					GLog.n(Game.getVar(R.string.LightningTrap_Desc));
				} else {
					((Hero) ch).belongings.charge(false);
				}
			}

			int[] points = new int[2];

			points[0] = pos - Level.WIDTH;
			points[1] = pos + Level.WIDTH;
			ch.sprite.parent.add(new Lightning(points, 2, null));

			points[0] = pos - 1;
			points[1] = pos + 1;
			ch.sprite.parent.add(new Lightning(points, 2, null));
		}

		CellEmitter.center(pos).burst(SparkParticle.FACTORY,
				Random.IntRange(3, 4));
	}
}
