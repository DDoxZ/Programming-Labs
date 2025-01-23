package Exercicio1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reads from a file composed of int values and convert it to Ascii art
 * @author Diogo Lopes 60447
 */
public class AsciiArtReader {

    String fileName;
    StringBuilder builder;
	
    public void drawAsciiArt(String fileName) throws FileNotFoundException {
    	
        this.fileName = fileName;
        Scanner reader = new Scanner(new File(fileName));
        StringBuilder builder = new StringBuilder();
       
        //reads the file line by line and store it in builder
        while(reader.hasNextLine()) {
        	builder.append(reader.nextLine());
            builder.append(System.lineSeparator());
        }
        
        convertAndPrint(builder);
        reader.close();
        
    }

    /*
     * reads the builder and convert each value there to a character
     * 
     */
    private void convertAndPrint(StringBuilder builder) {
    	
        //split the String from the builder by lines
        String[] values = builder.toString().split(System.lineSeparator());

        for(int i = 0; i < values.length; i++) {
            // split each line by empty spaces
            String[] line = values[i].split(" ");
            for(String value : line) {
                int intValue = Integer.parseInt(value);
                char charToPrint = (char) intValue;
                System.out.print(charToPrint);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
    		new AsciiArtReader().drawAsciiArt("ascii_art.txt");
        } catch (FileNotFoundException e) {
        	throw new FileNotFoundException("O ficheiro de leitura nao existe");
        }
    }
}
