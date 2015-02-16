package com.forney.pixeldungeon.rooms;

import java.util.Arrays;

import com.watabou.utils.Random;

public class RectRoom extends Room {
	private boolean square;
	private int minDimension;
	private int maxDimension;
	
	public RectRoom(int minDimension, int maxDimension, boolean square) {
		this.minDimension = minDimension;
		this.maxDimension = maxDimension;
		this.square = square;
	}
	
	@Override
	public boolean[] generate() {
		width = Random.Int(minDimension, maxDimension + 1);
		if(square) {
			height = width;
		} else {
			height = Random.Int(minDimension, maxDimension + 1);
		}
		tiles = new boolean [width*height];
		Arrays.fill(tiles, true);
		return tiles;
	
	}

	public int getMinDimension() {
		return minDimension;
	}

	public void setMinDimension(int minDimension) {
		this.minDimension = minDimension;
	}

	public int getMaxDimension() {
		return maxDimension;
	}

	public void setMaxDimension(int maxDimension) {
		this.maxDimension = maxDimension;
	}
}
