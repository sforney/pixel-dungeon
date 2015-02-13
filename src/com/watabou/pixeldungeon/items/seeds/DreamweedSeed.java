package com.watabou.pixeldungeon.items.seeds;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.items.potions.PotionOfInvisibility;
import com.watabou.pixeldungeon.plants.Dreamweed;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class DreamweedSeed extends Seed {
	public DreamweedSeed() {
		plantName = Game.getVar(R.string.Dreamweed_Name);

		name = String.format(TXT_SEED, plantName);
		image = ItemSpriteSheet.SEED_DREAMWEED;

		plantClass = Dreamweed.class;
		alchemyClass = PotionOfInvisibility.class;
	}

	@Override
	public String desc() {
		return Game.getVar(R.string.Dreamweed_Desc);
	}
}