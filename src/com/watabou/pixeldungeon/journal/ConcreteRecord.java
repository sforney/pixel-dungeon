package com.watabou.pixeldungeon.journal;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class ConcreteRecord implements Comparable<ConcreteRecord>, Bundlable {
	private final String FEATURE = "feature";
	private final String DEPTH = "depth";

	public ConcreteFeature feature;
	public int depth;

	public ConcreteRecord() {
	}

	public ConcreteRecord(ConcreteFeature feature, int depth) {
		this.feature = feature;
		this.depth = depth;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ConcreteRecord) {
			ConcreteRecord other = (ConcreteRecord)obj;
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
	public int compareTo(ConcreteRecord another) {
		return another.depth - depth;
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		feature = ConcreteFeature.valueOf(bundle.getString(FEATURE));
		depth = bundle.getInt(DEPTH);
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(FEATURE, feature.toString());
		bundle.put(DEPTH, depth);
	}

}
