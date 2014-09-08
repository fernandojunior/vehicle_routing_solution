package caixeiroviajante.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * @see https://github.com/fernandojunior/simple_java_xml_reader
 */
public class XmlReader {

	private DocumentBuilderFactory documentBuilderFactory;
	private DocumentBuilder documentBuilder;
	private Document document;

	public XmlReader(File file) throws ParserConfigurationException,
			SAXException, IOException {

		documentBuilderFactory = DocumentBuilderFactory.newInstance();

		documentBuilder = documentBuilderFactory.newDocumentBuilder();

		document = documentBuilder.parse(file);

		// normalize text representation
		document.getDocumentElement().normalize();

	}

	public XmlReader(String uri) throws ParserConfigurationException,
			SAXException, IOException {

		documentBuilderFactory = DocumentBuilderFactory.newInstance();

		documentBuilder = documentBuilderFactory.newDocumentBuilder();

		document = documentBuilder.parse(uri);

		// normalize text representation
		document.getDocumentElement().normalize();

	}

	/**
	 * Metodo que converte um no em um elemento
	 * 
	 * @param node
	 *            O no a ser convertido
	 * @return O Elemento
	 */
	public Element toElement(org.w3c.dom.Node node) {
		return (Element) node;
	}

	/**
	 * Metodo que converte uma lista de nos em uma lista de elementos
	 * 
	 * @param nodeList
	 *            A lista de nos a ser convertida
	 * @return A lista de elementos
	 */
	public ArrayList<Element> toArrayList(org.w3c.dom.NodeList nodeList) {

		ArrayList<Element> elements = new ArrayList<Element>();

		for (int i = 0; i < nodeList.getLength(); i++) {

			org.w3c.dom.Node currnode = nodeList.item(i);

			// Se o no corrente for realmente um no
			if (currnode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
				elements.add(toElement(currnode));

		}

		return elements;
	}

	/**
	 * Metodo que retorna os elementos filho do documento xml
	 * 
	 * @return Os elementos filhos do documento xml
	 */
	public ArrayList<Element> getChildren() {
		return getChildren(document);

	}

	/**
	 * Metodo que retorna uma lista de elementos filhos conforme o no pai
	 * passado como parametro
	 * 
	 * @param node
	 *            O no a ser passado como parametro
	 * @return A lista de elementos a ser retornada
	 */
	public ArrayList<Element> getChildren(org.w3c.dom.Node node) {

		return toArrayList(node.getChildNodes());

	}

	/**
	 * Metodo que retorna uma lista de elementos filhos conforme o elemento pai
	 * passado como parametro
	 * 
	 * @param element
	 *            O elemento a ser passado como parametro
	 * @return A lista de elementos a ser retornada
	 */
	public ArrayList<Element> getChildren(Element element) {

		return toArrayList(element.getChildNodes());

	}

	/**
	 * Metodo que retorna uma lista de elementos filhos conforme o elemento pai
	 * passado como parametro e o nome de elemento, na qual, servira de filtro,
	 * para que a lista retorne apenas elementos filhos que possuam esse mesmo
	 * nome
	 * 
	 * @param element
	 *            O elemento pai a ser passado como parametro
	 * @param elementName
	 *            O nome de elemento que servira de filtro
	 * @return A lista de elementos filhos
	 */
	public ArrayList<Element> getChildren(org.w3c.dom.Node node, String name) {

		org.w3c.dom.NodeList nodes = toElement(node).getElementsByTagName(name);

		return toArrayList(nodes);

	}

	/**
	 * Metodo que retorna uma lista de elementos filhos conforme o elemento pai
	 * passado como parametro e o nome de elemento, na qual, servira de filtro,
	 * para que a lista retorne apenas elementos filhos que possuam esse mesmo
	 * nome
	 * 
	 * @param element
	 *            O elemento pai a ser passado como parametro
	 * @param elementName
	 *            O nome de elemento que servira de filtro
	 * @return A lista de elementos filhos
	 */
	public ArrayList<Element> getChildren(Element element, String name) {

		org.w3c.dom.NodeList nodes = element.getElementsByTagName(name);

		return toArrayList(nodes);

	}

	/**
	 * Metodo que retorna um elemento filho (o primeiro de indice 0, caso exista
	 * outros com o mesmo nome) conforme o elemento pai passado como parametro e
	 * o nome de elemento, na qual, servira de filtro, para que a lista retorne
	 * apenas elementos filhos que possuam esse mesmo nome
	 * 
	 * @param element
	 *            O elemento pai a ser passado como parametro
	 * @param elementName
	 *            O nome de elemento que servira de filtro
	 * @return O elemento filho
	 */
	public Element getChild(Element element, String name) {

		return getChildren(element, name).get(0);

	}

	/**
	 * Metodo que retorna o primeiro elemento filho (o primeiro de indice 0,
	 * caso exista outros com o mesmo nome) conforme o elemento pai passado como
	 * parametro
	 * 
	 * @param element
	 *            O elemento pai a ser passado como parametro
	 * @return O primeiro elemento filho
	 */
	public Element getChild(Element element) {

		return getChildren(element).get(0);

	}

	/**
	 * Metodo que retorna o primeiro elemento filho (o primeiro de indice 0,
	 * caso exista outros com o mesmo nome) do documento xml
	 * 
	 * @return O primeiro elemento filho do documento xml
	 */
	public Element getChild() {
		return getChildren(document).get(0);

	}

}