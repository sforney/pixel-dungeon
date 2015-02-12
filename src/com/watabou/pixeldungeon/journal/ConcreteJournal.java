package com.watabou.pixeldungeon.journal;

import java.util.ArrayList;
import java.util.List;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class ConcreteJournal implements Bundlable {
	public List<ConcreteRecord> records;

	private static final String JOURNAL = "journal";

	public ConcreteJournal() {
		records = new ArrayList<ConcreteRecord>(25);
	}
	
	public void storeInBundle(Bundle bundle) {
		bundle.put(JOURNAL, records);
	}

	public void restoreFromBundle(Bundle bundle) {
		records = new ArrayList<ConcreteRecord>();
		for (Bundlable rec : bundle.getCollection(JOURNAL)) {
			records.add((ConcreteRecord) rec);
		}
	}

	public void add(ConcreteRecord newRecord) {
		for(ConcreteRecord record : records) {
			if (record == newRecord) {
				return;
			}
		}
		records.add(newRecord);
	}

	public void remove(ConcreteRecord recordToRemove) {
		for(ConcreteRecord record : records) {
			if (record == recordToRemove) {
				records.remove(record);
				break;
			}
		}
	}
}
