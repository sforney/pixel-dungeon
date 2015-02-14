package com.watabou.pixeldungeon.journal;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.R;

public enum Feature {
	WELL_OF_HEALTH, WELL_OF_AWARENESS, WELL_OF_TRANSMUTATION, ALCHEMY, GARDEN, STATUE,
	GHOST, WANDMAKER, TROLL, IMP;

	public String getDescription(Feature feature) {
		switch (feature) {
		case WELL_OF_HEALTH:
			return Game.getVar(R.string.Journal_WellHealth);
		case WELL_OF_AWARENESS:
			return  Game.getVar(R.string.Journal_WellAwareness);
		case WELL_OF_TRANSMUTATION:
			return  Game.getVar(R.string.Journal_WellTransmute);
		case ALCHEMY:
			return  Game.getVar(R.string.Journal_Alchemy);
		case GARDEN:
			return  Game.getVar(R.string.Journal_Garden);
		case STATUE:
			return Game.getVar(R.string.Journal_Statue);
		case GHOST:
			return  Game.getVar(R.string.Journal_Ghost);
		case WANDMAKER:
			return  Game.getVar(R.string.Journal_Wandmaker);
		case TROLL:
			return  Game.getVar(R.string.Journal_Troll);
		case IMP:
			return  Game.getVar(R.string.Journal_Imp);
		}
		// Should never get here
		return null;
	}
}
