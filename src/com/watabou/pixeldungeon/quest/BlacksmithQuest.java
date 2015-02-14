package com.watabou.pixeldungeon.quest;

import java.util.Collection;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.levels.Room;
import com.watabou.pixeldungeon.levels.Room.Type;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class BlacksmithQuest {
	private boolean spawned;	
	private boolean alternative;
	private boolean given;
	private boolean completed;
	private boolean reforged;
	
	public BlacksmithQuest() {
		spawned		= false;
		given		= false;
		completed	= false;
		reforged	= false;
	}
	
	private static final String NODE	= "blacksmith";
	private static final String SPAWNED		= "spawned";
	private static final String ALTERNATIVE	= "alternative";
	private static final String GIVEN		= "given";
	private static final String COMPLETED	= "completed";
	private static final String REFORGED	= "reforged";
	
	public void storeInBundle( Bundle bundle ) {	
		Bundle node = new Bundle();
		
		node.put( SPAWNED, spawned );
		
		if (spawned) {
			node.put( ALTERNATIVE, alternative );
			node.put( GIVEN, given );
			node.put( COMPLETED, completed );
			node.put( REFORGED, reforged );
		}	
		bundle.put( NODE, node );
	}
	
	public void restoreFromBundle( Bundle bundle ) {
		Bundle node = bundle.getBundle( NODE );
		
		if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {
			alternative	=  node.getBoolean( ALTERNATIVE );
			given = node.getBoolean( GIVEN );
			completed = node.getBoolean( COMPLETED );
			reforged = node.getBoolean( REFORGED );
		}
	}
	
	public void spawn( Collection<Room> rooms ) {
		if (!spawned && Dungeon.depth > 11 && Random.Int( 15 - Dungeon.depth ) == 0) {
			
			Room blacksmith = null;
			for (Room r : rooms) {
				if (r.type == Type.STANDARD && r.width() > 4 && r.height() > 4) {
					blacksmith = r;
					blacksmith.type = Type.BLACKSMITH;
					
					spawned = true;
					alternative = Random.Int( 2 ) == 0;
					
					given = false;
					
					break;
				}
			}
		}
	}

	public boolean isSpawned() {
		return spawned;
	}

	public void setSpawned(boolean spawned) {
		this.spawned = spawned;
	}

	public boolean isAlternative() {
		return alternative;
	}

	public void setAlternative(boolean alternative) {
		this.alternative = alternative;
	}

	public boolean isGiven() {
		return given;
	}

	public void setGiven(boolean given) {
		this.given = given;
	}

	public boolean isReforged() {
		return reforged;
	}

	public void setReforged(boolean reforged) {
		this.reforged = reforged;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void complete() {
		completed = true;
	}
}
