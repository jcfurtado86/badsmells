/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgraph;

/**
 *
 * @author Luigi
 */
public class node {
    int nivel;
    String valor = "";
    node pai;

    public node(int nivel, String valor, node pai) {
        this.nivel = nivel;
        this.valor = valor;
        this.pai = pai;
    }

    public node getPai() {
        return pai;
    }

    public int getNivel() {
        return nivel;
    }

    public String getValor() {
        return valor;
    }

    
    
    
}
