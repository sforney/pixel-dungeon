package com.forney.pixeldungeon.actors.mobs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.actors.mobs.Golem;
import com.watabou.pixeldungeon.utils.StringResolver;

@RunWith(JUnit4.class)
public class GolemTest {
	StringResolver resolver;	

	@Before
	public void setUp() {
		resolver = new TestStringResolver();
	}
	
	@Test
	public void createTest() {
		@SuppressWarnings("unused")
		Golem golem = new Golem(resolver);
	}
	
	@Test
	public void testDamageRoll() {
		Golem golem = new Golem(resolver);
		MobTestUtils.testDamageRoll(20, 40, golem);
	}
	
	@Test
	public void testDr() {
		Golem golem = new Golem(resolver);
		assertEquals(12, golem.dr());	
	}
	
	@Test
	public void testAttackSkill() {
		Golem golem = new Golem(resolver);
		assertEquals(28, golem.attackSkill(null));	
	}
}
