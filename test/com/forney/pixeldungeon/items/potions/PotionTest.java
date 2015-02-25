package com.forney.pixeldungeon.items.potions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.watabou.pixeldungeon.items.potions.Potion;
import com.watabou.pixeldungeon.items.potions.PotionOfExperience;
import com.watabou.pixeldungeon.items.potions.PotionOfFrost;
import com.watabou.pixeldungeon.items.potions.PotionOfHealing;
import com.watabou.pixeldungeon.items.potions.PotionOfInvisibility;
import com.watabou.pixeldungeon.items.potions.PotionOfLevitation;
import com.watabou.pixeldungeon.items.potions.PotionOfLiquidFlame;
import com.watabou.pixeldungeon.items.potions.PotionOfMight;
import com.watabou.pixeldungeon.items.potions.PotionOfMindVision;
import com.watabou.pixeldungeon.items.potions.PotionOfParalyticGas;
import com.watabou.pixeldungeon.items.potions.PotionOfStrength;
import com.watabou.pixeldungeon.items.potions.PotionOfToxicGas;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.pixeldungeon.utils.TestStringResolver;

@RunWith(JUnit4.class)
public class PotionTest {
	@Test
	public void testCreate() {
		StringResolver resolver = new TestStringResolver();
		@SuppressWarnings("unused")
		Potion pot = new PotionOfHealing(resolver);
		pot = new PotionOfExperience(resolver);
		pot = new PotionOfFrost(resolver);
		pot = new PotionOfInvisibility(resolver);
		pot = new PotionOfLevitation(resolver);
		pot = new PotionOfLiquidFlame(resolver);
		pot = new PotionOfStrength(resolver);
		pot = new PotionOfMight(resolver);
		pot = new PotionOfMindVision(resolver);
		pot = new PotionOfParalyticGas(resolver);
		pot = new PotionOfToxicGas(resolver);
	}
	
}
