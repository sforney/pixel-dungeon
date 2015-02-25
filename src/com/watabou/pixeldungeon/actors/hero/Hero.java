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
package com.watabou.pixeldungeon.actors.hero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Badges;
import com.watabou.pixeldungeon.Bones;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.GamesInProgress;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Barkskin;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.BuffOps;
import com.watabou.pixeldungeon.actors.buffs.Burning;
import com.watabou.pixeldungeon.actors.buffs.Combo;
import com.watabou.pixeldungeon.actors.buffs.EarthrootArmor;
import com.watabou.pixeldungeon.actors.buffs.Fury;
import com.watabou.pixeldungeon.actors.buffs.GasesImmunity;
import com.watabou.pixeldungeon.actors.buffs.Hunger;
import com.watabou.pixeldungeon.actors.buffs.Light;
import com.watabou.pixeldungeon.actors.buffs.Paralysis;
import com.watabou.pixeldungeon.actors.buffs.Poison;
import com.watabou.pixeldungeon.actors.buffs.Regeneration;
import com.watabou.pixeldungeon.actors.buffs.SnipersMark;
import com.watabou.pixeldungeon.actors.buffs.Vertigo;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;
import com.watabou.pixeldungeon.effects.CheckedCell;
import com.watabou.pixeldungeon.effects.Flare;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.items.Ankh;
import com.watabou.pixeldungeon.items.DewVial;
import com.watabou.pixeldungeon.items.Heap;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.KindOfWeapon;
import com.watabou.pixeldungeon.items.armor.Armor;
import com.watabou.pixeldungeon.items.rings.RingOfAccuracy;
import com.watabou.pixeldungeon.items.rings.RingOfDetection;
import com.watabou.pixeldungeon.items.rings.RingOfElements;
import com.watabou.pixeldungeon.items.rings.RingOfEvasion;
import com.watabou.pixeldungeon.items.rings.RingOfHaste;
import com.watabou.pixeldungeon.items.rings.RingOfShadows;
import com.watabou.pixeldungeon.items.rings.RingOfThorns;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfRecharging;
import com.watabou.pixeldungeon.items.wands.Wand;
import com.watabou.pixeldungeon.items.weapon.melee.MeleeWeapon;
import com.watabou.pixeldungeon.items.weapon.missiles.MissileWeapon;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.LevelState;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.CharSprite;
import com.watabou.pixeldungeon.sprites.HeroSprite;
import com.watabou.pixeldungeon.ui.AttackIndicator;
import com.watabou.pixeldungeon.ui.BuffIndicator;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.windows.WndResurrect;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Hero extends Char {
	private static final String TXT_LEVEL_UP = Game
			.getVar(R.string.Hero_LevelUp);
	private static final String TXT_NEW_LEVEL = Game
			.getVar(R.string.Hero_NewLevel);
	private static final String TXT_NOTICED_SMTH = Game
			.getVar(R.string.Hero_NoticedSmth);
	private static final String TXT_WAIT = Game.getVar(R.string.Hero_Wait);
	private static final String TXT_SEARCH = Game.getVar(R.string.Hero_Search);

	public static final int STARTING_STR = 10;

	private static final float TIME_TO_REST = 1f;
	private static final float TIME_TO_SEARCH = 2f;

	public HeroClass heroClass = HeroClass.ROGUE;
	public HeroSubClass subClass = HeroSubClass.NONE;

	private int attackSkill = 10;
	private int defenseSkill = 5;

	public boolean ready = false;

	public BaseHeroAction curAction = null;
	public BaseHeroAction lastAction = null;

	private Char enemy;

	public Armor.Glyph killerGlyph = null;

	public boolean restoreHealth = false;

	public MissileWeapon rangedWeapon = null;
	public Belongings belongings;

	public int STR;
	public boolean weakened = false;

	public float awareness;

	public int lvl = 1;
	public int exp = 0;

	private ArrayList<Mob> visibleEnemies;

	public Hero() {
		super();

		name = Game.getVar(R.string.Hero_Name);

		HP = HT = 20;
		STR = STARTING_STR;
		awareness = 0.1f;

		belongings = new Belongings(this);

		visibleEnemies = new ArrayList<Mob>();
	}

	public int STR() {
		return weakened ? STR - 2 : STR;
	}

	private static final String ATTACK = "attackSkill";
	private static final String DEFENSE = "defenseSkill";
	private static final String STRENGTH = "STR";
	private static final String LEVEL = "lvl";
	private static final String EXPERIENCE = "exp";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);

		heroClass.storeInBundle(bundle);
		subClass.storeInBundle(bundle);

		bundle.put(ATTACK, attackSkill);
		bundle.put(DEFENSE, defenseSkill);

		bundle.put(STRENGTH, STR);

		bundle.put(LEVEL, lvl);
		bundle.put(EXPERIENCE, exp);

		belongings.storeInBundle(bundle);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		heroClass = HeroClass.restoreInBundle(bundle);
		subClass = HeroSubClass.restoreInBundle(bundle);

		attackSkill = bundle.getInt(ATTACK);
		defenseSkill = bundle.getInt(DEFENSE);

		STR = bundle.getInt(STRENGTH);
		updateAwareness();

		lvl = bundle.getInt(LEVEL);
		exp = bundle.getInt(EXPERIENCE);

		belongings.restoreFromBundle(bundle);
	}

	public static void preview(GamesInProgress.Info info, Bundle bundle) {
		info.level = bundle.getInt(LEVEL);
	}

	public String className() {
		return subClass == null || subClass == HeroSubClass.NONE ? heroClass
				.title() : subClass.title();
	}

	public void live() {
		BuffOps.affect(this, Regeneration.class);
		BuffOps.affect(this, Hunger.class);
	}

	public int tier() {
		return belongings.armor == null ? 0 : belongings.armor.tier;
	}

	public boolean shoot(Char enemy, MissileWeapon wep) {

		rangedWeapon = wep;
		boolean result = attack(enemy);
		rangedWeapon = null;

		return result;
	}

	@Override
	public int attackSkill(Char target) {

		int bonus = 0;
		for (Buff buff : getBuffs(RingOfAccuracy.Accuracy.class)) {
			bonus += ((RingOfAccuracy.Accuracy) buff).level;
		}
		float accuracy = (bonus == 0) ? 1 : (float) Math.pow(1.4, bonus);
		if (rangedWeapon != null && Level.distance(pos, target.pos) == 1) {
			accuracy *= 0.5f;
		}

		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		if (wep != null) {
			return (int) (attackSkill * accuracy * wep.acuracyFactor(this));
		} else {
			return (int) (attackSkill * accuracy);
		}
	}

	@Override
	public int defenseSkill(Char enemy) {

		int bonus = 0;
		for (Buff buff : getBuffs(RingOfEvasion.Evasion.class)) {
			bonus += ((RingOfEvasion.Evasion) buff).level;
		}
		float evasion = bonus == 0 ? 1 : (float) Math.pow(1.2, bonus);
		if (paralysed) {
			evasion /= 2;
		}

		int aEnc = belongings.armor != null ? belongings.armor.STR - STR() : 0;

		if (aEnc > 0) {
			return (int) (defenseSkill * evasion / Math.pow(1.5, aEnc));
		} else {

			if (heroClass == HeroClass.ROGUE) {

				if (curAction != null && subClass == HeroSubClass.FREERUNNER
						&& !isStarving()) {
					evasion *= 2;
				}

				return (int) ((defenseSkill - aEnc) * evasion);
			} else {
				return (int) (defenseSkill * evasion);
			}
		}
	}

	@Override
	public int dr() {
		int dr = belongings.armor != null ? Math.max(belongings.armor.DR, 0)
				: 0;
		Barkskin barkskin = getBuff(Barkskin.class);
		if (barkskin != null) {
			dr += barkskin.level();
		}
		return dr;
	}

	@Override
	public int damageRoll() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		int dmg;
		if (wep != null) {
			dmg = wep.damageRoll(this);
		} else {
			dmg = STR() > 10 ? Random.IntRange(1, STR() - 9) : 1;
		}
		return getBuff(Fury.class) != null ? (int) (dmg * 1.5f) : dmg;
	}

	@Override
	public float speed() {
		int aEnc = belongings.armor != null ? belongings.armor.STR - STR() : 0;
		if (aEnc > 0) {
			return (float) (super.speed() * Math.pow(1.3, -aEnc));
		} else {
			float speed = super.speed();
			return ((HeroSprite) sprite)
					.sprint(subClass == HeroSubClass.FREERUNNER
							&& !isStarving()) ? 1.6f * speed : speed;
		}
	}

	public float attackDelay() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		if (wep != null) {
			return wep.speedFactor(this);
		} else {
			return 1f;
		}
	}

	@Override
	public void spend(float time) {
		int hasteLevel = 0;
		for (Buff buff : getBuffs(RingOfHaste.Haste.class)) {
			hasteLevel += ((RingOfHaste.Haste) buff).level;
		}
		super.spend(hasteLevel == 0 ? time : (float) (time * Math.pow(1.1,
				-hasteLevel)));
	};

	public void spendAndNext(float time) {
		busy();
		spend(time);
		next();
	}

	@Override
	public boolean act() {
		super.act();
		if (paralysed) {
			curAction = null;
			spendAndNext(TICK);
			return false;
		}
		checkVisibleMobs();
		AttackIndicator.updateState();
		if (curAction == null) {
			if (restoreHealth) {
				if (isStarving() || HP >= HT) {
					restoreHealth = false;
				} else {
					spend(TIME_TO_REST);
					next();
					return false;
				}
			}
			ready();
			return false;
		} else {
			restoreHealth = false;
			ready = false;
			return curAction.perform();
		}
	}

	public void busy() {
		ready = false;
	}

	void ready() {
		sprite.idle();
		curAction = null;
		ready = true;

		GameScene.ready();
	}

	public void interrupt() {
		if (isAlive() && curAction != null && curAction.getTarget() != pos) {
			lastAction = curAction;
		}
		curAction = null;
	}

	public void resume() {
		curAction = lastAction;
		lastAction = null;
		act();
	}

	public void rest(boolean tillHealthy) {
		spendAndNext(TIME_TO_REST);
		if (!tillHealthy) {
			sprite.showStatus(CharSprite.DEFAULT, TXT_WAIT);
		}
		restoreHealth = tillHealthy;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		if (wep != null) {

			wep.proc(this, enemy, damage);

			switch (subClass) {
			case GLADIATOR:
				if (wep instanceof MeleeWeapon) {
					damage += BuffOps.affect(this, Combo.class).hit(enemy, damage);
				}
				break;
			case BATTLEMAGE:
				if (wep instanceof Wand) {
					Wand wand = (Wand) wep;
					if (wand.curCharges >= wand.maxCharges) {

						wand.use();

					} else if (damage > 0) {

						wand.curCharges++;
						wand.updateQuickslot();

						ScrollOfRecharging.charge(this);
					}
					damage += wand.curCharges;
				}
			case SNIPER:
				if (rangedWeapon != null) {
					BuffOps.prolong(this, SnipersMark.class, attackDelay() * 1.1f).object = enemy
							.id();
				}
				break;
			default:
			}
		}

		return damage;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		RingOfThorns.Thorns thorns = getBuff(RingOfThorns.Thorns.class);
		if (thorns != null) {
			int dmg = Random.IntRange(0, damage);
			if (dmg > 0) {
				enemy.takeDamage(dmg, thorns);
			}
		}

		EarthrootArmor armor = getBuff(EarthrootArmor.class);
		if (armor != null) {
			damage = armor.absorb(damage);
		}

		if (belongings.armor != null) {
			damage = belongings.armor.proc(enemy, this, damage);
		}

		return damage;
	}

	@Override
	public void takeDamage(int dmg, Object src) {
		restoreHealth = false;
		super.takeDamage(dmg, src);

		if (subClass == HeroSubClass.BERSERKER && 0 < HP
				&& HP <= HT * Fury.LEVEL) {
			BuffOps.affect(this, Fury.class);
		}
	}

	private void checkVisibleMobs() {
		ArrayList<Mob> visible = new ArrayList<Mob>();
		boolean newMob = false;
		for (Mob m : Dungeon.level.mobs) {
			if (Level.fieldOfView[m.pos] && m.hostile) {
				visible.add(m);
				if (!visibleEnemies.contains(m)) {
					newMob = true;
				}
			}
		}
		if (newMob) {
			interrupt();
			restoreHealth = false;
		}
		visibleEnemies = visible;
	}

	public int visibleEnemies() {
		return visibleEnemies.size();
	}

	public Mob visibleEnemy(int index) {
		return visibleEnemies.get(index % visibleEnemies.size());
	}

	public boolean handle(int cell) {
		if (cell == -1) {
			return false;
		}

		Char ch;
		Heap heap;
		if (Dungeon.level.map[cell] == Terrain.ALCHEMY && cell != pos) {
			curAction = new CookAction(this, cell);
		} else if (Level.fieldOfView[cell]
				&& (ch = LevelState.findChar(cell)) instanceof Mob) {
			if (ch instanceof NPC) {
				curAction = new InteractAction(this, cell, (NPC) ch);
			} else {
				curAction = new AttackAction(this, cell, ch);
			}
		} else if (Level.fieldOfView[cell]
				&& (heap = Dungeon.level.heaps.get(cell)) != null) {
			switch (heap.type) {
			case HEAP:
				curAction = new PickupAction(this, cell);
				break;
			case FOR_SALE:
				curAction = heap.size() == 1 && heap.peek().price() > 0 ? new BuyAction(
						this, cell) : new PickupAction(this, cell);
				break;
			default:
				curAction = new OpenChestAction(this, cell);
			}
		} else if (Dungeon.level.map[cell] == Terrain.LOCKED_DOOR
				|| Dungeon.level.map[cell] == Terrain.LOCKED_EXIT) {
			curAction = new UnlockAction(this, cell);
		} else if (cell == Dungeon.level.exit) {
			curAction = new DescendAction(this, cell);
		} else if (cell == Dungeon.level.entrance) {
			curAction = new AscendAction(this, cell);
		} else {
			curAction = new MoveAction(this, cell);
		}
		lastAction = null;
		
		return act();
	}

	public void earnExp(int exp) {

		this.exp += exp;

		boolean levelUp = false;
		while (this.exp >= maxExp()) {
			this.exp -= maxExp();
			lvl++;

			HT += 5;
			HP += 5;
			attackSkill++;
			defenseSkill++;

			if (lvl < 10) {
				updateAwareness();
			}

			levelUp = true;
		}

		if (levelUp) {
			GLog.p(TXT_NEW_LEVEL, lvl);
			sprite.showStatus(CharSprite.POSITIVE, TXT_LEVEL_UP);
			Sample.INSTANCE.play(Assets.SND_LEVELUP);

			Badges.validateLevelReached();
		}

		if (subClass == HeroSubClass.WARLOCK) {

			int value = Math.min(HT - HP, 1 + (Dungeon.depth - 1) / 5);
			if (value > 0) {
				HP += value;
				sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			}

			((Hunger) getBuff(Hunger.class)).satisfy(10);
		}
	}

	public int maxExp() {
		return 5 + lvl * 5;
	}

	void updateAwareness() {
		awareness = (float) (1 - Math.pow((heroClass == HeroClass.ROGUE ? 0.85
				: 0.90), (1 + Math.min(lvl, 9)) * 0.5));
	}

	public boolean isStarving() {
		return ((Hunger) getBuff(Hunger.class)).isStarving();
	}

	@Override
	public void add(Buff buff) {
		super.add(buff);

		if (sprite != null) {
			if(!buff.getText().isEmpty()) {
				GLog.w(buff.getText());
			}
			if (buff instanceof Burning) {
				interrupt();
			} else if (buff instanceof Paralysis) {
				interrupt();
			} else if (buff instanceof Poison) {
				interrupt();
			} else if (buff instanceof Fury) {
				sprite.showStatus(CharSprite.POSITIVE,
						Game.getVar(R.string.Hero_StaFurious));
			} else if (buff instanceof Vertigo) {
				interrupt();
			} else if (buff instanceof Light) {
				sprite.add(CharSprite.State.ILLUMINATED);
			}
		}

		BuffIndicator.refreshHero();
	}

	@Override
	public void remove(Buff buff) {
		super.remove(buff);

		if (buff instanceof Light) {
			sprite.remove(CharSprite.State.ILLUMINATED);
		}

		BuffIndicator.refreshHero();
	}

	@Override
	public int stealth() {
		int stealth = super.stealth();
		for (Buff buff : getBuffs(RingOfShadows.Shadows.class)) {
			stealth += ((RingOfShadows.Shadows) buff).level;
		}
		return stealth;
	}

	@Override
	public void die(Object cause) {

		curAction = null;

		DewVial.autoDrink(this);
		if (isAlive()) {
			new Flare(8, 32).color(0xFFFF66, true).show(sprite, 2f);
			return;
		}

		LevelState.fixTime();
		super.die(cause);

		Ankh ankh = (Ankh) belongings.getItem(Ankh.class);
		if (ankh == null) {

			reallyDie(cause);

		} else {

			Dungeon.deleteGame(Dungeon.hero.heroClass, false);
			GameScene.show(new WndResurrect(ankh, cause));

		}
	}

	public static void reallyDie(Object cause) {

		int length = Level.LENGTH;
		int[] map = Dungeon.level.map;
		boolean[] visited = Dungeon.level.visited;
		boolean[] discoverable = Level.discoverable;

		for (int i = 0; i < length; i++) {

			int terr = map[i];

			if (discoverable[i]) {

				visited[i] = true;
				if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {
					Level.set(i, Terrain.discover(terr));
					GameScene.updateMap(i);
				}
			}
		}

		Bones.leave();

		Dungeon.observe();

		Dungeon.hero.belongings.identify();

		int pos = Dungeon.hero.pos;

		ArrayList<Integer> passable = new ArrayList<Integer>();
		for (Integer ofs : Level.NEIGHBOURS8) {
			int cell = pos + ofs;
			if ((Level.passable[cell] || Level.avoid[cell])
					&& Dungeon.level.heaps.get(cell) == null) {
				passable.add(cell);
			}
		}
		Collections.shuffle(passable);

		ArrayList<Item> items = new ArrayList<Item>(
				Dungeon.hero.belongings.backpack.items);
		for (Integer cell : passable) {
			if (items.isEmpty()) {
				break;
			}

			Item item = Random.element(items);
			Dungeon.level.drop(item, cell).sprite.drop(pos);
			items.remove(item);
		}

		GameScene.gameOver();

		if (cause instanceof Hero.Doom) {
			((Hero.Doom) cause).onDeath();
		}

		Dungeon.deleteGame(Dungeon.hero.heroClass, true);
	}

	@Override
	public void move(int step) {
		super.move(step);

		if (!flying) {

			if (Level.water[pos]) {
				Sample.INSTANCE.play(Assets.SND_WATER, 1, 1,
						Random.Float(0.8f, 1.25f));
			} else {
				Sample.INSTANCE.play(Assets.SND_STEP);
			}
			Dungeon.level.press(pos, this);
		}
	}

	@Override
	public void onMotionComplete() {
		super.onMotionComplete();
	}
	
	@Override
	public void onAttackComplete() {
		super.onAttackComplete();
	}
	
	@Override
	public void onOperateComplete() {
		super.onOperateComplete();
	}

	public boolean search(boolean intentional) {

		boolean smthFound = false;

		int positive = 0;
		int negative = 0;
		for (Buff buff : getBuffs(RingOfDetection.Detection.class)) {
			int bonus = ((RingOfDetection.Detection) buff).level;
			if (bonus > positive) {
				positive = bonus;
			} else if (bonus < 0) {
				negative += bonus;
			}
		}
		int distance = 1 + positive + negative;

		float level = intentional ? (2 * awareness - awareness * awareness)
				: awareness;
		if (distance <= 0) {
			level /= 2 - distance;
			distance = 1;
		}

		int cx = pos % Level.WIDTH;
		int cy = pos / Level.WIDTH;
		int ax = cx - distance;
		if (ax < 0) {
			ax = 0;
		}
		int bx = cx + distance;
		if (bx >= Level.WIDTH) {
			bx = Level.WIDTH - 1;
		}
		int ay = cy - distance;
		if (ay < 0) {
			ay = 0;
		}
		int by = cy + distance;
		if (by >= Level.HEIGHT) {
			by = Level.HEIGHT - 1;
		}

		for (int y = ay; y <= by; y++) {
			for (int x = ax, p = ax + y * Level.WIDTH; x <= bx; x++, p++) {

				if (Dungeon.visible[p]) {

					if (intentional) {
						sprite.parent.addToBack(new CheckedCell(p));
					}

					if (Level.secret[p]
							&& (intentional || Random.Float() < level)) {

						int oldValue = Dungeon.level.map[p];

						GameScene.discoverTile(p, oldValue);

						Level.set(p, Terrain.discover(oldValue));

						GameScene.updateMap(p);

						ScrollOfMagicMapping.discover(p);

						smthFound = true;
					}
				}
			}
		}

		if (intentional) {
			sprite.showStatus(CharSprite.DEFAULT, TXT_SEARCH);
			sprite.operate(pos);
			if (smthFound) {
				spendAndNext(Random.Float() < level ? TIME_TO_SEARCH
						: TIME_TO_SEARCH * 2);
			} else {
				spendAndNext(TIME_TO_SEARCH);
			}

		}

		if (smthFound) {
			GLog.w(TXT_NOTICED_SMTH);
			Sample.INSTANCE.play(Assets.SND_SECRET);
			interrupt();
		}

		return smthFound;
	}

	public void resurrect(int resetLevel) {

		HP = HT;
		Dungeon.gold = 0;
		exp = 0;

		belongings.resurrect(resetLevel);

		live();
	}

	@Override
	public HashSet<Class<?>> resistances() {
		RingOfElements.Resistance r = getBuff(RingOfElements.Resistance.class);
		return r == null ? super.resistances() : r.resistances();
	}

	@Override
	public HashSet<Class<?>> immunities() {
		GasesImmunity buff = getBuff(GasesImmunity.class);
		return buff == null ? super.immunities() : GasesImmunity.IMMUNITIES;
	}

	@Override
	public void next() {
		super.next();
	}

	public static interface Doom {
		public void onDeath();
	}

	public Char getEnemy() {
		return enemy;
	}

	public void setEnemy(Char enemy) {
		this.enemy = enemy;
	}
}
