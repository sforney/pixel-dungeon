package com.forney.pixeldungeon.actors.mobs.npcs;

import org.junit.Test;

import com.watabou.pixeldungeon.actors.mobs.npcs.Blacksmith;
import com.watabou.pixeldungeon.utils.StringResolver;
import com.watabou.pixeldungeon.utils.TestStringResolver;

public class BlacksmithTest {
	@Test
	public void testCreate() {
		StringResolver resolver = new TestStringResolver();
		@SuppressWarnings("unused")
		Blacksmith smith = new Blacksmith(resolver);
	}
}
