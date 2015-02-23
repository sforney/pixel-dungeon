package com.forney.pixeldungeon.time;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TimeTest {
	@Test
	public void testStep() {
		Time time = new Time();
		assertEquals(0, time.getTime());
		time.step();
		assertEquals(1, time.getTime());
	}
}
