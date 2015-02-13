package com.watabou.pixeldungeon.items.seeds;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.items.potions.PotionOfParalyticGas;
import com.watabou.pixeldungeon.plants.Earthroot;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class EarthrootSeed extends Seed {
	public EarthrootSeed(){
		plantName = Game.getVar(R.string.Earthroot_Name);
		
		name = String.format(TXT_SEED, plantName);
		image = ItemSpriteSheet.SEED_EARTHROOT;
		
		plantClass = Earthroot.class;
		alchemyClass = PotionOfParalyticGas.class;
	}
	
	@Override
	public String desc() {
		return Game.getVar(R.string.Earthroot_Desc);
	}
}
