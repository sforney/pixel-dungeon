package com.forney.pixeldungeon.journal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

		assertTrue(journal.records.contains(alchemy));
		assertTrue(journal.records.contains(garden));
	}

	@Test
	public void removeRecords() throws Exception {
		Journal journal = new Journal();
		addTestRecords(journal);

		journal.remove(alchemy);
		assertTrue(journal.records.contains(garden));
		assertFalse(journal.records.contains(alchemy));
	}

	private void addTestRecords(Journal journal) {
		journal.add(alchemy);
		journal.add(garden);
	}

	@Test
	// Make sure that duplicate records aren't stored
	public void testEquals() throws Exception {
		Journal journal = new Journal();
		addTestRecords(journal);
		journal.add(new Record(Feature.ALCHEMY, 1));
		assertTrue(journal.records.size() == 2);
	}
	
	@Test
	//Ensure that items on lower floors appear first in the list
	public void testSorting() throws Exception {
		Journal journal = new Journal();
		addTestRecords(journal);
		assertTrue(journal.records.first().equals(garden));	
	}
}
