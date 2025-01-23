package project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Criação de sequências de estados do Jogo da Vida, fixando a regra {3, 2, 3}
 *
 * @author Diogo Lopes 60447
 */
public class MundoSequencia implements Iterable<Mundo> {

  private ArrayList<Mundo> mundoSequencia;
  private int periodoParaAvanco;    // número de gerações que o iterador avança
  private int[] regra = {3, 2, 3};  // apenas iteramos com a regra habitual

  /**
   * Inicializa a sequencia com 1 mundo que e um clone(copia) do mundo que
   * lhe e passado como unico argumento. 
   * Alem disso, instancia o atributo periodoParaAvanco com o valor 1
   * 
   * @param mundo o mundo que vai ser clonado para inicializar a sequencia
   */
  public MundoSequencia(Mundo mundo) {
	  mundoSequencia = new ArrayList<Mundo>();
	  mundoSequencia.add(mundo.clone());
	  periodoParaAvanco = 1;
  }

  /**
   * Retorna o periodo para avanco do mundo
   * 
   * @return o periodo para avanco do mundo
   */
  public int getPeriodoParaAvanco() {
    return periodoParaAvanco;
  }
  
  /**
   * Define o periodo para avanco, sendo este o parametro dado
   * 
   * @param periodoParaAvanco novo periodo para avanco do mundo
   */
  public void setPeriodoParaAvanco(int periodoParaAvanco) {
    this.periodoParaAvanco = periodoParaAvanco;
  }

  /**
   * Retorna uma copia/clone do mundo que esta registado no fim
   * da sequencia iterada ate ao momento
   * 
   * @return uma copia/clone do mundo que esta registado no fim
   * da sequencia iterada ate ao momento
   */
  public Mundo getMundoActual() {
	  return mundoSequencia.get(mundoSequencia.size() - 1).clone();  
  }
  // nao E necessario @requires {@code mundoSequencia.size() > 0} pq
  // o construtor ja inicializa com pelo menos 1 mundo, e nao ha remocoes

  /**
   * Escreve no ficheiro passado por parametro a sequencia de mundos
   * 
   * @param nomeFicheiro ficheiro no qual vai ser escrito o mundo
   * @throws IOException 
   */
  public void escreveMundoSequencia(String nomeFicheiro) throws IOException {
    try(PrintWriter escritor = new PrintWriter(nomeFicheiro)) {
      for (Mundo mundo : mundoSequencia) {
        escritor.print(mundo);
        escritor.println(); // 1 linha em branco entre frames sucessivos
      }
    }
  }

  /**
   * Iterador do MundoSequencia
   */
  @Override
  public Iterator<Mundo> iterator() {
	  return new IteradorDeMundos(); 
  }
	  
  private class IteradorDeMundos implements Iterator<Mundo>{
    	
		  /**
		   * Verifica se o proximo mundo seria
		   * ou nao igual ao atual, retorna
		   * true se nao fosse igual, false se fosse igual
		   * 
		   * @return true se next() devolver
		   * um mundo diferente do atual, 
		   * false caso contrario
		   */
		  public boolean hasNext() {
			  Mundo mundoIter = getMundoActual();
			  mundoIter.iteraMundoNgeracoes(periodoParaAvanco, regra);
			  
			  return !getMundoActual().equals(mundoIter);
		  }
		  
		  /**
		   * Avanca o iterador por periodoParaAvanco
		   * mundos e retorna a ultima iteracao
		   * 
		   * @return ultima iteracao sobre o mundo
	       */
		  public Mundo next() {
			  Mundo mundoIter = getMundoActual();
			  for (int i = 0; i < periodoParaAvanco; i++) {
				  mundoIter.iteraMundo(regra);
				  mundoSequencia.add(mundoIter);
			  }
			  return mundoIter;
		  }
	  }
  
}
