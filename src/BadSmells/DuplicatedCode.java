package BadSmells;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import reconhecedor.Metodos_DuplicatedCode;

public class DuplicatedCode {
    ArrayList<Metodos_DuplicatedCode> metodos = new ArrayList<>();
    public ArrayList<Metodos_DuplicatedCode> linhas_repetidas = new ArrayList<>();
    
    public void armazenarMetodo(String corpo, String nome_metodo){
        Pattern pattern = Pattern.compile("\\s*[\\n]", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(corpo);
                
        while (matcher.find()) {
            corpo = corpo.replace(matcher.group(0), "\n");
        }
                
        metodos.add(new Metodos_DuplicatedCode(corpo, nome_metodo));
    }
    
    public void compararMetodos(){
        for (int i = 0; i < metodos.size(); i++) {
            
            for (int j = i+1; j < metodos.size(); j++) {    
                
                compararLinhas(metodos.get(i), metodos.get(j));
            }
        }
    }
    
    public void compararLinhas(Metodos_DuplicatedCode m1, Metodos_DuplicatedCode m2){
        String repetidas = "", l1, l2;
        int contador = 0;
        
        // Scanner método 1
        Scanner linhas1 = new Scanner(m1.corpo).useDelimiter("\n");
        
        // Scanner método 2
        Scanner linhas2 = new Scanner(m2.corpo).useDelimiter("\n");
        
        try{
            while(linhas1.hasNext()){
                l1 = linhas1.next();
                l2 = linhas2.next();
                //System.out.println("l1: "+l1+"\nl2:"+l2);
                
                //Se elas forem iguais, armazenar e contar;
                if(l1.equals(l2)){
                    repetidas += l1+"\n";
                    contador++;
                }else{
                    armazenarLinhas(repetidas, m1.nome_metodo1, m2.nome_metodo1, contador);
                    repetidas = "";
                    contador = 0;
                }
            }
            
            armazenarLinhas(repetidas, m1.nome_metodo1, m2.nome_metodo1, contador);
        }catch(Exception e){
            System.err.println("Erro em compararLinhas: "+e);
        }
        
    }
    
    public void armazenarLinhas(String linhas, String m1, String m2, int qtdLinhas){
        //Se o "linhas" fornecido for vazio, retorna;
        if(linhas.equals("")) return;
        
        linhas_repetidas.add(
                new Metodos_DuplicatedCode(
                        linhas, m1, m2, qtdLinhas
                )
        );
    }
    
}
