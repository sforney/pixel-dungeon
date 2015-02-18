package com.watabou.pixeldungeon.actors.hero;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.items.Dewdrop;
import com.watabou.pixeldungeon.items.Heap;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.potions.Potion;
import com.watabou.pixeldungeon.items.potions.PotionOfMight;
import com.watabou.pixeldungeon.items.potions.PotionOfStrength;
import com.watabou.pixeldungeon.items.scrolls.Scroll;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfEnchantment;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.watabou.pixeldungeon.utils.GLog;

public class PickupAction extends BaseHeroAction {

	public static final String TXT_YOU_NOW_HAVE = Game
			.getVar(R.string.Hero_YouNowHave);
	private static final String TXT_SOMETHING_ELSE = Game
			.getVar(R.string.Hero_SomethingElse);

	public PickupAction(Hero hero, int target) {
		super(hero, target);
	}

	@Override
	public boolean perform() {
		if (hero.pos == target) {

			Heap heap = Dungeon.level.heaps.get(hero.pos);
			if (heap != null) {
				Item item = heap.pickUp();
				if (item.doPickUp(hero)) {
					if (item instanceof Dewdrop) {
						// Do nothing
					} else {
						boolean important = ((item instanceof ScrollOfUpgrade || item instanceof ScrollOfEnchantment) && ((Scroll) item)
								.isKnown())
								|| ((item instanceof PotionOfStrength || item instanceof PotionOfMight) && ((Potion) item)
										.isKnown());
						if (important) {
							GLog.p(TXT_YOU_NOW_HAVE, item.name());
						} else {
							GLog.i(TXT_YOU_NOW_HAVE, item.name());
						}
					}
					if (!heap.isEmpty()) {
						GLog.i(TXT_SOMETHING_ELSE);
					}
					hero.curAction = null;
				} else {
					Dungeon.level.drop(item, hero.pos).sprite.drop();
					hero.ready();
				}
			} else {
				hero.ready();
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