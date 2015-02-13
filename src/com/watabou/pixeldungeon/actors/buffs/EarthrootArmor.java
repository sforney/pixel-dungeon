package com.watabou.pixeldungeon.actors.buffs;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class EarthrootArmor extends Buff {
	
	private final float STEP = 1f;
	
	public int pos;
	public int level;
	
	@Override
	public boolean attachTo( Char target ) {
		pos = target.pos;
		return super.attachTo( target );
	}
	
	@Override
	public boolean act() {
		if (target.pos != pos) {
			detach();
		}
		spend( STEP );
		return true;
	}
	
	public int absorb( int damage ) {
		if (damage >= level) {
			detach();
			return damage - level;
		} else {
			level -= damage;
			return 0;
		}
	}
	
	public void level( int value ) {
		if (level < value) {
			level = value;
		}
	}
	
	@Override
	public int icon() {
		return BuffIndicator.ARMOR;
	}
	
	@Override
	public String toString() {
		return Game.getVar(R.string.Earthroot_Buff);
	}
	
	private static final String POS		= "pos";
	private static final String LEVEL	= "level";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( POS, pos );
		bundle.put( LEVEL, level );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		pos = bundle.getInt( POS );
		level = bundle.getInt( LEVEL );
	}
}
