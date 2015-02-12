package com.watabou.pixeldungeon.event;

import java.util.ArrayList;
import java.util.List;

public class DefaultObservable implements Observable {
	private List<Observer> observers;
	
	public DefaultObservable() {
		observers = new ArrayList<Observer>();
	}
	
	@Override
	public void addObserver(Observer obs) {
		if(!observers.contains(obs)) {
			observers.add(obs);
		}

	}

	@Override
	public void removeObserver(Observer obs) {
		if(observers.contains(obs)) {
			observers.remove(obs);
		}
	}

	@Override
	public void notifyObservers(Event event) {
		for(Observer obs : observers) {
			if(obs != null) {
				obs.onEvent(event);
			}
		}
	}
}
