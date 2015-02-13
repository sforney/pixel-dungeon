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
import com.watabou.pixeldungeon.Journal;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.quest.CorpseDust;
import com.watabou.pixeldungeon.items.seeds.RotberrySeed;
import com.watabou.pixeldungeon.quest.WandmakerQuest;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.WandmakerSprite;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.pixeldungeon.windows.WndQuest;
import com.watabou.pixeldungeon.windows.WndWandmaker;

public class Wandmaker extends NPC {
	private WandmakerQuest quest;
	
	public Wandmaker(WandmakerQuest quest) {
		this.quest = quest;
		name = Game.getVar(R.string.WandMaker_Name);
		spriteClass = WandmakerSprite.class;		
	}
	
	private final String TXT_BERRY1 = Game.getVar(R.string.WandMaker_Berry1);
	private final String TXT_BERRY2 = Game.getVar(R.string.WandMaker_Berry2);
	private final String TXT_DUST1  = Game.getVar(R.string.WandMaker_Dust1);
	private final String TXT_DUST2  = Game.getVar(R.string.WandMaker_Dust2);
	
	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 1000;
	}
	
	@Override
	public String defenseVerb() {
		return Game.getVar(R.string.WandMaker_Defense);
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
	public void interact() {
		
		sprite.turnTo( pos, Dungeon.hero.pos );
		if (quest.given) {
			
			Item item = quest.alternative ?
				Dungeon.hero.belongings.getItem( CorpseDust.class ) :
				Dungeon.hero.belongings.getItem( RotberrySeed.class );
			if (item != null) {
				GameScene.show( new WndWandmaker( this, item ) );
			} else {
				tell( quest.alternative ? TXT_DUST2 : TXT_BERRY2, Dungeon.hero.className() );
			}
			
		} else {
			tell( quest.alternative ? TXT_DUST1 : TXT_BERRY1 );
			quest.given = true;
			
			quest.placeItem();
			
			Journal.add( Journal.Feature.WANDMAKER );
		}
	}
	
	private void tell( String format, Object...args ) {
		GameScene.show( new WndQuest( this, Utils.format( format, args ) ) );
	}
	
	@Override
	public String description() {
		return Game.getVar(R.string.WandMaker_Desc);
	}
}
