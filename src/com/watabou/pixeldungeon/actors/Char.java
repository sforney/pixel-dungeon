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

import java.util.HashSet;

import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.BuffOps;
import com.watabou.pixeldungeon.actors.buffs.Charm;
import com.watabou.pixeldungeon.actors.buffs.Cripple;
import com.watabou.pixeldungeon.actors.buffs.Frost;
import com.watabou.pixeldungeon.actors.buffs.Paralysis;
import com.watabou.pixeldungeon.actors.buffs.Slow;
import com.watabou.pixeldungeon.actors.buffs.Speed;
import com.watabou.pixeldungeon.actors.buffs.Vertigo;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.actors.hero.HeroSubClass;
import com.watabou.pixeldungeon.actors.mobs.Bestiary;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.LevelState;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.pixeldungeon.levels.features.Door;
import com.watabou.pixeldungeon.sprites.CharSprite;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

public abstract class Char extends Actor {
	protected String TXT_HIT;
	protected String TXT_KILL;
	protected String TXT_DEFEAT;
	private String TXT_YOU_MISSED;
	private String TXT_SMB_MISSED;
	private String TXT_OUT_OF_PARALYSIS;

	public Char() {
		init();
	}

	public Char(StringResolver resolver) {
		super(resolver);
		init();
	}

	private void init() {
		TXT_HIT = resolver.getVar(R.string.Char_Hit);
		TXT_KILL = resolver.getVar(R.string.Char_Kill);
		TXT_DEFEAT = resolver.getVar(R.string.Char_Defeat);
		TXT_YOU_MISSED = resolver.getVar(R.string.Char_YouMissed);
		TXT_SMB_MISSED = resolver.getVar(R.string.Char_SmbMissed);
		TXT_OUT_OF_PARALYSIS = resolver.getVar(R.string.Char_OutParalysis);
		name = resolver.getVar(R.string.Char_Name);
	}

	public int pos = 0;

	public CharSprite sprite;

	public String name;

	public int HT;
	public int HP;

	protected float baseSpeed = 1;

	public boolean paralysed = false;
	public boolean rooted = false;
	public boolean flying = false;
	public int invisible = 0;

	public int viewDistance = 8;

	private HashSet<Buff> buffs = new HashSet<Buff>();

	@Override
	public boolean act() {
		Dungeon.level.updateFieldOfView(this);
		return false;
	}

	private static final String POS = "pos";
	private static final String TAG_HP = "HP";
	private static final String TAG_HT = "HT";
	private static final String BUFFS = "buffs";

