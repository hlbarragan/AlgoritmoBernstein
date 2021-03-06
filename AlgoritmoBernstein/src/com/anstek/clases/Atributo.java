/**
 * 
 */
package com.anstek.clases;

import java.util.HashSet;

/**
 * @author ddmurillo
 *
 */
public class Atributo {
	private int Id;
	
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
	public Atributo(int id, String nombre){
		this.Id = id;
		this.Nombre = nombre;
	}

	/**
	 * Obtiene el Id del atributo
	 * @return
	 */
	public int getId() {
		return Id;
	}

	/**
	 * Establece Id del atributo
	 * @param id
	 */
	public void setId(int id) {
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
	
	/**
	 * Retorna el atributo de una lista de atributos dado su nombre.<br>
	 * 
	 * @param nombreAtributo
	 * @param listaAtributos
	 * @return
	 */
	public static Atributo retornarAtributoPorNombre(String nombreAtributo, Atributo[] listaAtributos) {
		Atributo resultado = null;
		
		for (Atributo atributo: listaAtributos) {
			if (atributo.getNombre().equals(nombreAtributo)) {
				resultado = atributo;
				break;
			}
		}
		
		return resultado;
	}
	
	/**
	 * Retorna la referencia de un atributo dado su código y una lista de 
	 * atributos.<br>
	 * 
	 * @param codigo
	 * @param listaAtributos
	 * @return
	 */
	public static Atributo retornarAtributoPorCodigo(int codigo, Atributo[] listaAtributos) {
		Atributo resultado = null;
		
		for (Atributo atributo: listaAtributos) {
			if (atributo.getId() == codigo) {
				resultado = atributo;
				break;
			}
		}
		
		return resultado;
	}
	
//	/**
//	 * Retorna la referencia de un atributo dado su código y una lista de 
//	 * atributos.<br>
//	 * 
//	 * @param codigo
//	 * @param listaAtributos
//	 * @return
//	 */
//	public static Atributo retornarAtributoPorCodigo(int codigo, Atributo[] listaAtributos) {
//		Atributo resultado = null;
//		
//		for (Atributo atributo: listaAtributos) {
//			if (atributo.getId() == codigo) {
//				resultado = atributo;
//				break;
//			}
//		}
//		
//		return resultado;
//	}
	
	/**
	 * Retorna la lista de nombres de atributos dada una lista de códigos de atributos dada.<br>
	 * 
	 * @param listaCodigos
	 * @param listaAtributos
	 * @return
	 */
	public static HashSet<String> retornarListadoNombresAtributos(HashSet<Integer> listaCodigos, Atributo[] listaAtributos) {
		HashSet<String> resultado = new HashSet<String>();
		
		for (Integer codigoAtributo: listaCodigos) {
			String nombreAtributo = Atributo.retornarAtributoPorCodigo(codigoAtributo, listaAtributos).getNombre();
			resultado.add(nombreAtributo);
		}
		
		return resultado;
	}
}
