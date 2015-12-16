package com.example.inservice_apk;

public class Clase_area {
	private String nombre_area = "";
	private String fecha_inicio = "";

	public String getTitulo() {
		return nombre_area;
	}

	public String getAutor() {
		return fecha_inicio;
	}

	public void setTitulo(String titulo) {
		nombre_area = titulo;
	}

	public void setAutor(String autor) {
		fecha_inicio = autor;
	}
}
