/**
 * 
 */
package com.anstek.negocio;

import java.util.HashSet;
import java.util.TreeMap;

import com.anstek.clases.Atributo;
import com.anstek.clases.DependenciaFuncional;

/**
 * @author ddmurillo
 *
 */
public class Bernstein {
	private Atributo[] atributos;
	
	private DependenciaFuncional[] dependencias;
	
	public Bernstein(){}
	
	public Bernstein(Atributo[] atributos, DependenciaFuncional[] dependencias){
		this.setAtributos(atributos);
		this.setDependencias(dependencias);
	}

	public DependenciaFuncional[] getDependencias() {
		return dependencias;
	}

	public void setDependencias(DependenciaFuncional[] dependencias) {
		this.dependencias = dependencias;
	}

	public Atributo[] getAtributos() {
		return atributos;
	}

	public void setAtributos(Atributo[] atributos) {
		this.atributos = atributos;
	}
	
	public TreeMap<String,HashSet<String>> NormalizadorBernstein(){
		// Lado derecho Sipmle
		DependenciaFuncional[] df1 = LDS.LadoDerechoSimple(this.dependencias);
		
		// Limpia atributos extraños
		DependenciaFuncional[] df2 = AtributosExtranios.LimpiaAtributosExtranios(df1);
		
		// Quita DF redundantes
		/// TODO
		
		// Obtiene ralaciones 
		return Particion.ParticionarRelaciones(df2);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
