package BadSmells;

public class LongParameterList {

    public String nomeMetodo, parametros;
    int qtdParametros = 0, limiteParametros = 3;

    public LongParameterList(String nomeMetodo, String parametros) {
        this.nomeMetodo = nomeMetodo;
        this.parametros = parametros;
    }

    public boolean muitosParametros() {
        char[] c = parametros.toCharArray();
        int contador = 0;
        for (char ci : c) {

            if (ci == ',') {
                qtdParametros++;
            }
            contador++;
        }

        if (contador > 0) {
            qtdParametros++;
        }

        return qtdParametros > limiteParametros;
    }

    public String mensagem() {

        String retorno = "\n\n#Nome do método: '" + nomeMetodo + "'"
                + "\n#Número máximo de parâmetros: " + limiteParametros + "*"
                + "\n#Quantidade de parâmetros identificados: " + qtdParametros
                + "\n\n\nO badsmell identificado mostra que o método '" + nomeMetodo + "' possui muitos parâmetros em sua assinatura."
                + "\n\n*Valor grande, arbitrário, usado para avaliação. Não há regra universal de quantos parâmetros seriam \n'parâmetros demais'. Portanto, aqui foi considerado maior que " + limiteParametros + ".";

        return retorno;
    }

}
