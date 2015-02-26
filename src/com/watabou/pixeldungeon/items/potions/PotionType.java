package com.watabou.pixeldungeon.items.potions;

public enum PotionType {
	Healing (PotionOfHealing.class),
	Experience(PotionOfExperience.class),
	ToxicGas(PotionOfToxicGas.class),
	LiquidFlame(PotionOfLiquidFlame.class),
	Strength(PotionOfStrength.class),
	Levitation(PotionOfLevitation.class),
	ParalyticGas(PotionOfParalyticGas.class),
	MindVision(PotionOfMindVision.class),
	Purity(PotionOfPurity.class),
	Invisibility(PotionOfInvisibility.class),
	Might(PotionOfMight.class),
	Frost(PotionOfFrost.class);

	private final Class<? extends Potion> clazz;
		
	private PotionType(Class<? extends Potion> clazz) {
		this.clazz = clazz;
	}
	
	public Class<? extends Potion> getClazz() {
		return clazz;
	}
	
	public static PotionType getType(Potion potion) {
		for(PotionType type : values()) {
			if(type.getClazz() == potion.getClass()) {
				return type;
			}
		}
		return null;
	}
}
