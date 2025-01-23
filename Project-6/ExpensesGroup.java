package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Diogo Lopes 60447
 *
 */
public class ExpensesGroup {
	
	/**
	 * Registo de quanto cada User deve a outro User
	 */
	private Map<String, Map<String, Integer>> debtGraph; // QuemÉdevido -> QuemDeve, QtDeve

	/**
	 * Nome do grupo
	 */
	private final String groupName;
	
	/**
	 * User que criou o grupo
	 */
	private final User user;
	
	/**
	 * Lista do grupo de users
	 */
	private List<User> usersInGroup;
	
	/**
	 * Lista das despesas adicionadas ao grupo
	 */
	private List<Expense> expensesList;

	/**
	 * Constroi a fatura da despesa do grupo
	 * 
	 * @param groupName Nome do grupo
	 * @param user User que criou o grupo
	 * @requires {@code user!= null}
	 */
	public ExpensesGroup(String groupName, User user) {
		this.groupName = groupName;
		this.user = user;
		this.usersInGroup = new ArrayList<User>();
		this.expensesList = new ArrayList<Expense>();
		this.debtGraph = new HashMap<>();
		addUser(user);
	}

	/**
	 * Retorna o User que criou o grupo
	 * 
	 * @return o User que criou o grupo
	 */
	public User getCreator() {
		return this.user;
	}

	/**
	 * Retorna o nome dado ao grupo
	 * 
	 * @return o nome dado ao grupo
	 */
	public String getGroupName() {
		return this.groupName;
	}
	
	/**
	 * Retorna uma lista com os usernames dos utilizadores no grupo
	 * pela ordem que foram adicionados 
	 * 
	 * @return uma lista com os usernames dos utilizadores no grupo
	 * pela ordem que foram adicionados 
	 */
	public List<String> usersInGroup(){
		List<String> userNamesInGroup = new ArrayList<String>();
		
		for(User user : usersInGroup) 
			userNamesInGroup.add(user.getUsername());
		
		return userNamesInGroup;
	}

	/**
	 * Retorna a lista com todas as despesas adicionadas ao grupo
	 * 
	 * @return a lista com todas as despesas adicionadas ao grupo
	 */
	public List<Expense> getExpenses() {
		return List.copyOf(this.expensesList);
	}

	/**
	 * Adiciona user ao grupo e atualiza os grupos de user
	 * 
	 * @param user user a ser adicionado ao grupo
	 * @requires user ainda nao faz parte do grupo
	 */
	public void addUser (User user) {
		this.usersInGroup.add(user);
		user.addGroup(this);
	}

	/**
	 * Cria e adiciona uma nova despesa ao grupo de despesas sendo
	 * value dividido em partes iguais pelos utilizadores do grupo
	 * 
	 * @param description descricao da despesa
	 * @param userWhoPaid user que pagou a despesa
	 * @param value valor da despesa
	 * @requires {@code userWhoPaid != null && value > 0 && usersInGroup().contains(userWhoPaid)}
	 */
	public void addExpense(String description, User userWhoPaid, Integer value) {
		Expense despesa = new Expense(description, userWhoPaid, value, usersInGroup());
		expensesList.add(despesa);
		
		Map<String, Integer> debtors = new HashMap<String, Integer>();
		
		for(User user : usersInGroup) 
			debtors.put(user.getUsername(), despesa.getSplit(user.getUsername()));
		
		if(debtGraph.putIfAbsent(userWhoPaid.getUsername(), debtors) != null) {
			for(Map.Entry<String, Integer> payerEntry : debtGraph.get(userWhoPaid.getUsername()).entrySet()) {
				if (payerEntry.getValue() == null) {
					debtors.put(payerEntry.getKey(), debtors.get(payerEntry.getKey()));
				}
				else if (debtors.get(payerEntry.getKey()) == null) {
					debtors.put(payerEntry.getKey(), payerEntry.getValue());
				}
				else {
					debtors.put(payerEntry.getKey(), payerEntry.getValue() + debtors.get(payerEntry.getKey()));
				}
			}
			debtGraph.put(userWhoPaid.getUsername(), debtors);
		}
		
		cleanDebtGraph();
	}
	
