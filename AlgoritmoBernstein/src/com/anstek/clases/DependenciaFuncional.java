/**
 * 
 */
package com.anstek.clases;

import java.util.HashSet;

/**
 * @author ddmurillo
 *
 */
public class DependenciaFuncional {
	private HashSet<String> Implicante;
	
	private HashSet<String> Implicado;

	public DependenciaFuncional(HashSet<String> implicante,HashSet<String> implicado){
		this.Implicante = implicante;
		this.Implicado = implicado;
	}
	
	public HashSet<String> getImplicante() {
		return Implicante;
	}

	public void setImplicante(HashSet<String> implicante) {
		Implicante = implicante;
	}

	public HashSet<String> getImplicado() {
		return Implicado;
	}

	public void setImplicado(HashSet<String> implicado) {
		Implicado = implicado;
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
}
