/**
 * Classe que define um Direction
 * 
 * @author fernandojr.ifcg@live.com, http://fjacademic.wordpress.com/
 * 
 */
public class Direction {

	/**
	 * Local de inicio
	 */
	public Location start_location;

	/**
	 * Local fim
	 */
	public Location end_location;

	/**
	 * Distancia (em metros) entre o local de inicio e o local fim
	 */
	public double distance;

	/**
	 * Duracao (em segundos) entre o local de inicio e o local fim
	 */
	public double duration;

	public Direction() {
	}

	/**
	 * Construtor que inicializa a instancia com os locais de inicio/fim,
	 * distancia (em metros) e duracao (em segundos)
	 * 
	 * @param start_location
	 *            O local de inicio
	 * @param end_location
	 *            O local fim
	 * @param distance A distancia em metros
	 * @param duration A duracao em segundos
	 */
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
