package project;

/**
 * 
 * @author diogo lopes 60447
 *
 */
public class Percolation {
	private boolean[][] grid; // a grelha de posicoes abertas/bloqueadas.
	private boolean[][] connectsToTop; // matriz com mesma dimensao de grid.
	// em cada posicao guarda booleano que representa
	// a existencia de ligacao ao topo.

	/**
	 * Construtor
	 * @param n dimensao da grelha
	 */
	public Percolation(int n) {
		this.grid = new boolean[n][n]; //grelha n-por-n inicializada com todas as posicoes bloqueadas
		this.connectsToTop = new boolean[n][n]; //matriz n-por-n inicialmente nenhuma posicao liga ao topo
	}

	/**
	 * Abre a grelha na posicao (row,col)
	 * @param row linha (indice)
	 * @param col coluna (indice)
	 * @requires {@code validPosition(row,col)}
	 */
	public void open(int row, int col) {
		this.grid[row][col] = true;
		if (row == 0) {
			connectsToTop[row][col] = true;
		}
		connectNeighbors(row, col);
	}

	/**
	 * Tenta atualizar a matriz connectsToTop em (row,col), conectando esta posicao com o auxilio dos seus vizinhos,
	 * caso esta posicao ainda nao esteja conectada ao topo,
	 * e tenta actualizar, conectando, cada um dos vizinhos abertos de (row,col) que ainda nao esteja conectado ao topo,
	 * caso a posicao (row,col) esteja, ou passe a estar, conectada ao topo
	 * @param row linha (indice)
	 * @param col coluna (indice)
	 */
	private void connectNeighbors(int row, int col) {
		
		int[][] neighbors = {{row - 1, col},  // cima
							 {row + 1, col},  // baixo
							 {row, col - 1},  // esquerda
							 {row, col + 1}}; // direita
				
		for (int i = 0; i < 4; i++) {			
			if (validPosition(neighbors[i][0], neighbors[i][1]) && isOpen(neighbors[i][0], neighbors[i][1]) && 
				isFull(neighbors[i][0], neighbors[i][1]))
					connectsToTop[row][col] = true;
		}
		
		for (int i = 0; i < 4; i++) {			
			if (validPosition(neighbors[i][0], neighbors[i][1]) && isOpen(neighbors[i][0], neighbors[i][1]) && 
				!isFull(neighbors[i][0], neighbors[i][1]) && connectsToTop[row][col])
					connectNeighbors(neighbors[i][0], neighbors[i][1]);
		}
	}

	/**
	 * Verifica se (row,col) e uma posicao valida
	 * @param row linha (indice)
	 * @param col coluna (indice)
	 * @returns true se posicao dentro das dimensoes de grid
	 */
	public boolean validPosition(int row, int col) {
		return row >= 0 && row < grid.length && col >= 0 && col < grid.length;
	}

	/**
	 * Verifica se (row,col) e uma posicao aberta
	 * @param row linha (indice)
	 * @param col coluna (indice)
	 * @returns true se posicao estiver aberta
	 */
	public boolean isOpen(int row, int col) {
		return this.grid[row][col];
	}

	/**
	 * Verifica se (row,col) e uma posicao cheia
	 * @param row linha (indice)
	 * @param col coluna (indice)
	 * @returns true se posicao estiver cheia
	 */
	public boolean isFull(int row, int col) {
		return connectsToTop[row][col];
	}

	/**
	 * Verifica se a grelha percola
	 * @returns true se a grelha percola
	 */
	public boolean percolates() {
		int n = this.grid.length;
		boolean resultado = false;
		int col = 0;
		while (col < n && !resultado) {
			if (isFull(n - 1, col)) {
				resultado = true;
			}
			col++;
		}
		return resultado;
	}

	/**
	 * Retorna uma matriz que denota o estado da grelha
	 * @returns uma matriz que denota o estado da grelha
	 */
	public boolean[][] getGrid(){
		boolean[][] myBool = new boolean[this.grid.length][];
		for(int i = 0; i < this.grid.length; i++)
			myBool[i] = this.grid[i].clone();
		return myBool;
	}

	/**
	 * Retorna tamanho da grelha
	 * @returns tamanho da grelha
	 */
	public int getSize(){
		return this.grid.length;
	}
}