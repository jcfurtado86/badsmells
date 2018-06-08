package reconhecedor;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reconhecedor {
    
    private final static String regexClasse = "\\s*((public|protected|private)*\\s*(static|abstract)*\\s*(class)+\\s*(.*?)\\s*)\\{\\s*((.\\}?|\\s?)*)\\s*\\}";
    private final static String regexMetodo = "\\s*((public|protected|private)*\\s*(static)*\\s*(.*?)\\s*([A-Za-z0-9]+)\\s*\\((.*?)\\)\\s*(.*?))\\{\\s*((.?|\\r?\\n)*)\\s*\\}";//"\\s*((public|protected|private)+\\s*(static)*\\s*(.*?)\\s*([A-Za-z0-9]+)\\s*\\((.*?)\\)\\s*(.*?))\\{\\s*((.?|\\s?)*)\\s*\\}";
    private final static String regexChamadaObjeto = "\\s*(.*?) \\s*(.*?)\\s*\\=\\s*(.*?)\\s*\\;|(.*?)\\.(.*?)\\((.*?)\\)(\\;)+";
    private final static String regexComentarios = "(//.*)|(?s)/\\*.*?\\*/|//(?s)\\n";
    
    private ArrayList<String> resultadoParcial = new ArrayList<>();
    private ArrayList<String> resultados = new ArrayList<>();
    private int qtdMetodos = 0;
    
    public ArrayList<String> executar(String codigo) {
        /*
        String enderecoArquivo = "src/reconhecedor/Executar.txt";
        String texto = "";
        
        try{
            String line = "";
            BufferedReader entrada = new BufferedReader(new FileReader(enderecoArquivo));
            while((line = entrada.readLine()) != null){ 
                texto = texto + line.trim() + "\n";
            }
        }catch(IOException e){
            System.out.println("Arquivo não encontrado: "+e);
        }
        */
        String texto = codigo;
        texto = consertarTexto(texto);

        
        //System.out.println(texto + "\n---------------------------------------------------------------\n");
        
        reconhecerClasse(texto); 
        
        return resultadoParcial;
    }
    
    public String consertarTexto(String texto){
        ArrayList<String> forEncontrados = reconhecer_for(texto);
        ArrayList<String> ifEncontrados = reconhecer_if(texto);
        ArrayList<String> whileEncontrados = reconhecer_while(texto);
        
        texto = removerComentariosEremoverDo_While(texto);
        
        for(int i=0;i<whileEncontrados.size();i++){
            texto = texto.replace(whileEncontrados.get(i), whileEncontrados.get(i).substring(0, whileEncontrados.get(i).length()-1));
        }
        
        for(int i=0;i<forEncontrados.size();i++){
            texto = texto.replace(forEncontrados.get(i), forEncontrados.get(i).substring(0, forEncontrados.get(i).length()-1));
        }
        
        for(int i=0;i<ifEncontrados.size();i++){
            texto = texto.replace(ifEncontrados.get(i), ifEncontrados.get(i).substring(0, ifEncontrados.get(i).length()-1));
        }
        
        return texto;
    }
    
    public String removerComentariosEremoverDo_While(String texto){
        Pattern pattern = Pattern.compile(regexComentarios, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(texto);
                
        while (matcher.find()) {
            texto = texto.replace(matcher.group(0), "");
        }
        
        String do_while = "\\}while";
        pattern = Pattern.compile(do_while, Pattern.MULTILINE);
        matcher = pattern.matcher(texto);
        
        while (matcher.find()) {
            texto = texto.replace(matcher.group(0), "while");
        }
        
        return texto;
    }
    
    public ArrayList<String> reconhecer_if(String texto){
        String regex = "if\\s*(.*?)\\s*\\{\\s*((.*?|\\s?)*)\\s*\\}";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(texto);
        
        ArrayList<String> encontrados = new ArrayList<>();
                
        while (matcher.find()) {
            encontrados.add(matcher.group(0));
        }
        
        return encontrados;
    }
    
    public ArrayList<String> reconhecer_for(String texto){
        String regex = "for\\s*(.*?)\\s*\\{\\s*((.*?|\\s?)*)\\s*\\}";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(texto);
        
        ArrayList<String> encontrados = new ArrayList<>();
                
        while (matcher.find()) {
            encontrados.add(matcher.group(0));
        }
        
        return encontrados;
    }
    
    public ArrayList<String> reconhecer_while(String texto){
        String regex = "while\\s*(.*?)\\s*\\{\\s*((.*?|\\s?)*)\\s*\\}";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(texto);
        
        ArrayList<String> encontrados = new ArrayList<>();
                
        while (matcher.find()) {
            encontrados.add(matcher.group(0));
        }
        
        return encontrados;
    }
            
    public void reconhecerClasse(String codigo){
        Pattern pattern = Pattern.compile(regexClasse, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
            qtdMetodos = 0;
            /*System.out.println("\n----------- INICIO DE CLASSE ------------------------\n");
            System.out.println(matcher.group(1));
            System.out.println("Classe: " + matcher.group(5));
            if(matcher.group(3) != null){
                System.out.println("Encapsulamento: " + matcher.group(2) + " " + matcher.group(3));
            }else{
                System.out.println("Encapsulamento: " + matcher.group(2));
            }            
            System.out.println("Qtd. linhas: " + qtdLinhas(matcher.group(6)));
            System.out.println("\n---------------------------------------------------------------\n");*/
            
            
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
            
            reconhecerMetodo(matcher.group(6));
            
            resultado = "\nA Classe avaliada possui "+qtdMetodos+" métodos";
            resultado = resultado + "\n---------------------------------------------------------------\n";
            resultadoParcial.add(resultado);
            
            //System.out.println("A Classe avaliada possui "+qtdMetodos+" métodos");
            //System.out.println("\n---------------------------------------------------------------\n");                  
        }
    }
    
    public void reconhecerMetodo(String codigo){
        Pattern pattern = Pattern.compile(regexMetodo, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
            qtdMetodos++;
            /*System.out.println("\n--------- INICIO DE METODO ----------------------------\n");
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
            System.out.println("\n---------------------------------------------------------------\n");*/
            
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
            
    public void reconhecerChamadaObjetos(String codigo){
        Pattern pattern = Pattern.compile(regexChamadaObjeto, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
            /*System.out.println(matcher.group(0).replaceFirst("\\s*", ""));
            System.out.println("Classe: " + matcher.group(1));
            System.out.println("Nome do Objeto: " + matcher.group(2));
            System.out.println("Instância: " + matcher.group(3));
            
            if(matcher.group(4) != null && matcher.group(5) != null && (matcher.group(6) != null && !matcher.group(6).isEmpty())){
                System.out.println("Objeto " + matcher.group(4).replaceFirst("\\s*", "") + " chama método " + matcher.group(5) + " com os seguintes parâmetros: " + matcher.group(6));
            }else if(matcher.group(4) != null && matcher.group(5) != null){
                System.out.println("Objeto " + matcher.group(4).replaceFirst("\\s*", "") + " chama método " + matcher.group(5));
            }*/
            
            String resultado = "\n--------- INSTANCIA ----------------------------\n";
            resultado = resultado + matcher.group(0).replaceFirst("\\s*", "") + "\n";
            resultado = resultado + "Classe: " + matcher.group(1) + "\n";
            resultado = resultado + "Nome do Objeto: " + matcher.group(2) + "\n";
            resultado = resultado + "Instância: " + matcher.group(3) + "\n";
            if(matcher.group(4) != null && matcher.group(5) != null && (matcher.group(6) != null && !matcher.group(6).isEmpty())){
                resultado = resultado + "Instância: " + "Objeto " + matcher.group(4).replaceFirst("\\s*", "") + " chama método " + matcher.group(5) + " com os seguintes parâmetros: " + matcher.group(6) + "\n";
            }else if(matcher.group(4) != null && matcher.group(5) != null){
                resultado = resultado + "Objeto " + matcher.group(4).replaceFirst("\\s*", "") + " chama método " + matcher.group(5) + "\n";
            }
            
            resultadoParcial.add(resultado);
            
            //System.out.println("\n---------------------------------------------------------------\n");
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