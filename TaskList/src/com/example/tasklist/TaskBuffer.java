package com.example.tasklist;

import java.util.ArrayList;
import java.util.Collections;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "taskbuffer")
public class TaskBuffer {
	
	@DatabaseField(generatedId=true)
	private int id;
	
	@ForeignCollectionField
	private ArrayList<Task> taskList;

	public TaskBuffer() {
		this.taskList = new ArrayList<Task>();
	}

	public TaskBuffer add(Task task) {

		taskList.add(task);

		return this;

	}

	public TaskBuffer delete(Task task) {

		taskList.remove(task);

		return this;
	}

	public TaskBuffer sort() {

		Collections.sort(taskList);

		return this;

	}
	

}
