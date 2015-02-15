package com.watabou.pixeldungeon.actors.mobs;

import java.util.HashSet;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.Blob;
import com.watabou.pixeldungeon.actors.blobs.Fire;
import com.watabou.pixeldungeon.actors.blobs.ToxicGas;
import com.watabou.pixeldungeon.actors.buffs.Amok;
import com.watabou.pixeldungeon.actors.buffs.Burning;
import com.watabou.pixeldungeon.actors.buffs.Sleep;
import com.watabou.pixeldungeon.actors.buffs.Terror;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.watabou.pixeldungeon.items.weapon.enchantments.Death;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.mechanics.Ballistica;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.BurningFistSprite;
import com.watabou.pixeldungeon.sprites.CharSprite;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.utils.Random;

public class BurningFist extends Mob {
	private Yog yog;

	public BurningFist(Yog yog) {
		super();
		this.yog = yog;
		yog.fistAdded();
		
		name = Game.getVar(R.string.Yog_NameBurningFist);
		spriteClass = BurningFistSprite.class;

		HP = HT = 200;
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
		return Random.NormalIntRange(20, 32);
	}

	@Override
	public int dr() {
		return 15;
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
	}

	@Override
	public boolean attack(Char enemy) {

		if (!Level.adjacent(pos, enemy.pos)) {
			spend(attackDelay());

			if (hit(this, enemy, true)) {

				int dmg = damageRoll();
				enemy.damage(dmg, this);

				enemy.sprite.bloodBurstA(sprite.center(), dmg);
				enemy.sprite.flash();

				if (!enemy.isAlive() && enemy == Dungeon.hero) {
					Dungeon.fail(Utils.format(
							Game.getVar(R.string.ResultDescriptions_Boss),
							name, Dungeon.depth));
					GLog.n(TXT_KILL, name);
				}
				return true;
			} else {
				enemy.sprite.showStatus(CharSprite.NEUTRAL,
						enemy.defenseVerb());
				return false;
			}
		} else {
			return super.attack(enemy);
		}
	}

	@Override
	public boolean act() {
		for (int i = 0; i < Level.NEIGHBOURS9.length; i++) {
			GameScene.add(Blob.seed(pos + Level.NEIGHBOURS9[i], 2,
					Fire.class));
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
		IMMUNITIES.add(Burning.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
