package com.example.mada_ueb03;

public class Kategorie {

	private String kategorie;
	private int id;

	Kategorie(int id, String kategorie) {
		setId(id);
		this.kategorie = kategorie;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

}
