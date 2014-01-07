package de.htwds.mada;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="category")
public class Category implements Serializable {

	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_ID = "_id";
	@DatabaseField(columnName=COLUMN_NAME)
	private String name;
	@DatabaseField(generatedId=true, columnName=COLUMN_ID)
	private int id;
	
	public Category(){};


	public Category(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

}
