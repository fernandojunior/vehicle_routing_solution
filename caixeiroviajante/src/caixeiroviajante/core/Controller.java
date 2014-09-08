package caixeiroviajante.core;

import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import caixeiroviajante.util.Util;

/**
 * Classe que controla a resposta a ser enviada
 * 
 * @author Fernando Felix (fernandojr.ifcg@live.com)
 * 
 */
public class Controller {

	private static DirectionsContainer directions = new DirectionsContainer();

	// indica se alguma requisicao para o google maps esta sendo executada
	private static Boolean isActive = false;

	private static final String ROUTEMAPURLBASE = "/caixeiroviajante/routemap.jsp?";

	/**
	 * Versao corrente. Metodo que retorna a rota minima (em um mapa) de
	 * enderecos a ser seguida, baseando-se em um array list de pedidos passado
	 * como parametro. Obs: esse metodo utiliza a api matrix distance do google
	 * maps para preencher a matriz de direcoes
	 * 
	 * @param pedidos
	 *            O array list de pedidos a ser passado como parametro
	 */
	public void minimumRouteResponse(HttpServletRequest request,
			HttpServletResponse response, String[] adresses) throws IOException {

		// adicionando os way points, baseando-se nos enderecos dos pedidos
		for (int i = 0; i < adresses.length; i++)
			adresses[i] = Util.normalizeString(adresses[i]);

		if (isActive == false) {

			isActive = true;

			try {

				String routeMapURL = DistanceMatrixSolution.getRouteMapURL(
						request.getContextPath() + ROUTEMAPURLBASE, directions,
						adresses);

				System.out.println(routeMapURL);

				response.sendRedirect(response.encodeRedirectURL(routeMapURL));

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (GoogleResponseException e) {
				e.printStackTrace();
			}

			isActive = false;

		}
		// } else
		// minimumRouteRequest(cities);

	}

	/**
	 * Metodo que a retorna rota minima (em um mapa) de enderecos, baseando-se
	 * em um array de way points (enderecos) passados pelo parametro. Obs: esse
	 * metodo utiliza a api matrix distance do google maps para preencher a
	 * matriz de direcoes (pesos)
	 * 
	 * @param waypoints
	 *            Os way points (enderecos) a serem passados pelo parametro
	 */
	public void minimumRouteVersion2Response(HttpServletResponse response,
			String waypoints[]) {

		if (isActive == false) {

			isActive = true;

			try {

				String routeMapURL = DistanceMatrixSolution.getRouteMapURL(
						ROUTEMAPURLBASE, directions, waypoints);

				response.sendRedirect(response.encodeRedirectURL(routeMapURL));

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (GoogleResponseException e) {
				e.printStackTrace();
			}

			isActive = false;

		}
		// } else
		// minimumRouteRequest(cities);

	}

	/**
	 * Metodo que a retorna rota minima (em um mapa) de enderecos, baseando-se
	 * em um array de way points (enderecos) passados pelo parametro. Obs: esse
	 * metodo utiliza a api distance response do google maps para preencher a
	 * matriz de direcoes (pesos)
	 * 
	 * @param waypoints
	 *            Os way points (enderecos) a serem passados pelo parametro
	 */
	public void minimumRouteVersion1Response(HttpServletResponse response,
			String waypoints[]) {

		if (isActive == false) {

			isActive = true;

			try {

				String routeMapURL = DirectionsResponseSolution.getRouteMapURL(
						ROUTEMAPURLBASE, directions, waypoints);

				response.sendRedirect(response.encodeRedirectURL(routeMapURL));

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (GoogleResponseException e) {
				e.printStackTrace();
			}

			isActive = false;

		}
		// } else
		// minimumRouteRequest(cities);

	}

	/**
	 * retorna a rota a ser seguida em txt
	 * 
	 * @param response
	 * @param adresses
	 */

	public void printAdressesResponse(HttpServletResponse response,
			String adresses[]) throws IOException {
		PrintWriter out = response.getWriter();

		for (int i = 0; i < adresses.length; i++)
			out.println(adresses[i]);

	}

	/**
	 * Retorna todos os directions armazenados na persistencia
	 * 
	 * @throws IOException
	 */
	public void listPersistenceResponse(HttpServletResponse response)
			throws IOException {

		for (Iterator<Direction> i = directions.getIterator(); i.hasNext();)
			response.getWriter().write(i.next().toString());

	}

	private static Controller responseControl;

	private Controller() {
	};

	public static Controller getInstance() {
		if (responseControl == null)
			responseControl = new Controller();
		return responseControl;
	}

	/**
	 * Factory Method
	 * 
	 * @param request
	 * @param response
	 * @param o
	 */
	public static void factoryMethod(HttpServletRequest request,
			HttpServletResponse response, Object o) throws IOException {

		Controller control = getInstance();

		// caso seja passado o parametro de requisicao 'type'
		if (request.getParameterMap().containsKey("type")) {

			// lendo o tipo da requisicao
			String requestType = request.getParameter("type");

			// fabricando resposta
			if (requestType.equals("minimum_route"))
				control.minimumRouteResponse(request, response, (String[]) o);


		}

	}

}