	@Override
	public void storeInBundle(Bundle bundle) {

		super.storeInBundle(bundle);

		bundle.put(POS, pos);
		bundle.put(TAG_HP, HP);
		bundle.put(TAG_HT, HT);
		bundle.put(BUFFS, buffs);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {

		super.restoreFromBundle(bundle);

		pos = bundle.getInt(POS);
		HP = bundle.getInt(TAG_HP);
		HT = bundle.getInt(TAG_HT);

		for (Bundlable b : bundle.getCollection(BUFFS)) {
			if (b != null) {
				((Buff) b).attachTo(this);
			}
		}
	}

	public boolean attack(Char enemy) {

		boolean visibleFight = Dungeon.visible[pos]
				|| Dungeon.visible[enemy.pos];

		if (hit(this, enemy, false)) {

			if (visibleFight) {
				GLog.i(TXT_HIT, name, enemy.name);
			}

			// FIXME
			int dr = this instanceof Hero && ((Hero) this).rangedWeapon != null
					&& ((Hero) this).subClass == HeroSubClass.SNIPER ? 0
					: Random.IntRange(0, enemy.dr());

			int dmg = damageRoll();
			int effectiveDamage = Math.max(dmg - dr, 0);

			effectiveDamage = attackProc(enemy, effectiveDamage);
			effectiveDamage = enemy.defenseProc(this, effectiveDamage);
			enemy.takeDamage(effectiveDamage, this);

			if (visibleFight) {
				Sample.INSTANCE.play(Assets.SND_HIT, 1, 1,
						Random.Float(0.8f, 1.25f));
			}

			if (enemy == Dungeon.hero) {
				Dungeon.hero.interrupt();
				if (effectiveDamage > enemy.HT / 4) {
					Camera.main.shake(GameMath.gate(1, effectiveDamage
							/ (enemy.HT / 4), 5), 0.3f);
				}
			}

			enemy.sprite.bloodBurstA(sprite.center(), effectiveDamage);
			enemy.sprite.flash();

			if (!enemy.isAlive() && visibleFight) {
				if (enemy == Dungeon.hero) {

					if (Dungeon.hero.killerGlyph != null) {

						// FIXME
						// Dungeon.fail( Utils.format( ResultDescriptions.GLYPH,
						// Dungeon.hero.killerGlyph.name(), Dungeon.depth ) );
						// GLog.n( TXT_KILL, Dungeon.hero.killerGlyph.name() );

					} else {
						if (Bestiary.isBoss(this)) {
							Dungeon.fail(Utils.format(resolver
									.getVar(R.string.ResultDescriptions_Boss),
									name, Dungeon.depth));
						} else {
							Dungeon.fail(Utils.format(resolver
									.getVar(R.string.ResultDescriptions_Mob),
									Utils.indefinite(name), Dungeon.depth));
						}

						GLog.n(TXT_KILL, name);
					}

				} else {
					GLog.i(TXT_DEFEAT, name, enemy.name);
				}
			}

			return true;

		} else {

			if (visibleFight) {
				String defense = enemy.defenseVerb();
				enemy.sprite.showStatus(CharSprite.NEUTRAL, defense);
				if (this == Dungeon.hero) {
					GLog.i(TXT_YOU_MISSED, enemy.name, defense);
				} else {
					GLog.i(TXT_SMB_MISSED, enemy.name, defense, name);
				}

				Sample.INSTANCE.play(Assets.SND_MISS);
			}

			return false;

		}
	}

	public static boolean hit(Char attacker, Char defender, boolean magic) {
		float acuRoll = Random.Float(attacker.attackSkill(defender));
		float defRoll = Random.Float(defender.defenseSkill(attacker));
		return (magic ? acuRoll * 2 : acuRoll) >= defRoll;
	}

	public void occupyCell() {
		LevelState.getChars()[pos] = this;
	}

	public void freeCell(int pos) {
		LevelState.getChars()[pos] = null;
	}

	public int attackSkill(Char target) {
		return 0;
	}

	public int defenseSkill(Char enemy) {
		return 0;
	}

	public String defenseVerb() {
		return resolver.getVar(R.string.Char_StaDodged);
	}

	public int dr() {
		return 0;
	}

	public int damageRoll() {
		return 1;
	}

	public int attackProc(Char enemy, int damage) {
		return damage;
	}

	public int defenseProc(Char enemy, int damage) {
		return damage;
	}

	public float speed() {
		return getBuff(Cripple.class) == null ? baseSpeed : baseSpeed * 0.5f;
	}

	public void takeDamage(int dmg, Object src) {

		if (HP <= 0) {
			return;
		}

		BuffOps.detach(this, Frost.class);

		Class<?> srcClass = src.getClass();
		if (immunities().contains(srcClass)) {
			dmg = 0;
		} else if (resistances().contains(srcClass)) {
			dmg = Random.IntRange(0, dmg);
		}

		if (getBuff(Paralysis.class) != null) {
			if (Random.Int(dmg) >= Random.Int(HP)) {
				BuffOps.detach(this, Paralysis.class);
				if (Dungeon.visible[pos]) {
					GLog.i(TXT_OUT_OF_PARALYSIS, name);
				}
			}
		}

		HP -= dmg;
		if (dmg > 0 || src instanceof Char) {
			sprite.showStatus(HP > HT / 2 ? CharSprite.WARNING
					: CharSprite.NEGATIVE, Integer.toString(dmg));
		}
		if (HP <= 0) {
			die(src);
		}
	}

	public void destroy() {
		HP = 0;
		remove();
		freeCell(pos);
	}

	public void die(Object src) {
		destroy();
		sprite.die();
	}

	public boolean isAlive() {
		return HP > 0;
	}

	@Override
	public void spend(float time) {

		float timeScale = 1f;
		if (getBuff(Slow.class) != null) {
			timeScale *= 0.5f;
		}
		if (getBuff(Speed.class) != null) {
			timeScale *= 2.0f;
		}

		super.spend(time / timeScale);
	}

	public HashSet<Buff> getBuffs() {
		return buffs;
	}

	@SuppressWarnings("unchecked")
	public <T extends Buff> HashSet<T> getBuffs(Class<T> c) {
		HashSet<T> filtered = new HashSet<T>();
		for (Buff b : buffs) {
			if (c.isInstance(b)) {
				filtered.add((T) b);
			}
		}
		return filtered;
	}

	@SuppressWarnings("unchecked")
	public <T extends Buff> T getBuff(Class<T> c) {
		for (Buff b : buffs) {
			if (c.isInstance(b)) {
				return (T) b;
			}
		}
		return null;
	}

	public boolean isCharmedBy(Char ch) {
		int chID = ch.id();
		for (Buff b : buffs) {
			if (b instanceof Charm && ((Charm) b).object == chID) {
				return true;
			}
		}
		return false;
	}

	public void add(Buff buff) {
		buffs.add(buff);
		LevelState.add(buff);

		if (sprite != null) {
			buff.onAttach();
		}
	}

	public void remove(Buff buff) {
		buffs.remove(buff);
		buff.remove();
		buff.onDetach();
	}

	public void remove(Class<? extends Buff> buffClass) {
		for (Buff buff : getBuffs(buffClass)) {
			remove(buff);
		}
	}

	@Override
	protected void onRemove() {
		for (Buff buff : buffs.toArray(new Buff[0])) {
			buff.detach();
		}
	}

	public void updateSpriteState() {
		for (Buff buff : buffs) {
			buff.onUpdateSprite();
		}
	}

	public int stealth() {
		return 0;
	}

	public void move(int step) {

		if (Level.adjacent(step, pos) && getBuff(Vertigo.class) != null) {
			step = pos + Level.NEIGHBOURS8[Random.Int(8)];
			if (!(Level.passable[step] || Level.avoid[step])
					|| LevelState.findChar(step) != null) {
				return;
			}
		}

		if (Dungeon.level.map[pos] == Terrain.OPEN_DOOR) {
			Door.leave(pos);
		}

		pos = step;

		if (flying && Dungeon.level.map[pos] == Terrain.DOOR) {
			Door.enter(pos);
		}

		if (this != Dungeon.hero) {
			sprite.visible = Dungeon.visible[pos];
		}
	}

	public int distance(Char other) {
		return Level.distance(pos, other.pos);
	}

	public void onMotionComplete() {
		next();
	}

	public void onAttackComplete() {
		next();
	}

	public void onOperateComplete() {
		next();
	}

	private static final HashSet<Class<?>> EMPTY = new HashSet<Class<?>>();

	public HashSet<Class<?>> resistances() {
		return EMPTY;
	}

	public HashSet<Class<?>> immunities() {
		return EMPTY;
	}
}
