/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon.actors.mobs.npcs;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.items.quest.DwarfToken;
import com.watabou.pixeldungeon.journal.Feature;
import com.watabou.pixeldungeon.journal.Record;
import com.watabou.pixeldungeon.quest.ImpQuest;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.ImpSprite;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.pixeldungeon.windows.WndImp;
import com.watabou.pixeldungeon.windows.WndQuest;

public class Imp extends NPC {
	private ImpQuest quest;
	public Imp(ImpQuest quest) {
		this.quest = quest;
		name = Game.getVar(R.string.Imp_Name);
		spriteClass = ImpSprite.class;
	}
	
	private static final String TXT_GOLEMS1  = Game.getVar(R.string.Imp_Golems1);
	private static final String TXT_GOLEMS2  = Game.getVar(R.string.Imp_Golems2);
	private static final String TXT_MONKS1   = Game.getVar(R.string.Imp_Monks1);
	private static final String TXT_MONKS2   = Game.getVar(R.string.Imp_Monks2);	
	private static final String TXT_CYA      = Game.getVar(R.string.Imp_Cya);
	private static final String TXT_HEY      = Game.getVar(R.string.Imp_Hey);
	
	private boolean seenBefore = false;
	
	@Override
	public boolean act() {		
		if (!quest.isGiven() && Dungeon.visible[pos]) {
			if (!seenBefore) {
				yell( Utils.format( TXT_HEY, Dungeon.hero.className() ) );
			}
			seenBefore = true;
		} else {
			seenBefore = false;
		}
		
		throwItem();
		
		return super.act();
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 1000;
	}
	
	@Override
	public String defenseVerb() {
		return Game.getVar(R.string.Imp_Defense);
	}
	
	@Override
	public void takeDamage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	@Override
	public void interact() {		
		sprite.turnTo( pos, Dungeon.hero.pos );
		if (quest.isGiven()) {			
			DwarfToken tokens = Dungeon.hero.belongings.getItem( DwarfToken.class );
			if (tokens != null && (tokens.quantity() >= 8 || (!quest.isAlternative() && tokens.quantity() >= 6))) {
				GameScene.show( new WndImp( this, tokens ) );
			} else {
				tell( quest.isAlternative() ? TXT_MONKS2 : TXT_GOLEMS2, Dungeon.hero.className() );
			}		
		} else {
			tell( quest.isAlternative() ? TXT_MONKS1 : TXT_GOLEMS1 );
			quest.setGiven(true);
			quest.complete();
			
			Dungeon.journal.add(new Record(Feature.IMP,
					Dungeon.depth));
		}
	}
	
	private void tell( String format, Object...args ) {
		GameScene.show( 
			new WndQuest( this, Utils.format( format, args ) ) );
	}
	
	public void flee() {	
		yell( Utils.format( TXT_CYA, Dungeon.hero.className() ) );
		
		destroy();
		sprite.die();
	}
	
	@Override
	public String description() {
		return Game.getVar(R.string.Imp_Desc);
	}
}
