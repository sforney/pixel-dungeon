/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon.items.potions;

import java.util.ArrayList;

import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.effects.Splash;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.ItemSprite;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.pixeldungeon.windows.WndOptions;

public class Potion extends Item {

	public static String AC_DRINK;

	private static String TXT_HARMFUL;
	private static String TXT_BENEFICIAL;
	private static String TXT_YES;
	private static String TXT_NO;
	private static String TXT_R_U_SURE_DRINK;
	private static String TXT_R_U_SURE_THROW;

	private static final float TIME_TO_DRINK = 1f;
	private PotionInfo potionInfo;
	private PotionType type;

	protected String color;

	/*
	 * This constructor is still needed as loading uses reflection with a zero-arg
	 * constructor to recreate potions (and everything else).  Remove once that
	 * system has been replaced with something that doesn't use reflection
	 */
	public Potion() {
		this.potionInfo = Dungeon.potionInfo;
		init();
	}
	
	public Potion(PotionInfo potionInfo) {
		super();
		this.potionInfo = potionInfo;
		init();
	}

	public Potion(PotionInfo potionInfo, StringResolver resolver) {
		super(resolver);
		this.potionInfo = potionInfo;
		init();
	}

	private void init() {
		TXT_HARMFUL = resolver.getVar(R.string.Potion_Harmfull);
		TXT_YES = resolver.getVar(R.string.Potion_Yes);
		TXT_NO = resolver.getVar(R.string.Potion_No);
		TXT_R_U_SURE_DRINK = resolver.getVar(R.string.Potion_SureDrink);
		TXT_R_U_SURE_THROW = resolver.getVar(R.string.Potion_SureThrow);
		AC_DRINK = resolver.getVar(R.string.Potion_ACDrink);

		type = PotionType.getType(this);
		image = potionInfo.getImage(type);
		color = potionInfo.getLabel(type);
		stackable = true;
		defaultAction = AC_DRINK;	
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_DRINK);
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals(AC_DRINK)) {

			if (isKnown()
					&& (this instanceof PotionOfLiquidFlame
							|| this instanceof PotionOfToxicGas || this instanceof PotionOfParalyticGas)) {

				GameScene.show(new WndOptions(TXT_HARMFUL, TXT_R_U_SURE_DRINK,
						TXT_YES, TXT_NO) {
					@Override
					protected void onSelect(int index) {
						if (index == 0) {
							drink(hero);
						}
					};
				});

			} else {
				drink(hero);
			}

		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public void doThrow(final Hero hero) {

		if (isKnown()
				&& (this instanceof PotionOfExperience
						|| this instanceof PotionOfHealing
						|| this instanceof PotionOfLevitation
						|| this instanceof PotionOfMindVision
						|| this instanceof PotionOfStrength
						|| this instanceof PotionOfInvisibility || this instanceof PotionOfMight)) {

			GameScene.show(new WndOptions(TXT_BENEFICIAL, TXT_R_U_SURE_THROW,
					TXT_YES, TXT_NO) {
				@Override
				protected void onSelect(int index) {
					if (index == 0) {
						Potion.super.doThrow(hero);
					}
				};
			});

		} else {
			super.doThrow(hero);
		}
	}

	protected void drink(Hero hero) {

		detach(hero.belongings.backpack);

		hero.spend(TIME_TO_DRINK);
		hero.busy();
		onThrow(hero.pos);

		Sample.INSTANCE.play(Assets.SND_DRINK);

		hero.sprite.operate(hero.pos);
	}

	@Override
	protected void onThrow(int cell) {
		if (Dungeon.hero.pos == cell) {

			apply(Dungeon.hero);

		} else if (Dungeon.level.map[cell] == Terrain.WELL || Level.pit[cell]) {

			super.onThrow(cell);

		} else {

			shatter(cell);

		}
	}

	protected void apply(Hero hero) {
		shatter(hero.pos);
	}

	public void shatter(int cell) {
		if (Dungeon.visible[cell]) {
			GLog.i(String.format(resolver.getVar(R.string.Potion_Shatter),
					color));
			Sample.INSTANCE.play(Assets.SND_SHATTER);
			splash(cell);
		}
	}

	public boolean isKnown() {
		return potionInfo.isKnown(type);
	}

	public void setKnown() {
		if (potionInfo.isUnknown(type)) {
			potionInfo.know(type);
		}	
	}

	@Override
	public Item identify() {
		setKnown();
		return this;
	}

	/*
	protected String color() {
		return color;
	}*/

	@Override
	public String name() {
		return isKnown() ? name : String.format(
				resolver.getVar(R.string.Potion_Name), color);
	}

	@Override
	public String info() {
		return isKnown() ? desc() : String.format(
				resolver.getVar(R.string.Potion_Info), color);
	}

	@Override
	public boolean isIdentified() {
		return isKnown();
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	protected void splash(int cell) {
		final int color = ItemSprite.pick(image, 8, 10);
		Splash.at(cell, color, 5);
	}

	@Override
	public int price() {
		return 20 * quantity;
	}
}
