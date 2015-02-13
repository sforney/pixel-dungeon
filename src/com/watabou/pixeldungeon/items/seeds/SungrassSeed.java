package com.watabou.pixeldungeon.items.seeds;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.items.potions.PotionOfHealing;
import com.watabou.pixeldungeon.plants.Sungrass;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class SungrassSeed extends Seed {
	public SungrassSeed() {
		plantName = Game.getVar(R.string.Sungrass_Name);
		
		name = String.format(TXT_SEED, plantName);
		image = ItemSpriteSheet.SEED_SUNGRASS;
		
		plantClass = Sungrass.class;
		alchemyClass = PotionOfHealing.class;
	}
	
	@Override
	public String desc() {
		return Game.getVar(R.string.Sungrass_Desc);
	}
}
