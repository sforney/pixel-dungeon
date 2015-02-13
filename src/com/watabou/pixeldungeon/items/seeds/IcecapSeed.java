package com.watabou.pixeldungeon.items.seeds;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.items.potions.PotionOfFrost;
import com.watabou.pixeldungeon.plants.Icecap;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class IcecapSeed extends Seed {
	public IcecapSeed() {
		plantName = Game.getVar(R.string.Icecap_Name);

		name = String.format(TXT_SEED, plantName);
		image = ItemSpriteSheet.SEED_ICECAP;

		plantClass = Icecap.class;
		alchemyClass = PotionOfFrost.class;
	}

	@Override
	public String desc() {
		return Game.getVar(R.string.Icecap_Desc);
	}
}
