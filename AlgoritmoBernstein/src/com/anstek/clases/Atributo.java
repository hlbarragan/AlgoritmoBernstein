/**
 * 
 */
package com.anstek.clases;

/**
 * @author ddmurillo
 *
 */
public class Atributo {
	private char Id;
	
	private String Nombre;
	
	public Atributo(char id, String nombre){
		this.Id = id;
		this.Nombre = nombre;
	}

	public char getId() {
		return Id;
	}

	public void setId(char id) {
		Id = id;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}
}
