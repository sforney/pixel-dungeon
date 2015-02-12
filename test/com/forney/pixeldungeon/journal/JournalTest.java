package com.forney.pixeldungeon.journal;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.watabou.pixeldungeon.journal.ConcreteFeature;
import com.watabou.pixeldungeon.journal.ConcreteJournal;
import com.watabou.pixeldungeon.journal.ConcreteRecord;

@RunWith(JUnit4.class)
public class JournalTest {
	private ConcreteRecord alchemy = new ConcreteRecord(ConcreteFeature.ALCHEMY, 1);
	private ConcreteRecord garden = new ConcreteRecord(ConcreteFeature.GARDEN, 2);
	
	@Test
	public void testAddRecords() throws Exception {
		ConcreteJournal journal = new ConcreteJournal();
		addTestRecords(journal);

		assertTrue(journal.records.get(0) == alchemy);
		assertTrue(journal.records.get(1) == garden);
	}
	
	@Test
	public void removeRecords() throws Exception {
		ConcreteJournal journal = new ConcreteJournal();
		addTestRecords(journal);
		
		journal.remove(alchemy);
		assertTrue(journal.records.get(0) == garden);
		assertFalse(journal.records.contains(alchemy));
	}
	
	private void addTestRecords(ConcreteJournal journal) {
		journal.add(alchemy);
		journal.add(garden);		
	}
}
