package BadSmells;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuplicatedCode {
    ArrayList<String> metodos = new ArrayList<>(),
                      linhas_repetidas = new ArrayList<>();
    int contador = 0 ;
    
    public void armazenarMetodo(String corpo){
        
//        Pattern pattern = Pattern.compile("\\n", Pattern.MULTILINE);
//        Matcher matcher = pattern.matcher(corpo);
//                
//        while (matcher.find()) {
//            corpo = corpo.replace(matcher.group(0), "");
//        }
//        
//        metodos.add(corpo);
//        System.out.println(corpo);

        metodos.add(corpo);
    }
    
    public void compararMetodos(){
        
        for (int i = 0; i < metodos.size(); i++) {
//            System.out.println("============================MÉTODO"+i+"============================");
//            System.out.println(metodos.get(i));
            for (int j = i+1; j < metodos.size(); j++) {
                compararLinhas(metodos.get(i), metodos.get(j));
            }
        }
        
        System.out.println("Número de códigos duplicados: "+contador+"\n");
    }
    
    public void compararLinhas(String m1, String m2){
        
        String armazenar_repetidas = "", l1, l2;
        
        // Scanner método 1
        Scanner linhas1 = new Scanner(m1);
        linhas1.useDelimiter("\n");
        
        // Scanner método 1
        Scanner linhas2 = new Scanner(m2);
        linhas2.useDelimiter("\n");
        
        try{
            
            while(linhas1.hasNext()){
                l1 = linhas1.next();
                System.out.println("linha1: "+l1);

                l2 = linhas2.next();
                System.out.println("linha2: "+l2);
                
                //Se elas forem iguais, armazenar e contar;
                if(l1.equals(l2)){
                    System.out.println("IGUAIS");
                    armazenar_repetidas += l1;
                    contador++;
                }else{
                    linhas_repetidas.add(armazenar_repetidas);
                    armazenar_repetidas = "";
                }
                
            }
        }catch(Exception e){
            System.err.println("Erro em compararLinhas: "+e);
        }
        
    }
    
}
