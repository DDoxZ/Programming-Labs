package project;

import backbone.ArrayStack;
import backbone.Stack;
import backbone.Utils;
/**
 * 
 * @author Diogo Lopes 60447
 *
 */
public class MatrioskaStack implements Stack<Matrioska>{
    
    private Stack<Matrioska> stack;
    private int weight;
    
    /**
     * Cria uma nova pilha de matrioskas
     */
    public MatrioskaStack() {
    	this.stack = new ArrayStack<Matrioska>();
    	this.weight = 0;
    }

    /**
     * Retorna o peso da pilha de matrioskas
     * @return o peso da pilha de matrioskas
     */
    public int getWeight() {
    	return weight;
    }

    /**
     * Tenta juntar a pilha atual com a pilha que recebe
     * como parametro na pilha atual
     * 
     * @param other uma pilha de matrioskas
     * @requires {@code other != null};
     * @return true se a pilhas se juntaram, false caso contrario
     */
    public boolean merge(MatrioskaStack other){
    	// Verifying...
    	
    	if ((isEmpty() && other.isEmpty()) || other.isEmpty())
    		return false;
    	
    	Stack<Matrioska> stackCopy = stack.copy();
    	Stack<Matrioska> otherCopy;
    	
    	while (!stackCopy.isEmpty()) {
    		otherCopy = other.copy();
    		while (!otherCopy.isEmpty()) {
    			if (stackCopy.peek().getSize() == otherCopy.peek().getSize())
    				return false;
    			otherCopy.pop();
    		}
    		stackCopy.pop();
    	}
    	
    	// Merging...
    	
    	otherCopy = other.copy();
    	while (!otherCopy.isEmpty()) 
    		push(Utils.peekPop(otherCopy)); 
    		
    	
    	
    	// Sorting...
    	
    	Stack<Matrioska> stackTmp = new ArrayStack<Matrioska>();
    	
    	while (!isEmpty()) {
    		Matrioska tmpMatrioska = Utils.peekPop(stack); 
    		double tmp = tmpMatrioska.getSize();
    		while (!stackTmp.isEmpty() && stackTmp.peek().getSize() > tmp) {
    			push(Utils.peekPop(stackTmp));
    			weight -= peek().getSize();
    		}
    		stackTmp.push(tmpMatrioska);
    	}
    	
    	// Completed!
    	
    	stack = stackTmp;
    	return true;
    } 
    
     /**
     * Devolve uma copia identica da pilha atual
     * 
     * @return uma copia identica da pilha atual
     */
    @Override
    public MatrioskaStack copy() {
    	MatrioskaStack matrioskaStack = new MatrioskaStack();
    	Stack<Matrioska> stackCopy = Utils.invertStack(stack);
    	
    	while (!stackCopy.isEmpty()) 
    		matrioskaStack.push(Utils.peekPop(stackCopy));
    	
    	return matrioskaStack;
    }
    
    /**
     * Coloca matrioska no topo da pilha
     * 
     * @requires {@code Matrioska.smallerThan(this.peek(),matrioska)}
     */
    @Override
    public void push(Matrioska matrioska) {
    	stack.push(matrioska);
    	weight += matrioska.getSize();
    }
    
    /**
     * Remove o elemento que esta no topo da pilha
     * 
     * @requires {@code !isEmpty()}
     */
    @Override
    public void pop() {
    	weight -= peek().getSize();
    	stack.pop();
    }
    
    /**
     * Retorna a matrioska que esta no topo da pilha
     * 
     * @requires {@code !isEmpty()}
     * @return a matrioska que esta no topo da pilha
     */
    @Override
    public Matrioska peek() {
    	return stack.peek();
    }

    /**
     * Verifica se a pilha esta vazia
     * 
     * @return true se pilha vazia, false caso contrario
     */
    @Override
    public boolean isEmpty() {
    	return stack.isEmpty();

    }
    
    /**
     * 
     * Retorna uma representacao de string do estado atual de uma instancia MatrioskaStack.
     * A string inclui o peso de toda a MatrioskaStack e uma representação de string de cada
     * Matrioska dentro da pilha, listados na ordem inversa a que foram adicionados à pilha.
     * @return uma representação de string do MatrioskaStack
     * 
     */
    @Override
    public String toString(){
        MatrioskaStack toPrint = this.copy();
        StringBuilder sb = new StringBuilder("      ");
        int i = 0;
        for(;!toPrint.isEmpty(); i++){
            sb.append(Utils.peekPop(toPrint));
            sb.append("[");
        }
        sb.setLength(sb.length()-1);
        for (; i > 1; i--) 
            sb.append("]");
        sb.append(" w: ").append(getWeight());
        return sb.toString();
    }

}
