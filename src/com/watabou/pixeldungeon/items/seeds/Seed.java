package com.watabou.pixeldungeon.items.seeds;

import java.util.ArrayList;

import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.pixeldungeon.plants.Plant;
import com.watabou.pixeldungeon.utils.Utils;

public class Seed extends Item {

	public static final String AC_PLANT = Game.getVar(R.string.Plant_ACPlant);
	private static final String TXT_INFO = Game.getVar(R.string.Plant_Info);
	protected static final String TXT_SEED = Game.getVar(R.string.Plant_Seed);

	private static final int TIME_TO_PLANT = 10;

	{
		stackable = true;
		defaultAction = AC_THROW;
	}

	protected Class<? extends Plant> plantClass;
	protected String plantName;

	public Class<? extends Item> alchemyClass;

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_PLANT);
		return actions;
	}

	@Override
	protected void onThrow(int cell) {
		if (Dungeon.level.map[cell] == Terrain.ALCHEMY || Level.pit[cell]) {
			super.onThrow(cell);
		} else {
			Dungeon.level.plant(this, cell);
		}
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_PLANT)) {

			hero.spend(TIME_TO_PLANT);
			hero.busy();
			((Seed) detach(hero.belongings.backpack)).onThrow(hero.pos);

			hero.sprite.operate(hero.pos);

		} else {

			super.execute(hero, action);

		}
	}

	public Plant couch(int pos) {
		try {
			if (Dungeon.visible[pos]) {
				Sample.INSTANCE.play(Assets.SND_PLANT);
			}
			Plant plant = plantClass.newInstance();
			plant.pos = pos;
			return plant;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int price() {
		return 10 * quantity;
	}

	@Override
	public String info() {
		return String.format(TXT_INFO, Utils.indefinite(plantName), desc());
	}
}
