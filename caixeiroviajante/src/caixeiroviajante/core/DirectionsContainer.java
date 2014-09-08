package caixeiroviajante.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import caixeiroviajante.util.Util;

/**
 * Classe que armazena um conjunto de directions
 * 
 * @author Fernando Felix (fernandojr.ifcg@live.com)
 * @see Direction
 */
public class DirectionsContainer {

	/**
	 * Array list contendo directions
	 */
	private ArrayList<Direction> directions;

	/**
	 * Construtor de incializacao da instancia
	 */
	public DirectionsContainer() {
		directions = new ArrayList<Direction>();
	}

	/**
	 * Metodo que adiciona um direction no container conforme o endereco de
	 * primeiro_dia_semana e o endereco de ultimo_dia_semana, utilizando-se da
	 * API do Google Maps
	 * 
	 * @param start
	 *            Endereco de primeiro_dia_semana
	 * @param end
	 *            Endereco de ultimo_dia_semana
	 * @return A direcao que foi inserida
	 */
	public Direction add(String start, String end)
			throws ParserConfigurationException, SAXException, IOException,
			GoogleResponseException {

		if (get(start, end) != null)
			return null; // false

		Direction response = DirectionsReader.run(start, end, false);

		// putting the system to sleep after the request
		Util.sleep(2);

		if (directions.add(response))
			return response; // true

		return null;

	}

	/**
	 * Metodo que adiciona um direction no container
	 * 
	 * @param direction
	 *            A direction a ser adicionada no container
	 * @return Retorna 'true' se for inserido; 'false' caso contrario
	 */
	public Boolean add(Direction direction) {

		if (get(direction.start_location.location,
				direction.end_location.location) != null)
			return false;

		return directions.add(direction);

	}

	/**
	 * Remove um direction do container com base nos enderecos de
	 * primeiro_dia_semana e de ultimo_dia_semana passsados como parametro
	 * 
	 * @param start
	 *            O endereco de primeiro_dia_semana
	 * @param end
	 *            O endereco de ultimo_dia_semana
	 * @return Retorna 'true' se o direction for removido; 'false' caso
	 *         contrario
	 */
	public Boolean remove(String start, String end) {

		return directions.remove(get(start, end));

	}

	/**
	 * Metodo que verifica se o container contem um direction que possui os
	 * enderecos de primeiro_dia_semana e ultimo_dia_semana passados como
	 * parametro
	 * 
	 * @param start
	 *            O endereco de inico
	 * @param end
	 *            O endereceo de ultimo_dia_semana
	 * @return Retorna 'true' se conter; 'false' caso contrario
	 */
	public Boolean contains(String start, String end) {
		if (get(start, end) != null)
			return true;

		return false;

	}

	/**
	 * Metodo que retorna um direction do container com base nos enderecos de
	 * primeiro_dia_semana e ultimo_dia_semana passados como parametro
	 * 
	 * @param start
	 *            O endereco de primeiro_dia_semana
	 * @param end
	 *            O endreco de ultimo_dia_semana
	 * @return O direction do container
	 */
	public Direction get(String start, String end) {

		for (Direction response : directions) {
			if (response.start_location.location.equalsIgnoreCase(start)
					&& response.end_location.location.equalsIgnoreCase(end))
				return response;
		}

		return null;
	}

	/**
	 * Metodo que retorna um iterator contendo os directions do container
	 * 
	 * @return O iterator contendo os directions do container
	 */
	public Iterator<Direction> getIterator() {
		return directions.iterator();
	}

	/**
	 * Metodo de Teste
	 */
	public static void test() {
		DirectionsContainer p = new DirectionsContainer();

		try {
			p.add("campina grande, PB", "joao pessoa, pb");

			p.add("campina grande, PB", "joao pessoa, pb");

			p.add("campina grande, PB", "joao pessoa, pb");

			p.add("esperanca, PB", "campina grande, pb");

			for (Iterator<Direction> i = p.getIterator(); i.hasNext();) {
				Direction d = i.next();

				System.out.println(d.toString());

			}

			System.out.println(p.get("campina grande, PB", "joao pessoa, pb")
					.toString());

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
