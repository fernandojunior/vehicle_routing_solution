package caixeiroviajante.core;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import caixeiroviajante.util.Util;
import caixeiroviajante.util.XmlReader;

/**
 * Ler resposta XML de uma requisicao a API Distance Matrix do Google Maps <br>
 * 
 * {@link http://code.google.com/apis/maps/documentation/directions}
 * 
 * @author Fernando Felix (fernandojr.ifcg@live.com)
 * @see XmlReader
 */
public class DistanceMatrixReader {

	public static final String URLBASE = "http://maps.googleapis.com/maps/api/distancematrix/xml?mode=driving&sensor=false";

	private static Direction[][] logic(XmlReader reader, String cities[])
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {

		// initing matrix
		Direction[][] matrix = new Direction[cities.length][cities.length];

		// Getting the root element 'DistanceMatrixResponse' from the document
		Element root = reader.getChild();

		// Status Response
		String rootStatus = reader.getChild(root, "status").getTextContent();

		// Handling status response of the document
		if (rootStatus.equals("OVER_QUERY_LIMIT"))
			return run(cities, true);

		if (rootStatus.equals("NOT_FOUND") || rootStatus.equals("ZERO_RESULTS")
				|| rootStatus.equals("MAX_WAYPOINTS_EXCEEDED")
				|| rootStatus.equals("INVALID_REQUEST")
				|| rootStatus.equals("REQUEST_DENIED")
				|| rootStatus.equals("UNKNOWN_ERROR"))
			throw new GoogleResponseException(
					"\nGoogle Directions Response Exception.\n Status of the request: "
							+ rootStatus
							+ ".\n See <http://code.google.com/apis/maps/documentation/directions/#StatusCodes> for more information");

		// if status equals OK, continue ...

		// getting rows children from the root
		ArrayList<Element> rows = reader.getChildren(root, "row");

		for (int i = 0; i < rows.size(); i++) {

			// Elements children of current row
			ArrayList<Element> rowChildren = reader.getChildren(rows.get(i),
					"element");

			// handling the elements of the current row
			for (int j = 0; j < rowChildren.size(); j++) {
				Element element = rowChildren.get(j);

				String status = reader.getChild(element, "status")
						.getTextContent();

				if (!status.equals("OK"))
					throw new GoogleResponseException(
							"\nGoogle Directions Response Exception.\n Status of the request: "
									+ status + ". FROM " + cities[i] + ", TO "
									+ cities[j]);

				double distance = Double.parseDouble(reader.getChild(
						reader.getChild(element, "distance"), "value")
						.getTextContent());

				double duration = Double.parseDouble(reader.getChild(
						reader.getChild(element, "duration"), "value")
						.getTextContent());

				Location start = new Location(cities[i], 0, 0);
				Location end = new Location(cities[j], 0, 0);

				matrix[i][j] = new Direction(start, end, distance, duration);

			}

		}

		Util.sleep(10);

		return matrix;

	} // end of logic

	public static Direction[][] run(String uri, String cities[])
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {

		XmlReader reader = new XmlReader(uri);

		return logic(reader, cities);

	}

	/**
	 * 
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
	public static Direction[][] run(String cities[], Boolean wait)
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {

		String origins = "origins=";
		String destinations = "destinations=";

		for (int i = 0; i < cities.length; i++) {
			if (i == cities.length - 1) {
				origins += cities[i];
				destinations += cities[i];
			} else {
				origins += cities[i] + "|";
				destinations += cities[i] + "|";
			}

		}

		String uri = URLBASE + "&" + origins + "&" + destinations;

		// Aguardar 40 segundos para enviar a requisicao
		if (wait == true)
			Util.sleep(40);

		return run(uri, cities);

	}

	public static void teste() {

		try {
			String cities[] = { "campina grande, pb", "esperanca, pb" };
			Direction[][] matrix = DistanceMatrixReader.run(cities, true);

			for (int i = 0; i < matrix.length; i++)
				for (int j = 0; j < matrix.length; j++)
					System.out.println(matrix[i][j].toString());

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GoogleResponseException e) {
			e.printStackTrace();
		}

	}

}
