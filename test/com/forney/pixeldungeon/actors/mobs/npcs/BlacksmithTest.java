package com.forney.pixeldungeon.actors.mobs.npcs;

import org.junit.Test;

import com.forney.test.utils.TestStringResolver;
import com.watabou.pixeldungeon.actors.mobs.npcs.Blacksmith;
import com.watabou.pixeldungeon.utils.StringResolver;

public class BlacksmithTest {
	@Test
	public void testCreate() {
		StringResolver resolver = new TestStringResolver();
		@SuppressWarnings("unused")
		Blacksmith smith = new Blacksmith(resolver);
	}
}
