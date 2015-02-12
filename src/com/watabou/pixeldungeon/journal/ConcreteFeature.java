package com.watabou.pixeldungeon.journal;

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
			return "Well of Health";
		case WELL_OF_AWARENESS:
			return "Well of Awareness";
		case WELL_OF_TRANSMUTATION:
			return "Well of Transmutation";
		case ALCHEMY:
			return "Alchemy Pot";
		case GARDEN:
			return "Garden";
		case STATUE:
			return "Animated Statue";
		case GHOST:
			return "Sad Ghost";
		case WANDMAKER:
			return "Old Wandmaker";
		case TROLL:
			return "Troll Blacksmith";
		case IMP:
			return "Ambitious Imp";
		}
		// Should never get here
		return "Invalid Feature";
	}
}
