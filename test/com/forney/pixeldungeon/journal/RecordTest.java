package com.forney.pixeldungeon.journal;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.watabou.pixeldungeon.journal.Feature;
import com.watabou.pixeldungeon.journal.Record;
import com.watabou.utils.Bundle;

@RunWith(JUnit4.class)
public class RecordTest {
	@Test
	public void testCreate() {
		@SuppressWarnings("unused")
		Record record = new Record();
	}
	
	@Test
	public void testEqualsNotEqual() {
		Record record1 = new Record(Feature.ALCHEMY, 1);
		assertNotEquals("Object should not be equal to a record", record1, new Object());
		
		Record record2 = new Record(Feature.ALCHEMY, 2);
		assertNotEquals("Features on different depths should not be equal", record1, record2);
		
		record2 = new Record(Feature.GARDEN, 1);
		assertNotEquals("Different features on the same depth should not be equal", record1, record2);		
	}
}
