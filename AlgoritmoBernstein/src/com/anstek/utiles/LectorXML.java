package com.anstek.utiles;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.anstek.clases.Atributo;
import com.anstek.clases.DependenciaFuncional;

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
	 * Almacena los atributos definidos en el archivo
	 */
	private Atributo[] atributos;
	
	/**
	 * Almacena las dependencias funcionales definidas en el archivo
	 */
	private DependenciaFuncional[] dependenciasFuncionales;
	
	/**
	 * Constructor de clase, carga el documento en memoria para habilitar su
	 * lectura.<br>
	 * 
	 * @param rutaArchivo
	 * @throws Exception
	 */
	public LectorXML(String rutaArchivo) throws Exception {
		System.out.println("Iniciando la lectura del documento XML en la ruta '" + rutaArchivo + "'");

		// Primero cargar el archivo en memoria
		Path ruta = Paths.get(rutaArchivo);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		documento = db.parse(ruta.toFile());
		documento.getDocumentElement().normalize();

		// Posteriormente, cargar el objeto de manejo de expresiones XPath
		xPath = XPathFactory.newInstance().newXPath();
		System.out.println("Documento abierto");
		
		// Por último, cargar los atributos de resultado
		String expresion = "count(/" + NODO_RAIZ + "/" + NODO_ATRIBUTO_PADRE + "/*)";
		int numeroNodos = ((Number) xPath.compile(expresion).evaluate(documento, XPathConstants.NUMBER)).intValue();
		if (numeroNodos < 1) {
			throw new Exception("El archivo de entrada debe contener por lo menos un atributo definido");
		}
		atributos = new Atributo[numeroNodos];
		
		expresion = "count(/" + NODO_RAIZ + "/" + NODO_DEPENDENCIA_PADRE + "/*)";
		numeroNodos = ((Number) xPath.compile(expresion).evaluate(documento, XPathConstants.NUMBER)).intValue();
		if (numeroNodos < 1) {
			throw new Exception("El archivo de entrada debe contener por lo menos una dependencia funcional definida");
		}
		dependenciasFuncionales = new DependenciaFuncional[numeroNodos];
	}

	/**
	 * @return the atributos
	 */
	public Atributo[] getAtributos() {
		return atributos;
	}

	/**
	 * @param atributos the atributos to set
	 */
	public void setAtributos(Atributo[] atributos) {
		this.atributos = atributos;
	}

	/**
	 * @return the dependenciasFuncionales
	 */
	public DependenciaFuncional[] getDependenciasFuncionales() {
		return dependenciasFuncionales;
	}

	/**
	 * @param dependenciasFuncionales the dependenciasFuncionales to set
	 */
	public void setDependenciasFuncionales(
			DependenciaFuncional[] dependenciasFuncionales) {
		this.dependenciasFuncionales = dependenciasFuncionales;
	}

	/**
	 * Método para leer la información contenida en el archivo cargado en
	 * memoria.<br>
	 * 
	 * @throws Exception 
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
	 * @throws Exception 
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
			
			Atributo atributo = new Atributo();
			atributo.setId(i + 1);
			atributo.setNombre(nodo.getFirstChild().getNodeValue());
			atributos[i] = atributo;
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
			DependenciaFuncional dependenciaFuncional =  cargarSeccionDependencia(nodo, i);
			dependenciasFuncionales[i] = dependenciaFuncional;
		}
	}
	
	/**
	 * Método para leer la información asociada a un detalle de dependencias funcional dada.<br>
	 *  
	 * @param nodoBase
	 * @param numeroNodo
	 * @return DependenciaFuncional
	 * @throws Exception
	 */
	private DependenciaFuncional cargarSeccionDependencia(Node nodoBase, int numeroNodo) throws Exception {
		DependenciaFuncional dependenciaFuncional = new DependenciaFuncional();
		
		// Primero obtener la cantidad de nodos implicantes e implicados
		String expresion = "count(" + NODO_IMPLICANTE + ")";
		int cantidadNodosImplicantes = ((Number) xPath.compile(expresion).evaluate(nodoBase, XPathConstants.NUMBER)).intValue();
		if (cantidadNodosImplicantes < 1) {
			throw new Exception("No hay nodos implicantes en la dependencia funcional numero [" + numeroNodo + "]");
		}
		expresion = "count(" + NODO_IMPLICADO + ")";
		int cantidadNodosImplicados = ((Number) xPath.compile(expresion).evaluate(nodoBase, XPathConstants.NUMBER)).intValue();
		if (cantidadNodosImplicados < 1) {
			throw new Exception("No hay nodos implicantes en la dependencia funcional numero [" + numeroNodo + "]");
		}
		
		// Después obtener la lista de nodos
		expresion = "*";
		NodeList listaNodos = (NodeList) xPath.compile(expresion).evaluate(nodoBase, XPathConstants.NODESET);
		if (listaNodos.getLength() == 0) {
			throw new Exception("No existe el detalle de la dependencia funcional " + numeroNodo + " en el documento de entrada");
		}
		
		// Recorrer la lista de nodos y almacenar los atributos
		Atributo[] atributosImplicantes = new Atributo[cantidadNodosImplicantes]; 
		Atributo[] atributosImplicados = new Atributo[cantidadNodosImplicados];
		int secuenciaImplicantes = 0; 
		int secuenciaImplicados = 0;
		for (int i = 0; i < listaNodos.getLength(); i++) {
			Node nodo = listaNodos.item(i);
			String nombreAtributo = nodo.getFirstChild().getNodeValue();
			Atributo atributo = Atributo.retornarAtributoPorNombre(nombreAtributo, this.atributos);
			String nombreNodo = nodo.getNodeName();
			
			switch (nombreNodo) {
			case NODO_IMPLICANTE:
				atributosImplicantes[secuenciaImplicantes] = atributo;
				secuenciaImplicantes++;
				System.out.println("Detalle implicante " + i +" nodo dependencia " + numeroNodo + ": " + nodo.getFirstChild().getNodeValue());
				break;
			case NODO_IMPLICADO:
				atributosImplicados[secuenciaImplicados] = atributo;
				secuenciaImplicados++;
				System.out.println("Detalle implicado " + i +" nodo dependencia " + numeroNodo + ": " + nodo.getFirstChild().getNodeValue());
				break;
			default:
				throw new Exception("Error en la estructura del detalle de la dependencia funcional " + numeroNodo + " en el docunento");
			}
		}
		dependenciaFuncional.setListaAtributos(this.atributos);
		dependenciaFuncional.setImplicado(atributosImplicados);
		dependenciaFuncional.setImplicante(atributosImplicantes);
		
		return dependenciaFuncional;
	}
}
