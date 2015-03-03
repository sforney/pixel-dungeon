package com.watabou.pixeldungeon.items.wands;

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

public class WandInfo {
	private StringResolver resolver;
	private Random random;

	private static final String PFX_IMAGE = "_image";
	private static final String PFX_LABEL = "_label";
	private static final String PFX_KNOWN = "_known";

	private Map<WandType, Integer> images = new HashMap<WandType, Integer>();
	private Map<WandType, String> labels = new HashMap<WandType, String>();
	private Set<WandType> known = new HashSet<WandType>();

	private String[] woods;

	private static final Integer[] imagesClasses = {
			ItemSpriteSheet.WAND_HOLLY, ItemSpriteSheet.WAND_YEW,
			ItemSpriteSheet.WAND_EBONY, ItemSpriteSheet.WAND_CHERRY,
			ItemSpriteSheet.WAND_TEAK, ItemSpriteSheet.WAND_ROWAN,
			ItemSpriteSheet.WAND_WILLOW, ItemSpriteSheet.WAND_MAHOGANY,
			ItemSpriteSheet.WAND_BAMBOO, ItemSpriteSheet.WAND_PURPLEHEART,
			ItemSpriteSheet.WAND_OAK, ItemSpriteSheet.WAND_BIRCH};
	private static final Integer magicMissileImage = ItemSpriteSheet.WAND_MAGIC_MISSILE;

	public WandInfo() {
		resolver = new DefaultStringResolver();
		random = new Random();
		init();
	}

	public WandInfo(StringResolver resolver, Random random) {
		this.resolver = resolver;
		this.random = random;
		init();
	}

	private void init() {
		woods = resolver.getVars(R.array.Wand_Wood);
		know(WandType.MagicMissile);
	}

	public void randomize() {
		List<String> labelsLeft = new ArrayList<String>(Arrays.asList(woods));
		List<Integer> imagesLeft = new ArrayList<Integer>(
				Arrays.asList(imagesClasses));

		for (WandType type : WandType.values()) {
			if(type == WandType.MagicMissile) {
				continue;
			}
			int index = random.nextInt(labelsLeft.size());

			labels.put(type, labelsLeft.get(index));
			labelsLeft.remove(index);

			images.put(type, imagesLeft.get(index));
			imagesLeft.remove(index);
		}
	}

	public void save(Bundle bundle) {
		for (WandType type : WandType.values()) {
			String itemName = type.name();
			bundle.put(itemName + PFX_IMAGE, images.get(type));
			bundle.put(itemName + PFX_LABEL, labels.get(type));
			bundle.put(itemName + PFX_KNOWN, known.contains(type));
		}
	}

	public void restore(Bundle bundle, StringResolver resolver) {
		ArrayList<String> labelsLeft = new ArrayList<String>(
				Arrays.asList(woods));
		ArrayList<Integer> imagesLeft = new ArrayList<Integer>(
				Arrays.asList(imagesClasses));

		for (WandType type : WandType.values()) {
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
			} else if(type.equals(WandType.MagicMissile)) {
				images.put(type, magicMissileImage);
			} else {
				// If a new wand was added since the last save
				int index = random.nextInt(labelsLeft.size());

				labels.put(type, labelsLeft.get(index));
				labelsLeft.remove(index);

				images.put(type, imagesLeft.get(index));
				imagesLeft.remove(index);
			}
		}
	}

	public String getLabel(WandType type) {
		return labels.get(type);
	}

	public Integer getImage(WandType type) {
		if(type == WandType.MagicMissile) {
			return magicMissileImage;
		} else {
			return images.get(type);
		}
	}

	public boolean isKnown(WandType type) {
		return known.contains(type);
	}

	public Set<WandType> getAllKnown() {
		return known;
	}

	public boolean isUnknown(WandType type) {
		return !known.contains(type);
	}

	public Set<WandType> getAllUnknown() {
		Set<WandType> results = new HashSet<WandType>();
		for (WandType type : WandType.values()) {
			if (!known.contains(type)) {
				results.add(type);
			}
		}
		return results;
	}

	public void know(Wand wand) {
		know(WandType.getType(wand));
	}

	public void know(WandType wand) {
		known.add(wand);
		if (known.size() == WandType.values().length) {
			Badges.validateAllPotionsIdentified(true);
		}
	}
}
