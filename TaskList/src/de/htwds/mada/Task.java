package de.htwds.mada;

import java.io.Serializable;

import android.os.Parcel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Task.TABLE_NAME)
public class Task implements Comparable<Task>, Serializable{

	static final String TABLE_NAME = "task";
	public static final String COLUMN_DAY = "day";
	public static final String COLUMN_MONTH = "month";
	public static final String COLUMN_YEAR = "year";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CATEGORY_ID = "category_id";
	public static final String COLUMN_PRIORITY = "priority_id";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_TITLE = "title";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField(columnName=COLUMN_TITLE)
	private String title;
	@DatabaseField(columnName=COLUMN_DESCRIPTION)
	private String description;
	@DatabaseField(foreign = true, foreignAutoRefresh=true, columnName=COLUMN_PRIORITY)
	private Priority priority;
	@DatabaseField(foreign = true, foreignAutoRefresh=true, columnName=COLUMN_CATEGORY_ID)
	private Category kategorie;
	@DatabaseField(generatedId = true, columnName=COLUMN_ID)
	private int id;
	@DatabaseField(columnName=COLUMN_YEAR)
	private int ablaufJahr;
	@DatabaseField(columnName=COLUMN_MONTH)
	private int ablaufMonat;
	@DatabaseField(columnName=COLUMN_DAY)
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
