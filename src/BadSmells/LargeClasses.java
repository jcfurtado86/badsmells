package BadSmells;

public class LargeClasses {

    String classe;
    int linhas, limiteDeLinhas = 150;

    public void getClasse(String codigo) {
//        //Retira todas as linhas em branco
//        classe = codigo.replaceAll("\\s*[\\n]", "\n");

        //Mantém linhas em branco
        classe = codigo;

        linhas = contadorClasse(classe);
    }

    public int contadorClasse(String corpo) {
        int linha_inicial = -1, cont_linhas = 1, linha_final = 0;
        char c;

        for (int i = 0; i < corpo.length(); i++) {
            c = corpo.charAt(i);

            //Conta a linha atual
            if (c == '\n') {
                cont_linhas++;
            }

            //Pega a última linha
            if (c == '}') {
                linha_final = cont_linhas;
            }

            //Grava a primeira linha
            if (c == '{' && linha_inicial == -1) {
                linha_inicial = cont_linhas;
            }

        }

        return linha_final - linha_inicial - 1;
    }

    public boolean classeLonga() {
        return linhas > limiteDeLinhas;
    }

    public int getLinhas() {
        return linhas;
    }

    public String[] mensagem(String nomeClasse) {
        String retorno = "\n\n#Nome da Classe: '" + nomeClasse + "'"
                + "\n#Número máximo de linhas: " + limiteDeLinhas + "*"
                + "\n       Com linhas em branco: " + linhas
                + "\n       Sem linhas em branco: " + qtdSemLinhasBrancas()
                + "\n\n\nO badsmell identificado mostra que a classe avaliada possui muitas linhas. É recomendado que a \nquantidade de linhas seja reduzida, pois facilita tanto na leitura/entendimento do código-fonte, \nquanto na refatoração do mesmo. \n\nPara uma classe ser mais organizada e menor, os métodos que a compõem devem ser pertinente \nao seu propósito. Por exemplo, uma classe 'Cliente' pode ter os métodos 'adicionarCarrinho' e \n'alterarDados', mas o método 'verificarPreco' deveria ficar na classe 'Produto', afim de manter o \ncódigo organizado estéticamente e conceitualmente."
                + "\n\n*Valor grande, arbitrário, usado para avaliação. Não há regra universal de quantas linhas seriam \n'linhas demais'. Portanto, aqui foi considerado " + limiteDeLinhas + ".";

        String[] ret = {retorno,Integer.toString(qtdSemLinhasBrancas())};
        return ret;
    }

    public int qtdSemLinhasBrancas() {
        String corpo_aux = classe.replaceAll("(?m)^[ \t]*\r?\n", "");

        return contadorClasse(corpo_aux);
    }

}
