package Exercicio1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * @author Diogo Lopes 60447
 */
public class ExceptionsExercise {

    /**
     * Le vetor de inteiros, um valor de indice e um valor de potencia.
     * Imprime o numero relativo a aquele indice elevado aquela potencia.
     * 
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InputMismatchException {
        try (Scanner sc = new Scanner(new File(args[0]))) {
        	int tamanho = lerTamanhoVetor(sc);
        	int[] inteiros = lerInteirosVetor(sc, tamanho);
        	int indice = lerValorIndice(sc);
        	double potencia = lerValorPotencia(sc);
        
        	if (potencia < 0)
        		throw new IllegalArgumentException("Ocorreu um erro, a potencia inserida nao pode ser negativa");
        	
        	double calculado = calculaPotencias(inteiros, indice, potencia);
        	System.out.println(calculado);
        } catch (FileNotFoundException e) {
        	System.out.println("Occoreu um erro, ficheiro de input nao encontrado");
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
        	System.out.println(e.getMessage());
        } catch (ArithmeticException e) {
        	System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
        	System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        	System.out.println("Ocorreu um erro, caracter nao convertivel para um numero");
        } catch (IllegalArgumentException e) {
        	System.out.println(e.getMessage());
        }
    }

    /**
     * Ler um valor que representa o indice do vetor
     * 
     * @param sc - o scanner usado para a leitura
     * @return o valor obtido da leitura
     * @requires sc != null
     */
    private static int lerValorIndice(Scanner sc) {
        return Integer.parseInt(sc.nextLine());
    }

    /**
     * Ler e devolver o tamanho do vetor original
     * 
     * @param sc - o scanner usado para a leitura
     * @return o tamanho do vetor a ler
     * @requires sc != null
     */
    public static int lerTamanhoVetor(Scanner sc) throws IOException {
        if (!sc.hasNextLine())
        	throw new IOException("Ocorreu um erro, o ficheiro nao possui linhas");
    	
        String line = sc.nextLine();
        
        if (Integer.parseInt(line) < 1)
        	throw new InputMismatchException("Ocorreu um erro, o tamanho do vetor inserido" + 
        									" nao corresponde a um numero inteiro positivo");
       
        return Integer.parseInt(line);  
    }

    /**
     * Ler um dado numero de inteiros e guardar num vetor
     * 
     * @param sc      - o scanner usado para a leitura
     * @param tamanho - o numero de valores a ler
     * @return o vetor de inteiros obtidos da leitura
     * @requires sc != null && tamanho > 0
     */
    public static int[] lerInteirosVetor(Scanner sc, int tamanho) throws IOException {    	
    	String line = sc.nextLine();
    	
    	if (!sc.hasNextLine())
        	throw new IOException("Ocorreu um erro, o ficheiro nao possui mais linhas");
    	
    	if ((line.length()+1)/2 != tamanho)
    		throw new InputMismatchException("Ocorreu um erro, o tamanho do vetor inserido" +
					   " nao corresponde 'a quantidade de numeros inseridos");
    	
    	String[] arrLine = new String[(line.length()+1)/2];
    	int[] values = new int[(line.length()+1)/2];
    	
    	for (int i = 0; i < line.length(); i++) 
    		arrLine = line.split(" ");
    	 	
    	for (int i = 0; i < arrLine.length; i++)
    		values[i] = Integer.parseInt(arrLine[i]);

    	return values;       	
    }

    /**
     * Ler um valor que representa a potencia
     * 
     * @param sc - o scanner usado para a leitura
     * @return o valor obtido da leitura
     * @requires sc != null
     */
    public static double lerValorPotencia(Scanner sc) {
        return Double.parseDouble(sc.nextLine());
    }

    /**
     * Determina as potencia do valor determinado pelo indice no vector
     * 
     * @param inteiros - o vetor original
     * @param indice - o indice a ser usado no vetor
     * @param potencia - a potencia que se pretende calcular
     * @return a potencia do valor determinado pelo indice 
     * @requires inteiros != null && potencia >= 0
     */
    private static double calculaPotencias(int[] inteiros, int indice, double potencia) {
        if (indice <= 0 || indice > inteiros.length)
        	throw new ArrayIndexOutOfBoundsException("Ocorreu um erro, o indice tem valor inv'alido");
    	
    	if (Double.isNaN(Math.pow((double)inteiros[indice-1], potencia)))
    		throw new ArithmeticException("Ocorreu um erro, o resultado 'e inv'alido (NaN)");
    	
    	return Math.pow((double)inteiros[indice-1], potencia);
    }

}