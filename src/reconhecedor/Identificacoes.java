package reconhecedor;

public interface Identificacoes {
    
    void identificarPacotes(String codigo);
    void identificarImports(String codigo);
    void identificarClasse(String codigo);
    void identificarAtributosClasse(String codigo);
    
    //Identificacao de Metodos   
    String identificarConstrutor(String codigo);
    void identificarMetodosAbstratos(String codigo);
    void identificarMetodos(String codigo);
    
    void identificarChamadaObjetos(String codigo);
    
}
