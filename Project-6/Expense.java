package project;

import java.util.List;

/**
 * 
 * @author Diogo Lopes 60447
 *
 */
public class Expense {

	/**
	 * Descritivo do significado da despesa
	 */
	private final String description;          
	
	/**
	 * Utilizador que pagou a despesa
	 */
	private final User userWhoPaid;
	
	/**
	 * Valor total pago pela despesa
	 */
	private final Integer value;                
	
	/**
	 * Lista de utilizadores envolvidos na despesa
	 */
	private final List<String> peopleInvolved; 
	
	/**
	 * Lista das proporcoes pagas por utilizador
	 */
	private List<Double> howToSplit;    
	
	/**
	 * Constroi a fatura da despesa
	 * 
	 * @param description Descritivo do significado da despesa
	 * @param userWhoPaid Utilizador que pagou a despesa
	 * @param value Valor total pago pela despesa
	 * @param peopleInvolved Lista de utilizadores envolvidos na despesa
	 * @requires {@code howToSplit.size() == peopleInvolved.size() && userWhoPaid != null
	 *            && peopleInvolved.contains(userWhoPaid.getUsername()}
	 */
	public Expense (String description, User userWhoPaid, Integer value, List<String> peopleInvolved) {
		this.description = description;
		this.userWhoPaid = userWhoPaid;
		this.value = value;
		this.peopleInvolved = List.copyOf(peopleInvolved);
	}

	/**
	 * Constroi a fatura da despesa
	 * 
	 * @param description Descritivo do significado da despesa
	 * @param userWhoPaid Utilizador que pagou a despesa
	 * @param value Valor total pago pela despesa
	 * @param peopleInvolved Lista de utilizadores envolvidos na despesa
	 * @param howToSplit Lista das proporcoes pagas por utilizador
	 * @requires {@code howToSplit.size() == peopleInvolved.size() && userWhoPaid != null
	 *            && peopleInvolved.contains(userWhoPaid.getUsername()}
	 */
	public Expense (String description, User userWhoPaid, Integer value, List<String> peopleInvolved, 
			List<Double> howToSplit) {
		this.description = description;
		this.userWhoPaid = userWhoPaid;
		this.value = value;
		this.peopleInvolved = List.copyOf(peopleInvolved);
		this.howToSplit = List.copyOf(howToSplit);
	}

	/**
	 * Retorna o valor total pago pela despesa
	 * 
	 * @return o valor total pago pela despesa
	 */
	public Integer getValue() {
		return this.value;
	}
	
	/**
	 * Retorna o User que pagou a despesa
	 * 
	 * @return o User que pagou a despesa
	 */
	public User getPayer() {
		return this.userWhoPaid;
	}
	
	/**
	 * Retorna a descricao da despesa
	 * 
	 * @return a descricao da despesa
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Retorna a parte do valor absoluto da despesa correspodente
	 * ao utilizador identificado com username
	 * 
	 * @param username utilizador ao qual corresponde a despesa
	 * @requires {@code peopleInvolved.contains(username)}
	 * @return a parte do valor absoluto da despesa correspodente
	 * ao utilizador identificado com username
	 */
	public Integer getSplit(String username) { 
		if(this.howToSplit != null) {
			double valueDouble = 0.0;
			
			for(double splitRate : howToSplit) {
				double value = this.value * splitRate;
				int valueInt = (int) (this.value * splitRate);
				valueDouble += value - (double) valueInt;
			}
			
			if(peopleInvolved.indexOf(username) < (int) valueDouble)
				return (int) (this.value * this.howToSplit.get(this.peopleInvolved.indexOf(username))) + 1;
			
			return (int) (this.value * this.howToSplit.get(this.peopleInvolved.indexOf(username)));
		}
		
		if(peopleInvolved.indexOf(username) < (this.value % this.peopleInvolved.size()))
			return (this.value / this.peopleInvolved.size()) + 1;
		
		return this.value / this.peopleInvolved.size();
	}
	
	/**
	 * Retorna o saldo da despesa correspondente ao utilizador identificado
	 * com username. O valor sera < 0 caso o utilizador esteja a dever e > 0
	 * caso o utilizador tenha a receber
	 * 
	 * @param username utilizador ao qual corresponde o saldo
	 * @requires {@code peopleInvolved.contains(username)}
	 * @return o saldo da despesa correspondente ao utilizador identificado
	 * com username. O valor sera < 0 caso o utilizador esteja a dever e > 0
	 * caso o utilizador tenha a receber
	 */
	public Integer getExpenseBalance(String username) {
		if(this.userWhoPaid.getUsername().equals(username)) 
				return this.value - getSplit(username);
		
		return -getSplit(username);
	}
}
