/**
 * 
 */
package com.anstek.utiles;

import java.util.HashSet;

import com.anstek.clases.Atributo;
import com.anstek.clases.DependenciaFuncional;

/**
 * @author ddmurillo
 *
 */
public class Cierre {
	/**
	 * Hace el cierre dado un array de atributos
	 * @param attr Array de los atributos a los que se les va a calcular el cierre
	 * @param dep Array de dependencias funcionales
	 * @return HashSet como resultado del cierre
	 */
	public static HashSet<Integer> HacerCierre(Atributo[] attr, DependenciaFuncional[] dep){
		HashSet<Integer> cierre = new HashSet<Integer>();
		
		// agrega los mismos atributos como parte del cierre
		for (int i = 0; i < attr.length; i++) {
			cierre.add(attr[i].getId());
		}
		
		// bucle principal
		while (true) {
			// temporal
			HashSet<Integer> cierretmp = new HashSet<Integer>();
			// longitud inicial
			int len = cierre.size();
			for (int j = 0; j < dep.length;j++) {
				if (DependenciaFuncional.conjuntoContenido(dep[j].getImplicante(), cierre, false)) {
					cierretmp.addAll(dep[j].getImplicado());
				}
			}
			
//			for (String v : cierre) {
//				for(int j = 0; j < dep.length;j++){
//					if(dep[j].getImplicante().contains(v)){
//						cierretmp.addAll(dep[j].getImplicado());
//					}
//				}
//			}
			
			cierre.addAll(cierretmp);
			
			// si la longitud no cambia sale
			if (len == cierre.size()) {
				break;
			}
		}
		
		return cierre;
	}
	
	/**
	 * Main de prueba
	 * @param args
	 */
	public static void main(String[] args){
//		Atributo a1 = new Atributo('a', "A");
//		Atributo a2 = new Atributo('b', "B");
//		//Atributo a3 = new Atributo('c', "C");
//		//Atributo a4 = new Atributo('d', "D");
//		
//		HashSet<String> hs1 = new HashSet<String>();
//		hs1.add("a");
//		hs1.add("b");
//		
//		HashSet<String> hs2 = new HashSet<String>();
//		hs2.add("e");
//		
//		HashSet<String> hs3 = new HashSet<String>();
//		hs3.add("a");
//		hs3.add("g");
//		
//		HashSet<String> hs4 = new HashSet<String>();
//		hs4.add("j");		
//		
//		HashSet<String> hs5 = new HashSet<String>();
//		hs5.add("b");
//		hs5.add("e");
//		
//		HashSet<String> hs6 = new HashSet<String>();
//		hs6.add("i");
//		
//		HashSet<String> hs7 = new HashSet<String>();
//		hs7.add("g");
//		
//		HashSet<String> hs8 = new HashSet<String>();
//		hs8.add("g");
//		hs8.add("i");
//		
//		HashSet<String> hs9 = new HashSet<String>();
//		hs9.add("h");
		
//		DependenciaFuncional dep1 = new DependenciaFuncional(hs1, hs2);
//		DependenciaFuncional dep2 = new DependenciaFuncional(hs3, hs4);
//		DependenciaFuncional dep3 = new DependenciaFuncional(hs5, hs6);
//		DependenciaFuncional dep4 = new DependenciaFuncional(hs2, hs7);
//		DependenciaFuncional dep5 = new DependenciaFuncional(hs8, hs9);
		
//		HashSet<String> res = Cierre.HacerCierre(new Atributo[]{a1, a2},new DependenciaFuncional[]{dep1,dep2,dep3,dep4,dep5});
		
//		System.out.println(res.toString());
	}
}
