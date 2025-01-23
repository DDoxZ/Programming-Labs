package project;

import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

/**
 * Criação e iteração de autómatos celulares.
 *
 * Na versão actual, está implementado o Jogo da Vida, de Conway, com a opção de
 * configurar variantes não-standard do mesmo através da regra de actualização
 * de células que é passada aos métodos de iteração do mundo.
 *
 * @author diogo lopes 60447
 */
public class Mundo {
	
	private int[][] mundo;  // int desperdiça muita memória, mas facilita alteração para outros autómatos celulares
	private int numLinhas;  // redundante, mas de acordo com o padrão "explanatory variable"
	private int numColunas; // redundante, mas de acordo com o padrão "explanatory variable"

	/**
	 * Inicializa o mundo a partir de uma matriz de 0s e 1s.
	 * 
	 * @param mundoInicial matriz constituída por 0s e 1s
	 * @requires mundoInicial.length >= 2
	 * @requires mundoInicial[0].length >= 2
	 */
	public Mundo(int[][] mundoInicial) {
		numLinhas = mundoInicial.length;
		numColunas = mundoInicial[0].length;
		mundo = new int[getNumLinhas()][getNumColunas()];
		
		for (int i = 0; i < numLinhas; i++) {
			for (int j = 0; j < numColunas; j++) {
				atribuiValorCelula(i, j, mundoInicial[i][j]);
			}
		}
	}
	
	/**
	 * Inicializa o mundo a partir de um ficheiro de texto.
	 * 
	 * O tamanho do mundo a inicializar é determinado pelo número de linhas e de "colunas"
	 * do ficheiro.
	 * 
	 * @param ficheiroMundoInicial nome (ou path) do ficheiro de inicialização
	 * @requires os dados têm de estar armazenados no ficheiro no mesmo formato
	 *   usado por escreveMundo()
	 * @requires o ficheiro tem de ter pelo menos 2 linhas com dados válidos,
	 *   permitindo assim criar um mundo com pelo menos 2 linhas
	 * @requires o ficheiro tem de ter pelo menos 2 "colunas" com dados válidos,
	 *   permitindo assim criar um mundo com pelo menos 2 colunas
	 * @requires robustez: o ficheiro pode terminar com uma mudança de linha, ou não;
	 *   isso não deve afectar o mundo lido nem o respectivo tamanho
	 */
	public Mundo(String ficheiroMundoInicial) throws IOException {
		Scanner reader = new Scanner(new File(ficheiroMundoInicial));
		StringBuilder bob = new StringBuilder();
		String[] lineArray;
		String verificar;
		
		int indexLinha = 0;
		int indexColuna = 0;		
		numLinhas = 0;
		
		while (reader.hasNextLine()) {
			verificar = reader.nextLine();
			if (verificar.length() > 0) { //verificar se n tem linha vazia
				bob.append(verificar);
				numLinhas++;
			}
		}
		
		lineArray = bob.toString().split(" ");
		
		numColunas = lineArray.length / getNumLinhas();	
		
		mundo = new int[getNumLinhas()][getNumColunas()];
		
		for (int i = 0; i < lineArray.length; i++) { 
			if (lineArray[i].equals("."))                       
				atribuiValorCelula(indexLinha, indexColuna, 0);
			else 
				atribuiValorCelula(indexLinha, indexColuna, 1);
			
			if (indexColuna < getNumColunas() - 1) 
				indexColuna++;
			else {
				indexColuna = 0;					
				indexLinha++;
			}
		}
		
		reader.close();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mundo other = (Mundo) obj;
		return Arrays.deepEquals(mundo, other.mundo);
	}

