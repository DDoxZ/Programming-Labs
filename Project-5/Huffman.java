package project;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.TreeMap;
/**
 * 
 * @author Diogo Lopes 60447
 *
 */
public class Huffman {

	/**
	 * Retorna as codificacoes correspondetes a
	 * cada caracter no formato de hashmap
	 * onde a chave e' o caracter e o conteudo essa
	 * codificacao
	 * 
	 * @param corpus a string que contem os caracteres 
	 * que vamos codificar
	 * @requires {@code corpus != null}
	 * @return um hashmap com a codificacoa de cada caracter
	 */
	public static HashMap<Character, String> getCodes(String corpus) {
		return treeFromCorpus(corpus).getHuffmanCodes();
	}
	
	/**
	 * Retorna os codigos de cada caracter no formato
	 * de string separados por um espacamento
	 * 
	 * @param codes os codigos correspondentes a cada caracter,
	 * ou seja, um hashmap no qual as chaves sao os caracteres
	 * e o seu conteudo os codigos correspondentes
	 * @requires {@code codes != null}
	 * @return os codigos de cada caracter no formato de
	 * string separados por um espacamento
	 */
	public static String codesToString(HashMap<Character, String> codes) {
		StringBuilder codesRepresentation = new StringBuilder();
		
		SortedMap<Character, String> orderedCodes = new TreeMap<Character, String>(codes);

		for (Object entry : orderedCodes.entrySet()) {
			codesRepresentation.append(entry);
			codesRepresentation.append(System.lineSeparator());
		}

		return codesRepresentation.toString();
	}
	
	/**
	 * Codifica a string passada por parametro devolvendo uma
	 * outra string com a mensagem codificada a partir dos codigos 
	 * de codes
	 * 
	 * @param message a string que vamos codificar
	 * @param codes os codigos para a codificacao da string
	 * @requires {@code message != null && codes != null}
	 * @return uma string que corresponde a' codificacao de message
	 * a partir dos codigos de codes
	 */
	public static String encode(String message, HashMap<Character, String> codes) {
		StringBuilder bob = new StringBuilder();
		char[] charMessage = message.toCharArray();
		boolean done = false;
		
		for (int i = 0; i < charMessage.length; i++) {
			for (Character caracter : codes.keySet()) {
				if(Character.valueOf(charMessage[i]).equals(caracter) && !done) {
					bob.append(codes.get(caracter));
					done = true;
				}
			}
			if(!done)
				bob.append("-");
			done = false;
		}
		
		return bob.toString();
	}

	/**
	 * Cria uma arvore de HuffmanNodes construida a partir
	 * da tabela de frequencias de corpus. Ou seja,
	 * vai criar uma arvore onde os nodes com as frequencias 
	 * mais baixas dos caracteres de corpus se encontram mais
	 * longe da root da arvore e as mais frequentes mais 
	 * perto da root
	 * 
	 * @param corpus a string sobre a qual vamos
	 * criar a tabela de frequencia para criar a arvore
	 * @return uma arvore de HuffmanNodes construida a partir
	 * da tabela de frequencias de corpus
	 */
	private static HuffmanTree treeFromCorpus(String corpus) {
		return new HuffmanTree(frequencyTable(corpus));
	} 
	//nao necessita @requires {@code corpus != null}
	//pq E um metodo privado que so e' chamado num outro metodo que ja 
	//tem esse requires implementado

	/**
	 * Cria uma tabela de frequencias construida 
	 * a partir dos caracteres de corpus.
	 * Ou seja, cria um hashmap no qual tem como chave 
	 * a ocorrencia de cada caracter em corpus e a sua 
	 * frequencia absoluta como conteudo
	 * 
	 * @param corpus a string sobre a qual vamos criar a tabela
	 * de frequencias
	 * @return uma tabela de frequencias a partir dos caracteres 
	 * de corpus
	 */
	private static HashMap<Character, Integer> frequencyTable(String corpus) {
		HashMap<Character, Integer> frequencyTable = new HashMap<Character, Integer>();
		char[] charCorpus = corpus.toCharArray();
		
		for (Character caracter : charCorpus) {
			if (frequencyTable.containsKey(caracter)) 
				frequencyTable.put(caracter, frequencyTable.get(caracter) + 1);
			else
				frequencyTable.put(caracter, 1);
		}
		
		return frequencyTable;
	}

