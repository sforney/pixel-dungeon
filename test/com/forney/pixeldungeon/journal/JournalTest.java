package com.forney.pixeldungeon.journal;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.watabou.pixeldungeon.journal.Feature;
import com.watabou.pixeldungeon.journal.Journal;
import com.watabou.pixeldungeon.journal.Record;

@RunWith(JUnit4.class)
public class JournalTest {
	private Record alchemy = new Record(Feature.ALCHEMY, 1);
	private Record garden = new Record(Feature.GARDEN, 2);
	
	@Test
	public void testAddRecords() throws Exception {
		Journal journal = new Journal();
		addTestRecords(journal);

		assertTrue(journal.records.get(0) == alchemy);
		assertTrue(journal.records.get(1) == garden);
	}
	
	@Test
	public void removeRecords() throws Exception {
		Journal journal = new Journal();
		addTestRecords(journal);
		
		journal.remove(alchemy);
		assertTrue(journal.records.get(0) == garden);
		assertFalse(journal.records.contains(alchemy));
	}
	
	private void addTestRecords(Journal journal) {
		journal.add(alchemy);
		journal.add(garden);		
	}
}
