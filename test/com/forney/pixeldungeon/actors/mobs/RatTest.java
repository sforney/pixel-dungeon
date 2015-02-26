package com.forney.pixeldungeon.actors.mobs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.actors.mobs.Rat;

@RunWith(JUnit4.class)
public class RatTest {
	@Test
	public void createTest() {
		@SuppressWarnings("unused")
		Rat rat = new Rat(new TestStringResolver());
	}
	
	@Test
	public void testDamageRoll() {
		Rat rat = new Rat(new TestStringResolver());
		MobTestUtils.testDamageRoll(1, 5, rat);
	}
	
	@Test
	public void testDr() {
		Rat rat = new Rat(new TestStringResolver());
		assertEquals(1, rat.dr());	
	}
	
	@Test
	public void testAttackSkill() {
		Rat rat = new Rat(new TestStringResolver());
		assertEquals(8, rat.attackSkill(null));	
	}
	
}