	/**
	 * Cria e adiciona uma nova despesa ao grupo de despesas sendo
	 * value dividido em partes diferentes pelos utilizadores do grupo
	 * de acordo com o parametro howToSplit
	 * 
	 * @param description descricao da despesa
	 * @param userWhoPaid user que pagou a despesa
	 * @param value valor da despesa
	 * @requires {@code userWhoPaid != null && value > 0 && usersInGroup().contains(userWhoPaid) 
	 *  		  && howToSplit.size() == usersInGroup().size()}
	 * @requires total da soma dos valores de howToSplit = 1
	 */
	public void addExpense(String description, User userWhoPaid, Integer value, List<Double> howToSplit) {
		Expense despesa = new Expense(description, userWhoPaid, value, usersInGroup(), howToSplit);
		expensesList.add(despesa);
		
		Map<String, Integer> debtors = new HashMap<String, Integer>();
		
		for(User user : usersInGroup) 
			debtors.put(user.getUsername(), despesa.getSplit(user.getUsername()));
		
		if(debtGraph.putIfAbsent(userWhoPaid.getUsername(), debtors) != null) {
			if(debtGraph.get(userWhoPaid.getUsername()) != null) {
				for(Map.Entry<String, Integer> payerEntry : debtGraph.get(userWhoPaid.getUsername()).entrySet()) {
					if (payerEntry.getValue() == null) {
						debtors.put(payerEntry.getKey(), debtors.get(payerEntry.getKey()));
					}
					else if (debtors.get(payerEntry.getKey()) == null) {
						debtors.put(payerEntry.getKey(), payerEntry.getValue());
					}
					else {
						debtors.put(payerEntry.getKey(), payerEntry.getValue() + debtors.get(payerEntry.getKey()));
					}
				}
				debtGraph.put(userWhoPaid.getUsername(), debtors);
			}
		}
		
		cleanDebtGraph();
	}
	
	/**
	 * Limpa o debtGraph, ou seja, retira valores desnecessarios
	 * e transforma outros de forma a organizar todas as despesas
	 * no menor espaco possivel
	 */
	private void cleanDebtGraph() {
		for(User user1 : usersInGroup) {
			for(User user2 : usersInGroup) {
				if(user1.equals(user2))
					continue;
				
				if(debtGraph.containsKey(user1.getUsername()) && 
				   debtGraph.containsKey(user2.getUsername()) &&
				   debtGraph.get(user1.getUsername()).containsKey(user2.getUsername()) &&
				   debtGraph.get(user2.getUsername()).containsKey(user1.getUsername())) {
				
					if(debtGraph.get(user1.getUsername()).get(user2.getUsername()) != null &&
					   debtGraph.get(user2.getUsername()).get(user1.getUsername()) != null	) {
					
						int user1Dept = debtGraph.get(user1.getUsername()).get(user2.getUsername());
						int user2Dept = debtGraph.get(user2.getUsername()).get(user1.getUsername());
						int mergeDept = 0;
				   
						if(user1Dept > user2Dept) {
							mergeDept = user1Dept - user2Dept;
							debtGraph.get(user1.getUsername()).put(user2.getUsername(), mergeDept);
							debtGraph.get(user2.getUsername()).put(user1.getUsername(), null);
						}
						else {
							mergeDept = user2Dept - user1Dept;
							debtGraph.get(user2.getUsername()).put(user1.getUsername(), mergeDept);
							debtGraph.get(user1.getUsername()).put(user2.getUsername(), null);
						}
				   }
				}
			}
		}
	}

