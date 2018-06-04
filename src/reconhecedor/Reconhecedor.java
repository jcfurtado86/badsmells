package reconhecedor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reconhecedor {
    
    final static String regexClasse = "\\s*((public|protected|private)*\\s*(static|abstract)*\\s*(class)+\\s*(.*?)\\s*)\\{\\s*((.\\}?|\\s?)*)\\s*\\}";
    final static String regexMetodo = "\\s*((public|protected|private)+\\s*(static)*\\s*(.*?)\\s*([A-Za-z0-9]+)\\s*\\((.*?)\\)\\s*(.*?))\\{\\s*((.?|\\s?)*)\\s*\\}";
    final static String regexChamadaObjeto = "\\s*(.*?) \\s*(.*?)\\s*\\=\\s*(.*?)\\s*\\;|(.*?)\\.(.*?)\\((.*?)\\)(\\;)+";
    
    private static int qtdMetodos = 0;
    
    public static void main(String[] args) {
        String enderecoArquivo = "src/reconhecedor/Executar.txt";
        String texto = "";
        
        try{
            String line = "";
            BufferedReader entrada = new BufferedReader(new FileReader(enderecoArquivo));
            while((line = entrada.readLine()) != null){ 
                texto = texto + line + "\n";
            }
        }catch(IOException e){
            System.out.println("Arquivo não encontrado: "+e);
        }
        
        System.out.println(texto + "\n---------------------------------------------------------------\n");
        
        reconhecerClasse(texto);        
    }
    
    public static void reconhecerClasse(String codigo){
        Pattern pattern = Pattern.compile(regexClasse, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
            qtdMetodos = 0;
            System.out.println("\n----------- INICIO DE CLASSE ------------------------\n");
            System.out.println(matcher.group(1));
            System.out.println("Classe: " + matcher.group(5));
            if(matcher.group(3) != null){
                System.out.println("Encapsulamento: " + matcher.group(2) + " " + matcher.group(3));
            }else{
                System.out.println("Encapsulamento: " + matcher.group(2));
            }            
            System.out.println("Qtd. linhas: " + qtdLinhas(matcher.group(6)));
            System.out.println("\n---------------------------------------------------------------\n");
            reconhecerMetodo(matcher.group(6));
            System.out.println("A Classe avaliada possui "+qtdMetodos+" métodos");
            System.out.println("\n---------------------------------------------------------------\n");
        }
    }
    
    public static void reconhecerMetodo(String codigo){
        Pattern pattern = Pattern.compile(regexMetodo, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
            qtdMetodos++;
            System.out.println("\n--------- INCIO DE METODO ----------------------------\n");
            System.out.println(matcher.group(1));
            System.out.println("Método: " + matcher.group(5));
            if(matcher.group(3) != null){
                System.out.println("Encapsulamento: " + matcher.group(2) + " " + matcher.group(3));
            }else{
                System.out.println("Encapsulamento: " + matcher.group(2));
            }
            System.out.println("Retorno: " + matcher.group(4));
            System.out.println("Parâmetros: " + matcher.group(6));
            System.out.println("Qtd. linhas: " + qtdLinhas(matcher.group(8)));
            System.out.println("\n---------------------------------------------------------------\n");
            reconhecerChamadaObjetos(matcher.group(8));
        }
    }
    
    public static void reconhecerChamadaObjetos(String codigo){
        Pattern pattern = Pattern.compile(regexChamadaObjeto, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
            System.out.println(matcher.group(0).replaceFirst("\\s*", ""));
            System.out.println("Classe: " + matcher.group(1));
            System.out.println("Nome do Objeto: " + matcher.group(2));
            System.out.println("Instância: " + matcher.group(3));
            
            if(matcher.group(4) != null && matcher.group(5) != null && (matcher.group(6) != null && !matcher.group(6).isEmpty())){
                System.out.println("Objeto " + matcher.group(4).replaceFirst("\\s*", "") + " chama método " + matcher.group(5) + " com os seguintes parâmetros: " + matcher.group(6));
            }else if(matcher.group(4) != null && matcher.group(5) != null){
                System.out.println("Objeto " + matcher.group(4).replaceFirst("\\s*", "") + " chama método " + matcher.group(5));
            }
            
            System.out.println("\n---------------------------------------------------------------\n");
        }
    }
    
    public static int qtdLinhas(String codigo){
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