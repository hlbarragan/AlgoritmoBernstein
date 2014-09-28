/**
 * 
 */
package com.anstek.negocio;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import com.anstek.clases.DependenciaFuncional;

/**
 * @author ddmurillo
 *
 */
public class Particion {
	/**
	 * Retorna las relaciones (tablas) dadas unas dependencias funcionales
	 * @param dependencias
	 * @return
	 */
	public static TreeMap<String,HashSet<String>> ParticionarRelaciones(DependenciaFuncional[] dependencias){
		TreeMap<String,HashSet<String>> particion = new TreeMap<String, HashSet<String>>();
		
		for (int i = 0; i < dependencias.length; i++) {
			
			if (particion.containsKey(dependencias[i].getImplicante().toString())) {
				particion.get(dependencias[i].getImplicante().toString()).addAll(dependencias[i].getImplicado());
			}	
			else {
				particion.put(dependencias[i].getImplicante().toString(), dependencias[i].getImplicante());
				particion.get(dependencias[i].getImplicante().toString()).addAll(dependencias[i].getImplicado());
			}
		}
		
		return particion;
	}

	/**
	 * Main prueba
	 * @param args
	 */
	public static void main(String[] args) {
		//Atributo a1 = new Atributo('a', "A");
		//Atributo a2 = new Atributo('b', "B");
		//Atributo a3 = new Atributo('c', "C");
		//Atributo a4 = new Atributo('d', "D");
		
		HashSet<String> hs1 = new HashSet<String>();
		hs1.add("a");
		hs1.add("d");
		
		HashSet<String> hs2 = new HashSet<String>();
		hs2.add("b");
		
		HashSet<String> hs3 = new HashSet<String>();
		hs3.add("c");
		
		HashSet<String> hs4 = new HashSet<String>();
		hs4.add("d");		
		
		HashSet<String> hs5 = new HashSet<String>();
		hs5.add("a");
		hs5.add("b");
		
		HashSet<String> hs6 = new HashSet<String>();
		hs6.add("e");
		
		HashSet<String> hs7 = new HashSet<String>();
		hs7.add("a");
		hs7.add("c");
		
		HashSet<String> hs8 = new HashSet<String>();
		hs8.add("f");
		
		DependenciaFuncional dep1 = new DependenciaFuncional(hs1, hs2);
		DependenciaFuncional dep2 = new DependenciaFuncional(hs2, hs3);
		DependenciaFuncional dep3 = new DependenciaFuncional(hs3, hs4);
		DependenciaFuncional dep4 = new DependenciaFuncional(hs5, hs6);
		DependenciaFuncional dep5 = new DependenciaFuncional(hs7, hs8);
		
		DependenciaFuncional[] ext = AtributosExtranios.LimpiaAtributosExtranios(new DependenciaFuncional[]{dep1,dep2,dep3,dep4,dep5});
		TreeMap<String,HashSet<String>> res = Particion.ParticionarRelaciones(ext);
		//System.out.println(res);
		
		for (Map.Entry<String, HashSet<String>> r : res.entrySet()) {
			System.out.println(r.getKey() + " --> " + r.getValue().toString());
		}
		
//		for (int i = 0; i < res.length; i++) {
//			System.out.println(res[i].getImplicante() + " -> " + res[i].getImplicado());
//		}
	}

}
