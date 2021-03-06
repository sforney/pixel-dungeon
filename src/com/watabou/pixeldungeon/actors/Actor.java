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

import com.watabou.pixeldungeon.levels.LevelState;
import com.watabou.pixeldungeon.utils.DefaultStringResolver;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public abstract class Actor implements Bundlable {
	private static final String TIME = "time";
	private static final String ID = "id";
	public static final float TICK = LevelState.TICK;
	protected StringResolver resolver;
	
	private float time;
	private int id = 0;

	public abstract boolean act();

	public Actor() {
		resolver = new DefaultStringResolver();
	}
	
	public Actor(StringResolver resolver) {
		this.setResolver(resolver);
	}
	
	public void spend(float time) {
		this.time += time;
	}

	public void postpone(float time) {
		if (this.time < LevelState.getNow() + time) {
			this.time = LevelState.getNow() + time;
		}
	}

	public float cooldown() {
		return time - LevelState.getNow();
	}

	public void deactivate() {
		time = Float.MAX_VALUE;
	}

	public void onAdd() {

	}

	protected void onRemove() {
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(TIME, time);
		bundle.put(ID, id);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		time = bundle.getFloat(TIME);
		id = bundle.getInt(ID);
	}

	public int id() {
		if (id > 0) {
			return id;
		} else {
			int max = 0;
			for (Actor a : LevelState.getActors()) {
				if (a.id > max) {
					max = a.id;
				}
			}
			return (id = max + 1);
		}
	}

	/* protected */public void next() {
		LevelState.next(this);
	}

	public void remove() {
		LevelState.getActors().remove(this);
		onRemove();

		if (id > 0) {
			LevelState.getActorIds().remove(id);
		}
	}

	public Actor find(int id) {
		return LevelState.getActorIds().get(id);
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public StringResolver getResolver() {
		return resolver;
	}

	public void setResolver(StringResolver resolver) {
		this.resolver = resolver;
	}
}
