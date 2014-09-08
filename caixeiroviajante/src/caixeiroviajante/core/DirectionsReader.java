package caixeiroviajante.core;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;

import org.w3c.dom.Element;

import caixeiroviajante.util.Util;
import caixeiroviajante.util.XmlReader;

/**
 * Ler resposta XML de uma requisicao a API Directions do Google Maps
 * 
 * <br>
 * {@link http ://code.google.com/apis/maps/documentation/directions}
 * 
 * @author Fernando Felix (fernandojr.ifcg@live.com)
 * @see XmlReader
 */
public class DirectionsReader {

	public static final String URLBASE = "http://maps.googleapis.com/maps/api/directions/xml?sensor=false&mode=driving&waypoints=optimize:true";

	private static Direction logic(XmlReader reader, String origin,
			String destination) throws ParserConfigurationException,
			SAXException, IOException, GoogleResponseException {

		/**
		 * initializing directions response variables
		 */

		Location start_location = new Location();
		Location end_location = new Location();
		double distance = 0.0;
		double duration = 0.0;

		start_location.location = origin;
		end_location.location = destination;

		/**
		 * handling the google maps directions response xml
		 */

		// Getting the root element 'directionsResponse' from the document
		Element rootElement = reader.getChild();

		// Getting the 'status' element from the root element.
		Element statusElement = reader.getChild(rootElement, "status");

		// Status Response
		String status = statusElement.getTextContent();

		// Handling status response
		if (status.equals("OVER_QUERY_LIMIT"))
			return run(origin, destination, true);

		if (status.equals("NOT_FOUND") || status.equals("ZERO_RESULTS")
				|| status.equals("MAX_WAYPOINTS_EXCEEDED")
				|| status.equals("INVALID_REQUEST")
				|| status.equals("REQUEST_DENIED")
				|| status.equals("UNKNOWN_ERROR"))
			throw new GoogleResponseException(
					"\nGoogle Directions Response Exception.\n Status of the request: "
							+ status
							+ ".\n See <http://code.google.com/apis/maps/documentation/directions/#StatusCodes> for more information");

		// if status equals OK, continue ...

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

		return new Direction(start_location, end_location, distance, duration);

	} // end of logic

	public static Direction run(String uri, String origin, String destination)
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {

		XmlReader reader = new XmlReader(uri);

		return logic(reader, origin, destination);

	}

	/**
	 * 
	 * @param origin
	 *            A local de origem da requisicao
	 * @param destination
	 *            O local de destino da requisicao
	 * @param wait
	 *            Se true, o sistema ira aguardar 40 segundos ate enviar a
	 *            requisicao; caso contrario a requisicao sera enviada
	 *            imediatamente
	 * @return Um direction response do google maps conforme os parametros
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws GoogleResponseException
	 */
	public static Direction run(String origin, String destination, Boolean wait)
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {

		String directions = "&origin=" + origin + "&destination=" + destination;

		String uri = URLBASE + directions;

		// Aguardar 40 segundos para enviar a requisicao
		if (wait == true)
			Util.sleep(40);

		return run(uri, origin, destination);

	}

}
