package com.watabou.pixeldungeon.actors.mobs.npcs;

import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.sprites.SheepSprite;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.utils.Random;

public class Sheep extends NPC {
	private static String[] QUOTES;
	public float lifespan;
	private boolean initialized = false;
	
	public Sheep() {
		init();
	}

	public Sheep(StringResolver resolver) {
		super(resolver);
		init();
	}

	private void init() {
		QUOTES = resolver.getVars(R.array.WandOfFlock_SheepBaa);
		name = resolver.getVar(R.string.WandOfFlock_SheepName);
		spriteClass = SheepSprite.class;
	}

	@Override
	public boolean act() {
		if (initialized) {
			HP = 0;

			destroy();
			sprite.die();
		} else {
			initialized = true;
			spend(lifespan + Random.Float(2));
		}
		return true;
	}

	@Override
	public void takeDamage(int dmg, Object src) {
	}

	@Override
	public String description() {
		return resolver.getVar(R.string.WandOfFlock_SheepInfo);
	}

	@Override
	public void interact() {
		yell(Random.element(QUOTES));
	}

}
