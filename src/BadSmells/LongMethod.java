package BadSmells;

public class LongMethod {
    String corpo;
    public int qtdLinhas = 0;
    
    public LongMethod(String corpo) {
        //Retira todas as linhas em branco
        this.corpo = corpo.replaceAll("\\s*[\\n]", "\n");
        
        //Mantém linhas em branco
        //this.corpo = corpo;
        
    }
    
    public boolean metodoLongo(){
        String[] s = corpo.split("\\n");
                
        for(String linhas : s) 
            qtdLinhas++;
        
        return qtdLinhas > 25;
    }
}
