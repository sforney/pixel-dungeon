package com.forney.test.utils;

import com.watabou.pixeldungeon.R;
import com.watabou.pixeldungeon.utils.StringResolver;

public class TestStringResolver implements StringResolver {

	@Override
	public String getVar(int id) {
		return "";
	}

	@Override
	public String[] getVars(int id) {
		if (id == R.array.Potion_Colors) {
			return new String[] { "turquoise", "crimson", "azure", "jade",
					"golden", "magenta", "charcoal", "ivory", "amber",
					"bistre", "indigo", "silver" };
		}
		return new String[] { "" };
	}
}
