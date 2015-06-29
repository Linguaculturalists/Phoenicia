/**
 * 	Copyright (c) 2010 Philipp Giese
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.orm.androrm.field;

import com.orm.androrm.DatabaseAdapter;
import com.orm.androrm.DatabaseBuilder;
import com.orm.androrm.Model;

import android.content.Context;
import android.database.SQLException;

/**
 * This class is the superclass for all database fields,
 * that need a real field in the database. This for example 
 * is a {@link IntegerField} field, but not a {@link OneToManyField}. 
 * 
 * @author Philipp Giese
 *
 * @param <T>	The Java data type this field represents. 
 */
public abstract class DataField<T> implements DatabaseField<T> {
	
	/**
	 * Type descriptor of this field used for the 
	 * database definition.
	 */
	protected String mType;
	/**
	 * Value of that field. 
	 */
	protected T mValue;
	/**
	 * Maximum length of that field.
	 */
	protected int mMaxLength;
	
	@Override
	public T get() {
		return mValue;
	}
	
	@Override
	public String getDefinition(String fieldName) {
		String definition = "`" + fieldName + "` " + mType;
		
		if(mMaxLength > 0) {
			definition += "(" + mMaxLength + ")";
		}
		
		return definition;
	}
	
	@Override
	public void set(T value) {
		mValue = value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(mValue);
	}
	
	protected boolean exec(Context context, Class<? extends Model> model, String sql) {
		DatabaseAdapter adapter = DatabaseAdapter.getInstance(context);
		
		adapter.open();
		
		try {
			adapter.exec(sql);
		} catch(SQLException e) {
			adapter.close();
		
			return false;
		}
		
		adapter.close();
		return true;
	}
	
	public boolean addToAs(Context context, Class<? extends Model> model, String name) {
		String sql = "ALTER TABLE `" + DatabaseBuilder.getTableName(model) + "` "
					+ "ADD COLUMN " + getDefinition(name);
		
		return exec(context, model, sql);
				
	}
}
