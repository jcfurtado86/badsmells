package reconhecedor;

public class BadSmells {
    
    private String nome;
    private String descricao;
    private String tipo;
    
    public BadSmells(String nome,String descricao,String tipo){
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public String getTipo() {
        return this.tipo;
    }
    
}
