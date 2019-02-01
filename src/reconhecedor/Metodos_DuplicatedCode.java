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

    public ArrayList<String> transformaCorpoEmArrayList(String corpo) {
        ArrayList<String> al = new ArrayList<>();

        String[] s = corpo.split("\\n");

        for (String linhas : s) {
            al.add(linhas);
//            System.out.println(linhas);
        }

        return al;
    }

    public String comentario() {
        int contador = 0;
        String retorno = "\n#Métodos comparados:" + "\n     " + nome_metodo1 + " => " + nome_metodo2 + "\n\n#Linhas duplicadas:\n";
        for (String linhas : corpo) {
            retorno += "  >  " + linhas + "\n";
            contador++;
        }

        retorno += "\n#Total de linha(s) duplicada(s): " + contador
                + "\n\n\nO badsmell identificado mostra que o método '" + nome_metodo1 + "' possui " + contador + " linha(s) duplicada(s) com \no método '" + nome_metodo2 + "'. Linhas duplicadas na codificação de um programa são redundantes \ne desnecessárias, o que resulta em um código maior do que indicado e reduz sua organização.";

        if (contador >= 5) {
            retorno += "\nNeste caso, é aconselhável que o código seja refatorado, pois apresenta mais de 5 linhas \nduplicadas.";
        } else {
            retorno += "\nPor se tratarem de menos de 5 linhas duplicadas (quantidade desprezível), a refatoração \ndo código é arbitrária.";
        }

        return retorno;
    }

}
