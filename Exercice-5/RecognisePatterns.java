package exercise;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author Diogo Lopes 60447
 *
 */
public class RecognisePatterns{

	/**
	 * Verifica se o parametro loop e' um ciclo for valido
	 * 
	 * @param loop for loop que vai ser verificado
	 * @return true se loop for um ciclo for valido, false caso contrario
	 */
    public static boolean validForInt(String loop){
       Pattern loopPattern = Pattern.compile("for\\(([a-zA-Z]+ [a-zA-Z]+=\\d+)?;"
       + "([a-zA-Z]+(==|!=|<|>|<=|>=)\\d+)?;([a-zA-Z]+[+|\\-]\\+?-?)?\\)\\{\\}");       
  
       Matcher loopMatcher = loopPattern.matcher(loop);
       
       return loopMatcher.find();
    }
    
    /**
     * Verifica se o parametro json representa um JSON valido para representar uma morada
     * 
     * @param json json que vai ser verificado
     * @return true se json for valido, false caso contrario
     */
    public static boolean validAddressJson(String json){
       Pattern validAddressPattern = Pattern.compile("\\{\\\"address\\\":\\{\\\"postalCode\\\""
       + ":\\\"\\d{4,5}\\\",\\\"city\\\":\\\"[^\\\"]+\\\",\\\"countryCode\\\":\\\"[A-Z]{3}+\\\"\\}\\}");
       
       Matcher validAddressMatcher = validAddressPattern.matcher(json);
       
       return validAddressMatcher.find();
    }

    /**
     * Retorna a forca relativa da pass passada por parametro caso esta seja valida, caso
     * nao seja retorna -1
     * 
     * @param pass pass a ser verificada
     * @return forca relativa da pass passada por parametro caso esta seja valida, caso
     * nao seja retorna -1.
     */
    public static int passwordStrength(String pass){
    	Pattern capitalLetter = Pattern.compile("[A-Z]+");
    	Pattern especialChar  = Pattern.compile("\\W|\\_");
    	Pattern algarism      = Pattern.compile("[0-9]+");
    	Pattern size          = Pattern.compile("^.{6,15}$");
    	
        boolean validPassword = capitalLetter.matcher(pass).find() &&
        						especialChar.matcher(pass).find() &&
        						algarism.matcher(pass).find() &&
        						size.matcher(pass).find();
        
        if (validPassword) {
        	int passwordStrength = 0;
        	
        	for(int i = 0; i < pass.length(); i++) 
        		if(Character.toString(pass.charAt(i)).matches("[A-Z]+|\\W|\\_|[0-9]+")) 
        			passwordStrength++;
             	
        	return passwordStrength + pass.length() - 6;
        }
        else {
        	return -1;
        }        	
    }
}



