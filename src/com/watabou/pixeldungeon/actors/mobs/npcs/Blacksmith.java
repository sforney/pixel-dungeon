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
import com.watabou.pixeldungeon.actors.hero.PickupAction;
import com.watabou.pixeldungeon.items.quest.DarkGold;
import com.watabou.pixeldungeon.items.quest.Pickaxe;
import com.watabou.pixeldungeon.journal.Feature;
import com.watabou.pixeldungeon.journal.Record;
import com.watabou.pixeldungeon.quest.BlacksmithQuest;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.BlacksmithSprite;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.windows.WndBlacksmith;
import com.watabou.pixeldungeon.windows.WndQuest;

public class Blacksmith extends NPC {

	private static final String TXT_GOLD_1       = Game.getVar(R.string.Blacksmith_Gold1);
	private static final String TXT_BLOOD_1      = Game.getVar(R.string.Blacksmith_Blood1);
	private static final String TXT2             = Game.getVar(R.string.Blacksmith_Txt2);
	private static final String TXT3             = Game.getVar(R.string.Blacksmith_Txt3);
	private static final String TXT4             = Game.getVar(R.string.Blacksmith_Txt4);
	private static final String TXT_COMPLETED    = Game.getVar(R.string.Blacksmith_Completed);
	private static final String TXT_GET_LOST     = Game.getVar(R.string.Blacksmith_GetLost);
	private BlacksmithQuest quest;
	
	public Blacksmith() {
		name = Game.getVar(R.string.Blacksmith_Name);
		spriteClass = BlacksmithSprite.class;
		quest = Dungeon.blacksmithQuest;
	}
	
	@Override
	public boolean act() {
		throwItem();		
		return super.act();
	}
	
	@Override
	public void interact() {	
		sprite.turnTo( pos, Dungeon.hero.pos );
		
		if (!quest.isGiven()) {
			
			GameScene.show( new WndQuest( this, 
					quest.isAlternative() ? TXT_BLOOD_1 : TXT_GOLD_1 ) {
				
				@Override
				public void onBackPressed() {
					super.onBackPressed();
					
					quest.setGiven(true);
					//Quest.completed = false;
					
					Pickaxe pick = new Pickaxe();
					if (pick.doPickUp( Dungeon.hero )) {
						GLog.i( PickupAction.TXT_YOU_NOW_HAVE, pick.name() );
					} else {
						Dungeon.level.drop( pick, Dungeon.hero.pos ).sprite.drop();
					}
				};
			} );
			
			Dungeon.journal.add(new Record(Feature.TROLL, Dungeon.depth));	
		} else if (!Dungeon.blacksmithQuest.isCompleted()) {
			if (quest.isAlternative()) {				
				Pickaxe pick = Dungeon.hero.belongings.getItem( Pickaxe.class );
				if (pick == null) {
					tell( TXT2 );
				} else if (!pick.bloodStained) {
					tell( TXT4 );
				} else {
					if (pick.isEquipped( Dungeon.hero )) {
						pick.doUnequip( Dungeon.hero, false );
					}
					pick.detach( Dungeon.hero.belongings.backpack );
					tell( TXT_COMPLETED );
					
					quest.complete();
					quest.setReforged(false);
				}			
			} else {	
				Pickaxe pick = Dungeon.hero.belongings.getItem( Pickaxe.class );
				DarkGold gold = Dungeon.hero.belongings.getItem( DarkGold.class );
				if (pick == null) {
					tell( TXT2 );
				} else if (gold == null || gold.quantity() < 15) {
					tell( TXT3 );
				} else {
					if (pick.isEquipped( Dungeon.hero )) {
						pick.doUnequip( Dungeon.hero, false );
					}
					pick.detach( Dungeon.hero.belongings.backpack );
					gold.detachAll( Dungeon.hero.belongings.backpack );
					tell( TXT_COMPLETED );
					
					quest.complete();
					quest.setReforged(false);
				}
				
			}
		} else if (!quest.isReforged()) {		
			GameScene.show( new WndBlacksmith( this, Dungeon.hero ) );			
		} else {			
			tell( TXT_GET_LOST );			
		}
	}
	
	private void tell( String text ) {
		GameScene.show( new WndQuest( this, text ) );
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 1000;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	@Override
	public String description() {
		return Game.getVar(R.string.Blacksmith_Desc);
	}
}
