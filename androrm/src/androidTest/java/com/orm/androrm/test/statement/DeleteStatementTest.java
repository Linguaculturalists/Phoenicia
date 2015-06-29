package com.orm.androrm.test.statement;

import com.orm.androrm.Where;
import com.orm.androrm.statement.DeleteStatement;
import com.orm.androrm.statement.Statement;

import android.test.AndroidTestCase;

public class DeleteStatementTest extends AndroidTestCase {

	private DeleteStatement mDelete;
	
	@Override
	public void setUp() {
		Where where = new Where();
		where.setStatement(new Statement("foo", "bar"));
		
		mDelete = new DeleteStatement();
		mDelete.from("table")
			   .where(where);
	}
	
	public void testDefault() {
		assertEquals("DELETE FROM table WHERE foo = 'bar'", mDelete.toString());
	}
	
}
