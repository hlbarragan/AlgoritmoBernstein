/**
 * 
 */
package com.anstek.negocio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.anstek.clases.Atributo;
import com.anstek.clases.DependenciaFuncional;

/**
 * @author ddmurillo
 *
 */
public class LDS {
	public static Atributo[] listaAtributos;
	
	/**
	 * Dado un conjunto de dependencias funcionales retorna un nuevo conjunto de DFs simplificando los implicados (lado derecho)
	 * @param dependencias
	 * @return
	 */
	public static DependenciaFuncional[] LadoDerechoSimple(DependenciaFuncional[] dependencias){
		List<DependenciaFuncional> newDependencias = new ArrayList<DependenciaFuncional>();
		
		for (int i = 0; i < dependencias.length; i++) {
			// Se simplifican los implicados con mas de un atributo
			if (dependencias[i].getImplicado().size() > 1) {
				for (Integer v : dependencias[i].getImplicado()) {
					HashSet<Integer> h1 = new HashSet<Integer>();
					h1.add(v);
					//Nueva dependencia
					DependenciaFuncional df = new DependenciaFuncional(dependencias[i].getImplicante(), h1);
					df.setListaAtributos(listaAtributos);
					newDependencias.add(df);
				}				
			}
			else if (dependencias[i].getImplicado().size() == 1) {
				DependenciaFuncional df = new DependenciaFuncional(dependencias[i].getImplicante(), dependencias[i].getImplicado());
				df.setListaAtributos(listaAtributos);
				newDependencias.add(df);
			}
		}
		
		// Convierte en array
		DependenciaFuncional[] arrDependencias = new DependenciaFuncional[newDependencias.size()];
		newDependencias.toArray(arrDependencias);
		
		return arrDependencias;
	}
	
	/**
	 * Main de prueba
	 * @param args
	 */
	public static void main (String[] args){
		//Atributo a1 = new Atributo('a', "A");
		//Atributo a2 = new Atributo('b', "B");
		//Atributo a3 = new Atributo('c', "C");
		//Atributo a4 = new Atributo('d', "D");
		
		HashSet<String> hs1 = new HashSet<String>();
		hs1.add("a");
		hs1.add("b");
		
		HashSet<String> hs2 = new HashSet<String>();
		hs2.add("e");
		
		HashSet<String> hs3 = new HashSet<String>();
		hs3.add("a");
		hs3.add("g");
		
		HashSet<String> hs4 = new HashSet<String>();
		hs4.add("j");		
		
		HashSet<String> hs5 = new HashSet<String>();
		hs5.add("b");
		hs5.add("e");
		
		HashSet<String> hs6 = new HashSet<String>();
		hs6.add("i");
		
		HashSet<String> hs7 = new HashSet<String>();
		hs7.add("g");
		
		HashSet<String> hs8 = new HashSet<String>();
		hs8.add("g");
		hs8.add("i");
		
		HashSet<String> hs9 = new HashSet<String>();
		hs9.add("h");
		
//		DependenciaFuncional dep1 = new DependenciaFuncional(hs1, hs3);
//		DependenciaFuncional dep2 = new DependenciaFuncional(hs3, hs4);
//		DependenciaFuncional dep3 = new DependenciaFuncional(hs4, hs7);
//		DependenciaFuncional dep4 = new DependenciaFuncional(hs5, hs8);
//		//DependenciaFuncional dep5 = new DependenciaFuncional(hs8, hs9);
//		
//		//HashSet<String> res = Cierre.HacerCierre(new Atributo[]{a1, a2},new DependenciaFuncional[]{dep1,dep2,dep3,dep4,dep5});
//		DependenciaFuncional[] res = LDS.LadoDerechoSimple(new DependenciaFuncional[]{dep1,dep2,dep3,dep4});
//		
//		for (int i = 0; i < res.length; i++) {
//			System.out.println(res[i].getImplicante() + " -> " + res[i].getImplicado());
//		}
	}
}
