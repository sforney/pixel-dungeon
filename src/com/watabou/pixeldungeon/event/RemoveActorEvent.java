package com.watabou.pixeldungeon.event;

import com.watabou.pixeldungeon.actors.Actor;

public class RemoveActorEvent extends Event {

	public RemoveActorEvent(Actor entity) {
		super(entity);
	}
	
	public Actor getSubject() {
		return (Actor)this.entity;
	}
}
