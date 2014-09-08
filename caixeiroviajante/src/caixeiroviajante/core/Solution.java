package caixeiroviajante.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import caixeiroviajante.util.Util;

/**
 * Define solucao para o problema do caixeiro viajante
 * 
 * @author Fernando Felix (fernandojr.ifcg@live.com)
 * 
 */
public abstract class Solution {

	/**
	 * Persistencia (volatil) contendo directions
	 */
	protected DirectionsContainer persistence;

	/**
	 * Vertices, nesse caso, as cidades. No entanto, pode ser qualquer endereco
	 * valido para a API do Google Maps
	 */
	protected String cities[];

	/**
	 * Matriz de pesos, nesse caso, matriz de directions
	 */
	protected Direction[][] directionsMatrix;

	/**
	 * Permutacoes dos indices dos vertices (sendo que a origem e o destino
	 * sempre sera o vertice 0), na qual representam todas as rotas possiveis
	 */
	protected List<String> possibleRoutes;

	/**
	 * A rota que possui a menor distancia
	 */
	protected String minimumRoute;

	/**
	 * Metodo que inicializa a instancia com um vetor de cidades, na qual
	 * servira de base para encontrar e melhor (de menor distancia) rota
	 * 
	 * @param cities
	 *            O vetor de cidades
	 */
	public void init(String cities[]) throws ParserConfigurationException,
			SAXException, IOException, GoogleResponseException {

		if (persistence == null)
			persistence = new DirectionsContainer();

		this.cities = cities;
		this.directionsMatrix = generateDirectionsMatrix(this.cities);
		this.possibleRoutes = generatePossibleRoutes(this.cities);
		this.minimumRoute = generateMinimumRoute(possibleRoutes);

	}

	/**
	 * Metodo que inicializa a instancia com um vetor de cidades (na qual
	 * servira de base para encontrar e melhor (de menor distancia) rota) e uma
	 * matriz de directions (pesos)
	 * 
	 * @param cities
	 *            O vetor de cidades
	 * @param directionsMatrix
	 *            A matriz de directions (pesos)
	 */
	public void init(String cities[], Direction[][] directionsMatrix)
			throws ParserConfigurationException, SAXException, IOException {

		if (persistence == null)
			persistence = new DirectionsContainer();

		this.cities = cities;
		this.directionsMatrix = directionsMatrix;
		this.possibleRoutes = generatePossibleRoutes(this.cities);
		this.minimumRoute = generateMinimumRoute(possibleRoutes);

	}

	/**
	 * Construtor que inicializa a instancia com uma persistencia
	 * 
	 * @param persistence
	 *            A persistencia
	 */
	public Solution(DirectionsContainer persistence) {
		this.persistence = persistence;
	}

	/**
	 * Construtor que inicializa a instancia com a persistencia e as cidades que
	 * servirao de base para gerar a melhor (menor distancia) roteirizacao
	 * 
	 * @param persistence
	 *            A persistencia
	 * @param cities
	 *            As cidades
	 */
	public Solution(DirectionsContainer persistence, ArrayList<String> cities)
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {

		this.persistence = persistence;

		this.init(Util.parseToStringVector(cities));
	}

	/**
	 * Construtor que inicializa a instancia com a persistencia e as cidades que
	 * servirao de base para gerar a melhor (menor distancia) roteirizacao
	 * 
	 * @param persistence
	 *            A persistencia
	 * @param cities
	 *            As cidades
	 */
	public Solution(DirectionsContainer persistence, ArrayList<String> cities,
			Direction[][] directionsMatrix)
			throws ParserConfigurationException, SAXException, IOException {
		this.persistence = persistence;
		this.init(Util.parseToStringVector(cities), directionsMatrix);
	}

	/**
	 * Construtor que inicializa a instancia com a persistencia e as cidades que
	 * servirao de base para gerar a melhor (menor distancia) roteirizacao
	 * 
	 * @param persistence
	 *            A persistencia
	 * @param cities
	 *            As cidades
	 */
	public Solution(DirectionsContainer persistence, String cities[])
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {
		this.persistence = persistence;
		this.init(cities);
	}

	/**
	 * Construtor que inicializa a instancia com a persistencia e as cidades que
	 * servirao de base para gerar a melhor (menor distancia) roteirizacao
	 * 
	 * @param persistence
	 *            A persistencia
	 * @param cities
	 *            As cidades
	 */
	public Solution(DirectionsContainer persistence, String cities[],
			Direction[][] directionsMatrix)
			throws ParserConfigurationException, SAXException, IOException {
		this.persistence = persistence;
		this.init(cities, directionsMatrix);
	}

	/**
	 * Metodo que gera uma matriz de directions (pesos), com base no vetor de
	 * cidades, utilizando-se da API do Google Maps
	 * 
	 * @param cities
	 *            O vetor de cidades
	 * 
	 * @return A matriz de directions (pesos)
	 */

	public abstract Direction[][] generateDirectionsMatrix(String cities[])
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException;

	/**
	 * Metodo que gera uma matriz de directions (pesos), com base em um array
	 * list de cidades e uma matriz de directions (pesos), utilizando-se da API
	 * do Google Maps
	 * 
	 * @param cities
	 *            O array list de cidades
	 * 
	 * @return A matriz de directions (pesos)
	 */
	public Direction[][] generateDirectionsMatrix(ArrayList<String> cities)
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {

		return generateDirectionsMatrix(Util.parseToStringVector(cities));

	}

