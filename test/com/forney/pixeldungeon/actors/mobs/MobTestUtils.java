package com.forney.pixeldungeon.actors.mobs;

import static org.junit.Assert.assertTrue;

import com.watabou.pixeldungeon.actors.mobs.Mob;

public class MobTestUtils {
	public static void testDamageRoll(int min, int max, Mob mob){
		for(int i = 0; i < 1000; i++) {
			int damage = mob.damageRoll();	
			assertTrue("Damage roll was: " + damage, damage >= min && damage <= max);
		}
	}
}
