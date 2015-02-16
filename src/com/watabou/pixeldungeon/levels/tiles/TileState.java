package com.watabou.pixeldungeon.levels.tiles;

import java.util.ArrayList;
import java.util.List;

import com.watabou.pixeldungeon.actors.Actor;

public class TileState {
	private List<Actor> actors;
	private boolean visited;
	private boolean mapped;
	
	public TileState() {
		visited = false;
		mapped = false;
		actors = new ArrayList<Actor>(1);
	}
	
	public List<Actor> getActors() {
		return actors;
	}
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	public void addActor(Actor actor) {
		actors.add(actor);
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public boolean isMapped() {
		return mapped;
	}
	public void setMapped(boolean mapped) {
		this.mapped = mapped;
	}
}
