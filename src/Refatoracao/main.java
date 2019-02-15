package Refatoracao;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class main {

    static ArrayList<String> corpoClasse = new ArrayList<>();
    static int dpContador = 0;

    public static void main(String[] args) {

        try {
            File fileIn = new File("a1 - menor.java");
            File fileOut = new File("a1 - menor(modificado).java");

            corpoClasse = lerArquivoEntrada(fileIn);

//            deletarIntervalo(57, 65);
//            ArrayList<String> linhas = guardarIntervaloLinhas(57, 65);
//            inserirIntervaloLinhas(linhas, 3);
            ArrayList<String> blocoDuplicado = new ArrayList<>();
            for (int i = 59; i < 66; i++) {
                blocoDuplicado.add(corpoClasse.get(i));
            }

            duplicatedCode(blocoDuplicado);

            escreverArquivoSaida(fileOut);

            Desktop.getDesktop().open(fileOut);

        } catch (Exception e) {
            System.out.println("Erro detectado em main: " + e);
        }
    }

    public static ArrayList<String> lerArquivoEntrada(File file) {
        ArrayList<String> corpo = new ArrayList<>();

        try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            String readLine = "";

            corpo.add("");
            while ((readLine = b.readLine()) != null) {
                corpo.add(readLine);
            }

        } catch (Exception e) {
            System.out.println("erro em lerArquivoEntrada: " + e);
        }

        return corpo;
    }

    public static void escreverArquivoSaida(File file) {
        file.delete();

        try {
            PrintWriter out = new PrintWriter(file);
            corpoClasse.remove(0);
            for (String s : corpoClasse) {
                out.println(s);
            }

            out.close();
        } catch (Exception e) {
            System.out.println("erro em escreverArquivoSaida: " + e);
        }
    }

    public static void deletarIntervalo(int inicio, int fim) {
        ArrayList<String> auxiliar = new ArrayList<>();

        for (int i = 0; i < corpoClasse.size(); i++) {

            if (!(i > inicio && i < fim)) {
                auxiliar.add(corpoClasse.get(i));
            }
        }

        corpoClasse = auxiliar;
    }

    public static ArrayList<String> guardarIntervaloLinhas(int inicio, int fim) {
        ArrayList<String> linhas = new ArrayList<>();

        for (int i = inicio; i <= fim; i++) {
            linhas.add(corpoClasse.get(i));
        }

        linhas.add("");
        deletarIntervalo(inicio, fim);

        return linhas;
    }

    public static void inserirIntervaloLinhas(ArrayList<String> intervalo, int inicio) {

        for (int i = 0; i < intervalo.size(); i++) {
            corpoClasse.add(i + inicio, intervalo.get(i));
        }

    }

    public static void duplicatedCode(ArrayList<String> linhasDuplicadas) {
        dpContador++;
        String n = System.getProperty("line.separator");
        String dc = "public void duplicatedCode" + dpContador + "(){" + n;

        for (String l : linhasDuplicadas) {
            dc += l + n;
        }
        dc += "}";

        corpoClasse.add(corpoClasse.size() - 1, dc + n + n);

        removerLinhasDuplicadas(linhasDuplicadas);
    }

    public static void removerLinhasDuplicadas(ArrayList<String> linhasDuplicadas) {
        ArrayList<Integer> inicios = new ArrayList<>();

        for (int i = 1; i < corpoClasse.size(); i++) {
            if (corpoClasse.get(i).equals(linhasDuplicadas.get(0))) {
                inicios.add(i);

                try {
                    for (String l : linhasDuplicadas) {
                        if (!corpoClasse.get(i++).equals(l)) {
                            throw new Exception();
                        }
                    }
                } catch (Exception e) {
                    inicios.remove(inicios.size());
                }

            }
        }
        inserirInstanciaLinhasDuplicadas(inicios, linhasDuplicadas);
    }

    public static void inserirInstanciaLinhasDuplicadas(ArrayList<Integer> inicios, ArrayList<String> linhasDuplicadas) {
        String dpInstancia = "duplicatedCode" + dpContador + "();";

        for (int i = inicios.size() - 1; i >= 0; i--) {
            deletarIntervalo(inicios.get(i), inicios.get(i) + linhasDuplicadas.size());
            corpoClasse.set(inicios.get(i), dpInstancia);
        }
    }

}
