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
	
	/**
	 * Constructor
	 */
	public Atributo(){}
	
	/**
	 * Constructor con valores de Id y Nombre del atributo
	 * @param id
	 * @param nombre
	 */
	public Atributo(char id, String nombre){
		this.Id = id;
		this.Nombre = nombre;
	}

	/**
	 * Obtiene el Id del atributo
	 * @return
	 */
	public char getId() {
		return Id;
	}

	/**
	 * Establece Id del atributo
	 * @param id
	 */
	public void setId(char id) {
		Id = id;
	}

	/**
	 * Obtiene el Nombre de un atributo
	 * @return
	 */
	public String getNombre() {
		return Nombre;
	}

	/**
	 * Establece el Nombre del atributo
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
}
