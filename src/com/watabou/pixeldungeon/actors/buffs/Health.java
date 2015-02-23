package com.watabou.pixeldungeon.actors.buffs;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class Health extends Buff {
	private final int STEP = 50;

	private int pos;

	@Override
	public boolean attachTo(Char target) {
		pos = target.pos;
		return super.attachTo(target);
	}

	@Override
	public boolean act() {
		if (target.pos != pos || target.HP >= target.HT) {
			detach();
		} else {
			target.HP = Math.min(target.HT, target.HP + target.HT / 10);
			target.sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
		}
		spend(STEP);
		return true;
	}

	@Override
	public int icon() {
		return BuffIndicator.HEALING;
	}

	@Override
	public String toString() {
		return Game.getVar(R.string.Sungrass_Buff);
	}

	private static final String POS = "pos";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(POS, pos);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		pos = bundle.getInt(POS);
	}
}
