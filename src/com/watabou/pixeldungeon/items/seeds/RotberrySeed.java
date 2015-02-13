package com.watabou.pixeldungeon.items.seeds;

import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.effects.CellEmitter;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.items.bags.Bag;
import com.watabou.pixeldungeon.items.potions.PotionOfStrength;
import com.watabou.pixeldungeon.plants.Rotberry;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.pixeldungeon.utils.GLog;

public class RotberrySeed extends Seed {
	public RotberrySeed() {
		plantName = Game.getVar(R.string.WandMaker_RotberryName);

		name = String.format(TXT_SEED, plantName);
		image = ItemSpriteSheet.SEED_ROTBERRY;

		plantClass = Rotberry.class;
		alchemyClass = PotionOfStrength.class;
	}

	@Override
	public boolean collect(Bag container) {
		if (super.collect(container)) {

			if (Dungeon.level != null) {
				for (Mob mob : Dungeon.level.mobs) {
					mob.beckon(Dungeon.hero.pos);
				}

				GLog.w(Game.getVar(R.string.WandMaker_RotberryInfo));
				CellEmitter.center(Dungeon.hero.pos).start(
						Speck.factory(Speck.SCREAM), 0.3f, 3);
				Sample.INSTANCE.play(Assets.SND_CHALLENGE);
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public String desc() {
		return Game.getVar(R.string.WandMaker_RotberryDesc);
	}
}
