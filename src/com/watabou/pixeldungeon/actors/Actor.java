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
package com.watabou.pixeldungeon.actors;

import java.util.Arrays;
import java.util.HashSet;

import android.util.SparseArray;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.Statistics;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public abstract class Actor implements Bundlable {
	protected int nextAction;
	private int id = 0;
	private static final String ID = "id";

	// **********************
	// *** Static members ***

	private static HashSet<Actor> all = new HashSet<Actor>();
	private static Actor current;

	private static SparseArray<Actor> ids = new SparseArray<Actor>();

	private static Char[] chars = new Char[Level.LENGTH];

	protected abstract boolean act();

	protected void spend(int time) {
		this.nextAction += time;
	}
	
	protected void spendTurn() {
		this.nextAction += Dungeon.level.time.getTurnLength();
	}

	protected void postpone(int time) {
		if (this.nextAction < Dungeon.level.time.getTime() + time) {
			this.nextAction = Dungeon.level.time.getTime() + time;
		}
	}
	
	protected void postponeTurn() {
		postpone(Dungeon.level.time.getTurnLength());
	}

	protected float cooldown() {
		return nextAction - Dungeon.level.time.getTime();
	}

	protected void deactivate() {
		nextAction = Integer.MAX_VALUE;
	}

	protected void onAdd() {

	}

	protected void onRemove() {
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(ID, id);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		id = bundle.getInt(ID);
	}

	public int id() {
		if (id > 0) {
			return id;
		} else {
			int max = 0;
			for (Actor a : all) {
				if (a.id > max) {
					max = a.id;
				}
			}
			return (id = max + 1);
		}
	}

	public static void clear() {
		Arrays.fill(chars, null);
		all.clear();

		ids.clear();
	}

	public static void fixTime() {
		if (Dungeon.hero != null && all.contains(Dungeon.hero)) {
			Statistics.duration += Dungeon.level.time.getTime();
		}

		int min = Integer.MAX_VALUE;
		for (Actor a : all) {
			if (a.nextAction < min) {
				min = a.nextAction;
			}
		}
		for (Actor a : all) {
			a.nextAction -= min;
		}
		Dungeon.level.time.setTime(0);
	}

	public static void init() {

		addDelayed(Dungeon.hero, Dungeon.level.time.getTime());

		for (Mob mob : Dungeon.level.mobs) {
			add(mob);
		}

		current = null;
	}

	public static void occupyCell(Char ch) {
		chars[ch.pos] = ch;
	}

	public static void freeCell(int pos) {
		chars[pos] = null;
	}

	public static void process() {
		boolean actorProcessed = false;
		for(Actor actor : all) {
			if (actor.nextAction <= Dungeon.level.time.getTime()) {
				actor.act();
				if (actor instanceof Char) {
					Char ch = (Char) actor;
					chars[ch.pos] = ch;
				}
				actorProcessed = true;
			}
		}
		if(actorProcessed == false && Dungeon.hero.isAlive()) {
			Dungeon.level.time.step();
			Dungeon.level.getRespawner().act(Dungeon.level.nMobs());
		}
	}

	public static void processOld() {

		if (current != null) {
			return;
		}

		boolean doNext;

		Dungeon.level.time.step();
		do {
			current = null;

			Arrays.fill(chars, null);

			for (Actor actor : all) {
				if (actor.nextAction < Dungeon.level.time.getTime()) {
					current = actor;
				}

				if (actor instanceof Char) {
					Char ch = (Char) actor;
					chars[ch.pos] = ch;
				}
			}

			if (current != null) {
				if (current instanceof Char && ((Char) current).sprite.isMoving) {
					// If it's character's turn to act, but its sprite
					// is moving, wait till the movement is over
					current = null;
					break;
				}
				GLog.i("Current actor is " + current.getClass().getName());
				doNext = current.act();
				if (doNext && !Dungeon.hero.isAlive()) {
					doNext = false;
					current = null;
				}
			} else {
				doNext = false;				
			}
		} while (doNext);
	}

	public static void add(Actor actor) {
		if(Dungeon.level != null && Dungeon.level.time != null) {
			add(actor, Dungeon.level.time.getTime());
		} else {
			add(actor, 0);
		}
	}

	public static void addDelayed(Actor actor, int delay) {
		add(actor, Dungeon.level.time.getTime() + delay);
	}

	private static void add(Actor actor, int time) {
		if (all.contains(actor)) {
			return;
		}

		if (actor.id > 0) {
			ids.put(actor.id, actor);
		}

		all.add(actor);
		if(Dungeon.level != null && Dungeon.level.time != null) {
			actor.nextAction = Dungeon.level.time.getTime();
		}
		actor.nextAction += time;
		actor.onAdd();

		if (actor instanceof Char) {
			Char ch = (Char) actor;
			chars[ch.pos] = ch;
			for (Buff buff : ch.buffs()) {
				all.add(buff);
				buff.onAdd();
			}
		}
	}

	public static void remove(Actor actor) {

		if (actor != null) {
			all.remove(actor);
			actor.onRemove();

			if (actor.id > 0) {
				ids.remove(actor.id);
			}
		}
	}

	public static Char findChar(int pos) {
		return chars[pos];
	}

	public static Actor findById(int id) {
		return ids.get(id);
	}

	public static HashSet<Actor> all() {
		return all;
	}
}
