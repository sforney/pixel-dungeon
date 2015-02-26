package com.forney.test.utils;

import java.util.Random;

public class TestRandom extends Random {
	private static final long serialVersionUID = 1L;

	public TestRandom() {
		setSeed(1);
	}

	public TestRandom(long seed) {
		setSeed(seed);
	}

	public float Float(float min, float max) {
		return min + nextFloat() * (max - min);
	}

	public float Float(float max) {
		return nextFloat() * max;
	}

	public float Float() {
		return nextFloat();
	}

	public int Int(int max) {
		return max > 0 ? nextInt(max) : 0;
	}

	public int Int(int min, int max) {
		return min + nextInt(max - min);
	}
}
