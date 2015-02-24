package com.forney.pixeldungeon.actors.mobs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.watabou.pixeldungeon.actors.mobs.Crab;
import com.watabou.pixeldungeon.utils.TestStringResolver;

@RunWith(JUnit4.class)
public class CrabTest {
	@Test
	public void createTest() {
		@SuppressWarnings("unused")
		Crab crab = new Crab(new TestStringResolver());
	}
	
	@Test
	public void testDamageRoll() {
		Crab crab = new Crab(new TestStringResolver());
		MobTestUtils.testDamageRoll(3, 6, crab);
	}
	
	@Test
	public void testDr() {
		Crab crab = new Crab(new TestStringResolver());
		assertEquals(4, crab.dr());
	}
	
	@Test
	public void testAttackSkill() {
		Crab crab = new Crab(new TestStringResolver());
		assertEquals(12, crab.attackSkill(null));
	}
}
