/**
 * 
 */
package com.anstek.negocio;

import java.util.HashSet;
import java.util.Map;
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
	
	/**
	 * Constructor
	 */
	public Bernstein(){}
	
	/**
	 * Constructor que recibe un arreglo de atributos y uno de dependencias funcionales
	 * @param atributos
	 * @param dependencias
	 */
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
	
	/**
	 * Normaliza las dependencias funcionales usando el algortimo de Bernstein
	 * @return
	 */
	public TreeMap<String,HashSet<String>> NormalizadorBernstein(){
		// Lado derecho Sipmle
		LDS.listaAtributos = this.atributos;
		DependenciaFuncional[] df1 = LDS.LadoDerechoSimple(this.dependencias);
		System.out.println(df1.length);
		
		// Limpia atributos extra�os
		AtributosExtranios.listaAtributos = this.atributos;
		DependenciaFuncional[] df2 = AtributosExtranios.LimpiaAtributosExtranios(df1);
		System.out.println(df2.length);
		
		// Quita DF redundantes
		DependenciasRedundantes dr = new DependenciasRedundantes(this.atributos);
		DependenciaFuncional[] df3 = dr.eliminarDependenciasRedundantes(df2);
		System.out.println(df3.length);
		
		// Obtiene ralaciones 
		TreeMap<String,HashSet<Integer>> rel = Particion.ParticionarRelaciones(df3);
		System.out.println(rel);
		// Obtiene el nombre de los atributos de acuerdo a los Ids de las relaciones
		TreeMap<String,HashSet<String>> result = new TreeMap<String, HashSet<String>>();		
		for (Map.Entry<String,HashSet<Integer>> r : rel.entrySet()) {
			String k = r.getKey().replace("[","").replace("]", "");
			String[] keys = k.split(",");
			
			HashSet<String> knames = new HashSet<String>();
			
			for (int i = 0; i < keys.length; i++) {
				//System.out.println(keys[i].length());
				knames.add(Atributo.retornarAtributoPorCodigo(Integer.valueOf(keys[i].trim()), this.getAtributos()).getNombre());
			}
			
			HashSet<String> vnames = new HashSet<String>();
			for (Integer s : r.getValue()) {
				vnames.add(Atributo.retornarAtributoPorCodigo(s, this.getAtributos()).getNombre());
			}
			
			result.put(knames.toString(), vnames);
		}
		
		return result;
	}

}
