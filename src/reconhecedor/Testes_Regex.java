package reconhecedor;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static reconhecedor.Regex.regexAtributos;

public class Testes_Regex {
    
    static String resultado = "\n----------- Resultados ------------------------\n";
    
    public static void main(String[] args){
        
        String entrada = 
            "private final static ImageIcon iconePag = new ImageIcon();\n" +
            "private Icon[] volatil = new Icon[2];\n" +
            "private MouseListener ml=1, m12 = new ArrayList<>(),ml3,ml4,ml5;\n" +
            "private ArrayList<Pagina> papeis;\n" +
            "private static String fileTemp;";
        
        Pattern pattern = Pattern.compile(regexAtributos, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(entrada);
        
        while (matcher.find()) {
            resultado("String: "+matcher.group(0));
            
            resultado("Tipo da variável: " + matcher.group(4));
            if(String.valueOf(matcher.group(7)).equals("null"))
                resultado("Variável: " + matcher.group(6));
            else{
                resultado("Variável: " + matcher.group(7));
                resultado("Atribuição: " + Objects.toString(matcher.group(8),"") + " " + matcher.group(9));
            }
            
            resultado("\n---------------------------------------------------------------");
        }
        
        System.out.println(resultado);
        
    }
    
    public static void resultado(String s){
        resultado = resultado + s + "\n";
    }
    
    public static boolean possuiVirgulas(String entrada){
        Pattern pattern = Pattern.compile("\\s*\\,\\s*", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(entrada);
        
        while (matcher.find()){
            return true;
        }
        
        return false;
    }
}
