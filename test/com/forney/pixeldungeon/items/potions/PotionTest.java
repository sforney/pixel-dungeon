package com.forney.pixeldungeon.items.potions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forney.test.utils.TestRandom;
import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.items.potions.Potion;
import com.watabou.pixeldungeon.items.potions.PotionInfo;
import com.watabou.pixeldungeon.items.potions.PotionOfExperience;
import com.watabou.pixeldungeon.items.potions.PotionOfFrost;
import com.watabou.pixeldungeon.items.potions.PotionOfHealing;
import com.watabou.pixeldungeon.items.potions.PotionOfInvisibility;
import com.watabou.pixeldungeon.items.potions.PotionOfLevitation;
import com.watabou.pixeldungeon.items.potions.PotionOfLiquidFlame;
import com.watabou.pixeldungeon.items.potions.PotionOfMight;
import com.watabou.pixeldungeon.items.potions.PotionOfMindVision;
import com.watabou.pixeldungeon.items.potions.PotionOfParalyticGas;
import com.watabou.pixeldungeon.items.potions.PotionOfPurity;
import com.watabou.pixeldungeon.items.potions.PotionOfStrength;
import com.watabou.pixeldungeon.items.potions.PotionOfToxicGas;
import com.watabou.pixeldungeon.utils.StringResolver;

@RunWith(JUnit4.class)
public class PotionTest {
	@Test
	public void testCreate() {
		StringResolver resolver = new TestStringResolver();
		PotionInfo potionInfo = new PotionInfo(resolver, new TestRandom(2));
		potionInfo.randomize();
		
		@SuppressWarnings("unused")
		Potion pot = new PotionOfHealing(potionInfo, resolver);
		pot = new PotionOfExperience(potionInfo, resolver);
		pot = new PotionOfFrost(potionInfo, resolver);
		pot = new PotionOfInvisibility(potionInfo, resolver);
		pot = new PotionOfLevitation(potionInfo, resolver);
		pot = new PotionOfLiquidFlame(potionInfo, resolver);
		pot = new PotionOfStrength(potionInfo, resolver);
		pot = new PotionOfMight(potionInfo, resolver);
		pot = new PotionOfMindVision(potionInfo, resolver);
		pot = new PotionOfParalyticGas(potionInfo, resolver);
		pot = new PotionOfToxicGas(potionInfo, resolver);
		pot = new PotionOfPurity(potionInfo, resolver);
	}
}
