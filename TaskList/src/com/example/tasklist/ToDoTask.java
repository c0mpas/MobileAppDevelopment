
package com.example.tasklist;


public class ToDoTask implements Comparable<ToDoTask>{
	
	private String title;
	private String description;	
	private Priorität priority;
	private int id;
	
	public ToDoTask(String title, String description, Priorität priority, int id) {
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.id = id;
	}

	public int getID(){
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Priorität getPriority() {
		return priority;
	}

	/*
	 * setzt prioritaet
	 */
	public void setPriority(Priorität priority){
		this.priority = priority;
	}
	
	@Override
	 public int compareTo(ToDoTask task) {
	        return this.priority.compareTo(task.priority);
	    }

	@Override
	public boolean equals(Object o) {
		ToDoTask task = (ToDoTask) o;
		
		if (this.id == task.getID()) return true;
		else return false;
	}

}
