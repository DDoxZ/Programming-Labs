package project;

/**
 * 
 * @author Diogo Lopes 60447
 *
 */
public class Book {
	//atributos de instancia
	private final String title;
	private final String isbn;
	private final String author;
	private int quantity;
	private double price;
	private double tax;
	
	/**
	 * Cria um livro com os seus respetivos atributos passados por parametro
	 * 
	 * @param title uma String
	 * @param isbn uma String
	 * @param author uma String
	 * @param quantity um valor inteiro
	 * @param price um valor em do tipo double
	 * @param tax um valor do tipo double
	 */
	public Book (String title, String isbn, String author, int quantity, double price, double tax) {
		this.title = title;
		this.isbn = isbn;
		this.author = author;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
	}
	
	//getters
	/**
	 * Retorna o titulo do respetivo livro
	 * 
	 * @return o titulo do respetivo livro
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Retorna o ISBN do respetivo livro
	 * 
	 * @return o ISBN do respetivo livro
	 */
	public String getISBN() {
		return this.isbn;
	}
	
	/**
	 * Retorna o autor do respetivo livro
	 * 
	 * @return o autor do respetivo livro
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Retorna a quantidade de livros disponiveis do respetivo livro
	 * 
	 * @return a quantidade de livros disponiveis do respetivo livro
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Retorna o preco do respetivo livro
	 * 
	 * @return o preco do respetivo livro
	 */
	public double getPrice() {
		return this.price;	
	}

	/**
	 * Retorna a taxa imposta sobre o respetivo livro
	 * 
	 * @return a taxa imposta sobre o respetivo livro
	 */
	public double getTax() {
		return this.tax;
	}
	
	//setter
	/**
	 * Retorna a quantidade de volumas que o livro tem subtraindo 1 ao total
	 * 
	 * @return a quantidade de volumas que o livro tem subtraindo 1 ao total
	 */
	public void decQuantity() {
		this.quantity--;
	}
}