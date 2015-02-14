package com.watabou.pixeldungeon.journal;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class Record implements Comparable<Record>, Bundlable {
	private final String FEATURE = "feature";
	private final String DEPTH = "depth";

	public Feature feature;
	public int depth;

	public Record() {
	}

	public Record(Feature feature, int depth) {
		this.feature = feature;
		this.depth = depth;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Record) {
			Record other = (Record)obj;
			if(other.depth == depth && other.feature == feature) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public int compareTo(Record another) {
		return another.depth - depth;
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		feature = Feature.valueOf(bundle.getString(FEATURE));
		depth = bundle.getInt(DEPTH);
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(FEATURE, feature.toString());
		bundle.put(DEPTH, depth);
	}

}
