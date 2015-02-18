package com.watabou.pixeldungeon.actors.hero;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.items.Heap;
import com.watabou.pixeldungeon.items.Heap.Type;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.windows.WndTradeItem;

public class BuyAction extends BaseHeroAction {

	public BuyAction(Hero hero, int target) {
		super(hero, target);
	}

	@Override
	public boolean perform() {
		if (hero.pos == target || Level.adjacent(hero.pos, target)) {
			hero.ready();

			Heap heap = Dungeon.level.heaps.get(target);
			if (heap != null && heap.type == Type.FOR_SALE && heap.size() == 1) {
				GameScene.show(new WndTradeItem(heap, true));
			}
			return false;
		} else if (getCloser(target)) {
			return true;
		} else {
			hero.ready();
			return false;
		}
	}
}
