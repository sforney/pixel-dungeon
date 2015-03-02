package com.watabou.pixeldungeon.journal;

import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.utils.StringResolver;

public enum Feature {
	WELL_OF_HEALTH(R.string.Journal_WellHealth), 
	WELL_OF_AWARENESS(R.string.Journal_WellAwareness),
	WELL_OF_TRANSMUTATION(R.string.Journal_WellTransmute),
	ALCHEMY(R.string.Journal_Alchemy),
	GARDEN(R.string.Journal_Garden),
	STATUE(R.string.Journal_Statue),
	GHOST(R.string.Journal_Ghost), 
	WANDMAKER(R.string.Journal_Wandmaker), 
	TROLL(R.string.Journal_Troll), 
	IMP(R.string.Journal_Imp);

	private int descriptionId;
	
	private Feature(int descriptionId) {
		this.descriptionId = descriptionId;
	}
	
	public String getDescription(StringResolver resolver) {
		return(resolver.getVar(descriptionId));
	}
}
