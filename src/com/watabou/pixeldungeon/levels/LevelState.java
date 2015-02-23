package com.watabou.pixeldungeon.levels;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.Statistics;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.Blob;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.mobs.Mob;

/*
 * Current dumping ground for the static members in Actor.
 * Would eventually like to turn this into a non-static class
 * that can be passed around, but this works for now
 */
public class LevelState {
	public static final float TICK = 1.0f;
	private static HashSet<Actor> actors = new HashSet<Actor>();
	private static Actor currentActor;
	// Using a HashMap instead of SparseArray to cut dependency on Android
	// framework. Makes for easier testability
	private static Map<Integer, Actor> actorIds = new HashMap<Integer, Actor>();

	private static float now = 0;
	protected static Char[] chars = new Char[Level.LENGTH];

	public static void clear() {
		now = 0;

		Arrays.fill(chars, null);
		actors.clear();

		actorIds.clear();
	}

	public static void fixTime() {
		if (Dungeon.hero != null && actors.contains(Dungeon.hero)) {
			Statistics.duration += now;
		}

		float min = Float.MAX_VALUE;
		for (Actor a : actors) {
			if (a.getTime() < min) {
				min = a.getTime();
			}
		}
		for (Actor a : actors) {
			a.setTime(a.getTime() - min);
		}
		now = 0;
	}

	public static void init() {
		addDelayed(Dungeon.hero, -Float.MIN_VALUE);

		for (Mob mob : Dungeon.level.mobs) {
			add(mob);
		}

		for (Blob blob : Dungeon.level.blobs.values()) {
			add(blob);
		}

		currentActor = null;
	}

	public static void next(Actor actor) {
		if (currentActor == actor) {
			currentActor = null;
		}
	}

	public static void process() {
		if (currentActor != null) {
			return;
		}

		boolean doNext;

		do {
			now = Float.MAX_VALUE;
			currentActor = null;

			Arrays.fill(chars, null);

			for (Actor actor : actors) {
				if (actor.getTime() < now) {
					now = actor.getTime();
					currentActor = actor;
				}

				if (actor instanceof Char) {
					Char ch = (Char) actor;
					chars[ch.pos] = ch;
				}
			}

			if (currentActor != null) {
				if (currentActor instanceof Char
						&& ((Char) currentActor).sprite.isMoving) {
					// If it's character's turn to act, but its sprite
					// is moving, wait till the movement is over
					currentActor = null;
					break;
				}

				doNext = currentActor.act();
				if (doNext && !Dungeon.hero.isAlive()) {
					doNext = false;
					currentActor = null;
				}
			} else {
				doNext = false;
			}

		} while (doNext);
	}

	public static HashSet<Actor> getActors() {
		return actors;
	}

	public static void setActors(HashSet<Actor> actors) {
		LevelState.actors = actors;
	}

	public static Map<Integer, Actor> getActorIds() {
		return actorIds;
	}

	public static void setActorIds(Map<Integer, Actor> actorIds) {
		LevelState.actorIds = actorIds;
	}

	public static float getNow() {
		return now;
	}

	public static void setNow(float now) {
		LevelState.now = now;
	}

	public static Char findChar(int pos) {
		return chars[pos];
	}
	
	public static Char[] getChars() {
		return chars;
	}

	public static void setChars(Char[] chars) {
		LevelState.chars = chars;
	}
	
	public static void add(Actor actor, float time) {
		if (actors.contains(actor)) {
			return;
		}

		if (actor.getId() > 0) {
			actorIds.put(actor.getId(), actor);
		}

		actors.add(actor);
		actor.setTime(actor.getTime() + time);
		actor.onAdd();
		
		if(actor instanceof Char) {
			Char chararacter = (Char)actor;
			add(chararacter, time);
			chars[chararacter.pos] = chararacter;
			for (Buff buff : chararacter.buffs()) {
				add(chararacter);
				buff.onAdd();
			}
		}
	}
	
	public static void addDelayed(Actor actor, float delay) {
		add(actor, LevelState.getNow() + delay);
	}
	

	public static void add(Actor actor) {
		addDelayed(actor, LevelState.getNow());
	}
}
