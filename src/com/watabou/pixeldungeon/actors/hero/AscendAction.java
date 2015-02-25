package com.watabou.pixeldungeon.actors.hero;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.buffs.Hunger;
import com.watabou.pixeldungeon.items.Amulet;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.scenes.InterlevelScene;
import com.watabou.pixeldungeon.scenes.SurfaceScene;
import com.watabou.pixeldungeon.windows.WndMessage;

public class AscendAction extends BaseHeroAction {
	private static final String TXT_LEAVE = Game.getVar(R.string.Hero_Leave);
	
	public AscendAction(Hero hero, int target) {
		super(hero, target);
	}

	@Override
	public boolean perform() {
		if (hero.pos == target && hero.pos == Dungeon.level.entrance) {
			if (Dungeon.depth == 1) {
				if (hero.belongings.getItem(Amulet.class) == null) {
					GameScene.show(new WndMessage(TXT_LEAVE));
					hero.ready();
				} else {
					Dungeon.win(Game.getVar(R.string.ResultDescriptions_Win));
					Dungeon.deleteGame(Dungeon.hero.heroClass, true);
					Game.switchScene(SurfaceScene.class);
				}
			} else {
				hero.curAction = null;

				Hunger hunger = hero.getBuff(Hunger.class);
				if (hunger != null && !hunger.isStarving()) {
					hunger.satisfy(-Hunger.STARVING / 10);
				}

				InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
				Game.switchScene(InterlevelScene.class);
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
