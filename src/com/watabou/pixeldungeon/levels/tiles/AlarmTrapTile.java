package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.effects.CellEmitter;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.utils.GLog;

public class AlarmTrapTile extends Tile {
	public AlarmTrapTile() {
		avoid = true;
	}
	
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Level_TileDescTrap);
	}
	
	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileAlarmTrap);
	}
	
	public void trigger( int pos, Char ch ) {
		for (Mob mob : Dungeon.level.mobs) {
			if (mob != ch) {
				mob.beckon( pos );
			}
		}
		
		if (Dungeon.visible[pos]) {
			GLog.w(Game.getVar(R.string.AlarmTrap_Desc));
			CellEmitter.center( pos ).start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
		}
		
		Sample.INSTANCE.play( Assets.SND_ALERT );
	}
}
