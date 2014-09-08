package caixeiroviajante.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Contem metodos estaticos uteis
 * 
 * @author Fernando Felix (fernandojr.ifcg@live.com)
 * 
 */
public class Util {

	/**
	 * Metodo que converte um array list de string em um vetor de String
	 * 
	 * @param list
	 *            O array list a ser convertido
	 * @return O vetor de string
	 */
	public static String[] parseToStringVector(ArrayList<String> list) {

		String vetor[] = new String[list.size()];

		// parsing array list to vector
		for (int i = 0; i < list.size(); i++)
			vetor[i] = list.get(i);

		return vetor;
	}

	/**
	 * Passa um Array de String para String
	 * 
	 * @param arr
	 *            um Array de String
	 * @return Uma String
	 */
	public static String toString(String[] arr) {

		String content = "{";

		for (int i = 0; i < arr.length; i++) {
			if (i == arr.length - 1)
				content += arr[i];
			else
				content += arr[i] + ",";
		}

		content += "}";

		return content;

	}

	public static String toString(List<? extends Object> list) {

		String content = list.toString();

		return "{" + content.substring(1, content.length() - 1) + "}";

	}

	/**
	 * Metodo que retorna o conteudo de uma url passada como parametro
	 * 
	 * @param urlName
	 *            A url a ser passada como parametro
	 * @return O conteudo da URL
	 * @throws MalformedURLException
	 *             Erro ao criar URL. Formato inválido
	 * @throws IOException
	 *             Erro ao acessar URL
	 */
	public static String openURL(String urlName) throws MalformedURLException,
			IOException {

		String content = "";

		URL url = new URL(urlName);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();

		urlConnection.connect();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));

		String line = null;

		while ((line = in.readLine()) != null) {
			content += line + "\n";
		}

		in.close();

		urlConnection.disconnect();

		return content;
	}

	/**
	 * Metodo que produz um delay conforme os segundos (int)
	 * 
	 * @param seg
	 *            Os segundos
	 */
	public static void sleep(int seg) {
		try {
			Thread.sleep(seg * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Normaliza uma string
	 * 
	 * @param string
	 *            A string
	 * @return String normalizada
	 */
	public static String normalizeString(String string) {

		String badchars = "!@#$%Z^&*(){}|\\[]\'\"?/>.<,`~-_=+:; ";

		for (int i = 0; i < badchars.length(); i++)
			string = string.replace(badchars.charAt(i), '+');

		return string;

	}

	/**
	 * Retorna o diretorio absoluto do WebContent da aplicacao, conforme o
	 * request passado como parametro
	 * 
	 * @param request
	 *            O request
	 * @return Diretorio absoluto WebContent
	 */
	public static File webContentFolder(HttpServletRequest request) {

		String sysname = request.getContextPath().substring(1,
				request.getContextPath().length());

		@SuppressWarnings("deprecation")
		File directory = new File(request.getRealPath(request.getServletPath()));

		while (true) {
			String path[] = directory.getName().split("/");
			if (path[path.length - 1].equals(sysname))
				break;
			else
				directory = directory.getParentFile();
		}

		return directory;
	}

	/**
	 * @param request
	 * @return
	 * @deprecated
	 */
	public static String getContextPath(HttpServletRequest request) {
		return request.getContextPath();
	}

	@Deprecated
	public static boolean hasName(HttpSession session, String name) {
		Enumeration<?> e = session.getAttributeNames();

		while (e.hasMoreElements()) {
			if (e.nextElement().toString().equals(name))
				return true;

		}

		return false;
	}

	/**
	 * Verifica se a enumeracao possui determinado nome
	 * 
	 * @param e
	 *            A enumeracao
	 * @param name
	 *            O nome
	 * @return true se tiver; false caso contrario
	 */
	public static boolean hasName(Enumeration<?> e, String name) {

		while (e.hasMoreElements())
			if (e.nextElement().toString().equals(name))
				return true;

		return false;
	}

	public static String arrToString(Object array[]) {

		String l = "";
		for (int i = 0; i < array.length; i++) {

			l += "'" + array[i] + "'";

			if (i < array.length - 1)
				l += ",";
		}
		// l += ")";

		return l;
	}

	public static String arrToString(List array) {

		String l = "";
		for (int i = 0; i < array.size(); i++) {

			l += "'" + array.get(i) + "'";

			if (i < array.size() - 1)
				l += ",";
		}
		// l += ")";

		return l;
	}

}
