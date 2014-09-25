package com.anstek.prueba;

import com.anstek.utiles.LectorXML;

public class Prueba {

	public static void main(String[] args) {
		try {
			LectorXML lector = new LectorXML("/home/leonardo/Dropbox/ESPECIALIZACION EN INGENIERIA DE SOFTWARE/BASES DE DATOS/TALLER/archivoEntrada.xml");
			lector.cargarInformacion();
			System.out.println(lector.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
	}

}
