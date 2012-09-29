package br.usp.ime.servicosusp.model;

import java.util.Date;

public class Comment {
	public String texto;
	public Date hora;
	public String fila;

	public enum Fila {
		SEM_FILA, PEQUENA, MEDIA, GRANDE, MUITO_GRANDE
	};

	public Comment(String texto, Date hora, String fila) {
		this.texto = texto;
		this.hora = hora;
		this.fila = fila;
	}
}