	/**
	 * Metodo que gera uma lista contendo todas as permutacoes possiveis
	 * (baseadas nos indices das cidades). Cada permutacao representa uma rota
	 * possivel, e toda rota inicia/termina no indice 0 (a primeira cidade do
	 * vetor)
	 * 
	 * @param cities
	 *            As cidades, na qual, seus indices serao permutados, de forma a
	 *            gerar todas as rotas possiveis
	 * 
	 * @return A lista de permutacoes (rotas possiveis)
	 */
	protected List<String> generatePossibleRoutes(String cities[]) {

		String indices = "";

		// permutar indices de 1 ate o tamanho do vetor de cidades menos 1, pois
		// o indice 0 sempre sera o indice de partida
		for (int i = 0; i < cities.length; i++) {
			indices += i;
		}

		// permutacoes = rotas possiveis
		List<String> permutacoes = Permutation.getPermutations(indices);

		// reconfigurando as permutacoes, para que o indice 0 seja tanto o
		// vertice de origem como de destino
		// for (int i = 0; i < permutacoes.size(); i++) {

		// permutacao corrente
		// String curr = permutacoes.get(i);

		// setando o indice 0 como o vertice de origem e de destino para o
		// vertice corrente
		// permutacoes.set(i, "0" + curr + "0");

		// }

		// possible routes
		return permutacoes;
	}

	/**
	 * Metodo que gera a rota minima (de menor distancia) de todas as rotas
	 * possiveis
	 * 
	 * @param possibleRoutes
	 *            Uma lista contendo todas as rotas possiveis
	 * @return A rota minima gerada
	 */
	protected String generateMinimumRoute(List<String> possibleRoutes) {
		String miniRoute = "";

		if (possibleRoutes.size() >= 0) {
			miniRoute = possibleRoutes.get(0);
			double minivalue = getRouteDistance(possibleRoutes.get(0));

			for (Iterator<String> iterator = possibleRoutes.iterator(); iterator
					.hasNext();) {

				String currRoute = iterator.next();

				double currValue = getRouteDistance(currRoute);

				if (currValue < minivalue) {
					minivalue = currValue;
					miniRoute = currRoute;
				}
			}
		}

		return miniRoute;

	}

	/**
	 * Verifica se a sequencia de indices (route) eh valida, ou seja, se os
	 * indices correspondem a algum indice do vetor de cidades
	 * 
	 * @param route
	 *            A rota, na qual eh uma sequencia de indices (que representam
	 *            as cidades/vertices)
	 * @return retorna 'true', caso seja uma rota valida; 'false', caso
	 *         contrario
	 */
	public Boolean isValidRoute(String route) {

		for (int i = 0; i < route.length(); i++) {

			// getting curr index
			int curr = Integer.parseInt(route.charAt(i) + "");

			// cheching if curr index is a index of cities
			if (curr >= cities.length || curr < 0)
				return false;

		}

		return true;

	}

	/**
	 * Metodo que traduz uma sequencia de indices (route) para uma sequencia de
	 * nomes de cidades (enderecos), conforme o vetor cities
	 * 
	 * @param route
	 *            A sequencia de indices
	 * @return A rota traduzida em uma sequencia de nomes de cidades
	 */
	public String[] routeRender(String route) {

		if (!isValidRoute(route))
			throw new ArrayIndexOutOfBoundsException();

		// vetor contendo a sequencia de nomes de cidades
		String routeCities[] = new String[route.length()];

		for (int i = 0; i < route.length(); i++) {

			// indice corrente da rota
			int iCurr = Integer.parseInt(route.charAt(i) + "");

			// adicionando uma cidade do vetor cities com base no indice
			// corrente da rota
			routeCities[i] = cities[iCurr];

		}

		return routeCities;

	}

	/**
	 * Metodo que retorna a distancia total de uma rota (sequencia de indices)
	 * passada como parametro. Exemplo: Para uma rota igual a '01234', a
	 * distancia total sera igual a distancia(aresta(0,1)) +
	 * distancia(aresta(1,2)) + distancia(aresta(2,3)) + distancia(aresta(3,4))
	 * 
	 * @param route
	 *            A rota (sequencia de indices) a ser passada como parametro
	 * @return A distancia total da rota
	 */
	public Double getRouteDistance(String route) {

		// se nao for uma rota valida
		if (!isValidRoute(route))
			throw new ArrayIndexOutOfBoundsException();

		Double total = 0.0;

		// Acumulando o total com base nas arestas
		for (int i = 0; i < route.length(); i++) {

			if (i + 1 < route.length()) {

				// primeiro_dia_semana da aresta
				int x = Integer.parseInt(route.charAt(i) + "");

				// ultimo_dia_semana da aresta
				int y = Integer.parseInt(route.charAt(i + 1) + "");

				total += directionsMatrix[x][y].distance;
			}
		}

		return total;
	}

	/**
	 * Metodo que retorna o vetor de cidades (enderecos validos)
	 * 
	 * @return O vetor de cidades (enderecos validos)
	 */
	public String[] getCities() {
		return cities;
	}

	/**
	 * Metodo que retorna a matriz de directions (pesos)
	 * 
	 * @return A matriz de directions (pesos)
	 */
	public Direction[][] getDirectionsMatrix() {
		return directionsMatrix;
	}

	/**
	 * Metodo que retorna todas as rotas (permutacoes) possiveis
	 * 
	 * @return Todas as rotas (permutacoes) possiveis
	 */
	public List<String> getPossibleRoutes() {
		return possibleRoutes;
	}

	/**
	 * Metodo que retorna a rota minima (de menor distancia)
	 * 
	 * @return
	 */
	public String getMinimumRoute() {
		return minimumRoute;
	}

}