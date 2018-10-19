package BadSmells;

public class LargeClasses implements BadSmells {
    String classe;
    int linhas;
    
    public void getClasse(String codigo){
        classe = codigo;
        contadorClasse();
    }
    
    public void contadorClasse(){
        int linha_inicial = -1, cont_linhas = 1, linha_final = 0;
        char c;
        
        for(int i=0; i<classe.length(); i++){
            c = classe.charAt(i);
            
            //Conta a linha atual
            if(c == '\n') cont_linhas ++;
            
            //Pega a última linha
            if(c == '}') linha_final = cont_linhas;
            
            //Grava a primeira linha
            if(c == '{' && linha_inicial == -1) linha_inicial = cont_linhas;
            
        }
        
        linhas = linha_final - linha_inicial - 1;
        System.out.println("========= linhas: "+linhas+" (inicial: "+linha_inicial+", final:"+linha_final+")");
    }
    
    public boolean classeLonga(){
        return linhas > 150;
    }
    
    public int getLinhas(){
        return linhas;
    }
    
}
