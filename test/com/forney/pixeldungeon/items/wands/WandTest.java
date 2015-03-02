package com.forney.pixeldungeon.items.wands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forney.test.utils.TestRandom;
import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.items.wands.Wand;
import com.watabou.pixeldungeon.items.wands.WandInfo;
import com.watabou.pixeldungeon.items.wands.WandOfAmok;
import com.watabou.pixeldungeon.items.wands.WandOfAvalanche;
import com.watabou.pixeldungeon.items.wands.WandOfBlink;
import com.watabou.pixeldungeon.items.wands.WandOfDisintegration;
import com.watabou.pixeldungeon.items.wands.WandOfFirebolt;
import com.watabou.pixeldungeon.items.wands.WandOfFlock;
import com.watabou.pixeldungeon.items.wands.WandOfLightning;
import com.watabou.pixeldungeon.items.wands.WandOfMagicMissile;
import com.watabou.pixeldungeon.items.wands.WandOfPoison;
import com.watabou.pixeldungeon.items.wands.WandOfRegrowth;
import com.watabou.pixeldungeon.items.wands.WandOfSlowness;
import com.watabou.pixeldungeon.items.wands.WandOfTelekinesis;
import com.watabou.pixeldungeon.items.wands.WandOfTeleportation;
import com.watabou.pixeldungeon.items.wands.WandType;
import com.watabou.pixeldungeon.utils.StringResolver;

@RunWith(JUnit4.class)
public class WandTest {
	private WandInfo wandInfo;
	private StringResolver resolver;
	
	@Before
	public void setUp() {
		resolver = new TestStringResolver();
		wandInfo = new WandInfo(resolver, new TestRandom(2));
		wandInfo.randomize();
	}
	
	@Test
	public void createTest() {
		@SuppressWarnings("unused")
		Wand wand = new WandOfAmok(wandInfo, resolver);
		wand = new WandOfAvalanche(wandInfo, resolver);
		wand = new WandOfBlink(wandInfo, resolver);
		wand = new WandOfDisintegration(wandInfo, resolver);
		wand = new WandOfFirebolt(wandInfo, resolver);
		wand = new WandOfFlock(wandInfo, resolver);
		wand = new WandOfLightning(wandInfo, resolver);
		wand = new WandOfMagicMissile(wandInfo, resolver);
		wand = new WandOfPoison(wandInfo, resolver);
		wand = new WandOfRegrowth(wandInfo, resolver);
		wand = new WandOfSlowness(wandInfo, resolver);
		wand = new WandOfTelekinesis(wandInfo, resolver);
		wand = new WandOfTeleportation(wandInfo, resolver);
	}
	
	/*
	 * Need to remove dependency in Quickslot
	@Test
	public void testIdentify() {
		Wand wand = new WandOfAmok(wandInfo, resolver);
		assertFalse(wandInfo.isKnown(WandType.Amok));
		assertFalse(wand.isIdentified());
		wand.identify();
		assertTrue(wand.isIdentified());
	}*/
}
