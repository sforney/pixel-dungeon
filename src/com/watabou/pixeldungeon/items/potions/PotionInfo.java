package com.watabou.pixeldungeon.items.potions;

import java.util.HashSet;

import com.watabou.pixeldungeon.Badges;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.items.ItemStatusHandler;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.utils.Bundle;

public class PotionInfo {
	private static final Class<?>[] potions = { PotionOfHealing.class,
			PotionOfExperience.class, PotionOfToxicGas.class,
			PotionOfLiquidFlame.class, PotionOfStrength.class,
			PotionOfParalyticGas.class, PotionOfLevitation.class,
			PotionOfMindVision.class, PotionOfPurity.class,
			PotionOfInvisibility.class, PotionOfMight.class,
			PotionOfFrost.class };
	private static String[] colors;

	private static final Integer[] images = { ItemSpriteSheet.POTION_TURQUOISE,
			ItemSpriteSheet.POTION_CRIMSON, ItemSpriteSheet.POTION_AZURE,
			ItemSpriteSheet.POTION_JADE, ItemSpriteSheet.POTION_GOLDEN,
			ItemSpriteSheet.POTION_MAGENTA, ItemSpriteSheet.POTION_CHARCOAL,
			ItemSpriteSheet.POTION_IVORY, ItemSpriteSheet.POTION_AMBER,
			ItemSpriteSheet.POTION_BISTRE, ItemSpriteSheet.POTION_INDIGO,
			ItemSpriteSheet.POTION_SILVER };

	private static ItemStatusHandler<Potion> handler;

	public static void initColors(StringResolver resolver) {
		colors = resolver.getVars(R.array.Potion_Colors);
		handler = new ItemStatusHandler<Potion>(
				(Class<? extends Potion>[]) potions, colors, images);
	}
	
	public static HashSet<Class<? extends Potion>> getKnown() {
		return handler.known();
	}

	public static HashSet<Class<? extends Potion>> getUnknown() {
		return handler.unknown();
	}

	public static boolean allKnown() {
		return handler.known().size() == potions.length;
	}
	
	public static void save(Bundle bundle) {
		handler.save(bundle);
	}

	@SuppressWarnings("unchecked")
	public static void restore(Bundle bundle, StringResolver resolver) {
		colors = resolver.getVars(R.array.Potion_Colors);
		handler = new ItemStatusHandler<Potion>(
				(Class<? extends Potion>[]) potions, colors, images, bundle);
	}
	
	public static int getImage(Potion potion) {
		return handler.image(potion);
	}
	
	public static String getLabel(Potion potion) {
		return handler.label(potion);
	}
	
	public static boolean isKnown(Potion potion) {
		return handler.isKnown(potion);
	}
	
	public static void know(Potion potion) {
		handler.know(potion);
		Badges.validateAllPotionsIdentified();
	}
}
