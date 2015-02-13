package com.watabou.pixeldungeon.items.seeds;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.items.potions.PotionOfToxicGas;
import com.watabou.pixeldungeon.plants.Sorrowmoss;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;


public class SorrowmossSeed extends Seed {
	public SorrowmossSeed() {
		plantName = Game.getVar(R.string.Sorrowmoss_Name);;
		
		name = String.format(TXT_SEED, plantName);
		image = ItemSpriteSheet.SEED_SORROWMOSS;
		
		plantClass = Sorrowmoss.class;
		alchemyClass = PotionOfToxicGas.class;
	}
	
	@Override
	public String desc() {
		return Game.getVar(R.string.Sorrowmoss_Desc);
	}
}
