package reconhecedor;

public class Metodos_DuplicatedCode {
    public String corpo, nome_metodo1, nome_metodo2;
    public int qtdLinhas;
    
    public Metodos_DuplicatedCode(String corpo, String nome_metodo, String nome_metodo2, int qtdLinhas) {
        this.corpo = corpo;
        this.nome_metodo1 = nome_metodo;
        this.nome_metodo2 = nome_metodo2;
        this.qtdLinhas = qtdLinhas;
    }
    
    public Metodos_DuplicatedCode(String corpo, String nome_metodo) {
        this.corpo = corpo;
        this.nome_metodo1 = nome_metodo;
        this.nome_metodo2 = null;
    }

}
