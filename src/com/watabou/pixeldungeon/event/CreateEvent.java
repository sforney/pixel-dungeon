package com.watabou.pixeldungeon.event;

import com.watabou.pixeldungeon.actors.Actor;

public class CreateEvent extends Event {

	public CreateEvent(Actor entity) {
		super(entity);
	}

	public Actor getSubject() {
		return (Actor)this.entity;
	}
}
