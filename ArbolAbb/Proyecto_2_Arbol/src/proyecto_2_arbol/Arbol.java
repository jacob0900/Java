/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_arbol;

import java.util.Stack;

/**
 *
 * @author adbadillo
 */
public class Arbol {
    
    private Nodo raiz;
    private boolean lleno;
    
    //getter
    public Nodo getRaiz() {
        return raiz;
    }
    
    public Nodo getPadre(int valor, Nodo arbol) {
        if (arbol == null) {
            return null;
        }

        if ((arbol.getIzq() != null && arbol.getIzq().getDato() == valor) ||
            (arbol.getDer() != null && arbol.getDer().getDato() == valor)) {
            return arbol;
        }
        
        if (valor < arbol.getDato()) {
            return getPadre(valor, arbol.getIzq());
        } else {
            return getPadre(valor, arbol.getDer());
        }
    }
    
    
    //Operaciones del arbol
    public void AgregarDato(int dato){
        Nodo nodo=new Nodo(dato);
        if(raiz==null){
            raiz=nodo;
        }else{
            AgregarRecursivo(raiz,nodo);
        }
    }
    
    public void AgregarRecursivo(Nodo nodo1, Nodo nodo2){
        if( nodo2.getDato() < nodo1.getDato()){
            if(nodo1.getIzq()==null){
                nodo1.setIzq(nodo2);
            }else{
                AgregarRecursivo(nodo1.getIzq(),nodo2);
            }
        }else{
            if(nodo1.getDer()==null){
                nodo1.setDer(nodo2);
            }else{
                AgregarRecursivo(nodo1.getDer(),nodo2);
            }
        }
    }
    
    public void Imprimir(Nodo nodo, int nivel){
        if(nodo!=null){
            Imprimir(nodo.getDer(),nivel+1);
            for (int i = 0; i < nivel; i++) {
                System.out.print("     ");
            }
            System.out.println(nodo.getDato());
            Imprimir(nodo.getIzq(), nivel+1);
        }
    }
    
    public int Sumar(Nodo nodo){
        if(nodo==null){
            return 0;
        }
        return nodo.getDato()+Sumar(nodo.getIzq())+Sumar(nodo.getDer());
    }
    
    public int Altura(Nodo nodo){
        if(nodo==null){
            return 0;
        }
        return Math.max(Altura(nodo.getIzq()), Altura(nodo.getDer()))+1;
    }
    
    public int mayor(Nodo nodo){
        if(nodo.getDer()  == null){
            return nodo.getDato();
        }
        return mayor(nodo.getDer());
    }
    
    public int menor(Nodo nodo){
        if(nodo.getIzq() == null){
            return nodo.getDato();
        }
        return menor(nodo.getIzq());
    }
    
    public boolean verificarExistencia(int valor, Nodo raiz){
        if(raiz == null){
            return false;
        }
        
        if(raiz.getDato() == valor){
            return true;
        }else if(valor < raiz.getDato()){
            return verificarExistencia(valor, raiz.getIzq());
        }else{
            return verificarExistencia(valor, raiz.getDer());
        }
    }
    
    public void eliminarHoja(Nodo nodo, Nodo raiz){
        Nodo padre = this.getPadre(nodo.getDato(), raiz);
        if(padre == null){ // el nodo es la raíz
            this.raiz = null;
        }else if(padre.getDato() > nodo.getDato()){
            padre.setIzq(null);
        }else{
            padre.setDer(null);
        }
        
        System.out.println("se elimino el nodo " + nodo.getDato());
    }
    
    public Nodo buscar(int valor, Nodo raiz){    
        if(raiz.getDato() == valor){
            return raiz;
        }else if(valor < raiz.getDato()){
            return buscar(valor, raiz.getIzq());
        }else{
            return buscar(valor, raiz.getDer());
        }
    }
    
    public void eliminarConHijo(Nodo nodo, Nodo raiz){    
        Nodo hijo = (nodo.getIzq() != null) ? nodo.getIzq() : nodo.getDer();

        if(nodo == this.raiz){ // el hijo pasa a ser la nueva raíz
            this.raiz = hijo;
            System.out.println("se elimino el nodo " + nodo.getDato());
            return;
        }
        this.eliminarHoja(nodo, raiz);
        this.AgregarRecursivo(raiz, hijo);
    }
    
    public void eliminarPredecesor(Nodo nodo) {
        int predecesor = this.mayor(nodo.getIzq());
        this.eliminar(predecesor, this.raiz, 1); // usa raíz del árbol y modo 1
        nodo.setDato(predecesor);
    }
 
