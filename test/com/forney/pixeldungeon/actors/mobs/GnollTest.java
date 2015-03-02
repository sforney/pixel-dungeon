package com.forney.pixeldungeon.actors.mobs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.actors.mobs.Gnoll;
import com.watabou.pixeldungeon.utils.StringResolver;

@RunWith(JUnit4.class)
public class GnollTest {
	StringResolver resolver;
	
	@Before
	public void setUp() {
		resolver = new TestStringResolver();
	}
	
	@Test
	public void createTest() {
		@SuppressWarnings("unused")
		Gnoll gnoll = new Gnoll(resolver);
	}
	
	@Test
	public void testDamageRoll() {
		Gnoll gnoll = new Gnoll(resolver);
		MobTestUtils.testDamageRoll(2, 5, gnoll);
	}
	
	@Test
	public void testDr() {
		Gnoll gnoll = new Gnoll(resolver);
		assertEquals(2, gnoll.dr());	
	}
	
	@Test
	public void testAttackSkill() {
		Gnoll gnoll = new Gnoll(resolver);
		assertEquals(11, gnoll.attackSkill(null));	
	}
}
