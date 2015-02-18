package com.watabou.pixeldungeon.actors.hero;

import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.keys.IronKey;
import com.watabou.pixeldungeon.items.keys.Key;
import com.watabou.pixeldungeon.items.keys.SkeletonKey;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.utils.GLog;

public class UnlockAction extends BaseHeroAction {
	Item key = null;
	private static final String TXT_LOCKED_DOOR = Game
			.getVar(R.string.Hero_LockedDoor);
	
	public UnlockAction(Hero hero, int target) {
		super(hero, target);
	}

	@Override
	public boolean perform() {
		if (Level.adjacent(hero.pos, target)) {
			int door = Dungeon.level.map[target];
			if (door == Terrain.LOCKED_DOOR) {
				key = hero.belongings.getKey(IronKey.class, Dungeon.depth);
			} else if (door == Terrain.LOCKED_EXIT) {
				key = hero.belongings.getKey(SkeletonKey.class, Dungeon.depth);
			}
			if (key != null) {
				unlock();
			} else {
				GLog.w(TXT_LOCKED_DOOR);
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
	
	private void unlock() {
		int door = Dungeon.level.map[target];
		
		hero.sprite.operate(target);
		Sample.INSTANCE.play(Assets.SND_UNLOCK);
		hero.spend(Key.TIME_TO_UNLOCK);
		key.detach(hero.belongings.backpack);
		key = null;	
		Level.set(target, door == Terrain.LOCKED_DOOR ? Terrain.DOOR
				: Terrain.UNLOCKED_EXIT);
		GameScene.updateMap(target);
		hero.curAction = null;
	}
}
