package com.forney.pixeldungeon.time;

import com.watabou.pixeldungeon.utils.GLog;


public class Time {
	private int time;
	private int tick = 1;
	private int turn = 10;
	
	public Time() {
		time = 0;
	}

	public int getTime() {
		return time;
	}
	
	public void step() {
		time += tick;
		if(time % 10 == 0) {
			GLog.i("Time: " + time);
		}
	}
	
	public int getTurnLength() {
		return turn;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
}
