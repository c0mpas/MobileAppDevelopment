package com.example.tasklist;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="priority")
public class Priority implements Serializable, Comparable<Priority>  {

	@DatabaseField(columnName="name")
	private String name;
	@DatabaseField(generatedId=true, columnName="_id")
	private int id;
	@DatabaseField(columnName="value")
	private int value;
	
	public Priority(){};

	public Priority(String name, int value) {
		setName(name);
		setValue(value);
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

	@Override
	public int compareTo(Priority prio) {
		
		if (this.value == prio.getValue()) {
			return 0;
		} else if (this.value > prio.getValue()) {
			return 1;
		} else {
			return -1;
		}
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}


}
