package com.forney.pixeldungeon.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forney.test.utils.TestRandom;
import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.items.potions.PotionInfo;
import com.watabou.pixeldungeon.items.potions.PotionOfExperience;
import com.watabou.pixeldungeon.items.potions.PotionType;
import com.watabou.pixeldungeon.utils.StringResolver;

@RunWith(JUnit4.class)
public class PotionInfoTest {
	@Test
	public void testCreate() {
		@SuppressWarnings("unused")
		PotionInfo info = new PotionInfo(
				new TestStringResolver(), new TestRandom(2));
	}

	@Test
	public void testPotionShuffle() {
		PotionInfo info = new PotionInfo(
				new TestStringResolver(), new TestRandom(2));
		info.randomize();
		assertEquals("golden", info.getLabel(PotionType.Healing));
		assertEquals("azure", info.getLabel(PotionType.Experience));
		assertEquals("turquoise", info.getLabel(PotionType.ToxicGas));
		assertEquals("jade", info.getLabel(PotionType.LiquidFlame));
		assertEquals("magenta", info.getLabel(PotionType.Levitation));
		assertEquals("amber", info.getLabel(PotionType.Frost));
		assertEquals("indigo", info.getLabel(PotionType.Purity));
		assertEquals("charcoal", info.getLabel(PotionType.Invisibility));
		assertEquals("bistre", info.getLabel(PotionType.Might));
		assertEquals("crimson", info.getLabel(PotionType.ParalyticGas));
		assertEquals("silver", info.getLabel(PotionType.MindVision));
		assertEquals("ivory", info.getLabel(PotionType.Strength));
		assertEquals("indigo", info.getLabel(PotionType.Purity));
	}

	@Test
	public void testKnown() {
		PotionInfo info = new PotionInfo(
				new TestStringResolver(), new TestRandom(2));
		assertFalse(info.isKnown(PotionType.Experience));
		info.know(PotionType.Experience);
		assertTrue(info.isKnown(PotionType.Experience));
		
		info.getAllKnown().contains(PotionType.Experience);
	}
	
	@Test
	public void testAllKnown() {
		PotionInfo info = new PotionInfo(
				new TestStringResolver(), new TestRandom(2));
		assertFalse(info.isKnown(PotionType.Experience));
		info.know(PotionType.Experience);
		assertTrue(info.getAllKnown().contains(PotionType.Experience));		
	}

	@Test
	public void testKnownFromPotion() {
		StringResolver resolver = new TestStringResolver();
		PotionInfo info = new PotionInfo(resolver,
				new TestRandom(2));
		info.randomize();
		info.know(new PotionOfExperience(info, resolver));
		assertTrue(info.isKnown(PotionType.Experience));
	}
}
