package com.watabou.pixeldungeon.utils;

import com.watabou.noosa.Game;

public class DefaultStringResolver implements StringResolver {

	@Override
	public String getVar(int id) {
		return Game.getVar(id);
	}

	@Override
	public String[] getVars(int id) {
		return Game.getVars(id);
	}

}
