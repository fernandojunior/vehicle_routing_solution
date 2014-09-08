package caixeiroviajante.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * referencia:http://ensino.univates.br/~jorgef/estrutura2/0504/
 * 
 * @author Fernando Felix (fernandojr.ifcg@live.com)
 * @TODO implementar documentacao da classe Permutation
 * 
 */
public class Permutation {

	// Imprime todas as permutações do conjunto de caracteres fornecido
	private static void permut(String prefixo, HashSet<Character> conjunto,
			List<String> permutacoes) {
		if (conjunto.size() == 0) {
			// Caso base: não há mais itens no conjunto, então salve o prefixo
			permutacoes.add(prefixo);

		} else {
			// Caso geral: primeiro, repita para cada item do conjunto
			Iterator<Character> it = conjunto.iterator();
			while (it.hasNext()) {
				// Para cada item, crie uma cópia do conjunto sem o mesmo
				// ("restantes")
				// e continue recursivamente, passando o item adicionado o ao
				// final do
				// prefixo e passando também o conjunto de itens restantes
				char c = it.next();
				@SuppressWarnings({ "unchecked", "rawtypes" })
				HashSet<Character> restantes = (HashSet) conjunto.clone();
				restantes.remove(c);
				permut(prefixo + c, restantes, permutacoes);
			}
		}
	}

	/**
	 * Retorna uma lista de permutacoes
	 * 
	 * @param string
	 *            A string a ser permutada
	 * @return A lista de permutacoes da string
	 */
	public static List<String> getPermutations(String string) {

		List<String> permutacoes = new ArrayList<String>();

		// parsing String to Character HashSet
		HashSet<Character> charset = new HashSet<Character>();
		for (int i = 0; i < string.length(); i++)
			charset.add(string.charAt(i));

		permut("", charset, permutacoes);

		return permutacoes;

	}

	/**
	 * Teste
	 */
	public static void test() {
		String chars = "fernando";

		System.out.println(getPermutations(chars));

	}
}
