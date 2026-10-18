// Clase para representar un nodo del árbol
class Nodo {
    int valor;
    Nodo izq;
    Nodo der;
    
    public Nodo(int valor) {
        this.valor = valor;
        this.izq = null;
        this.der = null;
    }
}

public class ConstruirArbolInorden {
    
    // Método para construir un BST balanceado desde un array inorden
    public static Nodo construirArbolDesdeInorden(int[] inorden, int inicio, int fin) {
        if (inicio > fin) {
            return null;
        }
        
        // El elemento del medio será la raíz
        int medio = (inicio + fin) / 2;
        Nodo nodo = new Nodo(inorden[medio]);
        
        // Recursivamente construir los subárboles
        nodo.izq = construirArbolDesdeInorden(inorden, inicio, medio - 1);
        nodo.der = construirArbolDesdeInorden(inorden, medio + 1, fin);
        
        return nodo;
    }
    
    // Método sobrecargado para facilitar el uso
    public static Nodo construirArbolDesdeInorden(int[] inorden) {
        return construirArbolDesdeInorden(inorden, 0, inorden.length - 1);
    }
    
    // Método para imprimir el árbol de forma visual
    public static void imprimirArbol(Nodo nodo, String prefijo, boolean esUltimo) {
        if (nodo != null) {
            System.out.println(prefijo + (esUltimo ? "└── " : "├── ") + nodo.valor);
            
            // Verificar si tiene hijos
            boolean tieneHijoIzq = nodo.izq != null;
            boolean tieneHijoDer = nodo.der != null;
            
            // Imprimir hijo derecho primero (para que se vea como un árbol normal)
            if (tieneHijoDer) {
                imprimirArbol(nodo.der, prefijo + (esUltimo ? "    " : "│   "), !tieneHijoIzq);
            }
            
            // Imprimir hijo izquierdo
            if (tieneHijoIzq) {
                imprimirArbol(nodo.izq, prefijo + (esUltimo ? "    " : "│   "), true);
            }
        }
    }
    
    // Método para verificar el recorrido inorden del árbol construido
    public static void verificarInorden(Nodo nodo) {
        if (nodo != null) {
            verificarInorden(nodo.izq);
            System.out.print(nodo.valor + " ");
            verificarInorden(nodo.der);
        }
    }
    
    // Método principal con ejemplo de uso
    public static void main(String[] args) {
        // Ejemplo: recorrido inorden dado
        int[] recorridoInorden = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        
        System.out.println("Recorrido inorden dado: ");
        for (int valor : recorridoInorden) {
            System.out.print(valor + " ");
        }
        System.out.println("\n");
        
        // Construir el árbol
        Nodo raiz = construirArbolDesdeInorden(recorridoInorden);
        
        // Imprimir el árbol construido
        System.out.println("Árbol construido:");
        imprimirArbol(raiz, "", true);
        
        System.out.println("\nVerificación - recorrido inorden del árbol construido:");
        verificarInorden(raiz);
        System.out.println();
        
        // Otro ejemplo con diferentes valores
        System.out.println("\n" + "==================================================");
        int[] otroEjemplo = {10, 20, 30, 40, 50};
        
        System.out.println("Otro ejemplo - recorrido inorden: ");
        for (int valor : otroEjemplo) {
            System.out.print(valor + " ");
        }
        System.out.println("\n");
        
        Nodo otraRaiz = construirArbolDesdeInorden(otroEjemplo);
        System.out.println("Árbol construido:");
        imprimirArbol(otraRaiz, "", true);
        
        System.out.println("\nVerificación:");
        verificarInorden(otraRaiz);
    }
}