	/**
	 * Determina se uma célula vive na geração seguinte.
	 *
	 * submatriz é uma matriz quadrada com 9 elementos, onde o elemento central é
	 * a célula cuja vida é determinada de acordo com a regra escolhida.
	 * 1 denota uma célula viva, 0 denota uma célula morta.
	 * Seja regra = {maxVizinhos, minVizinhos, vizinhosParaNascer}.
	 * Uma célula viva:
	 * -- morre se tiver mais de maxVizinhos vivos;
	 * -- morre se tiver menos de minVizinhos vivos;
	 * -- permanece viva nos restantes casos.
	 * Uma célula morta:
	 * -- renasce se tiver exactamente vizinhosParaNascer vizinhos vivos;
	 * -- permanece morta nos restantes casos.
	 * 
	 * @param submatriz matriz 3x3 de 0s e 1s
	 * @param regra array com 3 elementos
	 * @requires cada elemento de regra está entre 0 e 8 inclusive
	 * @return true se a célula central fica viva com a regra escolhida, false caso contrário
	 */
	// visibilidade poderia ser private ou package, mas fica public por simplicidade
	public static boolean celulaVive(int[][] submatriz, int[] regra) {
		int vizinhosVivos = 0;
		
		for (int i = 0; i < submatriz.length; i++) {
			for (int j = 0; j < submatriz[0].length; j++) {
				if ( !(i == 1 && j == 1) && submatriz[i][j] == 1) {
					vizinhosVivos++;
				}
			}			
		}
		
		if (submatriz[1][1] == 1 && (vizinhosVivos > regra[0] || vizinhosVivos < regra[1])) 
			return false;
		if (submatriz[1][1] == 0 && vizinhosVivos != regra[2]) 
			return false;
		
		return true;
	}
	
	public int getNumLinhas() {
		return numLinhas;
	}
	
	public int getNumColunas() {
		return numColunas;
	}
	
	/**
	 * Reinicializa o mundo, tornando todas as suas células mortas.
	 */
	public void zeraMundo() {
		for (int i = 0; i < getNumLinhas(); i++) {
			for (int j = 0; j < getNumColunas(); j++) {
				atribuiValorCelula(i, j, 0);
			}
		}
	}
	
	/**
	 * Atribui um valor a uma célula.
	 *
	 * Força a atribuição de um valor à célula do mundo identificada pela respectiva posição.
	 * 
	 * @param linha índice da linha da célula que se quer afectar
	 * @param coluna índice da coluna da célula que se quer afectar
	 * @param valor a atribuir à célula
	 * @requires {@literal 0 <= linha < numLinhas}
	 * @requires {@literal 0 <= coluna < numColunas}
	 * @requires valor é 0 ou 1
	 */
	public void atribuiValorCelula(int linha, int coluna, int valor) {
		mundo[linha][coluna] = valor;
	}
	
	/**
	 * Devolve o valor de uma célula do mundo.
	 *
	 * @param linha índice da linha da célula cujo valor se quer obter
	 * @param coluna índice da coluna da célula cujo valor se quer obter
	 * @requires {@literal 0 <= linha < numLinhas}
	 * @requires {@literal 0 <= coluna < numColunas}
	 * @return 0 ou 1, conforme o valor da célula
	 */
	public int valorDaCelula(int linha, int coluna) {
		return mundo[linha][coluna];
	}
	
	/**
	 * Actualiza o estado de todas as células do mundo, de acordo com a regra.
	 * 
	 * Define o valor de cada célula do mundo para a iteração seguinte com a regra dada,
	 * alterando esse valor onde necessário.
	 * As condições de fronteira a concretizar são periódicas.
	 *  
	 * @param regra array com 3 elementos
	 * @requires cada elemento de regra está entre 0 e 8 inclusive
	 */
	public void iteraMundo(int[] regra) {
		int [][] proximoMundo = new int[getNumLinhas()][getNumColunas()];
		
		for (int i = 0; i < getNumLinhas(); i++) {
			for (int j = 0; j < getNumColunas(); j++) {
				if (celulaVive(getSubmatrix(i,j), regra))
					proximoMundo[i][j] = 1;
				else
					proximoMundo[i][j] = 0;
			}
		}
		
		for (int i = 0; i < getNumLinhas(); i++) {
			for (int j = 0; j < getNumColunas(); j++) {
				atribuiValorCelula(i, j, proximoMundo[i][j]);		
			}
		}
	}
	
