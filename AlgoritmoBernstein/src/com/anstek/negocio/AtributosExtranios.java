/**
 * 
 */
package com.anstek.negocio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.anstek.clases.Atributo;
import com.anstek.clases.DependenciaFuncional;
import com.anstek.utiles.Cierre;

/**
 * @author ddmurillo
 *
 */
public class AtributosExtranios {
	public static Atributo[] listaAtributos;
	
	/**
	 * Elimina atributos extra�os de las dependencias funcionales (solo aplica para los implicantes com mas de un atributo)
	 * @param dependencias Dependencias funcionales
	 * @return Array con las dependencias funcionales sin atributos extra�os
	 */
	public static DependenciaFuncional[] LimpiaAtributosExtranios(DependenciaFuncional[] dependencias) {
		List<DependenciaFuncional> newDependencias = new ArrayList<DependenciaFuncional>();

		for (int i = 0; i < dependencias.length; i++) {
			// Se simplifican los implicantes con mas de un atributo
			if (dependencias[i].getImplicante().size() > 1) {
				//
				HashSet<String> dfExtr = new HashSet<String>();
				for (String v : dependencias[i].getImplicante()) {
					// Quita el implicante actual
					HashSet<String> temp = new HashSet<String>(dependencias[i].getImplicante());
					temp.remove(v);
					
					// Array temporal de atributos de implicantes sobrantes
					Atributo[] arrAtt = new Atributo[temp.size()];
					int flag = 0;
					for (String t : temp) {
						arrAtt[flag] = new Atributo(t.charAt(0), t);
						flag++;
					}						
					
					// Realiza el cierre de los demas implicantes
					HashSet<String> strange = Cierre.HacerCierre(arrAtt, dependencias);
					
					//Es extra�o
					if (strange.contains(v)) {
						dfExtr.add(v);
					}
				}
				
				// La dependencia tiene atributos extra�os
				if (dfExtr.size() > 0) {
					HashSet<String> implicantesLimpios = new HashSet<String>(dependencias[i].getImplicante());
					for (String s : dfExtr) {
						implicantesLimpios.remove(s);
					}
					// Nueva dependencia
					DependenciaFuncional df = new DependenciaFuncional(implicantesLimpios, dependencias[i].getImplicado());
					df.setListaAtributos(listaAtributos);
					newDependencias.add(df);
				}
				
			} else if (dependencias[i].getImplicante().size() == 1) {
				DependenciaFuncional df = new DependenciaFuncional(dependencias[i].getImplicante(),dependencias[i].getImplicado());
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
	 * Main prueba
	 * @param args
	 */
	public static void main (String[] args){
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
		
		DependenciaFuncional[] res = AtributosExtranios.LimpiaAtributosExtranios(new DependenciaFuncional[]{dep1,dep2,dep3,dep4,dep5});
		
		for (int i = 0; i < res.length; i++) {
			System.out.println(res[i].getImplicante() + " -> " + res[i].getImplicado());
		}
	}
}
