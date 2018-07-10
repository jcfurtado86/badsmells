package reconhecedor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reconhecedor {
    
    private final static String regexClasse = "\\s*((public|protected|private)*\\s*(static|abstract)*\\s*(class|interface)+\\s*(.*?)\\s*)\\{\\s*((.\\}?|\\s?)*)\\s*\\}";
    //private final static String regexMetodo = "(public|protected|private)+\\s*(static)*\\s*([A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇ<>Ñ_]+)\\s*([A-Za-z0-9_]+)\\s*\\((.*?)\\)\\s*\\{";
    private final static String regexMetodo = "(public|protected|private)+\\s*(static)*\\s*([A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇ<>Ñ_]+)\\s*([A-Za-z0-9_]+)\\s*\\((.*?)\\)\\s*([A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕ;ÖÚÇ<>Ñ_ ]*)\\s*\\{*";    
    private final static String regexChamadaObjeto = "(private|public|protected)*\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]*)\\s+([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\=*\\s*(new)+\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\((.*?)\\)\\s*\\;|([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\.\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\((.*?)\\)\\;+|([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\.\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*=+\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\(*(.*?)\\)*\\s*\\;+|([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]*)\\s+([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\=+\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\.\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\((.*?)\\)\\;+|([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s+\\=\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\((.*?)\\)\\;|([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\((.*?)\\);|(private|public|protected)*\\s*(static)*\\s+([A-Z]+[A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ<>_]+)\\s+([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚM<>ÇÑ_,]+)\\;|([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚM<>ÇÑ_,]*)\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚM<>ÇÑ_,]+)\\s*\\=\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚM<>ÇÑ_\\[\\],]+)\\;|System\\.out\\.(.*?)\\((.*?)\\)\\;";
    
    private final static String regexComentarios = "(//.*)|(?s)/\\*.*?\\*/|//(?s)\\n";
    private final static String regexCatch = "catch\\(.*?\\)\\s*\\{";
    private final static String regexChaves = "(\\{)|(\\})";
    private final static String regexSystemOut = "System\\.out\\.(.*?)\\((.*?)\\)\\;";
    
    private ArrayList<String> resultadoFinal = new ArrayList<>();   
    private ArrayDeque<String> pilhaChaves = new ArrayDeque<>();
    
    private int qtdMetodos = 0;
    
    public ArrayList<String> executar(String codigo) {
        String texto = codigo.replaceAll(",\\s*", ",");
        reconhecerClasse(texto); 
        
        return resultadoFinal;
    }
        
    public String removerComentarios(String texto){
        Pattern pattern = Pattern.compile(regexComentarios, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(texto);
                
        while (matcher.find()) {
            texto = texto.replace(matcher.group(0), "");
        }
                
        return texto;
    }
                
    public void reconhecerClasse(String codigo){
        Pattern pattern = Pattern.compile(regexClasse, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
            qtdMetodos = 0;
            
            String resultado = "\n----------- INICIO DE CLASSE ------------------------\n";
            resultado = resultado + matcher.group(1) + "\n";
            resultado = resultado + "Classe: " + matcher.group(5) + "\n";
            if(matcher.group(3) != null){
                resultado = resultado + "Encapsulamento: " + matcher.group(2) + " " + matcher.group(3) + "\n";
            }else{
                resultado = resultado + "Encapsulamento: " + matcher.group(2) + "\n";
            } 
            resultado = resultado + "Qtd. linhas: " + qtdLinhas(matcher.group(6)) + "\n";
            
            resultadoFinal.add(resultado);
            
            findMethod(matcher.group(6));
            
            resultado = "\nA Classe avaliada possui "+qtdMetodos+" método(s)";
            resultado = resultado + "\n---------------------------------------------------------------\n";
            resultadoFinal.add(resultado);
                 
        }
    }
    
    public void findMethod(String codigo){
        Scanner scan = new Scanner(codigo);
        scan.useDelimiter("\n");
        
        Pattern pattern = Pattern.compile(regexMetodo);
        Matcher matcher;
        
        while(scan.hasNext()){
            matcher = pattern.matcher(scan.next());
            if (matcher.find()) {
                if(!temCatch(matcher.group(0))){
                    qtdMetodos++;
                    
                    String resultado = "\n--------- INICIO DE METODO ----------------------------\n";
                    resultado = resultado + matcher.group(0).substring(0, matcher.group(0).length()-1) + "\n";
                    resultado = resultado + "Método: " + matcher.group(4) + "\n";
                    if(matcher.group(3) != null){
                        resultado = resultado + "Encapsulamento: " + matcher.group(1) + " " + matcher.group(2) + "\n";
                    }else{
                        resultado = resultado + "Encapsulamento: " + matcher.group(1) + "\n";
                    }
                    resultado = resultado + "Retorno: " + matcher.group(3) + "\n";
                    resultado = resultado + "Parâmetros: " + matcher.group(5) + "\n";
                    
                    String corpoMetodo = findBodyMethod(scan);
                    
                    resultado = resultado + "Qtd. linhas: " + qtdLinhas(corpoMetodo) + "\n";

                    resultadoFinal.add(resultado);
                     
                    reconhecerChamadaObjetos(corpoMetodo);
                    
                }
            }
        }
    }
    
    public String findBodyMethod(Scanner scan){
        String corpo = "";
        Pattern pattern = Pattern.compile(regexChaves, Pattern.MULTILINE);
        Matcher matcher;
        pilhaChaves.addFirst("X");
        while(scan.hasNext()){
            String linha = scan.next();
            matcher = pattern.matcher(linha);
            while (matcher.find()) {
                if(matcher.group(1) != null){
                    pilhaChaves.addFirst("X");
                }else{
                    pilhaChaves.removeFirst();
                }
            }
            
            if(pilhaChaves.isEmpty()){
                break;
            }else{
                corpo = corpo + linha+"\n";
            }
        }
        
        return corpo;
    }
    
    public boolean temCatch(String codigo){
                
        final Pattern pattern = Pattern.compile(regexCatch);
        final Matcher matcher = pattern.matcher(codigo.trim());

        if (matcher.find()) {
            return true;
        }
        
        return false;
    }
 
    public void reconhecerChamadaObjetos(String codigo){
        Pattern pattern = Pattern.compile(regexChamadaObjeto, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
            
            String resultado = "\n--------- INSTANCIA ----------------------------\n";
            resultado = resultado + matcher.group(0).replaceFirst("\\s*", "") + "\n";
            
            if(matcher.group(3) != null){
                resultado = resultado + "Encapsulamento: " + matcher.group(1) + "\n";
                resultado = resultado + "Classe: " + matcher.group(2) + "\n";
                resultado = resultado + "Objeto: " + matcher.group(3) + "\n";
                resultado = resultado + "Classe Instanciada: " + matcher.group(5) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(6) + "\n";
            }else if(matcher.group(8) != null){  
                resultado = resultado + "Classe / Objeto: " + matcher.group(7) + "\n";
                resultado = resultado + "Método estático: " + matcher.group(8) + "\n";
                
                verificarContinuacao(matcher.group(9));
                
                resultado = resultado + "Parametros: " + matcher.group(9) + "\n";
            }else if(matcher.group(11) != null){
                resultado = resultado + "Classe / Objeto: " + matcher.group(10) + "\n";
                resultado = resultado + "Atributo estático: " + matcher.group(11) + "\n";
                resultado = resultado + "Método / Atributo: " + matcher.group(12) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(13) + "\n";
            }else if(matcher.group(16) != null){
                resultado = resultado + "Classe / Tipo de Variavel: " + matcher.group(14) + "\n";
                resultado = resultado + "Objeto: " + matcher.group(15) + "\n";
                resultado = resultado + "Classe Instanciada: " + matcher.group(16) + "\n";
                resultado = resultado + "Método estático: " + matcher.group(17) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(18) + "\n";
            }else if(matcher.group(20) != null){
                resultado = resultado + "Objeto: " + matcher.group(19) + "\n";
                resultado = resultado + "Método: " + matcher.group(20) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(21) + "\n";
            }else if(matcher.group(22) != null){
                resultado = resultado + "Método: " + matcher.group(22) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(23) + "\n";
            }else if(matcher.group(26) != null){
                resultado = resultado + "Encapsulamento: " + matcher.group(24) + " " + matcher.group(25) + "\n";
                resultado = resultado + "Classe: " + matcher.group(26) + "\n";
                resultado = resultado + "Objeto: " + matcher.group(27) + "\n";
            }else if(matcher.group(29) != null){
                resultado = resultado + "Classe: " + matcher.group(28) + "\n";
                resultado = resultado + "Objeto: " + matcher.group(29) + "\n";
                resultado = resultado + "Valor Atribuído: " + matcher.group(30) + "\n";
            }else if(matcher.group(31) != null){
                resultado = resultado + "Método de Saída de System.out \n";
                resultado = resultado + "Método: " + matcher.group(31) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(32) + "\n";
            }
            
            resultadoFinal.add(resultado);

        }
    }
      
    public String verificarContinuacao(String codigo){
        return "";
    }
    
    public int qtdLinhas(String codigo){
        int cont = 0;
        Scanner scanner = new Scanner(codigo);
        scanner.useDelimiter("\n");
        
        while(scanner.hasNext()){
            scanner.next();
            cont++;
        }
        
        return cont;
    }
    
}
