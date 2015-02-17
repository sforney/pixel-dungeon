package com.watabou.pixeldungeon.event;

public interface Observable {
	public void addObserver(Observer obs);
	public void removeObserver(Observer obs);
	public void notifyObservers(Event e);
}
