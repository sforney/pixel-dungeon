package com.forney.pixeldungeon.actors.mobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forney.test.utils.TestRandom;
import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.actors.buffs.Burning;
import com.watabou.pixeldungeon.actors.mobs.Elemental;
import com.watabou.pixeldungeon.items.potions.PotionInfo;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.watabou.pixeldungeon.items.wands.WandOfFirebolt;
import com.watabou.pixeldungeon.items.weapon.enchantments.Fire;
import com.watabou.pixeldungeon.utils.StringResolver;

@RunWith(JUnit4.class)
public class ElementalTest {
	private PotionInfo potionInfo;
	private StringResolver resolver;
	
	@Before
	public void setUp() {
		resolver = new TestStringResolver();
		potionInfo = new PotionInfo(resolver, new TestRandom(2));
		potionInfo.randomize();
	}
	
	@Test
	public void createTest() {
		@SuppressWarnings("unused")
		Elemental elemental = new Elemental(resolver, potionInfo);
	}
	
	@Test
	public void testDamageRoll() {
		Elemental elemental = new Elemental(resolver, potionInfo);
		MobTestUtils.testDamageRoll(16, 20, elemental);
	}
	
	@Test
	public void testDr() {
		Elemental elemental = new Elemental(resolver, potionInfo);
		assertEquals(5, elemental.dr());
	}
	
	@Test
	public void testAttackSkill() {
		Elemental elemental = new Elemental(resolver, potionInfo);
		assertEquals(25, elemental.attackSkill(null));
	}
	
	@Test
	public void testImmunities() {
		Elemental elemental = new Elemental(resolver, potionInfo);
		HashSet<Class<?>> set = elemental.immunities();
		assertTrue(set.contains(Burning.class));
		assertTrue(set.contains(Fire.class));
		assertTrue(set.contains(WandOfFirebolt.class));
		assertTrue(set.contains(ScrollOfPsionicBlast.class));
		assertTrue(set.size() == 4);
	}
}