	/**
	 * Retorna a submatriz 3x3 com a celula central de indice i,j e
	 * os seus respetivos vizinhos.
	 * 
	 * @param i indice da linha da celula central da submatriz
	 * @param j indice da coluna da celula central da submatriz
	 * @requires {@literal 0 <= i < numLinhas}
	 * @requires {@literal 0 <= j < numColunas}
	 * @return submatriz de dimensao 3x3 com os vizinhos corretos
	 */
	private int[][] getSubmatrix (int i, int j) {
		int[][] submatrix = new int[3][3];
		int indexI = 0;
		int indexJ = 0;

		for (int n = 0; n < 3; n++) {
			for (int m = 0; m < 3; m++) {
					indexI = i-1+n;
					indexJ = j-1+m;
					
					if (indexI < 0 || indexI > getNumLinhas() - 1)
						indexI = iCorrection(indexI);
					
					if (indexJ < 0 || indexJ > getNumColunas() - 1)
						indexJ = jCorrection(indexJ);
					
					submatrix[n][m] = valorDaCelula(indexI, indexJ);
			}			
		}		
		return submatrix;
	}
	
	/**
	 * Traduz um indice linha "impossivel" ou fora do mundo para um indice
	 * correto, correspondente ao indice caso o mundo fosse fechado 
	 * e as suas extremidades interligadas
	 * 
	 * @param i indice da linha da celula a ser corrigida
	 * @requires {@literal -1 <= i <= numLinhas}
	 * @return indice real da linha da celula (indice correto)
	 */
	private int iCorrection (int i) {
		if (i < 0)
			return getNumLinhas() - 1;			
		return 0; // if i > getNumLinhas() - 1
	}
	
	/**
	 * Traduz um indice coluna "impossivel" ou fora do mundo para um indice
	 * correto, correspondente ao indice caso o mundo fosse fechado 
	 * e as suas extremidades interligadas
	 * 
	 * @param j indice da coluna da celula a ser corrigida
	 * @requires {@literal -1 <= j <= numColunas}
	 * @return indice real da coluna da celula (indice correto)
	 */
	private int jCorrection (int j) {
		if (j < 0)
			return getNumColunas() - 1;	
		return 0; // if j > getNumColunas() - 1
	}
	
	/**
	 * Itera o mundo n vezes, de acordo com a regra.
	 * 
	 * @param n número de iterações a efectuar
	 * @param regra array com 3 elementos
	 * @requires cada elemento de regra está entre 0 e 8 inclusive
	 */
	public void iteraMundoNgeracoes(int n, int[] regra) {
		for (int i = 0; i < n; i++)
			iteraMundo(regra);
	}
	
