package com.watabou.pixeldungeon.journal;

import java.util.SortedSet;
import java.util.TreeSet;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class Journal implements Bundlable {
	public SortedSet<Record> records;

	private static final String JOURNAL = "journal";

	public Journal() {
		records = new TreeSet<Record>();
	}
	
	public void storeInBundle(Bundle bundle) {
		bundle.put(JOURNAL, records);
	}

	public void restoreFromBundle(Bundle bundle) {
		records = new TreeSet<Record>();
		for (Bundlable rec : bundle.getCollection(JOURNAL)) {
			records.add((Record) rec);
		}
	}

	public void add(Record newRecord) {
		records.add(newRecord);
	}

	public void remove(Record record) {
		records.remove(record);
	}
}
