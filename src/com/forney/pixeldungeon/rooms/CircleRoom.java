package com.forney.pixeldungeon.rooms;

import java.util.Arrays;

import com.watabou.utils.Random;

public class CircleRoom extends Room {
	private int x0 = 0;
	private int y0 = 0;
	private int minRadius;
	private int maxRadius;
	private int radius;

	public CircleRoom(int minRadius, int maxRadius) {
		this.minRadius = minRadius;
		this.maxRadius = maxRadius;
	}

	public boolean [] generateRoom() {
		radius = Random.Int(minRadius, maxRadius);
		// area of the array is (2r+1)^2. Not every square will be used.
		int radiusSquared = radius * radius; //precalc for performance		
		int radius2 = 2 * radius;
		
		height = width = (radius2 + 1);
		tiles = new boolean[height * width];
		Arrays.fill(tiles, false);
		int offset = radius;

		/*
		 * The array starts at 0,0 and occupies the lower right quadrant.
		 * However, we want to draw the circle at 0,0. So we translate it using
		 * an offset so that it's centered in the array.
		 */
		for (int y = 0; y < (radius2 + 1); y++) {
			for (int x = 0; x < (radius2 + 1); x++) {
				int dx = x0 - offset + x; // horizontal offset
				int dy = y0 + offset - y; // vertical offset
				if ((dx * dx + dy * dy) <= radiusSquared) {
					tiles[y*(radius2+1) + x] = true;
					System.out.print("*");
				} else {
					System.out.print("_");
				}
			}
			System.out.println();
		}
		return tiles;
	}

	public static void main(String[] args) {
		CircleRoom room = new CircleRoom(10, 10);
		room.generateRoom();
	}
}
