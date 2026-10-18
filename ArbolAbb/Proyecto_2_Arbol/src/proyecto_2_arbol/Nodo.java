/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_arbol;

/**
 *
 * @author adbadillo
 */
public class Nodo {
    
    private int dato;
    private Nodo der;
    private Nodo izq;

    public Nodo(int dato) {
        this.dato = dato;
        der= null;
        izq= null;
    }

    public int getDato() {
        return dato;
    }

    public Nodo getDer() {
        return der;
    }

    public Nodo getIzq() {
        return izq;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }
    
    
}
