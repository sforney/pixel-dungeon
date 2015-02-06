package com.watabou.pixeldungeon.event;

public abstract class Event {
	Object entity;
	
	public Event(Object entity) {
		this.entity = entity;
	}
}
