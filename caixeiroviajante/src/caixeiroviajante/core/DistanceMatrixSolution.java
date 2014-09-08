package caixeiroviajante.core;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Solucao para o caixeiro viajante baseado na API Distance Matrix do Google
 * Maps
 * 
 * @author Fernando Felix (fernandojr.ifcg@live.com)
 * @see DistanceMatrixReader
 */
public class DistanceMatrixSolution extends Solution {

	/**
	 * {@inheritDoc}
	 */
	public DistanceMatrixSolution(DirectionsContainer persistence) {
		super(persistence);
	}

	/**
	 * {@inheritDoc}
	 */
	public DistanceMatrixSolution(DirectionsContainer persistence,
			String[] cities) throws ParserConfigurationException, SAXException,
			IOException, GoogleResponseException {
		super(persistence, cities);
	}

	/**
	 * {@inheritDoc}
	 */
	public DistanceMatrixSolution(DirectionsContainer persistence,
			String[] cities, Direction[][] directionsMatrix)
			throws ParserConfigurationException, SAXException, IOException {
		super(persistence, cities, directionsMatrix);
	}

	/**
	 * {@inheritDoc}
	 */
	public DistanceMatrixSolution(DirectionsContainer persistence,
			ArrayList<String> cities) throws ParserConfigurationException,
			SAXException, IOException, GoogleResponseException {
		super(persistence, cities);
	}

	/**
	 * {@inheritDoc}
	 */
	public DistanceMatrixSolution(DirectionsContainer persistence,
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

		// instanciando a matriz
		Direction[][] directions = new Direction[cities.length][cities.length];

		Boolean flag = false;

		// preenchendo a matriz de direcoes
		for (int i = 0; i < cities.length; i++) {
			for (int j = 0; j < cities.length; j++) {

				// checando se as direcoes existem na persistencia
				directions[i][j] = persistence.get(cities[i], cities[j]);

				// caso nao exista alguma direcao na persistencia, os lacos
				// devem ser quebrados e a flag deve ser ativada
				if (directions[i][j] == null)
					flag = true;

				if (flag == true)
					break;

			}

			if (flag == true)
				break;
		}

		// se a flag estiver ativada, significa que alguma direcao nao foi
		// encontrada na persistencia, portanto, a api distance matrix do google
		// maps deve ser utilizada de forma a preencher a matriz de direcoes e
		// salva na persistencia
		if (flag == true) {

			directions = DistanceMatrixReader.run(cities, false);

			for (int i = 0; i < directions.length; i++)
				for (int j = 0; j < directions.length; j++)
					persistence.add(directions[i][j]);

		}

		return directions;
	}

	/**
	 * Metodo que retorna uma URL (com os seguintes parametros: start, end,
	 * waypts), na qual, quando renderizada pela api javasript do google maps,
	 * retorna a visualizacao da rota minima. A rota minima eh feita baseando-se
	 * nos enderecos passados como parametro, onde o primeiro endereco
	 * represanta tanto o endereco de origem como o endereco destino (final).
	 * 
	 * @param persistence
	 *            A persistencia, na qual, contem uma lista directions salvos na
	 *            memoria
	 * @param adresses
	 *            Os enderecos, na qual servirao de base para gerar a
	 *            roteirizacao de menor distancia
	 * @return A URL personalizada, contendo parametros que serviram de base
	 *         para o google maps renderizar a rota
	 */
	public static String getRouteMapURL(String URLBASE,
			DirectionsContainer persistence, String adresses[])
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {

		// instanciando solucao
		DistanceMatrixSolution solution = new DistanceMatrixSolution(
				persistence);

		// criando a matriz de direcoes
		Direction directionsMatrix[][] = solution
				.generateDirectionsMatrix(adresses);

		// inicializando o processo de roteirizacao da solucao
		solution.init(adresses, directionsMatrix);

		// recebendo a menor rota (sequencia de indices, baseados nos indices do
		// vetor de enderecos)
		String minimumRoute = solution.getMinimumRoute();

		// renderizando os indices da menor rota em enderecos em formato de
		// string
		String route[] = solution.routeRender(minimumRoute);

		for (String r : route) {
			System.out.print(r + ", ");

		}

		// endereco de origem
		String start = "start=" + route[0];

		// endereco de destino
		String end = "end=" + route[route.length - 1];

		// enderecos entre o endereco de origem e de destino, separados por ';;'
		String waypts = "waypts=";

		for (int i = 1; i < route.length - 1; i++)

			if (i == minimumRoute.length() - 1 - 1)
				waypts += route[i];
			else
				waypts += route[i] + ";;";

		String URLparameters = start + "&" + end + "&" + waypts;

		return URLBASE + URLparameters;

	}

}
