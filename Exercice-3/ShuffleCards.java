package exercicioShuffleCards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Diogo Lopes 60447
 *
 */
public class ShuffleCards {
	
	private List<Integer> deck;
	private int numberOfCards;
	private final int[] piles;
	
	/**
	 * Cria o deck e inicializa os atributos para ser
	 * possivel baralhar o deck
	 * 
	 * @param numberOfCards o numero de cartas no deque
	 * @param piles array com o numero de montes por ronda
	 * @requires {@code numberOfCards > 0}
	 */
	public ShuffleCards(int numberOfCards, int[] piles) {
		this.numberOfCards = numberOfCards;
		this.piles = Arrays.copyOf(piles, piles.length);
		
		deck = new ArrayList<Integer>();
		
		for (int i = 0; i < numberOfCards; i++) 
			deck.add(i+1);
		
	}
	
	/**
	 * Baralha o deck
	 */
	public void shuffle() {
	    for (int i = 0; i < piles.length; i++) {
	        ArrayList<Integer>[] montes = new ArrayList[piles[i]]; 

	        for (int p = 0; p < piles[i]; p++) 
	            montes[p] = new ArrayList<>();  
	        
	        for (int k = 0; k < montes.length; k++) 
	            for (int j = k; j < numberOfCards; j += montes.length)
	                montes[k].add(deck.get(j));
	            
	        deck.clear();
	      
	        for (int j = 0; j < montes.length; j++) 
	            for (int k = 0; k < montes[j].size(); k++) 
	                deck.add(montes[j].get(k));	          	        
	    }
	}
		
	
	/**
	 * Retorna a n-esima carta do baralho partindo do topo
	 * 
	 * @param n n-esima carta
	 * @requires {@code 0 < n && n <= numberOfCards}
	 * @return a n-esima carta do baralho partindo do topo
	 */
	public int nthCard(int n) {
		return deck.get(n - 1);
	}
	
	/**
	 * Retorna uma mao de cartas para um jogador, de
	 * tamanho m cartas sendo estas retiradas da base
	 * do baralho
	 * 
	 * @param m quantidade de cartas devolvidas para a mao
	 * @return uma mao de cartas de tamanho m
	 */
	public List<Integer> giveHand(int m) {
		List<Integer> hand = new ArrayList<Integer>();
		
		while (m > 0) {
			hand.add(deck.remove(numberOfCards-- - 1));
			m--;
		}
		
		return hand;
	}
}
