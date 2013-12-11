package com.example.tasklist;

import java.io.Serializable;

import android.os.Parcel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "task")
public class Task implements Comparable<Task>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField
	private String title;
	@DatabaseField
	private String description;
	@DatabaseField(foreign = true)
	private transient Priority priority;
	@DatabaseField(foreign = true)
	private transient Category kategorie;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int ablaufJahr;
	@DatabaseField
	private int ablaufMonat;
	@DatabaseField
	private int ablaufTag;

	public Task() {
	};

	public Task(String title, String description, Priority priority,
			Category kategorie, int ablaufJahr, int ablaufMonat, int ablaufTag) {
		this.kategorie = kategorie;
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.ablaufJahr = ablaufJahr;
		this.ablaufMonat = ablaufMonat;
		this.ablaufTag = ablaufTag;
	}

	public int getID() {
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

	public Priority getPriority() {
		return priority;
	}

	/*
	 * setzt prioritaet
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(Task task) {
		return this.priority.compareTo(task.priority);
	}

	/**
	 * @return the kategorie
	 */
	public Category getCategory() {
		return kategorie;
	}

	/**
	 * @param kategorie
	 *            the kategorie to set
	 */
	public void setKategorie(Category kategorie) {
		this.kategorie = kategorie;
	}

	@Override
	public boolean equals(Object o) {
		Task task = (Task) o;

		if (this.id == task.getID())
			return true;
		else
			return false;
	}

	/**
	 * @return the ablaufJahr
	 */
	public int getAblaufJahr() {
		return ablaufJahr;
	}

	/**
	 * @param ablaufJahr
	 *            the ablaufJahr to set
	 */
	public void setAblaufJahr(int ablaufJahr) {
		this.ablaufJahr = ablaufJahr;
	}

	/**
	 * @return the ablaufMonat
	 */
	public int getAblaufMonat() {
		return ablaufMonat;
	}

	/**
	 * @param ablaufMonat
	 *            the ablaufMonat to set
	 */
	public void setAblaufMonat(int ablaufMonat) {
		this.ablaufMonat = ablaufMonat;
	}

	/**
	 * @return the ablaufTag
	 */
	public int getAblaufTag() {
		return ablaufTag;
	}

	/**
	 * @param ablaufTag
	 *            the ablaufTag to set
	 */
	public void setAblaufTag(int ablaufTag) {
		this.ablaufTag = ablaufTag;
	}



}