	/**
	 * Mostra o mundo no output standard (ecrã, terminal, consola).
	 *
	 * Imprime a matriz mundo no ecrã, linha a linha, representando
	 *  cada 0 pela sequência ". " (ponto seguido de espaço) e
	 *  cada 1 pela sequência "X " (x maiúsculo seguido de espaço);
	 *  cada linha impressa termina com um espaço, que se segue a '.' ou a 'X'.
	 */
	public void mostraMundo() {
		for (int i = 0; i < getNumLinhas(); i++) {
			for (int j = 0; j < getNumColunas(); j++) {
				if (valorDaCelula(i,j) == 0)
					System.out.print(". ");
				else 
					System.out.print("X ");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Regista o mundo num ficheiro de texto.
	 *
	 * Comporta-se como mostraMundo, mas escrevendo num ficheiro em vez de no ecrã.
	 * Regista a matriz mundo no ficheiro, linha a linha, representando
	 *  cada 0 pela sequência ". " (ponto seguido de espaço) e
	 *  cada 1 pela sequência "X " (x maiúsculo seguido de espaço);
	 *  cada linha registada termina com um espaço, que se segue a '.' ou a 'X'.
	 * Se o ficheiro nomeFicheiro existir, ele é reescrito; caso contrário,
	 * é criado um novo ficheiro de texto com esse path.
	 * Nota de API: poderia ser apenas uma variante de mostraMundo sobrecarregada (overloaded),
	 * mas optou-se por métodos com nomes diferentes para escrever no output standard e em ficheiros.
	 * 
	 * @see mostraMundo() formato de armazenamento dos dados no ficheiro
	 * @param nomeFicheiro é uma string que representa um path válido para um ficheiro
	 */
	public void escreveMundo(String nomeFicheiro)  throws IOException {
		PrintWriter myWriter = new PrintWriter(nomeFicheiro);
		StringBuilder bob = new StringBuilder();
		
		for (int i = 0; i < getNumLinhas(); i++) {
			for (int j = 0; j < getNumColunas(); j++) {
				if (valorDaCelula(i,j) == 0)
					bob.append(". ");
				else 
					bob.append("X ");
			}
			bob.append(System.lineSeparator());
		}
		
		myWriter.print(bob.toString());
		myWriter.close();
	}
		
	/**
	 * Representação deste mundo como uma string.
	 * 
	 * O formato da string é como descrito no contrato de mostraMundo.
	 * 
	 * @return uma string que termina com um carácter de mudança de linha
	 */
	@Override
	public String toString() {
		StringBuilder bob = new StringBuilder();
		
		for (int i = 0; i < getNumLinhas(); i++) {
			for (int j = 0; j < getNumColunas(); j++) {
				if (valorDaCelula(i,j) == 0)
					bob.append(". ");
				else
					bob.append("X ");
			}
			bob.append(System.lineSeparator());
		}		
		return bob.toString();
	}
	
	/**
	 * Faz uma copia exata do mundo passado em parametro
	 * 
	 * @param mundo o mundo que deve ser clonado
	 * @return mundo clonado a partir do parametro mundo
	 */
	public Mundo clone() {
		return new Mundo(mundo);
	}

	/**
	 * Mostra uma animação do mundo no output standard (ecrã).
	 *
	 * Imprime a condição inicial do mundo, e imprime depois
	 *    n sucessivos fotogramas (frames) de uma animação da evolução do mundo
	 *    a partir dessa condição inicial, usando a regra dada;
	 *  em cada frame, imprime a matriz mundo no ecrã, linha a linha,
	  *   representando
	 *    cada 0 pela string ". " (ponto seguido de espaço) e
	 *    cada 1 pela string "X " (x maiúsculo seguido de espaço);
	 *    cada linha impressa termina com um espaço, que se segue a '.' ou a 'X'.
	 *  antes de ser impresso o fotograma inicial (condição inicial), o ecrã é
	 *    apagado;
	 *  cada fotograma sobrepõe-se ao anterior, que foi entretanto apagado;
	 *  o tempo entre fotogramas é dado pelo valor de atraso em segundos;
	 *  este método altera o estado do mundo.
	 *  
	 * @param n número de iterações a efectuar
	 * @param regra array com 3 elementos
	 * @param atrasoEmSegundos intervalo de tempo entre fotogramas sucessivos
	 * @requires {@literal n > 0}
	 * @requires cada elemento de regra está entre 0 e 8 inclusive
	 * @requires {@literal atrasoEmSegundos > 0}
	 */
	// A implementação actual NÃO RESPEITA O CONTRATO no requisito de apagar o ecrã
	// antes da apresentação de cada frame.
	// Isso tem a ver com limitações da consola do Eclipse, onde é suposto o projecto ser
	// testado. Noutros ambientes / terminais é possível ter um método limpaConsola efectivo.
	// Prém, a implementação actual prejudica pouco a experiência de visualização.
	public void animaMundo(int n, int[] regra, double atrasoEmSegundos) {
		int i; // contador de iterações
		limpaConsola();
		mostraMundo();
		for (i = 0; i < n; i++) {
			atrasa(atrasoEmSegundos);
			limpaConsola();
			iteraMundo(regra);
			mostraMundo();
		}	
	}
	  
    // limpa a consola, preparando-a para nova escrita
  	// implementação provisória; NÃO FAZ O PRETENDIDO;
    // apenas deixa 2 linhas de intervalo entre frames, o que facilita a visualização da animação
    public final static void limpaConsola() {
    	System.out.println("\n");  // 2 linhas de intervalo
    }

	// atrasa (i.e., empata) a execução desta thread
	public final static void atrasa(double segundos) {
		try {
			Thread.sleep((long)(segundos * 1000));
	    }
	    catch(InterruptedException ex) {
	        Thread.currentThread().interrupt();
	    }
	}

}
