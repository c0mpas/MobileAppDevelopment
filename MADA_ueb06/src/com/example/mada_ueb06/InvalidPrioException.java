package com.example.mada_ueb06;

public class InvalidPrioException extends Exception {
	
	/*
	 * Konstrukor fuer eigene Exception
	 */
	public InvalidPrioException(){
		super();
	}
	
	
	/*
	 * Konstrukor fuer eigene Exception mit Nachricht
	 */
	
	public InvalidPrioException(String msg){
		super(msg);
	}
	
	public InvalidPrioException(Throwable tr) {
		super(tr);
	}

}
