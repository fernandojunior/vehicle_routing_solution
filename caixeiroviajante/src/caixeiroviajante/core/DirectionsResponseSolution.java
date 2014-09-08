package caixeiroviajante.core;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Solucao para o caixeiro viajante baseado na API Directions do Google Maps
 * 
 * @author Fernando Felix (fernandojr.ifcg@live.com)
 * @see DirectionsReader
 */
public class DirectionsResponseSolution extends Solution {

	/**
	 * {@inheritDoc}
	 */
	public DirectionsResponseSolution(DirectionsContainer persistence) {
		super(persistence);
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectionsResponseSolution(DirectionsContainer persistence,
			String[] cities) throws ParserConfigurationException, SAXException,
			IOException, GoogleResponseException {
		super(persistence, cities);
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectionsResponseSolution(DirectionsContainer persistence,
			String[] cities, Direction[][] directionsMatrix)
			throws ParserConfigurationException, SAXException, IOException {
		super(persistence, cities, directionsMatrix);
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectionsResponseSolution(DirectionsContainer persistence,
			ArrayList<String> cities) throws ParserConfigurationException,
			SAXException, IOException, GoogleResponseException {
		super(persistence, cities);
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectionsResponseSolution(DirectionsContainer persistence,
			ArrayList<String> cities, Direction[][] directionsMatrix)
			throws ParserConfigurationException, SAXException, IOException {
		super(persistence, cities, directionsMatrix);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Direction[][] generateDirectionsMatrix(String[] cities)
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {

		Integer size = cities.length;

		Direction directionsMatrix[][] = new Direction[size][size];

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {

				directionsMatrix[i][j] = persistence.get(cities[i], cities[j]);

				if (directionsMatrix[i][j] == null)
					directionsMatrix[i][j] = persistence.add(cities[i],
							cities[j]);

			}

		return directionsMatrix;

	}

	/**
	 * Metodo que retorna uma URL personalizada, na qual, quando renderizada
	 * pelo google maps, retorna a rota minima (de menor distancia) de way
	 * points (enderecos) passados como paramtro.
	 * 
	 * @param persistence
	 *            A persistencia, na qual, contem uma lista directions salvos na
	 *            memoria
	 * @param waypoints
	 *            As cidades, na qual servirao de base para gerar a roteirizacao
	 *            de menor distancia
	 * @return A URL personalizada, contendo parametros que serviram de base
	 *         para o google maps renderizar a rota
	 */
	public static String getRouteMapURL(String URLBASE,
			DirectionsContainer persistence, String waypoints[])
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {

		DirectionsResponseSolution solution = new DirectionsResponseSolution(
				persistence);

		Direction directionsMatrix[][] = solution
				.generateDirectionsMatrix(waypoints);

		solution.init(waypoints, directionsMatrix);

		String minimumRoute = solution.getMinimumRoute();

		String route[] = solution.routeRender(minimumRoute);

		String start = "start=" + route[0];
		String end = "end=" + route[route.length - 1];
		String waypts = "waypts=";

		for (int i = 1; i < route.length - 1; i++)

			if (i == minimumRoute.length() - 1 - 1)
				waypts += route[i];
			else
				waypts += route[i] + ";;";

		String parameters = start + "&" + end + "&" + waypts;

		return URLBASE + parameters;

	}

}
