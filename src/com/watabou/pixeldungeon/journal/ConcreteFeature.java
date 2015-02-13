package com.watabou.pixeldungeon.journal;

import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.R;

/**
 * This should probably be replaced with something that allows for
 * localizations
 * @author Shawn
 *
 */
public enum ConcreteFeature {
	WELL_OF_HEALTH, WELL_OF_AWARENESS, WELL_OF_TRANSMUTATION, ALCHEMY, GARDEN, STATUE,
	GHOST, WANDMAKER, TROLL, IMP;

	public String getDescription(ConcreteFeature feature) {
		switch (feature) {
		case WELL_OF_HEALTH:
			return PixelDungeon.resources.getString(R.string.well_health);
		case WELL_OF_AWARENESS:
			return PixelDungeon.resources.getString(R.string.well_awareness);
		case WELL_OF_TRANSMUTATION:
			return PixelDungeon.resources.getString(R.string.well_transmute);
		case ALCHEMY:
			return PixelDungeon.resources.getString(R.string.alchemy_pot);
		case GARDEN:
			return PixelDungeon.resources.getString(R.string.garden);
		case STATUE:
			return PixelDungeon.resources.getString(R.string.statue);
		case GHOST:
			return PixelDungeon.resources.getString(R.string.sad_ghost);
		case WANDMAKER:
			return PixelDungeon.resources.getString(R.string.wandmaker);
		case TROLL:
			return PixelDungeon.resources.getString(R.string.blacksmith);
		case IMP:
			return PixelDungeon.resources.getString(R.string.imp);
		}
		// Should never get here
		return PixelDungeon.resources.getString(R.string.invalid_feature);
	}
}
