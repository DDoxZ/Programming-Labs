package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 
 * @author Diogo Lopes 60447
 *
 */
public class Bookshop {
	
	//atributos de instancia
	private Book[] books;
	private int amountOfBooks; 
	private double revenue;
	private double profit;
	
	public static final String EOL = System.getProperty("line.separator");
	
	/**
	 * Cria uma livraria contendo numberOfBooks livros 
	 * 
	 * @param fileName, uma String com o nome do ficheiro CSV onde as informacoes dos livros estam
	 * @param numberOfBooks, inteiro que tem o numero de livros
	 * @requires {@code numberOfBooks > 0}
	 * @throws FileNotFoundException
	 */
	public Bookshop (String fileName, int numberOfBooks) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(fileName));
		books = new Book[numberOfBooks];
		StringBuilder bob = new StringBuilder();
		
		String[] booksString;
		amountOfBooks = 0;
		revenue = 0;
		profit = 0;
		
		reader.nextLine();
		while (reader.hasNextLine() && amountOfBooks < numberOfBooks) {
			
			bob.append(reader.nextLine());
			booksString = bob.toString().split(",");
			if (booksString[5].contains("%")) {
				books[amountOfBooks] = new Book(booksString[0], booksString[1], booksString[2], Integer.parseInt(booksString[3]), 
									   Double.parseDouble(booksString[4]), 
									   Double.parseDouble(booksString[5].substring(0,booksString[5].length()-1)));
			}
			else {
				books[amountOfBooks] = new Book(booksString[0], booksString[1], booksString[2], Integer.parseInt(booksString[3]), 
						   Double.parseDouble(booksString[4]), 
						   Double.parseDouble(booksString[5]));
			}
			amountOfBooks++;
			bob.delete(0, bob.length());
		}
		reader.close();
	}
	/**
	 * Retorna o numero de livros unicos que a livraria tem
	 * 
	 * @return o numero de livros unicos que a livraria tem
	 */
	public int getNumberOfBooks() {
		return amountOfBooks;
	}
	/**
	 * Retorna o numero de livros unicos e que estam disponiveis na livraria
	 * 
	 * @return o numero de livros unicos e que estam disponiveis na livraria
	 */
	public int availableBooks() {
		int availableBooks = 0;
		
		for (int i = 0; i < getNumberOfBooks(); i++) {
			if (books[i].getQuantity() > 0)
				availableBooks++;
		}
		
		return availableBooks;
	}
	/**
	 * Retorna o livro na posicao de indice i
	 * 
	 * @param i, inteiro que corresponde 'a posicao do livro pretendido
	 * @return o livro na posicao de indice i
	 */
	public Book getBook (int i) {
		return books[i-1];
	}
	/**
	 * Retorna a receita de todas as vendas do dia
	 * 
	 * @return a receita de todas as vendas do dia
	 */
	public double getTotalRevenue() {
		return revenue;
	}
	/**
	 * Retorna o lucro de todas as vendas do dia
	 * 
	 * @return o lucro de todas as vendas do dia
	 */
	public double getTotalProfit() {				
		return profit;		
	}
	/**
	 * Retorna uma String com todos os livros escritos por author, dividida em
     * linhas, onde cada linha contem o titulo e o preco de um livro.
     * 
	 * @param author, uma String que corresponde ao autor do livro
	 * @return uma String com todos os livros escritos por author, dividida em
     * linhas, onde cada linha contem o titulo e o preco de um livro.
	 */
	public String filterByAuthor (String author) {
		StringBuilder bob = new StringBuilder();
		
		for (int i = 0; i < getNumberOfBooks(); i++) {
			if (books[i].getAuthor().equals(author))
				bob.append("Title:" + books[i].getTitle() + ",Price:$" + books[i].getPrice() + EOL);			
		}
		
		return bob.toString();
	}
	/**
	 * Retorna uma String, dividida em linhas, onde cada linha contem o titulo e o
     * autor dos livros que tem o preco menor que price.
     * 
	 * @param price, um double que corresponde ao preco do livro
	 * @return uma String, dividida em linhas, onde cada linha contem o titulo e o
     * autor dos livros que tem o preco menor que price.
	 */
	public String filterByPrice (double price) {
		StringBuilder bob = new StringBuilder();
		
		for (int i = 0; i < getNumberOfBooks(); i++) {
			if (books[i].getPrice() - price < 0.01)
				bob.append("Title:" + books[i].getTitle() + ",Author:" + books[i].getAuthor() + EOL);			
		}
		
		return bob.toString();
	}
	/**
	 * Retorna as informacoes de uma compra e executa esse compra, alterando o numero de livros disponiveis, 
	 * o lucro e a receita. Le as compras apartir do ficheiro FileName
	 * 
	 * @param fileName, uma String com o nome do ficheiro CSV que contem as compras que se querem realizar
	 * @return as informacoes da compra realizada
	 * @throws FileNotFoundException
	 */
	public String readPurchase (String fileName) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(fileName));
		StringBuilder bob = new StringBuilder();
		
		reader.nextLine();
		
		while (reader.hasNextLine()) {
			String[] purchase = reader.nextLine().split(",");
			boolean bookfound = false;
			for (int i = 0; i < books.length; i++) {
				if ((purchase[0].equals(books[i].getTitle()) || purchase[0].equals(books[i].getISBN())) &&
					books[i].getQuantity() > 0) {
					//sucesso (n esquecer do EOL)
				
					bob.append("Purchase successful: " + purchase[1] + " bought " + books[i].getTitle() + 
						   " by " + books[i].getAuthor() + ", price: $" + String.format("%.2f",books[i].getPrice()) + EOL);
				
					revenue += books[i].getPrice();
					profit += books[i].getPrice() - books[i].getPrice() * (books[i].getTax() / 100);
					books[i].decQuantity();
					bookfound = true;
				}
			
				else if ((purchase[0].equals(books[i].getTitle()) || purchase[0].equals(books[i].getISBN())) &&
					books[i].getQuantity() <= 0) {
					//insucesso por falta de quatidade 		 
				
					bob.append("Book out of stock: " + purchase[1] + " asked for " + books[i].getTitle() + 
						" by " + books[i].getAuthor() + ", price: $" + String.format("%.2f",books[i].getPrice()) + EOL);
					bookfound = true;
				}				
			}
			if (!bookfound) {
				bob.append("Book not found: " + purchase[1] + " asked for " + purchase[0] + EOL);
			}
			bookfound = false;
		}
		bob.append("Total: $" + String.format("%.2f", revenue) + " [$" + String.format("%.2f", profit) + "]" );
		// + EOL + EOL + "Revenue: $" + String.format("%.2f", revenue) + EOL + "Profit: $" + String.format("%.2f", profit));
		
		reader.close();
		
		return bob.toString();
	}
	/**
	 * Atualiza o stock para refletir as vendas realizadas no dia. Grava num ficheiro
     * CSV identificado por fileName o estado do stock.
	 * 
	 * @param fileName, uma String com o nome do ficheiro CSV que ir'a conter as informacoes do Stock
	 * @throws FileNotFoundException
	 */
	public void updateStock (String fileName) throws FileNotFoundException {
		PrintWriter myWriter = new PrintWriter(fileName);
		StringBuilder bob = new StringBuilder();
		
		bob.append("Title,ISBN,Author,Quantity,Price,Tax" + EOL);
		for (int i = 0; i < books.length; i++) {
			
				bob.append(books[i].getTitle() + "," + books[i].getISBN() + "," + books[i].getAuthor() + "," + books[i].getQuantity() 
				+ "," + String.format("%.2f", books[i].getPrice()) + "," + String.format("%.2f", books[i].getTax()) + EOL);
				System.out.println(bob.toString());
		}
		
		myWriter.println(bob.toString());
		myWriter.close();
	}
}