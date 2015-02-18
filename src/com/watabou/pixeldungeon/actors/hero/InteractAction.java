package com.watabou.pixeldungeon.actors.hero;

import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;
import com.watabou.pixeldungeon.levels.Level;

public class InteractAction extends BaseHeroAction {
	private NPC npc;
	
	public InteractAction(Hero hero, int target, NPC npc) {
		super(hero, target);
		this.npc = npc;
	}

	@Override
	public boolean perform() {
		if (Level.adjacent(hero.pos, npc.pos)) {
			hero.ready();
			hero.sprite.turnTo(hero.pos, npc.pos);
			npc.interact();
			return false;
		} else {
			if (Level.fieldOfView[npc.pos] && getCloser(npc.pos)) {
				return true;
			} else {
				hero.ready();
				return false;
			}
		}
	}
}
