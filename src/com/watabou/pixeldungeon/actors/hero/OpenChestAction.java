package com.watabou.pixeldungeon.actors.hero;

import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.items.Heap;
import com.watabou.pixeldungeon.items.Heap.Type;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.keys.GoldenKey;
import com.watabou.pixeldungeon.items.keys.Key;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.utils.GLog;

public class OpenChestAction extends BaseHeroAction {
	Item key = null;
	private static final String TXT_LOCKED_CHEST = Game
			.getVar(R.string.Hero_LockedChest);

	public OpenChestAction(Hero hero, int target) {
		super(hero, target);
	}

	@Override
	public boolean perform() {
		if (Level.adjacent(hero.pos, target) || hero.pos == target) {
			Heap heap = Dungeon.level.heaps.get(target);
			if (heap != null
					&& (heap.type != Type.HEAP && heap.type != Type.FOR_SALE)) {
				if (heap.type == Type.LOCKED_CHEST
						|| heap.type == Type.CRYSTAL_CHEST) {

					key = hero.belongings
							.getKey(GoldenKey.class, Dungeon.depth);

					if (key == null) {
						GLog.w(TXT_LOCKED_CHEST);
						hero.ready();
						return false;
					} else {
						key.detach(hero.belongings.backpack);
						Sample.INSTANCE.play(Assets.SND_UNLOCK);
						key = null;
					}
					if (heap.type == Type.SKELETON) {
						Sample.INSTANCE.play(Assets.SND_BONES);
					}
					open(heap, hero);
				} else {
					switch (heap.type) {
					case TOMB:
						Sample.INSTANCE.play(Assets.SND_TOMB);
						Camera.main.shake(1, 0.5f);
						break;
					case SKELETON:
						break;
					default:
						Sample.INSTANCE.play(Assets.SND_UNLOCK);
					}
					open(heap, hero);
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
	
	private void open(Heap heap, Hero hero) {
		hero.sprite.operate(target);
		hero.spend(Key.TIME_TO_UNLOCK);
		heap.open(hero);
		hero.curAction = null;
	}
}
