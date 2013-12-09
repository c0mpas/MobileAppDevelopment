package com.example.tasklist;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="priority")
public class Prioritšt implements Comparable<Prioritšt> {

	@DatabaseField
	private String name;
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private int value;

	public Prioritšt(String name, int id, int value) {
		setName(name);
		setId(id);
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
	public int compareTo(Prioritšt prio) {
		
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
