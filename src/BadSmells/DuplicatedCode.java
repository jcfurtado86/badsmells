package BadSmells;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import reconhecedor.Metodos_DuplicatedCode;

public class DuplicatedCode {
    ArrayList<Metodos_DuplicatedCode> metodos = new ArrayList<>();
    public ArrayList<Metodos_DuplicatedCode> codigos_duplicados = new ArrayList<>();
    ArrayList<String> blocoDuplicado = new ArrayList<>();
    
    Pattern pattern;
    Matcher matcher;
    
    public void armazenarMetodo(String corpo, String nome_metodo){
        
        //Remove quebras de linhas vazias e identação
        corpo = corpo.replaceAll("\\s*[\\n]", "\n");
        corpo = corpo.replaceAll("\\t+", "");
        
        metodos.add(new Metodos_DuplicatedCode(corpo, nome_metodo, null));
    }
    
    public void compararMetodos(){
        for (int i = 0; i < metodos.size(); i++) {
            
            for (int j = 0; j < metodos.size(); j++) {    
                
                //Não comparar método com ele mesmo
                if(i == j) continue;
                compararLinhas(metodos.get(i), metodos.get(j));
            }
        }
    }
    
    public void compararLinhas(Metodos_DuplicatedCode m1, Metodos_DuplicatedCode m2){
        ArrayList<String> linhas1 = m1.corpo, linhas2 = m2.corpo;
        int linha_comparada = -1, contador = 0, ultima_linha = 0;
        blocoDuplicado = new ArrayList<>();
        
        try{
            //Linhas 1º método
            for(int i=0; i<linhas1.size();i++){
                
                if(linha_comparada+1 == linhas2.size()) linha_comparada = -1;
                
                //Linhas 2º método
                for(int j=linha_comparada+1; j<linhas2.size(); j++){
                    
                    if(linhas1.get(i).equals(linhas2.get(j)) && ultima_linha == 0){
                        blocoDuplicado.add(linhas1.get(i));
                        contador++;
                        linha_comparada = j;
                        
                        //Verifica se é a última linha do 2º método
                        if(j == linhas2.size()-1) ultima_linha = 1;
                        
                        break;
                    }else{
                        if(contador != 0)
                            armazenarBlocoDuplicado(m1.nome_metodo1, m2.nome_metodo1);
                        
                        blocoDuplicado = new ArrayList<>();
                        contador = 0;
                        linha_comparada = -1;
                        ultima_linha = 0;
                   }   
                }   
            }
            
            armazenarBlocoDuplicado(m1.nome_metodo1, m2.nome_metodo1);
        }catch(Exception e){
            System.err.println("Erro em compararLinhas: "+e);
        }
        
    }
    
    public void armazenarBlocoDuplicado(String m1, String m2){
        if(blocoDuplicado.isEmpty()) return;
        codigos_duplicados.add(
                new Metodos_DuplicatedCode(blocoDuplicado, m1, m2)
        );
    }
    
}
