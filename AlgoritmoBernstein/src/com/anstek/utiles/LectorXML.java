package com.anstek.utiles;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LectorXML {

	/**
	 * Nombre del nodo padre de la sección de atributos
	 */
	public static final String NODO_ATRIBUTO_PADRE = "atributos";

	/**
	 * Nombre del nodo hijo de la sección de atributos
	 */
	public static final String NODO_ATRIBUTO_HIJO = "atributo";

	/**
	 * Nombre del nodo padre de la sección de dependencias funcionales
	 */
	public static final String NODO_DEPENDENCIA_PADRE = "dependenciasF";

	/**
	 * Nombre del nodo hijo de la sección de dependencias funcionales
	 */
	public static final String NODO_DEPENDENCIA_HIJO = "dependencia";
	
	/**
	 * Nombre del nodo raíz del documento
	 */
	public static final String NODO_RAIZ = "documento";
	
	/**
	 * Nombre del nodo de implicante
	 */
	public static final String NODO_IMPLICANTE = "implicante";
	
	/**
	 * Nombre del nodo de implicado
	 */
	public static final String NODO_IMPLICADO = "implicado";

	/**
	 * Atributo para el manejo del documento en memoria
	 */
	private Document documento;

	/**
	 * Atributo para el manejo de sentencias XPath
	 */
	private XPath xPath;

	/**
	 * Constructor de clase, carga el documento en memoria para habilitar su
	 * lectura.<br>
	 * 
	 * @param rutaArchivo
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public LectorXML(String rutaArchivo) throws ParserConfigurationException, SAXException, IOException {
		System.out.println("Iniciando la lectura del documento XML en la ruta '" + rutaArchivo + "'");

		// Primero cargar el archivo en memoria
		Path ruta = Paths.get(rutaArchivo);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		documento = db.parse(ruta.toFile());
		documento.getDocumentElement().normalize();

		// Por último, cargar el objeto de manejo de expresiones XPath
		xPath = XPathFactory.newInstance().newXPath();
		System.out.println("Documento abierto");
	}

	/**
	 * Método para leer la información contenida en el archivo cargado en
	 * memoria
	 * @throws Exception 
	 * @throws XPathExpressionException 
	 */
	public void cargarInformacion() throws Exception {
		System.out.println("Iniciando el método de carga de información del archivo");

		// Primero cargar la sección de atributos
		cargarSeccionAtributos();

		// Por último cargar la sección de dependencias funcionales
		cargarSeccionDependenciaPadre();

		System.out.println("Carga de información del archivo finalizada");
	}

	/**
	 * Método para leer la información de la sección Atributos del archivo.<br>
	 * 
	 * @param nombreNodo
	 * @throws Exception 
	 * @throws XPathExpressionException 
	 */
	private void cargarSeccionAtributos() throws Exception {
		// Primero obtener la lista de nodos
		String expresion = "/" + NODO_RAIZ + "/" + NODO_ATRIBUTO_PADRE + "/*";
		NodeList listaNodos = (NodeList) xPath.compile(expresion).evaluate(documento, XPathConstants.NODESET);
		if (listaNodos.getLength() == 0) {
			throw new Exception("No existe la seccion de atributos en el documento");
		}

		// Recorrer la lista de nodos y almacenar los atributos
		for (int i = 0; i < listaNodos.getLength(); i++) {
			Node nodo = listaNodos.item(i);
			if (!nodo.getNodeName().equals(NODO_ATRIBUTO_HIJO)) {
				throw new Exception("Error en la estructura de la seccion de atributos en el docunento");
			}
			
			System.out.println("Nodo atributo " + i + ": " + nodo.getFirstChild().getNodeValue());
		}
	}

	/**
	 * Método para leer la información de la sección Atributos del archivo.<br>
	 * 
	 * @param nombreNodo
	 * @throws Exception 
	 */
	private void cargarSeccionDependenciaPadre() throws Exception {
		// Primero obtener la lista de nodos
		String expresion = "/" + NODO_RAIZ + "/" + NODO_DEPENDENCIA_PADRE + "/*";
		NodeList listaNodos = (NodeList) xPath.compile(expresion).evaluate(documento, XPathConstants.NODESET);
		if (listaNodos.getLength() == 0) {
			throw new Exception("No existe la seccion de atributos en el documento de entrada");
		}

		// Recorrer la lista de nodos y almacenar los atributos
		for (int i = 0; i < listaNodos.getLength(); i++) {
			Node nodo = listaNodos.item(i);
			if (!nodo.getNodeName().equals(NODO_DEPENDENCIA_HIJO)) {
				throw new Exception("Error en la estructura de la seccion de atributos en el docunento");
			}
			
			System.out.println("Nodo dependencia " + i + "...");
			cargarSeccionDependencia(nodo, i);
		}
	}
	
	private void cargarSeccionDependencia(Node nodoBase, int numeroNodo) throws Exception {
		// Primero obtener la lista de nodos
		String expresion = "*";
		NodeList listaNodos = (NodeList) xPath.compile(expresion).evaluate(nodoBase, XPathConstants.NODESET);
		if (listaNodos.getLength() == 0) {
			throw new Exception("No existe el detalle de la dependencia funcional " + numeroNodo + " en el documento de entrada");
		}
		
		// Recorrer la lista de nodos y almacenar los atributos
		for (int i = 0; i < listaNodos.getLength(); i++) {
			Node nodo = listaNodos.item(i);
			String nombreNodo = nodo.getNodeName();
			switch (nombreNodo) {
			case NODO_IMPLICANTE:
				System.out.println("Detalle implicante " + i +" nodo dependencia " + numeroNodo + ": " + nodo.getFirstChild().getNodeValue());
				break;
			case NODO_IMPLICADO:
				System.out.println("Detalle implicado " + i +" nodo dependencia " + numeroNodo + ": " + nodo.getFirstChild().getNodeValue());
				break;
			default:
				throw new Exception("Error en la estructura del detalle de la dependencia funcional " + numeroNodo + " en el docunento");
			}
		}
	}
}
