package com.forney.pixeldungeon.actors.mobs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.mobs.Scorpio;
import com.watabou.pixeldungeon.utils.TestStringResolver;

@RunWith(JUnit4.class)
public class ScorpioTest {
	@Test
	public void createTest() {
		Actor actor = new Scorpio(new TestStringResolver());
		actor.getTime();
	}
}
