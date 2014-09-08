package caixeiroviajante.core;


/**
 * Classe que define uma direcao com seus pesos (distancia, duracao)
 * 
 * @author Fernando Felix (fernandojr.ifcg@live.com)
 * @TODO implementar getters e setters da classe  Direction
 * 
 */
public class Direction {

	/**
	 * Localizacao inicial
	 */
	public Location start_location;

	/**
	 * Localizacao final
	 */
	public Location end_location;

	/**
	 * Distancia entre a localizacao inicial e final
	 */
	public double distance;

	/**
	 * Duracao entre a localizacao inicial e final
	 */
	public double duration;

	public Direction() {
	}

	public Direction(Location start_location, Location end_location,
			Double distance, Double duration) {
		this.start_location = start_location;
		this.end_location = end_location;
		this.distance = distance;
		this.duration = duration;

	}

	public String toString() {
		String content = "";

		content += "start_address=" + start_location.location + "\n";
		content += "end_address=" + end_location.location + "\n";
		content += "start_location=" + start_location.toString(false) + "\n";
		content += "end_location=" + end_location.toString(false) + "\n";
		content += "duration=" + duration + "\n";
		content += "distance=" + distance + "\n";

		return content;
	}

}
