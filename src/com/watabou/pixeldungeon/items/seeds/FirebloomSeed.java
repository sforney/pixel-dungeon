package com.watabou.pixeldungeon.items.seeds;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.items.potions.PotionOfLiquidFlame;
import com.watabou.pixeldungeon.plants.Firebloom;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class FirebloomSeed extends Seed {
	public FirebloomSeed() {
		plantName = Game.getVar(R.string.Firebloom_Name);

		name = String.format(TXT_SEED, plantName);
		image = ItemSpriteSheet.SEED_FIREBLOOM;

		plantClass = Firebloom.class;
		alchemyClass = PotionOfLiquidFlame.class;
	}

	@Override
	public String desc() {
		return Game.getVar(R.string.Firebloom_Desc);
	}
}
