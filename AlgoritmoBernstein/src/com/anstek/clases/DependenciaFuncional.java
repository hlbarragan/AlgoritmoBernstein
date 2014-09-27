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

	/**
	 * Constructor
	 */
	public DependenciaFuncional(){}
	
	/**
	 * Constructor que recibe HashSet de implicantes e implicados
	 * @param implicante
	 * @param implicado
	 */
	public DependenciaFuncional(HashSet<String> implicante,HashSet<String> implicado){
		this.Implicante = implicante;
		this.Implicado = implicado;
	}
	
	/**
	 * Obtiene HashSet de implicantes
	 * @return
	 */
	public HashSet<String> getImplicante() {
		return Implicante;
	}

	/**
	 * Establece implicantes desde un HashSet
	 * @param implicante
	 */
	public void setImplicante(HashSet<String> implicante) {
		Implicante = implicante;
	}

	/**
	 * Obtiene HashSet de implicados
	 * @return
	 */
	public HashSet<String> getImplicado() {
		return Implicado;
	}

	/**
	 * Establece implicados desde un HashSet
	 * @param implicado
	 */
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

	/**
	 * Establece implicados desde un array de atributos
	 * @param atributosImplicados
	 */
	public void setImplicado(Atributo[] atributosImplicados) {
		for (int i = 0; i < atributosImplicados.length; i++) {
			this.Implicado.add(String.valueOf(atributosImplicados[i].getId()));
		}
	}

	/**
	 * Establece implicantes desde un array de atributos
	 * @param atributosImplicantes
	 */
	public void setImplicante(Atributo[] atributosImplicantes) {
		for (int i = 0; i < atributosImplicantes.length; i++) {
			this.Implicante.add(String.valueOf(atributosImplicantes[i].getId()));
		}
	}
}
