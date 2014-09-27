/**
 * 
 */
package com.anstek.clases;

/**
 * @author ddmurillo
 *
 */
public class DependenciaFuncional {
	private Atributo[] Implicante;
	
	private Atributo[] Implicado;

	public Atributo[] getImplicado() {
		return Implicado;
	}

	public void setImplicado(Atributo[] implicado) {
		Implicado = implicado;
	}

	public Atributo[] getImplicante() {
		return Implicante;
	}

	public void setImplicante(Atributo[] implicante) {
		Implicante = implicante;
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
