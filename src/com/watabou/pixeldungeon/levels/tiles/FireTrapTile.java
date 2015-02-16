package com.watabou.pixeldungeon.levels.tiles;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.Blob;
import com.watabou.pixeldungeon.actors.blobs.Fire;
import com.watabou.pixeldungeon.effects.CellEmitter;
import com.watabou.pixeldungeon.effects.particles.FlameParticle;
import com.watabou.pixeldungeon.scenes.GameScene;

public class FireTrapTile extends Tile {
	@Override
	public String getDescription() {
		return Game.getVar(R.string.Level_TileDescTrap);
	}
	
	@Override
	public String getName() {
		return Game.getVar(R.string.Level_TileFireTrap);
	}
	
	public void trigger( int pos, Char ch ) {
		GameScene.add( Blob.seed( pos, 2, Fire.class ) );
		CellEmitter.get( pos ).burst( FlameParticle.FACTORY, 5 );		
	}
}
