package com.watabou.pixeldungeon.utils;

/**
 * This interface helps break the dependency on the Android
 * framework for resolving Strings.  Replace with a 
 * TestStringResolver for testing
 * @author sforney
 *
 */
public interface StringResolver {
	public String getVar(int id);
	public String [] getVars(int id);
}
