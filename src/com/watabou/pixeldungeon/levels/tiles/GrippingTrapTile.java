package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Bleeding;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.Cripple;
import com.watabou.pixeldungeon.effects.Wound;
import com.watabou.utils.Random;

public class GrippingTrapTile extends Tile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Level_TileDescTrap);
	}
	
	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileGrippingTrap);
	}
	
	public void trigger( int pos, Char c ) {		
		if (c != null) {
			int damage = Math.max( 0,  (Dungeon.depth + 3) - Random.IntRange( 0, c.dr() / 2 ) );
			Buff.affect( c, Bleeding.class ).set( damage );
			Buff.prolong( c, Cripple.class, Cripple.DURATION );
			Wound.hit( c );
		} else {
			Wound.hit( pos );
		}	
	}
}
