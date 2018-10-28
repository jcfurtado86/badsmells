package reconhecedor;

import java.util.ArrayList;

public class Metodos_DuplicatedCode {
    public ArrayList<String> corpo = new ArrayList<>();
    public String nome_metodo1, nome_metodo2;
    
    public Metodos_DuplicatedCode(String corpo, String nome_metodo1, String nome_metodo2) {
        this.corpo = transformaCorpoEmArrayList(corpo);
        this.nome_metodo1 = nome_metodo1;
        this.nome_metodo2 = nome_metodo2;
    }
    
    public Metodos_DuplicatedCode(ArrayList<String> corpo, String nome_metodo1, String nome_metodo2) {
        this.corpo = corpo;
        this.nome_metodo1 = nome_metodo1;
        this.nome_metodo2 = nome_metodo2;
    }
    
    public ArrayList<String> transformaCorpoEmArrayList(String corpo){
        ArrayList<String> al = new ArrayList<>();
        
        String[] s = corpo.split("\\n");
                
        for(String linhas : s){
            al.add(linhas);
//            System.out.println(linhas);
        }
        
        return al;
    }

}
