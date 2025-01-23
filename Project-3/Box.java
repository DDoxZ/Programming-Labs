package project;

import backbone.ArrayStack;
import backbone.Stack;
/**
 * 
 * @author Diogo Lopes 60447
 *
 */
public class Box {

    public static final int CAPACITY = 5;
    private int weight;
    private int numMatrioskas;
    private MatrioskaStack[] content;
    
    /**
     * Verifica se a caixa peek e' mais pesada que box
     * 
     * @param peek uma caixa
     * @param box uma caixa
     * @requires {@code peek != null && box != null}
     * @return true se a caixa peek e' mais pesada que box, false caso contrario
     */
    public static boolean heavierThan(Box peek, Box box) {
    	return peek.getWeight() > box.getWeight();
    }
    
    /**
     * Cria uma caixa
     */
    public Box() {
    	content = new MatrioskaStack[CAPACITY];
    	weight = 0;
    	numMatrioskas = 0;
    }
    
    /**
     * Devolve o numero de pilhas de matrioskas que a caixa tem
     * 
     * @return numero de pilhas de matrioskas que a caixa tem
     */
    public int getNumMatrioskas() {
    	return numMatrioskas;
    }

    /**
     * Adiciona uma pilha de matrioskas a' caixa na primeira posicao disponivel
     * 
     * @param matrioska uma stack de matrioskas
     * @requires {@code getNumMatrioskas() < Box.CAPACITY && matrioska != null}
     */
    public void add(MatrioskaStack matrioska) {
    	boolean added = false;
    	MatrioskaStack matrioskaCopy = matrioska.copy(); //<-precisamos fzr isto pq se mais 
        for (int i = 0; i < CAPACITY && !added; i++) {   // tarde fizermos mais pushs ele
        	if (content[i] == null) {                    // iria alteraras outras ja postas 
        		content[i] = matrioskaCopy;              // na caixa! (mutavel)
        		added = true;
        		weight += matrioska.getWeight();
        		numMatrioskas++;
        		
        	}
        } 
    }

    /**
     * Retorna uma copia da caixa corrente
     * 
     * @return uma copia da caixa corrente
     */
    public Box copy() {
    	Box newBox = new Box();
    	for (int i = 0; i < getNumMatrioskas(); i++) 
    		newBox.add(content[i].copy());
    	
        return newBox;   
    }
    
    /**
     * Retorna uma copia da stack de matrioskas na posicao pos
     * dentro da caixa
     * 
     * @param pos a posicao da stack de matrioskas dentro da caixa 
     * que queremos copiar
     * @requires {@code 1 <= pos && pos <= getNumMatrioska()}
     * @return uma copia da stack de matrioskas na posicao pos
     * dentro da caixa
     */
    public MatrioskaStack getMatrioska(int pos) {      
    	return this.content[pos-1].copy();
    }
    
    /**
     * Remove da caixa a ultima matrioska inserida
     * 
     * @requires {@code getNumMatrioskas() > 0}
     */
    public void removeLast() {
    	weight -= content[numMatrioskas - 1].getWeight();
        content[numMatrioskas] = null;
        numMatrioskas--;
    }
    
    /**
     * Verifica se a caixa esta vazia
     * 
     * @return true se a caixa estiver vazia, false caso contrario
     */
    public boolean isEmpty() {
        return numMatrioskas == 0;
    }
    
    /**
     * Verifica se a caixa esta cheia
     * 
     * @return true se a caixa estiver cheia, false caso contrario
     */
    public boolean isFull() {
        return numMatrioskas == CAPACITY;
    }

    /**
     * Retorna o peso da caixa
     * 
     * @return o peso da caixa
     */
	public int getWeight() {
		return weight;
	}

	/**
	 * Compacta a caixa, se possivel, para reduzir o numero de pilhas de matrioskas.
	 * Ou seja, se tivermos pilhas de matrioskas que sao possiveis juntar em uma so
	 * iremos proceder a essa operacao na posicao mais a' esquerda possivel na caixa
	 * 
	 * @requires {@code getNumMatrioskas() > 1}
	 */
    public void compact() {     
    	for (int i = 0; i < numMatrioskas-1; i++) {
    		for (int j = i+1; j < numMatrioskas; j++) {
    			if (content[i].merge(content[j])) {
    				int times = j;
    				while (times < numMatrioskas - 1) {
    					content[times] = content[times+1];
    					times++;
    				}
    				numMatrioskas--;
    				j--;
    			}
    		}
    	}
    } 

    /**
     * Constroi uma representacao de string do estado atual da instancia de Box.
        A string começa com o peso da caixa e o numero de MatrioskaStack dentro da caixa, e entao
        inclui uma representacao de string de cada MatrioskasStack aninhada.
        @return uma representacao textual da instancia de Box
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("   ↳BOX: w: ").append(getWeight()) ;
        sb.append(" #itens: ").append(getNumMatrioskas()).append(System.lineSeparator());
        for (int i = 0; i < getNumMatrioskas(); i++) {
            sb.append(content[i].toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

}
