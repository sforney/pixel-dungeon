package com.watabou.pixeldungeon.items.potions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.watabou.pixeldungeon.Badges;
import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.pixeldungeon.utils.DefaultStringResolver;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.utils.Bundle;

public class PotionInfo {
	private StringResolver resolver;
	private Random random;

	private static final String PFX_IMAGE = "_image";
	private static final String PFX_LABEL = "_label";
	private static final String PFX_KNOWN = "_known";

	private Map<PotionType, Integer> images = new HashMap<PotionType, Integer>();
	private Map<PotionType, String> labels = new HashMap<PotionType, String>();
	private Set<PotionType> known = new HashSet<PotionType>();

	private String[] colors;

	private final Integer[] imagesClasses = { ItemSpriteSheet.POTION_TURQUOISE,
			ItemSpriteSheet.POTION_CRIMSON, ItemSpriteSheet.POTION_AZURE,
			ItemSpriteSheet.POTION_JADE, ItemSpriteSheet.POTION_GOLDEN,
			ItemSpriteSheet.POTION_MAGENTA, ItemSpriteSheet.POTION_CHARCOAL,
			ItemSpriteSheet.POTION_IVORY, ItemSpriteSheet.POTION_AMBER,
			ItemSpriteSheet.POTION_BISTRE, ItemSpriteSheet.POTION_INDIGO,
			ItemSpriteSheet.POTION_SILVER };

	public PotionInfo() {
		resolver = new DefaultStringResolver();
		random = new Random();
		init();
	}

	public PotionInfo(StringResolver resolver, Random random) {
		this.resolver = resolver;
		this.random = random;
		init();
	}

	private void init() {
		colors = resolver.getVars(R.array.Potion_Colors);
	}

	public void randomize() {
		List<String> labelsLeft = new ArrayList<String>(Arrays.asList(colors));
		List<Integer> imagesLeft = new ArrayList<Integer>(
				Arrays.asList(imagesClasses));

		for (PotionType type : PotionType.values()) {
			int index = random.nextInt(labelsLeft.size());

			labels.put(type, labelsLeft.get(index));
			labelsLeft.remove(index);

			images.put(type, imagesLeft.get(index));
			imagesLeft.remove(index);
		}
	}

	public void save(Bundle bundle) {
		for (PotionType type : PotionType.values()) {
			String itemName = type.name();
			bundle.put(itemName + PFX_IMAGE, images.get(type));
			bundle.put(itemName + PFX_LABEL, labels.get(type));
			bundle.put(itemName + PFX_KNOWN, known.contains(type));
		}
	}

	public void restore(Bundle bundle, StringResolver resolver) {
		ArrayList<String> labelsLeft = new ArrayList<String>(
				Arrays.asList(colors));
		ArrayList<Integer> imagesLeft = new ArrayList<Integer>(
				Arrays.asList(imagesClasses));

		for (PotionType type : PotionType.values()) {
			String itemName = type.name();

			if (bundle.contains(itemName + PFX_LABEL)) {
				String label = bundle.getString(itemName + PFX_LABEL);
				labels.put(type, label);
				labelsLeft.remove(label);

				Integer image = bundle.getInt(itemName + PFX_IMAGE);
				images.put(type, image);
				imagesLeft.remove(image);

				if (bundle.getBoolean(itemName + PFX_KNOWN)) {
					known.add(type);
				}
			} else {
				//If a new potion was added since the last save
				int index = random.nextInt(labelsLeft.size());

				labels.put(type, labelsLeft.get(index));
				labelsLeft.remove(index);

				images.put(type, imagesLeft.get(index));
				imagesLeft.remove(index);
			}
		}
	}

	public String getLabel(PotionType type) {
		return labels.get(type);
	}

	public Integer getImage(PotionType type) {
		return images.get(type);
	}

	public boolean isKnown(PotionType type) {
		return known.contains(type);
	}
	
	public Set<PotionType> getAllKnown() {
		return known;
	}

	public boolean isUnknown(PotionType type) {
		return !known.contains(type);
	}
	
	public Set<PotionType> getAllUnknown() {
		Set<PotionType> results = new HashSet<PotionType>();
		for(PotionType type : PotionType.values()) {
			if(!known.contains(type)) {
				results.add(type);
			}
		}
		return results;
	}

	public void know(Potion potion) {
		know(PotionType.getType(potion));
	}

	public void know(PotionType potion) {
		known.add(potion);
		if (known.size() == PotionType.values().length) {
			Badges.validateAllPotionsIdentified(true);
		}
	}
}
