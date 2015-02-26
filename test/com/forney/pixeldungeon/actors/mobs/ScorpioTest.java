package com.forney.pixeldungeon.actors.mobs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forney.test.utils.TestRandom;
import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.mobs.Scorpio;
import com.watabou.pixeldungeon.items.potions.PotionInfo;
import com.watabou.pixeldungeon.utils.StringResolver;

@RunWith(JUnit4.class)
public class ScorpioTest {
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
		Actor actor = new Scorpio(potionInfo, resolver);
		actor.getTime();
	}

	@Test
	public void testDamageRoll() {
		Scorpio scorpio = new Scorpio(potionInfo, resolver);
		MobTestUtils.testDamageRoll(20, 32, scorpio);
	}

	@Test
	public void testDr() {
		Scorpio scorpio = new Scorpio(potionInfo, resolver);
		assertEquals(16, scorpio.dr());
	}

	@Test
	public void testAttackSkill() {
		Scorpio scorpio = new Scorpio(potionInfo, resolver);
		Scorpio enemy = new Scorpio(potionInfo, resolver);
		assertEquals(36, scorpio.attackSkill(enemy));
	}

	/*
	 * Need to break more dependencies for this one
	 * 
	 * @Test public void testDamage() { Scorpio scorpio = new Scorpio(new
	 * TestStringResolver()); int originalHp = scorpio.HP; Scorpio enemy = new
	 * Scorpio(new TestStringResolver()); scorpio.damage(10, enemy);
	 * assertTrue(scorpio.HP == originalHp - 10); }
	 */
}
