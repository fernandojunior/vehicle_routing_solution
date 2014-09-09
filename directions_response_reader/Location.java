/**
 * Classe que define uma direcao (local, latitude e longitude)
 * 
 * @author fernandojr.ifcg@live.com, http://fjacademic.wordpress.com/
 * 
 */
public class Location {

	/**
	 * O endereco do local
	 */
	public String location;

	/**
	 * A latitude do local
	 */
	public double latitude;

	/**
	 * A longitude do local
	 */
	public double longitude;

	public Location() {
	}

	/**
	 * Construtor que inicializa a instancia com o endereco do local, a latitude
	 * e a longitude
	 * 
	 * @param location
	 *            O endereco do local
	 * @param latitude
	 *            A latitude do local
	 * @param longitude
	 *            A longitude do local
	 */
	public Location(String location, double latitude, double longitude) {

		this.location = location;
		this.latitude = latitude;
		this.longitude = longitude;

	}
	
	public String toString(Boolean showLocation) {
		String content = "";

		content += "latitude:" + latitude + ", ";
		content += "longitude:" + longitude;

		if (showLocation == true)
			content += ", location:" + location;

		return '{' + content + '}';
	}
}
