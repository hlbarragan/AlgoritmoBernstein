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
	
}