	private static class HuffmanTree {

		/**
		 * HuffmanNode correspondente a' raiz da arvore
		 */
		private HuffmanNode root;

		/**
		 * Cria uma arvore de HuffmanNodes a partir
		 * da tabela de frequencies. Ou seja,
		 * vai criar uma arvore onde os nodes com as frequencias 
		 * mais baixas se encontram mais longe da root da arvore 
		 * e as mais frequentes mais perto da root
		 * 
		 * @param frequencies tabela de frequencias
		 * @return uma arvore de HuffmanNodes construida a partir
		 * da tabela de frequencias
		 */
		private HuffmanTree(HashMap<Character, Integer> frequencies) {
			PriorityQueue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>();
			
			for (Character i : frequencies.keySet()) 
				pq.add(new HuffmanNode(frequencies.get(i), i.charValue()));
			
			while (pq.size() >= 2) {
				HuffmanNode leftNode = pq.poll();
				HuffmanNode rightNode = pq.poll();
				pq.add(new HuffmanNode(leftNode.frequency + rightNode.frequency, leftNode, rightNode));
			}
			
			root = pq.poll();			
		}

		/**
		 * Retorna as codificacoes de cada caracter no
		 * formato de hashmap onde a chave corresponde
		 * ao caracter e o conteudo dessa chave a' sua
		 * codificacao
		 * 
		 * @return as codificacoes de cada caracter no
		 * formato de hashmap
		 */
		private HashMap<Character, String> getHuffmanCodes() {
			HashMap<Character, String> codes = new HashMap<>();

			getHuffmanCodesAux(root, "", codes);

			return codes;
		}
		
		/**
		 * Percorre a arvore criada para obter as codificacoes
		 * de cada caracter
		 * 
		 * @param node os nodes que vamos percorrer
		 * @param code a codificacao de cada caracter
		 * @param codes a lista com as codificacoes de todos os caracteres
		 * @requires {@code node != null && code != null && codes != null}
		 */
		private void getHuffmanCodesAux(HuffmanNode node, String code, HashMap<Character, String> codes) {
			if(!node.isLeaf()) {
				getHuffmanCodesAux(node.left, code.concat("0"), codes);
				getHuffmanCodesAux(node.right, code.concat("1"), codes);
			} 
			else {
				codes.put(node.c, code);
			}
		}
	}

	private static class HuffmanNode implements Comparable<HuffmanNode> {

		int frequency;
		char c;
		HuffmanNode left;
		HuffmanNode right;

		// creates a leaf
		/**
		 * Cria uma folha ou no externo
		 * 
		 * @param frequency a frequencia correspondente a esta folha
		 * @param c o caracter correspondente a esta folha
		 */
		private HuffmanNode(int frequency, char c) {
			this.frequency = frequency;
			this.c = c;
			// this.left not initialized; remains null
			// this.right not initialized; remains null
		}

		// creates an internal node
		/**
		 * Cria um ramo ou no interno
		 * 
		 * @param frequency a frequencia correspondente a este ramo
		 * @param left no filho da esquerda do ramo
		 * @param right no filho da direita do ramo
		 */
		private HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
			this.frequency = frequency;
			// no need to initialize this.c, because it is not used
			this.left = left;
			this.right = right;
		}

		/**
		 * Verifica se o no corrente e' uma folha e devolve
		 * true se sim, nao caso contrario
		 *
		 * @return true se o no corrente for uma folha, false
		 * caso contrario
		 */
		private boolean isLeaf() {
			return left == null && right == null;
		}

		/**
		 * Compara a frequencia do no corrente com o do no passado 
		 * por parametro e retorna o resultado da subtracao das 
		 * frequencias por essa ordem. Por isso, se o resultado for
		 * positivo temos que a frequencia do no corrente e' maior que
		 * a do no' passado por parametro, se negativo o contrario, e
		 * se 0 e' porquem tem a mesma frequencia
		 * 
		 * @return resultado posivito se a frequencia do no corrente for
		 * maior que a do passado por parametro, negativo caso contrario,
		 * 0 se forem iguais
		 */
		@Override
		public int compareTo(HuffmanNode node) {
			return this.frequency - node.frequency;
		}
	}
}
