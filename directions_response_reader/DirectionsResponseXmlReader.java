import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * 
 * DirectionsResponseXmlReader, Classe que define um leitor xml de directions
 * response do google maps
 * 
 * http://code.google.com/apis/maps/documentation/directions/
 * 
 * @author fernandojr.ifcg@live.com, http://fjacademic.wordpress.com/
 * 
 */
public class DirectionsResponseXmlReader {

	/**
	 * Google Maps directions response URL base
	 */
	public static final String googlemapsurlbase = "http://maps.googleapis.com/maps/api/directions/xml?sensor=false&travelMode=google.maps.DirectionsTravelMode.DRIVING";

	/**
	 * Metodo que contem a logica de leitura do xml retornado pelo google maps
	 * 
	 * @return Direction
	 */
	private static Direction logic(XmlReader reader) {

		/**
		 * initializing directions response variables
		 */
		String start_address = "";
		String end_address = "";
		Location start_location = new Location();
		Location end_location = new Location();
		double distance = 0.0;
		double duration = 0.0;

		/**
		 * handling the google maps directions response xml
		 */

		// Getting the root element 'directionsResponse' from the document .
		Element rootElement = reader.getChild();

		// Getting the 'route' element from the root element.
		Element routeElement = reader.getChild(rootElement, "route");

		// Getting the 'leg' child from the route element.
		Element legElement = reader.getChild(routeElement, "leg");

		// Getting a list of children of the leg element.
		ArrayList<Element> legChildren = reader.getChildren(legElement);

		// Handling the leg children elements
		for (Iterator<Element> i = legChildren.iterator(); i.hasNext();) {

			// Getting the current child element of the list.
			Element curr = i.next();

			// Getting the name of current child.
			String childName = curr.getTagName();

			// Factoring
			if (childName.equals("start_address"))
				start_address = curr.getTextContent();

			if (childName.equals("end_address"))
				end_address = curr.getTextContent();

			if (childName.equals("start_location")) {

				start_location.latitude = Double.parseDouble(reader.getChild(
						curr, "lat").getTextContent());

				start_location.longitude = Double.parseDouble(reader.getChild(
						curr, "lng").getTextContent());

			}

			if (childName.equals("end_location")) {
				end_location.latitude = Double.parseDouble(reader.getChild(
						curr, "lat").getTextContent());

				end_location.longitude = Double.parseDouble(reader.getChild(
						curr, "lng").getTextContent());
			}

			if (childName.equals("distance"))
				distance = Double.parseDouble(reader.getChild(curr, "value")
						.getTextContent());

			if (childName.equals("duration"))
				duration = Double.parseDouble(reader.getChild(curr, "value")
						.getTextContent());

		} // loop end

		start_location.location = start_address;
		end_location.location = end_address;

		return new Direction(start_location, end_location, distance, duration);

	} // end of logic

	/**
	 * Metodo que retorna um direction com base na URI passada como parametro
	 * (deve ser uma requisicao de um directions response do google maps)
	 * 
	 * @param
	 * @return Direction
	 */
	public static Direction run(String uri)
			throws ParserConfigurationException, SAXException, IOException {

		XmlReader reader = new XmlReader(uri);

		return logic(reader);

	}

	/**
	 * Metodo que retorna um direction com base em uma respota directin response
	 * do google maps com base no endereco de origem/inicio e no endereco de
	 * destino/fim
	 * 
	 * @param origin
	 *            Endereco de origem
	 * @param destination
	 *            Endereco de destino
	 * @return Direction
	 */
	public static Direction run(String origin, String destination)
			throws ParserConfigurationException, SAXException, IOException {

		String directions = "&origin=" + origin + "&destination=" + destination;

		String uri = googlemapsurlbase + directions;

		return run(uri);

	}

}
