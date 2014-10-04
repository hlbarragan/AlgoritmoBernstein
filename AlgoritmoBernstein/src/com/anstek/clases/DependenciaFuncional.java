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
	private HashSet<Integer> Implicante;
	
	private HashSet<Integer> Implicado;
	
	private Atributo[] listaAtributos;

	/**
	 * Constructor
	 */
	public DependenciaFuncional(){}
	
	@Override
	public String toString() {
		String contenido = "";
		String grupo = "";
		
		for (Integer implicante: this.Implicante) {
			Atributo atributoImplicante = Atributo.retornarAtributoPorCodigo(implicante, this.listaAtributos);
			grupo += grupo.equals("") ? atributoImplicante.getNombre() : "," + atributoImplicante.getNombre();
		}
		contenido = grupo + " -> ";
		grupo = "";
		for (Integer implicado: this.Implicado) {
			Atributo atributoImplicado = Atributo.retornarAtributoPorCodigo(implicado, this.listaAtributos);
			grupo += grupo.equals("") ? atributoImplicado.getNombre() : "," + atributoImplicado.getNombre();
		}
		contenido += grupo; 
		
		return contenido;
	}
	
	/**
	 * Constructor que recibe HashSet de implicantes e implicados
	 * @param implicante
	 * @param implicado
	 */
	public DependenciaFuncional(HashSet<Integer> implicante,HashSet<Integer> implicado){
		this.Implicante = implicante;
		this.Implicado = implicado;
	}
	
	/**
	 * Obtiene HashSet de implicantes
	 * @return
	 */
	public HashSet<Integer> getImplicante() {
		return Implicante;
	}

	/**
	 * Establece implicantes desde un HashSet
	 * @param implicante
	 */
	public void setImplicante(HashSet<Integer> implicante) {
		Implicante = implicante;
	}

	/**
	 * Obtiene HashSet de implicados
	 * @return
	 */
	public HashSet<Integer> getImplicado() {
		return Implicado;
	}

	/**
	 * Establece implicados desde un HashSet
	 * @param implicado
	 */
	public void setImplicado(HashSet<Integer> implicado) {
		Implicado = implicado;
	}

	/**
	 * Establece implicados desde un array de atributos
	 * @param atributosImplicados
	 */
	public void setImplicado(Atributo[] atributosImplicados) {
		this.Implicado = new HashSet<Integer>();
		for (int i = 0; i < atributosImplicados.length; i++) {
			this.Implicado.add(atributosImplicados[i].getId());
		}
	}

	/**
	 * Establece implicantes desde un array de atributos
	 * @param atributosImplicantes
	 */
	public void setImplicante(Atributo[] atributosImplicantes) {
		this.Implicante = new HashSet<Integer>();
		for (int i = 0; i < atributosImplicantes.length; i++) {
			this.Implicante.add(atributosImplicantes[i].getId());
		}
	}

	/**
	 * @param listaAtributos the listaAtributos to set
	 */
	public void setListaAtributos(Atributo[] listaAtributos) {
		this.listaAtributos = listaAtributos;
	}
	
	/**
	 * Método que indica si el conjunto de atributos conjuntoA está contenido en
	 * el conjunto de atributos conjuntoB. Si el parámetro completo es verdadero,
	 * se evalúa que los dos conjuntos sean idénticos.<br>
	 *  
	 * @param conjuntoA
	 * @param conjuntoB
	 * @param completo
	 * @return
	 */
	public static boolean conjuntoContenido(HashSet<Integer> conjuntoA, HashSet<Integer> conjuntoB, boolean completo) {
		boolean resultado = true;
		
		for (Integer atributoA: conjuntoA) {
			boolean atributoContenido = false;
			for (Integer atributoB: conjuntoB) {
				if (atributoA.equals(atributoB)) {
					atributoContenido = true;
					break;
				}
			}
			
			// Solo continúa con el ciclo de búsqueda si el atributo actual está contenido
			if (!atributoContenido) {
				resultado = false;
				break;
			}
			
		}
		
		// Si la bandera está activa y el resultado fué verdadero, comparar que el tamaño de los dos conjuntos sea el mismo
		if (completo && resultado) {
			resultado = conjuntoA.size() == conjuntoB.size(); 
		}
		
		return resultado;
	}
}
