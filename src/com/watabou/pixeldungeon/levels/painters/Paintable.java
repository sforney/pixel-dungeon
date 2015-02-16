package com.watabou.pixeldungeon.levels.painters;

import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.Room;

public interface Paintable {
	public void paint(Level level, Room room);
}
