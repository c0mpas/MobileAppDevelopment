package com.example.tasklist;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="category")
public class Category implements Serializable {

	@DatabaseField
	private String name;
	@DatabaseField(generatedId=true)
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
