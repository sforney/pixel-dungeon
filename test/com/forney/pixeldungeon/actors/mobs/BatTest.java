package com.forney.pixeldungeon.actors.mobs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forney.test.utils.TestRandom;
import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.actors.mobs.Bat;
import com.watabou.pixeldungeon.items.potions.PotionInfo;
import com.watabou.pixeldungeon.utils.StringResolver;

@RunWith(JUnit4.class)
public class BatTest {
	StringResolver resolver;
	PotionInfo potionInfo;
	
	@Before
	public void setUp() {
		resolver = new TestStringResolver();
		potionInfo = new PotionInfo(resolver, new TestRandom(2));
		potionInfo.randomize();
	}
	
	@Test
	public void createTest() {
		@SuppressWarnings("unused")
		Bat bat = new Bat(potionInfo, resolver);
	}
	
	@Test
	public void testDamageRoll() {
		Bat bat = new Bat(potionInfo, resolver);
		MobTestUtils.testDamageRoll(6, 12, bat);
	}
	
	@Test
	public void testDr() {
		Bat bat = new Bat(potionInfo, resolver);
		assertEquals(4, bat.dr());	
	}
	
	@Test
	public void testAttackSkill() {
		Bat bat = new Bat(potionInfo, resolver);
		assertEquals(16, bat.attackSkill(null));	
	}
}
