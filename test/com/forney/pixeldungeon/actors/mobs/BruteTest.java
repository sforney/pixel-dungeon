package com.forney.pixeldungeon.actors.mobs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.actors.mobs.Brute;
import com.watabou.pixeldungeon.utils.StringResolver;

@RunWith(JUnit4.class)
public class BruteTest {
	private StringResolver resolver;
	
	@Before
	public void setUp() {
		resolver = new TestStringResolver();
	}
	
	@Test
	public void createTest() {
		@SuppressWarnings("unused")
		Brute brute = new Brute(resolver);
	}
	
	@Test
	public void testDamageRoll() {
		Brute brute = new Brute(resolver);
		brute.setEnraged(true);
		MobTestUtils.testDamageRoll(10, 40, brute);
	}
	
	@Test
	public void testEnragedDamageRoll() {
		Brute brute = new Brute(resolver);
		MobTestUtils.testDamageRoll(8, 18, brute);		
	}
	
	@Test
	public void testDr() {
		Brute brute = new Brute(resolver);
		assertEquals(8, brute.dr());	
	}
	
	@Test
	public void testAttackSkill() {
		Brute brute = new Brute(resolver);
		assertEquals(20, brute.attackSkill(null));	
	}
}
