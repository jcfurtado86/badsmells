package reconhecedor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reconhecedor {
    
    private final static String regexClasse = "\\s*((public|protected|private)*\\s*(static|abstract)*\\s*(class)+\\s*(.*?)\\s*)\\{\\s*((.\\}?|\\s?)*)\\s*\\}";
    private final static String regexMetodo = "\\s*((public|protected|private)*\\s*(static)*\\s*(.*?)\\s*([A-Za-z0-9]+)\\s*\\((.*?)\\)\\s*(.*?))\\{\\s*((.?|\\r?\\n)*)\\s*\\}";//"\\s*((public|protected|private)+\\s*(static)*\\s*(.*?)\\s*([A-Za-z0-9]+)\\s*\\((.*?)\\)\\s*(.*?))\\{\\s*((.?|\\s?)*)\\s*\\}";
    private final static String regexChamadaObjeto = "([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]*)\\s+([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]*)\\s*\\=*\\s*(new)+\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\((.*?)\\)\\s*\\;|([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\.\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\(+(.*?)\\)+\\;+|([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\.\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*=+\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\(*(.*?)\\)*\\s*\\;+|([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]*)\\s+([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\=+\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s*\\.\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\(+(.*?)\\)+\\;+|([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\s+\\=\\s*([A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ_]+)\\((.*?)\\)\\;";
    private final static String regexComentarios = "(//.*)|(?s)/\\*.*?\\*/|//(?s)\\n";
    
    private final ArrayList<String> resultadoParcial = new ArrayList<>();
    private final ArrayList<String> resultados = new ArrayList<>();
    private static String regex = "(public|protected|private)+\\s*(static)*\\s*([A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇ<>Ñ_]+)\\s*([A-Za-z0-9_]+)\\s*\\((.*?)\\)\\s*\\{";
    private final static String regexCatch = "catch\\(.*?\\)\\s*\\{";
    private final static String regexChaves = "(\\{)|(\\})";
    private static ArrayDeque<String> pilhaChaves = new ArrayDeque<>();
    
    private int qtdMetodos = 0;
    
    public ArrayList<String> executar(String codigo) {
        String texto = codigo;
        reconhecerClasse(texto); 
        
        return resultadoParcial;
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
            
            resultadoParcial.add(resultado);
            
            findMethod(matcher.group(6));
            //reconhecerMetodo(matcher.group(6));
            
            resultado = "\nA Classe avaliada possui "+qtdMetodos+" métodos";
            resultado = resultado + "\n---------------------------------------------------------------\n";
            resultadoParcial.add(resultado);
                 
        }
    }
    
    public void reconhecerMetodo(String codigo){
        Pattern pattern = Pattern.compile(regexMetodo, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
            qtdMetodos++;
            
            String resultado = "\n--------- INICIO DE METODO ----------------------------\n";
            resultado = resultado + matcher.group(1) + "\n";
            resultado = resultado + "Método: " + matcher.group(5) + "\n";
            if(matcher.group(3) != null){
                resultado = resultado + "Encapsulamento: " + matcher.group(2) + " " + matcher.group(3) + "\n";
            }else{
                resultado = resultado + "Encapsulamento: " + matcher.group(2) + "\n";
            }
            resultado = resultado + "Retorno: " + matcher.group(4) + "\n";
            resultado = resultado + "Parâmetros: " + matcher.group(6) + "\n";
            resultado = resultado + "Qtd. linhas: " + qtdLinhas(matcher.group(8)) + "\n";
            
            resultadoParcial.add(resultado);
            
            reconhecerChamadaObjetos(matcher.group(8));
        }
    }

    public void findMethod(String codigo){
        Scanner scan = new Scanner(codigo);
        scan.useDelimiter("\n");
        
        Pattern pattern = Pattern.compile(regex);
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

                    resultadoParcial.add(resultado);
                     
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
                resultado = resultado + "Classe: " + matcher.group(1) + "\n";
                resultado = resultado + "Objeto: " + matcher.group(2) + "\n";
                resultado = resultado + "Classe Instanciada: " + matcher.group(4) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(5) + "\n";
            }else if(matcher.group(7) != null){
                resultado = resultado + "Objeto: " + matcher.group(6) + "\n";
                resultado = resultado + "Método estático: " + matcher.group(7) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(8) + "\n";    
            }else if(matcher.group(10) != null){
                resultado = resultado + "Classe: " + matcher.group(9) + "\n";
                resultado = resultado + "Atributo estático: " + matcher.group(10) + "\n";
                resultado = resultado + "Método: " + matcher.group(11) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(12) + "\n";
            }else if(matcher.group(15) != null){
                resultado = resultado + "Classe: " + matcher.group(13) + "\n";
                resultado = resultado + "Objeto: " + matcher.group(14) + "\n";
                resultado = resultado + "Classe Instanciada: " + matcher.group(15) + "\n";
                resultado = resultado + "Método estático: " + matcher.group(16) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(17) + "\n";
            }else if(matcher.group(19) != null){
                resultado = resultado + "Objeto: " + matcher.group(18) + "\n";
                resultado = resultado + "Método: " + matcher.group(19) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(20) + "\n";
            }
            
            resultadoParcial.add(resultado);

        }
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