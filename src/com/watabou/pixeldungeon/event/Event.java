package com.watabou.pixeldungeon.event;

public abstract class Event {
	protected Object entity;
	
	public Event(Object entity) {
		this.entity = entity;
	}
}
