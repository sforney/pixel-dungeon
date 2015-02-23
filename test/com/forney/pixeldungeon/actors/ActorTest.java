package com.forney.pixeldungeon.actors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.watabou.pixeldungeon.actors.Actor;

@RunWith(JUnit4.class)
public class ActorTest {
	@SuppressWarnings("unused")
	@Test
	public void testCreate() {
		Actor a = new TestActor();
	}
}
