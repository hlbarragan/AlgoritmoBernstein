/**
 * 
 */
package com.anstek.negocio;

import java.util.Arrays;
import java.util.HashSet;

import com.anstek.clases.Atributo;
import com.anstek.clases.DependenciaFuncional;
import com.anstek.utiles.Cierre;
import com.anstek.utiles.LectorXML;

/**
 * @author leonardo
 *
 */
public class DependenciasRedundantes {
	
	/**
	 * Conjunto de atributos de entrada
	 */
	private Atributo[] listaAtributos;
	
	/**
	 * Constructor de clase.<br>
	 * 
	 * @param listaAtributos
	 */
	public DependenciasRedundantes(Atributo[] listaAtributos) {
		this.listaAtributos = listaAtributos;
	}
	
	/**
	 * Elimina las dependencias funcionales redundantes de un esquema de 
	 * dependencias funcionales dada.<br>
	 * 
	 * @param dependenciasEntrada
	 * @return
	 */
	public DependenciaFuncional[] eliminarDependenciasRedundantes(DependenciaFuncional[] dependenciasEntrada) {
		HashSet<DependenciaFuncional> resultado = new HashSet<DependenciaFuncional>();
		
		// Recorrer el conjunto de dependencias funcionales
		for (DependenciaFuncional dependencia: dependenciasEntrada) {
			// Si la dependencia es redundante, eliminarla
			if (!dependenciaEsRedundante(dependencia, dependenciasEntrada)) {
				resultado.add(dependencia);
			}
		}
		
		DependenciaFuncional[] retorno = new DependenciaFuncional[resultado.size()];
		resultado.toArray(retorno);
		return retorno;
	}
	
	/**
	 * Determina si una dependencia funcional dada es redundante dado
	 * un esquema de dependencias funcionales.<br>
	 * 
	 * @param dependencia
	 * @param dependenciasEntrada
	 * @return
	 */
	private boolean dependenciaEsRedundante(DependenciaFuncional dependencia, DependenciaFuncional[] dependenciasEntrada) {
		// Se calcula el cierre del implicante de la dependencia a validar
		HashSet<DependenciaFuncional> dependencias = new HashSet<DependenciaFuncional>(Arrays.asList(dependenciasEntrada));
		dependencias.remove(dependencia);
		Atributo[] atributosImplicante = new Atributo[dependencia.getImplicante().size()];
		int i = 0;
		for (String codigo: dependencia.getImplicante()) {
			Atributo atributo = Atributo.retornarAtributoPorCodigo(codigo, this.listaAtributos);
			atributosImplicante[i] = atributo;
			i++;
		}
		DependenciaFuncional[] arregloDependencias = new DependenciaFuncional[dependencias.size()];
		dependencias.toArray(arregloDependencias);
		HashSet<String> cierre = Cierre.HacerCierre(atributosImplicante,arregloDependencias);
		if (DependenciaFuncional.conjuntoContenido(dependencia.getImplicado(), cierre, false)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Cargar el archivo de parametrizacion
		try {
			LectorXML lector = new LectorXML("/home/leonardo/Dropbox/ESPECIALIZACION EN INGENIERIA DE SOFTWARE/BASES DE DATOS/TALLER/pruebaRedundancia.xml");
			lector.cargarInformacion();
			
			DependenciasRedundantes dr = new DependenciasRedundantes(lector.getAtributos());
			DependenciaFuncional[] res = dr.eliminarDependenciasRedundantes(lector.getDependenciasFuncionales());
			
			System.out.println("");
			System.out.println("Dependencias funcionales...");
			for (DependenciaFuncional dependencia: res) {
				System.out.println(dependencia.toString());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
