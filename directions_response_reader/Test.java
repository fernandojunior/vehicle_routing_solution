import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Test {

	public static void main(String[] args) {

		try {

			// Direction de campina grande a joao pessoa
			Direction campinaToJampa = DirectionsResponseXmlReader.run(
					"campina grande, PB", "joao pessoa, PB");

			// Imprimindo o objeto
			System.out.println(campinaToJampa.toString());

			/*
			 * Saida: 
			 * start_address=Campina Grande - Paraíba, Brazil
			 * end_address=João Pessoa - Paraíba, Brazil
			 * start_location={latitude:-7.23072, longitude:-35.88167}
			 * end_location={latitude:-7.11532, longitude:-34.86105}
			 * duration=5968.0 distance=132110.0
			 */

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
