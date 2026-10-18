package proyecto_2_arbol;

public class ConstruirArbol {

    public static Nodo crearBST(int[] inorden, int inicio, int fin) {
        if (inicio > fin) return null;

        int medio = (inicio + fin) / 2;
        Nodo nodo = new Nodo(inorden[medio]);

        nodo.setIzq(crearBST(inorden, inicio, medio - 1));
        nodo.setDer(crearBST(inorden, medio + 1, fin));

        return nodo;
    }

  public static void imprimir(Nodo nodo, int nivel) {
        if (nodo != null) {
            imprimir(nodo.getDer(), nivel + 1);
            for (int i = 0; i < nivel; i++) {
                System.out.print("     ");
            }
            System.out.println(nodo.getDato());
            imprimir(nodo.getIzq(), nivel + 1);
        }
    }
    
    // Verificar que el inorden sea correcto
    public static void verificarInorden(Nodo nodo) {
        if (nodo != null) {
            verificarInorden(nodo.getIzq());
            System.out.print(nodo.getDato() + " ");
            verificarInorden(nodo.getDer());
        }
    }
    
    public static void main(String[] args) {
        // 🔥 AQUÍ PONES TU ENTRADA 🔥
        int[] entrada = {1, 2, 3, 4, 5, 6, 7}; // ← CAMBIA ESTOS NÚMEROS
        
        System.out.println("Entrada (inorden): ");
        for (int num : entrada) {
            System.out.print(num + " ");
        }
        System.out.println("\n");
        
        // Crear el BST
        Nodo raiz = crearBST(entrada, 0, entrada.length - 1);
        
        // Mostrar el árbol
        System.out.println("BST creado:");
        imprimir(raiz, 0);
        
        // Verificar que el inorden sea correcto
        System.out.println("\nVerificación inorden:");
        verificarInorden(raiz);
        System.out.println();
        
        // Prueba con otra entrada
        System.out.println("\n" + "========================================");
        int[] otraEntrada = {10, 20, 30, 40, 50}; // ← OTRA PRUEBA
        
        System.out.println("Segunda prueba:");
        for (int num : otraEntrada) {
            System.out.print(num + " ");
        }
        System.out.println("\n");
        
        Nodo raiz2 = crearBST(otraEntrada, 0, otraEntrada.length - 1);
        imprimir(raiz2, 0);
        
        System.out.println("\nInorden:");
        verificarInorden(raiz2);
    }
}