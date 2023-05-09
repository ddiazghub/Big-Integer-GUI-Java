/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lab2.punto2.lists;

/**
 *
 * @author USER
 */
public class Nodo {
    private int valor;
    private Nodo siguiente;

    public Nodo(){
        this.valor = 0;
        this.siguiente = null;
    }

    public int getValor() {
        return this.valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Nodo getSiguiente() {
        return this.siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }   
}
