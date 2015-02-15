package com.watabou.pixeldungeon.actors.mobs;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.sprites.LarvaSprite;
import com.watabou.utils.Random;

public class Larva extends Mob {
	public Larva(){
		name = Game.getVar(R.string.Yog_NameLarva);
		spriteClass = LarvaSprite.class;

		HP = HT = 25;
		defenseSkill = 20;

		EXP = 0;

		state = HUNTING;
	}

	@Override
	public int attackSkill(Char target) {
		return 30;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 20);
	}

	@Override
	public int dr() {
		return 8;
	}

	@Override
	public String description() {
		return Game.getVar(R.string.Yog_Desc);

	}
}
