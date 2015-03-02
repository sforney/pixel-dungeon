package com.watabou.pixeldungeon.items.wands;

public enum WandType {
	Teleportation(WandOfTeleportation.class),
	Slowness(WandOfSlowness.class),
	Firebolt(WandOfFirebolt.class),
	Poison(WandOfPoison.class),
	Regrowth(WandOfRegrowth.class),
	Blink(WandOfBlink.class),
	Lightning(WandOfLightning.class),
	Amok(WandOfAmok.class),
	Telekinesis(WandOfTelekinesis.class),
	Flock(WandOfFlock.class),
	Disintegration(WandOfDisintegration.class),
	Avalanche(WandOfAvalanche.class),
	MagicMissile(WandOfMagicMissile.class);
	
	private final Class<? extends Wand> clazz;
	
	private WandType(Class<? extends Wand> clazz) {
		this.clazz = clazz;
	}
	
	public Class<? extends Wand> getClazz() {
		return clazz;
	}
	
	public static WandType getType(Wand wand) {
		for(WandType type : values()) {
			if(type.getClazz() == wand.getClass()) {
				return type;
			}
		}
		return null;
	}
}
