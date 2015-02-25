package com.watabou.pixeldungeon.plants;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.Blob;
import com.watabou.pixeldungeon.actors.blobs.ToxicGas;
import com.watabou.pixeldungeon.actors.buffs.BuffOps;
import com.watabou.pixeldungeon.actors.buffs.Roots;
import com.watabou.pixeldungeon.items.seeds.RotberrySeed;
import com.watabou.pixeldungeon.scenes.GameScene;

public class Rotberry extends Plant {
	private final String TXT_DESC = Game
			.getVar(R.string.WandMaker_RotberryDesc);

	public Rotberry() {
		image = 7;
		plantName = Game.getVar(R.string.WandMaker_RotberryName);
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		GameScene.add(Blob.seed(pos, 100, ToxicGas.class));

		Dungeon.level.drop(new RotberrySeed(), pos).sprite.drop();

		if (ch != null) {
			BuffOps.prolong(ch, Roots.class, TICK * 3);
		}
	}

	@Override
	public String desc() {
		return TXT_DESC;
	}

	@Override
	public boolean act() {
		return false;
	}
}