	/**
	 * Retorna o saldo total do utilizador user no grupo
	 * 
	 * @param user utilizador do qual vamos retornar o saldo
	 * @requires {@code user != null && usersInGroup().contains(user)
	 * @return o saldo total do utilizador user no grupo
	 */
	public Integer getBalance (User user) {
		int balance = 0;
		
		if (!debtGraph.isEmpty()) {
			for(User userUsernames : usersInGroup) {
				if(getUserDebtors(user).get(userUsernames.getUsername()) != null)
					balance += getUserDebtors(user).get(userUsernames.getUsername());
				
				if(getUserDebts(user).get(userUsernames.getUsername()) != null) 
					balance -= getUserDebts(user).get(userUsernames.getUsername());
			}		       
		}
		
		return balance;
	}
	
	/**
	 * Retorna um mapa com a informacao de quem sao os outros membros
	 * do grupo que devem a user e quanto deve cada um
	 * 
	 * @param user utilizador que E devido pelos outros membros do grupo
	 * @requires {@code user != null && usersInGroup().contains(user.getUser)}
	 * @return um mapa com a informacao de quem sao os outros membros
	 * do grupo que devem a user e quanto deve cada um
	 */
	public Map<String, Integer> getUserDebtors(User user){	
		Map<String, Integer> userDebtors = new HashMap<String, Integer>();
			
		if(debtGraph.get(user.getUsername()) != null) {
			for(Map.Entry<String, Integer> payerEntry : debtGraph.get(user.getUsername()).entrySet()) {
				if(!payerEntry.getKey().equals(user.getUsername()) && payerEntry.getValue() != null) 
					userDebtors.put(payerEntry.getKey(), payerEntry.getValue());
			}
		}
		return userDebtors;
	}

	/**
	 * Retorna um mapa com a informacao de quem sao os outros membros
	 * do grupo a quem user deve e qual o valor devido
	 * 
	 * @param user utilizador que deve aos outros membros do grupo
	 * @requires {@code user != null && usersInGroup().contains(user)}
	 * @return um mapa com a informacao de que sao os outros membros
	 * do grupo a quem user deve e qual o valor devido
	 */
	public Map<String, Integer> getUserDebts(User user){
		Map<String, Integer> userDebts = new HashMap<String, Integer>();
		
		for(Map.Entry<String, Map<String, Integer>> receiverEntry : debtGraph.entrySet()) {
			String receiverKey = receiverEntry.getKey();
			Map<String, Integer> value = receiverEntry.getValue();
			
			for(Map.Entry<String, Integer> payerEntry : value.entrySet()) {
				if(payerEntry.getKey().equals(user.getUsername()) && !receiverKey.equals(user.getUsername()) && payerEntry.getValue() != null) {
					if(payerEntry.getValue() != null && payerEntry.getValue() != 0)
						userDebts.put(receiverKey, payerEntry.getValue());
					else
						userDebts.put(receiverKey, null);
				}
			}
		}
		
		return userDebts;
	}

	/**
	 * Salda todo o valor em divida do userPayer
	 * 
	 * @param userPayer utilizador ao qual vamos saldar a divida
	 * @requires {@code userPayer != null && usersInGroup().contains(user)}
	 */
	public void settleUp(User userPayer) {
		for(Map.Entry<String, Map<String, Integer>> receiverEntry : debtGraph.entrySet()) {
			String receiverKey = receiverEntry.getKey();
			Map<String, Integer> value = receiverEntry.getValue();
			
			for(Map.Entry<String, Integer> payerEntry : value.entrySet()) {
				if(payerEntry.getKey().equals(user.getUsername()));
					debtGraph.get(receiverKey).put(userPayer.getUsername(), null);
			}
		}
	}

	/**
	 * Salda todo o valor em divida do userPayer ao userReceiver
	 * 
	 * @param userPayer utilizador ao qual vamos saldar as dividas que devia a userReceiver
	 * @param userReceiver utilizador que era devido
	 * @requires {@code userPayer != null && userReceiver != null && !userPayer.equals(userReceiver)
	 *            && usersInGroup().contains(userPayer) && usersInGroup().contains(userReceiver)}
	 */
	public void settleUp(User userPayer, User userReceiver) {
		debtGraph.get(userReceiver.getUsername()).put(userPayer.getUsername(), null);
	}
}