    public void eliminarSucesor(Nodo nodo) {
        int sucesor = this.menor(nodo.getDer()); // corregido: getDer()
        this.eliminar(sucesor, this.raiz, 2);    // usa raíz del árbol y modo 2
        nodo.setDato(sucesor);
    }

    
    public void eliminar(int valor, Nodo raiz, int modo) {
        if (!this.verificarExistencia(valor, raiz)) {
            System.out.println("El nodo no está en el árbol");
            return;
        }

        Nodo nodo = buscar(valor, raiz);

        if (nodo.getIzq() == null && nodo.getDer() == null) {
            this.eliminarHoja(nodo, raiz);
        } else if (nodo.getIzq() != null && nodo.getDer() != null) {
            // modo: 1 = predecesor, 2 = sucesor
            if (modo == 1) {
                this.eliminarPredecesor(nodo);
            } else if (modo == 2) {
                this.eliminarSucesor(nodo);
            } else {
                System.out.println("Modo inválido. Use 1 (predecesor) o 2 (sucesor).");
            }
        } else {
            this.eliminarConHijo(nodo, raiz);
        }
    }

    public boolean verificarCompleto(Nodo raiz, int altura, int nivel) {
        if (raiz == null) return true;
        System.out.println(raiz.getDato());

        if ((nivel < altura - 1) && (raiz.getIzq() == null || raiz.getDer() == null)) {
            System.out.println("El nivel " + (nivel + 1) + " esta incompleto, el nodo " + raiz.getDato() + " no tiene ambos hijos");
            return false;
        }

        if (nivel == altura - 1) {
            if (!this.lleno && (raiz.getIzq() != null || raiz.getDer() != null)) {
                System.out.println("Despues de un nodo incompleto, el nodo " + raiz.getDato() + " no deberia tener hijos");
                return false;
            }

            if (this.lleno) {
                if (raiz.getIzq() == null && raiz.getDer() != null) {
                    System.out.println("En el último nivel, el nodo " + raiz.getDato() + " tiene hijo derecho sin izquierdo");
                    return false;
                }

                if (raiz.getIzq() != null && raiz.getDer() == null) {
                    this.lleno = false;
                }

                if (raiz.getIzq() == null && raiz.getDer() == null) {
                    this.lleno = false;
                }
            }
        }

        return verificarCompleto(raiz.getIzq(), altura, nivel + 1) &&
               verificarCompleto(raiz.getDer(), altura, nivel + 1);
    }

    public boolean esCompleto(Nodo raiz) {
        this.lleno = true; // Reinicia el estado antes de empezar
        boolean resultado = verificarCompleto(raiz, this.Altura(raiz), 1);

        if (resultado) {
            System.out.println("El arbol esta completo.");
        }

        return resultado;
    }
    
    //Algoritmos de busqueda recursivos 
    public void Preorden(Nodo nodo){
        if(nodo==null){
            return;
        }
        System.out.print(nodo.getDato());
        Preorden(nodo.getIzq());
        Preorden(nodo.getDer());
    }
    
    public void Enorden(Nodo nodo){
        if(nodo==null){
            return;
        }
        Enorden(nodo.getIzq());
        System.out.print(nodo.getDato());
        Enorden(nodo.getDer());
    }
    
    public void Posorden(Nodo nodo){
        if(nodo==null){
         return;   
        }
        Posorden(nodo.getIzq());
        Posorden(nodo.getDer());
        System.out.print(nodo.getDato());
    }
    
    
    //Algoritmos de busqueda iterativos  
    public void preordenIterativo() {
            if (raiz == null) return;

            Stack<Nodo> pila = new Stack<>();
            Nodo p = raiz;

            while (p != null || !pila.isEmpty()) {
                if (p != null) {
                    // Se escribe la información del nodo
                    System.out.print(p.getDato() + " ");

                    // Guardamos el nodo en la pila
                    pila.push(p);

                    // Avanzamos al hijo izquierdo
                    p = p.getIzq();
                } else {
                    // Cuando p es nulo, sacamos de la pila
                    p = pila.pop();

                    // Ahora nos movemos al hijo derecho
                    p = p.getDer();
                }
            }
        }

    public void InordenIterativo() {
            if (raiz == null) return;

            Stack<Nodo> pila = new Stack<>();
            Nodo p = raiz;

            while (p != null || !pila.isEmpty()) {
                if (p != null) {
                    // Avanzamos a la izquierda y guardamos en pila
                    pila.push(p);
                    p = p.getIzq();
                } else {
                    // Retrocedemos
                    p = pila.pop();

                    // Imprimimos el nodo al volver del hijo izquierdo
                    System.out.print(p.getDato() + " ");

                    // Ahora vamos al hijo derecho
                    p = p.getDer();
                }
            }
        }

    public void PosordenIterativo() {
            if (raiz == null) return;

            Stack<Nodo> pila1 = new Stack<>();
            Stack<Nodo> pila2 = new Stack<>();

            pila1.push(raiz);

            while (!pila1.isEmpty()) {
                Nodo p = pila1.pop();
                pila2.push(p);

                // Primero metemos el hijo izquierdo
                if (p.getIzq() != null) pila1.push(p.getIzq());

                // Luego el hijo derecho
                if (p.getDer() != null) pila1.push(p.getDer());
            }

            // Al final, pila2 tiene el recorrido en posorden
            while (!pila2.isEmpty()) {
                System.out.print(pila2.pop().getDato() + " ");
            }
        }
    
}