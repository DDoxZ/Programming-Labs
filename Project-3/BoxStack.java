package project;

import backbone.ArrayStack;
import backbone.Stack;
import backbone.Utils;
/**
 * 
 * @author Diogo Lopes 60447
 *
 */
public class BoxStack implements Stack<Box>{

    private Stack<Box> boxStack;
    private int weight;
    private int height;
    
    /**
     * Verifica se a caixa mais leve de first e' mais pesada
     * que a caixa mais pesada de second
     * 
     * @param first uma pilha de caixas
     * @param second uma pilha de caixas
     * @requires {@code first != null && second != null}
     * @return true se a caixa mais leve de first e' mais pesada
     * que a caixa mais pesada de second
     */
    public static boolean canPile(BoxStack first, BoxStack second){
    	return first.peek().getWeight() > Utils.invertStack(second).peek().getWeight();
    }
    /**
     * Cria uma pilha de caixas
     */
    public BoxStack() {
    	boxStack = new ArrayStack<Box>();
    	weight = 0;
    	height = 0;
    }
    
    /**
     * Retorna a altura da pilha de caixas
     * 
     * @return a altura da pilha de caixas
     */
    public int getHeight() {
    	return height;
    }
    
    /**
     * Retorna o peso total da pilha de caixas
     * 
     * @return o peso total da pilha de caixas
     */
    public int getWeight() {
    	return weight;
    }

    /**
     * Empilha other na pilha corrente
     * 
     * @param other uma pilha de caixas
     * @requires {@code canPile(this, other}
     */
    public void pile(BoxStack other){
    	Stack<Box> otherInverted = Utils.invertStack(other);
    	while (!otherInverted.isEmpty()) {
    		push(Utils.peekPop(otherInverted)); 
    	}
    }
    
    /**
     * Verifica se a pilha esta vazia
     * 
     * @return true se pilha vazia, false caso contrario
     */
    @Override
    public boolean isEmpty() {
    	return boxStack.isEmpty();
    }

    /**
     * Devolve uma copia identica da pilha atual
     * 
     * @return uma copia identica da pilha atual
     */
    @Override
    public BoxStack copy() {
    	BoxStack boxboxStack = new BoxStack();
    	Stack<Box> boxStackCopy = Utils.invertStack(boxStack);
    	
    	while (!boxStackCopy.isEmpty()) 
    		boxboxStack.push(Utils.peekPop(boxStackCopy).copy());
    	
    	return boxboxStack;
    }

    /**
     * Coloca box no topo da pilha
     * 
     * @requires Box.heavierThan(this.peek(),box)
     */
    @Override
    public void push(Box box) {
    	boxStack.push(box);
    	weight += box.getWeight();
    	height++;
    }
    
    /**
     * Remove o elemento que esta no topo da pilha
     * 
     * @requires {@code !isEmpty()}
     */
    @Override
    public void pop() {
    	weight -= peek().getWeight();
    	boxStack.pop();
    	height--;
    }
    
    /**
     * Retorna uma copia da instancia da instancia de Box que esta 
     * no topo da pilha
     * 
     * @requires {@code !isEmpty()} 
     * @return uma copia da instancia da instancia de Box que esta 
     * no topo da pilha
     */
    @Override
    public Box peek() {
    	return boxStack.peek().copy();
    }
    
    /**
     * Retorna uma representação de string do estado atual de uma instancia BoxStack.

       A string inclui o peso de todo o BoxStack e uma representação de string de cada Box
       dentro da pilha, listados na ordem inversa a que foram adicionados à pilha.
       @return uma representação textual do BoxStack
     */
    @Override
    public String toString() {
        BoxStack toPrint = this.copy();
        StringBuilder sb = new StringBuilder("↙----Box Stack----↘");
        sb.append(" w: ").append(getWeight());
        sb.append(System.lineSeparator());
        while(!toPrint.isEmpty())
            sb.append(Utils.peekPop(toPrint).toString());
        sb.append("↖-------End-------↗");
        sb.append(System.lineSeparator());
        return sb.toString();
    }
    
    /*
     * 
     * Método desafio. Implementacao facultativa
     * 
     */
    public void pack(){}
    
    

}