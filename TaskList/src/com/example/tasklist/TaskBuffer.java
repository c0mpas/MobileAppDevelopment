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
	private ArrayList<ToDoTask> taskList;

	public TaskBuffer() {
		this.taskList = new ArrayList<ToDoTask>();
	}

	public TaskBuffer add(ToDoTask task) {

		taskList.add(task);

		return this;

	}

	public TaskBuffer delete(ToDoTask task) {

		taskList.remove(task);

		return this;
	}

	public TaskBuffer sort() {

		Collections.sort(taskList);

		return this;

	}
	

}
