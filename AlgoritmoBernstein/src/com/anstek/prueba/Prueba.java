package com.anstek.prueba;

import java.util.HashSet;

import com.anstek.clases.Atributo;
import com.anstek.utiles.Cierre;
import com.anstek.utiles.LectorXML;

public class Prueba {

	public static void main(String[] args) {
		try {
			LectorXML lector = new LectorXML("/home/leonardo/Dropbox/ESPECIALIZACION EN INGENIERIA DE SOFTWARE/BASES DE DATOS/TALLER/pruebaCierre2.xml");
			lector.cargarInformacion();
			
			HashSet<String> res = Cierre.HacerCierre(new Atributo[]{Atributo.retornarAtributoPorNombre("C", lector.getAtributos()), Atributo.retornarAtributoPorNombre("D", lector.getAtributos())}, lector.getDependenciasFuncionales());
			HashSet<String> atributosCierre = Atributo.retornarListadoNombresAtributos(res, lector.getAtributos());
			System.out.println("Resultado cierre: " + atributosCierre);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
	}

}
