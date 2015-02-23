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

import java.util.HashSet;

import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.Paralysis;
import com.watabou.pixeldungeon.actors.buffs.Roots;
import com.watabou.pixeldungeon.effects.CellEmitter;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.quest.DriedRose;
import com.watabou.pixeldungeon.items.quest.RatSkull;
import com.watabou.pixeldungeon.journal.Feature;
import com.watabou.pixeldungeon.journal.Record;
import com.watabou.pixeldungeon.quest.GhostQuest;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.GhostSprite;
import com.watabou.pixeldungeon.windows.WndQuest;
import com.watabou.pixeldungeon.windows.WndSadGhost;

public class Ghost extends NPC {
	private final String TXT_ROSE1 = Game.getVar(R.string.Ghost_Rose1);
	private final String TXT_ROSE2 = Game.getVar(R.string.Ghost_Rose2);
	private final String TXT_RAT1 = Game.getVar(R.string.Ghost_Rat1);
	private final String TXT_RAT2 = Game.getVar(R.string.Ghost_Rat2);

	private GhostQuest quest;

	public Ghost(GhostQuest quest) {
		super();
		name = Game.getVar(R.string.Ghost_Name);
		spriteClass = GhostSprite.class;

		flying = true;

		state = WANDERING;

		this.quest = quest;

		Sample.INSTANCE.load(Assets.SND_GHOST);
	}

	@Override
	public int defenseSkill(Char enemy) {
		return 1000;
	}

	@Override
	public String defenseVerb() {
		return Game.getVar(R.string.Ghost_Defense);
	}

	@Override
	public float speed() {
		return 0.5f;
	}

	@Override
	protected Char chooseEnemy() {
		return null;
	}

	@Override
	public void damage(int dmg, Object src) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	public void interact() {
		sprite.turnTo(pos, Dungeon.hero.pos);

		Sample.INSTANCE.play(Assets.SND_GHOST);

		if (quest.given) {

			Item item = quest.alternative ? Dungeon.hero.belongings
					.getItem(RatSkull.class) : Dungeon.hero.belongings
					.getItem(DriedRose.class);
			if (item != null) {
				GameScene.show(new WndSadGhost(this, item));
			} else {
				GameScene.show(new WndQuest(this, quest.alternative ? TXT_RAT2
						: TXT_ROSE2));

				int newPos = -1;
				for (int i = 0; i < 10; i++) {
					newPos = Dungeon.level.randomRespawnCell();
					if (newPos != -1) {
						break;
					}
				}
				if (newPos != -1) {

					this.freeCell(pos);

					CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT),
							0.2f, 3);
					pos = newPos;
					sprite.place(pos);
					sprite.visible = Dungeon.visible[pos];
				}
			}

		} else {
			GameScene.show(new WndQuest(this, quest.alternative ? TXT_RAT1
					: TXT_ROSE1));
			quest.given = true;

			Dungeon.journal.add(new Record(Feature.GHOST,
					Dungeon.depth));
		}
	}

	@Override
	public String description() {
		return Game.getVar(R.string.Ghost_Desc);
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Paralysis.class);
		IMMUNITIES.add(Roots.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
