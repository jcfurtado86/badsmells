package BadSmells;

public class LongMethod {

    String corpo;
    public int qtdLinhas = 0;
    int limiteDeLinhas = 30;

    public LongMethod(String corpo) {
        this.corpo = corpo;
    }

    public boolean metodoLongo() {
        qtdLinhas = contadorLinhasCorpo(corpo);
        return qtdLinhas > limiteDeLinhas;
    }

    public int contadorLinhasCorpo(String corpoAux) {
        String[] s = corpoAux.split("\\n");
        int linhas = 0;

        for (String x : s) {
            linhas++;
        }

        return linhas;
    }

    public String[] mensagem(String nome_metodo) {

        String retorno = "\n\n#Nome do método: '" + nome_metodo + "'"
                + "\n#Número máximo de linhas: " + limiteDeLinhas + "*"
                + "\n#Quantidade de linhas identificadas:"
                + "\n       Com linhas em branco: " + qtdLinhas
                + "\n       Sem linhas em branco: " + qtdSemLinhasBrancas()
                + "\n\n\nO badsmell identificado mostra que o método '" + nome_metodo + "' possui muitas linhas de código \nem sua composição. É recomendado que a quantidade de linhas seja reduzida, pois facilita tanto \nna leitura/entendimento do código-fonte, quanto na refatoração do mesmo. Tornando o código-fonte \nmais organizado."
                + "\n\n*Valor grande, arbitrário, usado para avaliação. Não há regra universal de quantas linhas seriam \n'linhas demais'. Portanto, aqui foi considerado " + limiteDeLinhas+".";

        String[] ret = {retorno, String.valueOf(qtdSemLinhasBrancas())};
        return ret;
    }

    public int qtdSemLinhasBrancas() {
        String corpo_aux = corpo.replaceAll("(?m)^[ \t]*\r?\n", "");

        return contadorLinhasCorpo(corpo_aux);
    }
}
