package br.unifap.serde.projectvisualizer.actions;

//teste

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Metodo {
    
    static String l = "";
    static BufferedReader bf;
    Scanner codigo = null;
    private String resultado = "";
    ArrayList<String> resultados = new ArrayList<>();
        
    public ArrayList<String> executar(String entrada){
        
        try{
            
            codigo = new Scanner(entrada);
            
            while(codigo.hasNextLine()){ 
            
                l = codigo.nextLine();
                
                if(encontraMetodo(l)){
                    resultado = l.replaceFirst("\\s+","") + "\n";
                    System.out.println(l.replaceFirst("\\s+",""));
                    entrouMetodo();
                }
                
                resultados.add(resultado);
                resultado = "";
                
            }
                    
        }catch(Exception e){
            System.out.println("Erro em Executar: "+e);
        }
        
        return resultados;
        
    }
    
    public static boolean encontraMetodo(String l){
        String regex = "\\s*(public|protected|private)+\\s*(static)*\\s*(.*?)\\s*([A-Za-z0-9]+)\\s*\\((.*?)\\)\\s*\\{\\s*";        
        return regex(l,regex);
    }
        
    public void entrouMetodo(){
        
        StringTokenizer st = new StringTokenizer(l);
        ArrayList<String> info = new ArrayList<>(), tokens = new ArrayList<>();        
        
        //Grava todos os tokens
        while(st.hasMoreTokens()){
            tokens.add(st.nextToken());
        }
        //Pega os tokens importantes
        for (int i = 0; i < tokens.size(); i++) {
            
            if(tokens.get(i).equals("static")) continue;
            
            //Se o token for 'nome()', não faz verificação de atributos
            if(tokens.get(i).contains("(") && tokens.get(i).contains(")")){
                getParenteses(tokens.get(i), info);
                break;
            }
            
            if(tokens.get(i).contains("(")){
                
                String s = "";
                do{
                    s = s + tokens.get(i++) +" ";
                }while(!tokens.get(i).contains(")"));
                s = s + tokens.get(i++) +" ";
                
                getParenteses(s, info);
                break;
            }
            info.add(tokens.get(i));
        }
        
        //Quantidade de Linhas
        int qtdlinhas = 1, qtdchaves = 1;
        
        
        try{
            while(codigo.hasNextLine()){
                l = codigo.nextLine();
                if(l.contains("{")) qtdchaves++;
                if(l.contains("}")) qtdchaves--;
                qtdlinhas++;
                if(qtdchaves==0) break;
            }
            info.add(""+qtdlinhas);
            
        }catch(Exception e){
            System.out.println("Erro em entrouMetodo: "+e);
        }
                
        printaValores(info);
        
    }
    
    public static void getParenteses(String s, ArrayList<String> info){
        //Nome do método
        String aux = s.substring(0, s.indexOf("("));
        if(!aux.equals("")) info.add(aux);
        
        //Parâmetros
        aux = s.substring(s.indexOf("(")+1, s.indexOf(")"));
        if(aux.equals("")) info.add("--Não há atributos--");
        else info.add(aux);
    }
    
    public void printaValores(ArrayList<String> info){
        
        if(info.size() == 5){
            System.out.println("Método: "+info.get(2));
            System.out.println("Encapsulamento: "+info.get(0));
            System.out.println("Retorno: "+info.get(1));
            System.out.println("Parâmetros: "+info.get(3));
            System.out.println("Qtd. linhas: "+info.get(4));
            
            resultado = resultado + "Método: "+info.get(2) + "\n";
            resultado = resultado + "Encapsulamento: "+info.get(0) + "\n";
            resultado = resultado + "Retorno: "+info.get(1) + "\n";
            resultado = resultado + "Parâmetros: "+info.get(3) + "\n";
            resultado = resultado + "Qtd. linhas: "+info.get(4) + "\n";
        }else{
            
            for (int i = 0; i < info.size(); i++) {
                System.out.println(info.get(i));
            }
        }
        System.out.println("\n--------------//-------------\n");
        
        resultado = resultado + "\n--------------//-------------\n";
        
    }
            
    public static boolean regex(String s, String r){
        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(s);
        
        return m.matches();
    }

}