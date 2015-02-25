package com.watabou.pixeldungeon.actors.mobs;

import java.util.HashSet;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.ToxicGas;
import com.watabou.pixeldungeon.actors.buffs.Amok;
import com.watabou.pixeldungeon.actors.buffs.BuffOps;
import com.watabou.pixeldungeon.actors.buffs.Ooze;
import com.watabou.pixeldungeon.actors.buffs.Poison;
import com.watabou.pixeldungeon.actors.buffs.Sleep;
import com.watabou.pixeldungeon.actors.buffs.Terror;
import com.watabou.pixeldungeon.actors.buffs.Vertigo;
import com.watabou.pixeldungeon.effects.particles.ShadowParticle;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.watabou.pixeldungeon.items.weapon.enchantments.Death;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.sprites.RottingFistSprite;
import com.watabou.utils.Random;

public class RottingFist extends Mob {

	private static final int REGENERATION = 4;
	private Yog yog;
	
	public RottingFist(Yog yog) {
		super();
		this.yog = yog;
		yog.fistAdded();
		
		name = Game.getVar(R.string.Yog_NameRottingFist);
		spriteClass = RottingFistSprite.class;

		HP = HT = 300;
		defenseSkill = 25;

		EXP = 0;

		state = WANDERING;
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		yog.fistDied();
	}

	@Override
	public int attackSkill(Char target) {
		return 36;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(24, 36);
	}

	@Override
	public int dr() {
		return 15;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0) {
			BuffOps.affect(enemy, Ooze.class);
			enemy.sprite.burst(0xFF000000, 5);
		}

		return damage;
	}

	@Override
	public boolean act() {

		if (Level.water[pos] && HP < HT) {
			sprite.emitter().burst(ShadowParticle.UP, 2);
			HP += REGENERATION;
		}

		return super.act();
	}

	@Override
	public String description() {
		return Game.getVar(R.string.Yog_Desc);

	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Sleep.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(Poison.class);
		IMMUNITIES.add(Vertigo.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
