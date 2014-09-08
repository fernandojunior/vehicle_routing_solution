package caixeiroviajante.core;

/**
 * Classe que define um local (endereco, latitude e longitude)
 * 
 * @author Fernando Felix (fernandojr.ifcg@live.com)
 * @TODO implementar getters e setters da classe Location
 * 
 */
public class Location {

	/**
	 * Local
	 */
	public String location;
	
	/**
	 * Latitude
	 */
	public double latitude;
	
	/**
	 * Longitude
	 */
	public double longitude;

	public Location() {
	}

	public Location(String location, double latitude, double longitude) {

		this.location = location;
		this.latitude = latitude;
		this.longitude = longitude;

	}

	public String toString(Boolean showLocation) {
		String content = "";

		if (latitude == 0 && longitude == 0) {
			content += "latitude: null, ";
			content += "longitude: null";
		} else {

			content += "latitude:" + latitude + ", ";
			content += "longitude:" + longitude;
		}

		if (showLocation == true)
			content += ", location:" + location;

		return '{' + content + '}';
	}
}
