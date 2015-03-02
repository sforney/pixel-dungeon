package com.forney.pixeldungeon.items.wands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forney.test.utils.TestRandom;
import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.items.wands.WandInfo;
import com.watabou.pixeldungeon.items.wands.WandOfAmok;
import com.watabou.pixeldungeon.items.wands.WandType;
import com.watabou.pixeldungeon.utils.StringResolver;

@RunWith(JUnit4.class)
public class WandInfoTest {
	@Test
	public void testCreate() {
		@SuppressWarnings("unused")
		WandInfo info = new WandInfo(new TestStringResolver(),
				new TestRandom(2));
	}
	
	@Test
	public void testWandShuffle() {
		WandInfo info = new WandInfo(
				new TestStringResolver(), new TestRandom(2));
		info.randomize();
		assertEquals("birch", info.getLabel(WandType.Amok));
		assertEquals("bamboo", info.getLabel(WandType.Avalanche));
		assertEquals("rowan", info.getLabel(WandType.Blink));
		assertEquals("purpleheart", info.getLabel(WandType.Disintegration));
		assertEquals("holly", info.getLabel(WandType.Firebolt));
		assertEquals("willow", info.getLabel(WandType.Flock));
		assertEquals("yew", info.getLabel(WandType.Lightning));
		assertEquals("cherry", info.getLabel(WandType.Poison));
		assertEquals("mahogany", info.getLabel(WandType.Regrowth));
		assertEquals("ebony", info.getLabel(WandType.Slowness));
		assertEquals("oak", info.getLabel(WandType.Telekinesis));
		assertEquals("teak", info.getLabel(WandType.Teleportation));
	}
	
	@Test
	public void testKnown() {
		WandInfo info = new WandInfo(
				new TestStringResolver(), new TestRandom(2));
		assertFalse(info.isKnown(WandType.Amok));
		info.know(WandType.Amok);
		assertTrue(info.isKnown(WandType.Amok));
		
		info.getAllKnown().contains(WandType.Amok);
	}
	
	@Test
	public void testAllKnown() {
		WandInfo info = new WandInfo(
				new TestStringResolver(), new TestRandom(2));
		assertFalse(info.isKnown(WandType.Amok));
		info.know(WandType.Amok);
		assertTrue(info.getAllKnown().contains(WandType.Amok));		
	}

	@Test
	public void testKnownFromPotion() {
		StringResolver resolver = new TestStringResolver();
		WandInfo info = new WandInfo(resolver,
				new TestRandom(2));
		info.randomize();
		info.know(new WandOfAmok(info, resolver));
		assertTrue(info.isKnown(WandType.Amok));
	}
}
