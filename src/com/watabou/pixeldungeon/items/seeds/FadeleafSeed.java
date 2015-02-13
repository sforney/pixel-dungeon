package com.watabou.pixeldungeon.items.seeds;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.items.potions.PotionOfMindVision;
import com.watabou.pixeldungeon.plants.Fadeleaf;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class FadeleafSeed extends Seed {
	public FadeleafSeed() {
		plantName = Game.getVar(R.string.Fadeleaf_Name);
		
		name = String.format(TXT_SEED, plantName);
		image = ItemSpriteSheet.SEED_FADELEAF;
		
		plantClass = Fadeleaf.class;
		alchemyClass = PotionOfMindVision.class;
	}
	
	@Override
	public String desc() {
		return Game.getVar(R.string.Fadeleaf_Desc);
	}
}