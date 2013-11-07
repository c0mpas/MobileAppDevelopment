package com.example.mada_ueb03;

public class ToDoTask {

	private String title;
	private String description;
	private int priority;

	public ToDoTask(String title, String description, int priority) {

		this.title = title;
		this.description = description;
		this.priority = priority;

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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) throws InvalidPrioException {
		if(priority < 1 || priority > 3)
			throw new InvalidPrioException("Invalid Priority Value");
		this.priority = priority;
	}

}
