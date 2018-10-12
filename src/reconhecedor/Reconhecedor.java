package reconhecedor;

import BadSmells.LargeClasses;
import BadSmells.LongMethod;
import BadSmells.LongParameterList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reconhecedor extends Regex implements Identificacoes {
        
    private ArrayList<String> resultadoFinal = new ArrayList<>();   
    private ArrayDeque<String> pilhaChaves = new ArrayDeque<>();   
    private int qtdMetodos = 0;
    
    private static String codigoFinal;
    private static ArrayList<String> corposMetodo = new ArrayList<>();
    
    public static ArrayList<String> badsmells = new ArrayList<>();
    
    public ArrayList<String> executar(String codigo) {
        badsmells = new ArrayList<>();
        codigo = codigo.replaceAll(",\\s*", ",");
        codigo = codigo.replaceAll("\\s*\\=\\s*", "=");
        codigo = removerComentarios(codigo);
        identificarPacotes(codigo); 
        identificarImports(codigo); 
        identificarClasse(codigo); 
        
        return resultadoFinal;
    }
        
    public String removerComentarios(String codigo){
        Pattern pattern = Pattern.compile(regexComentarios, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
                
        while (matcher.find()) {
            codigo = codigo.replace(matcher.group(0), "");
        }
                
        return codigo;
    }
    
    @Override
    public void identificarPacotes(String codigo){
        Pattern pattern = Pattern.compile(regexPacote, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);

        while (matcher.find()) {
            resultadoFinal.add(matcher.group(0)+"\n");
        }
    }
    
    @Override
    public void identificarImports(String codigo){
        Pattern pattern = Pattern.compile(regexImport, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);

        while (matcher.find()) {
            resultadoFinal.add(matcher.group(0)+"\n");
        }
    } 
                
    @Override
    public void identificarClasse(String codigo){
        Pattern pattern = Pattern.compile(regexClasse, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
            qtdMetodos = 0;
            
            String resultado = "\n----------- INICIO DE CLASSE ------------------------\n";
            resultado = resultado + matcher.group(1) + "\n";
            resultado = resultado + "Classe: " + matcher.group(5) + "\n";
            badsmells.add(matcher.group(5));
            if(matcher.group(3) != null){
                resultado = resultado + "Encapsulamento: " + matcher.group(2) + " " + matcher.group(3) + "\n";
            }else{
                resultado = resultado + "Encapsulamento: " + matcher.group(2) + "\n";
            } 
            
            System.out.println("Classe longa: "/*
                                        + new LongMethod().metodoLongo(qtdLinhas(matcher.group(6)))
                                        + " ("+qtdLinhas(matcher.group(6))+" linhas)"*/);
            
            resultadoFinal.add(resultado);
            
            String codigoSemConstrutor = identificarConstrutor(matcher.group(6));
            identificarMetodosAbstratos(codigoSemConstrutor);
            identificarMetodos(codigoSemConstrutor);
            
            codigoFinal = codigoSemConstrutor;
            
            retirarCorpoMetodo();
            identificarAtributosClasse(codigoFinal);
                        
            resultado = "\nA Classe avaliada possui "+qtdMetodos+" método(s)";
            resultado = resultado + "\n---------------------------------------------------------------\n";
            resultadoFinal.add(resultado);
                 
        }
    }
    
    @Override
    public void identificarAtributosClasse(String codigo){
        Pattern pattern = Pattern.compile(regexAtributos, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        String resultado = "\n--------- ATRIBUTOS DE CLASSE ----------------------\n";
        
        while (matcher.find()) {
            resultado = resultado + matcher.group(0) + "\n";
        }
        
        resultadoFinal.add(resultado);
    }
    
    //Identificacao de Metodos
    
    @Override
    public String identificarConstrutor(String codigo){
        Scanner scan = new Scanner(codigo);
        scan.useDelimiter("\n");
        
        Pattern pattern = Pattern.compile(regexMetodoConstrutor);
        Matcher matcher;
        
        String codigoSemConstrutor = codigo;
        
        while(scan.hasNext()){
            matcher = pattern.matcher(scan.next());
            if (matcher.find()) {
                if(!temCatch(matcher.group(0))){
                    qtdMetodos++;
                    
                    String resultado = "\n--------- INICIO DE METODO CONSTRUTOR----------------------\n";
                    resultado = resultado + matcher.group(0).substring(0, matcher.group(0).length()-1) + "\n";
                    resultado = resultado + "Método: " + matcher.group(2) + "\n";
                    resultado = resultado + "Encapsulamento: " + matcher.group(1) + "\n";
                    resultado = resultado + "Parâmetros: " + matcher.group(3) + "\n";
                    
                    String corpoMetodo = identificarCorpoMetodo(scan);
                    
                    System.out.println("Método construtor longo: "
                                        + new LongMethod().metodoLongo(qtdLinhas(corpoMetodo))
                                        + " ("+qtdLinhas(corpoMetodo)+" linhas)"
                                        + "\n\tMuitos parâmetros: "+ new LongParameterList().muitosParametros(matcher.group(3)));
                    
                    badsmells.add("Método construtor longo: "
                                        + new LongMethod().metodoLongo(qtdLinhas(corpoMetodo))
                                        + " ("+qtdLinhas(corpoMetodo)+" linhas)"
                                        + "\n\tMuitos parâmetros: "+ new LongParameterList().muitosParametros(matcher.group(3)));
                    
                    resultado = resultado + "Qtd. linhas: " + qtdLinhas(corpoMetodo) + "\n";

                    resultadoFinal.add(resultado);
                     
                    identificarChamadaObjetos(corpoMetodo);
                    
                    codigoSemConstrutor = codigoSemConstrutor.replace(matcher.group(0), "");
                    
                    corposMetodo.add(corpoMetodo);
                }
            }
        }
                
        return codigoSemConstrutor;
    }
    
    @Override
    public void identificarMetodosAbstratos(String codigo){
        Pattern pattern = Pattern.compile(regexMetodoAbstrato, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);

        while (matcher.find()) {
            qtdMetodos++;
            
            String resultado = "\n--------- INICIO DE METODO ABSTRATO ----------------------------\n";
            resultado = resultado + matcher.group(0).substring(0, matcher.group(0).length()-1) + "\n";
            resultado = resultado + "Método Abstrato: " + matcher.group(4) + "\n";
            if(matcher.group(3) != null){
                resultado = resultado + "Encapsulamento: " + matcher.group(1) + " " + matcher.group(2) + "\n";
            }else{
                resultado = resultado + "Encapsulamento: " + matcher.group(1) + "\n";
            }
            resultado = resultado + "Retorno: " + matcher.group(3) + "\n";
            resultado = resultado + "Parâmetros: " + matcher.group(5) + "\n";
            
            System.out.println("Método absrato "+matcher.group(4)
                                        + " muitos parâmetros: "+ new LongParameterList().muitosParametros(matcher.group(5)));
            
            badsmells.add("Método absrato "+matcher.group(4)
                                        + " muitos parâmetros: "+ new LongParameterList().muitosParametros(matcher.group(5)));
            
            corposMetodo.add(matcher.group(0));
            
            resultadoFinal.add(resultado);
        }
    }
    
    @Override
    public void identificarMetodos(String codigo){
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
                    if(matcher.group(2) != null){
                        resultado = resultado + "Encapsulamento: " + matcher.group(1) + " " + matcher.group(2) + "\n";
                    }else{
                        resultado = resultado + "Encapsulamento: " + matcher.group(1) + "\n";
                    }
                    resultado = resultado + "Retorno: " + matcher.group(3) + "\n";
                    resultado = resultado + "Parâmetros: " + matcher.group(5) + "\n";
                    
                    String corpoMetodo = identificarCorpoMetodo(scan);
                    
                    System.out.println("Método "+matcher.group(4)+" longo: "
                                        + new LongMethod().metodoLongo(qtdLinhas(corpoMetodo))
                                        + " ("+qtdLinhas(corpoMetodo)+" linhas)"
                                        + "\n\tMuitos parâmetros: "+ new LongParameterList().muitosParametros(matcher.group(5)));
                    
                    badsmells.add("Método "+matcher.group(4)+" longo: "
                                        + new LongMethod().metodoLongo(qtdLinhas(corpoMetodo))
                                        + " ("+qtdLinhas(corpoMetodo)+" linhas)"
                                        + "\n\tMuitos parâmetros: "+ new LongParameterList().muitosParametros(matcher.group(5)));
                    
                    resultadoFinal.add(resultado);
                     
                    identificarChamadaObjetos(corpoMetodo);
                                        
                    corposMetodo.add(corpoMetodo); 
                }
            }
        }
    }
          
    public String sequenciaDeMetodos(String codigo){
          
        String resultado = "";
                
        Pattern pattern = Pattern.compile(metodosDaSequencia, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
                    
            resultado = resultado + "Método: " + matcher.group(1) + "\n";
            resultado = resultado + "Parametros: " + matcher.group(2) + "\n";
                        
        }
        
        return resultado;
    }
    
    public String identificarCorpoMetodo(Scanner scan){
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
    
    public void retirarCorpoMetodo(){
        for(int i=0;i<corposMetodo.size();i++){
            if(!corposMetodo.get(i).trim().isEmpty()){
                codigoFinal = codigoFinal.replace(corposMetodo.get(i), "");  
            }
        }
    }
    
    @Override
    public void identificarChamadaObjetos(String codigo){
        Pattern pattern = Pattern.compile(regexInstancia, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(codigo);
        
        while (matcher.find()) {
            
            String resultado = "\n--------- INSTÂNCIA ----------------------------\n";
            resultado = resultado + matcher.group(0).replaceFirst("\\s*", "") + "\n";
            
            if(matcher.group(3) != null){
                resultado = resultado + "Classe: " + matcher.group(1) + "\n";
                resultado = resultado + "Objeto: " + matcher.group(2) + "\n";
                resultado = resultado + "Classe Instanciada: " + matcher.group(4) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(5) + "\n";
            }else if(matcher.group(7) != null){  
                resultado = resultado + "Objeto: " + matcher.group(6) + "\n";
                resultado = resultado + "Classe Instanciada: " + matcher.group(8) + "\n";                
                resultado = resultado + "Parametros: " + matcher.group(9) + "\n";
            }else if(matcher.group(10) != null){
                resultado = resultado + "Método: " + matcher.group(10) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(11) + "\n";
            }else if(matcher.group(13) != null){
                resultado = resultado + "Objeto: " + matcher.group(12) + "\n";
                resultado = resultado + "Método: " + matcher.group(13) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(14) + "\n";
            }else if(matcher.group(17) != null){
                resultado = resultado + "Classe: " + matcher.group(15) + "\n";
                resultado = resultado + "Objeto: " + matcher.group(16) + "\n";
                resultado = resultado + "Método: " + matcher.group(17) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(18) + "\n";
            }else if(matcher.group(19) != null){
                resultado = resultado + "Objeto: " + matcher.group(19) + "\n";
                resultado = resultado + "Valor Atribuído: " + matcher.group(20) + "\n";
            }else if(matcher.group(22) != null){
                resultado = resultado + "Objeto: " + matcher.group(21) + "\n";
                resultado = resultado + "Atributo: " + matcher.group(22) + "\n";
                resultado = resultado + "Método: " + matcher.group(23) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(24) + "\n";              
            }else if(matcher.group(26) != null){
                resultado = resultado + "Objeto: " + matcher.group(25) + "\n";
                //resultado = resultado + "Método: " + matcher.group(26) + "\n";
                //resultado = resultado + "Parametros: " + matcher.group(27) + "\n";
                
                //sequencia
                resultado = resultado + sequenciaDeMetodos(matcher.group(0));
                
            }else if(matcher.group(30) != null){
                resultado = resultado + "Classe: " + matcher.group(28).replaceFirst("\\s*", "") + "\n";
                resultado = resultado + "Objeto1: " + matcher.group(29) + "\n";
                resultado = resultado + "Objeto2: " + matcher.group(30) + "\n";
                //resultado = resultado + "Método: " + matcher.group(31) + "\n";
                //resultado = resultado + "Parametros: " + matcher.group(32) + "\n";
                
                //sequencia
                resultado = resultado + sequenciaDeMetodos(matcher.group(0));
                
            }else if(matcher.group(34) != null){
                resultado = resultado + "Objeto1: " + matcher.group(33) + "\n";
                resultado = resultado + "Objeto2: " + matcher.group(34) + "\n";
                //resultado = resultado + "Método: " + matcher.group(35) + "\n";
                //resultado = resultado + "Parametros: " + matcher.group(36) + "\n";
                
                //sequencia
                resultado = resultado + sequenciaDeMetodos(matcher.group(0));
                
            }else if(matcher.group(40) != null) {
                resultado = resultado + "Objeto: " + matcher.group(39) + "\n";
                resultado = resultado + "Atributo: " + matcher.group(40) + "\n";
                resultado = resultado + "Valor Atribuido: " + matcher.group(41) + "\n";
            }else if(matcher.group(43) != null){
                resultado = resultado + "Objeto1: " + matcher.group(42) + "\n";
                resultado = resultado + "Atributo: " + matcher.group(43) + "\n";
                resultado = resultado + "Objeto2: " + matcher.group(44) + "\n";
                //resultado = resultado + "Método: " + matcher.group(45) + "\n";
                //resultado = resultado + "Parametros: " + matcher.group(46) + "\n";
                
                //sequencia
                resultado = resultado + sequenciaDeMetodos(matcher.group(0));
                
            }else{
                resultado = resultado + "Método de Saída de System.out \n";
                resultado = resultado + "Método: " + matcher.group(37) + "\n";
                resultado = resultado + "Parametros: " + matcher.group(38) + "\n";
            }
            
            resultadoFinal.add(resultado);

        }
    }
    
}
