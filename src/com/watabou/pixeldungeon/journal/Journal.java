package com.watabou.pixeldungeon.journal;

import java.util.ArrayList;
import java.util.List;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class Journal implements Bundlable {
	public List<Record> records;

	private static final String JOURNAL = "journal";

	public Journal() {
		records = new ArrayList<Record>(25);
	}
	
	public void storeInBundle(Bundle bundle) {
		bundle.put(JOURNAL, records);
	}

	public void restoreFromBundle(Bundle bundle) {
		records = new ArrayList<Record>();
		for (Bundlable rec : bundle.getCollection(JOURNAL)) {
			records.add((Record) rec);
		}
	}

	public void add(Record newRecord) {
		for(Record record : records) {
			if (record.equals(newRecord)) {
				return;
			}
		}
		records.add(newRecord);
	}

	public void remove(Record recordToRemove) {
		for(Record record : records) {
			if (record == recordToRemove) {
				records.remove(record);
				break;
			}
		}
	}
}
