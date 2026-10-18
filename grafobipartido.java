import java.util.Scanner;

public class grafobipartido {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int n, m, BooleanoX, BooleanoY;
        BooleanoX = 0;
        BooleanoY = 0;
        System.out.println("un grafo es bipartido si solo si no hay ninguna arista que este entre el conjunto de vertices X y Y los cuales son disyuntos ");
        System.out.println("Determine el numero de vertices en X");

                n = scanner.nextInt();
        scanner.nextLine();
        int[][] matrizAdyacenciaX = new int[n + 1][n + 1];
        System.out.println("Determine el numero de vertices en Y");
        m = scanner.nextInt();
        scanner.nextLine(); 
        int[][] matrizAdyacenciaY = new int[m + 1][m + 1];
        String[] verticesY = new String[m + 1];
        for (int j = 1; j <= m; j++) {  
            System.out.println("Ingrese el nombre del vértice " + j + " Y:");
            verticesY[j] = scanner.nextLine();
        }
        String[] verticesX = new String[n + 1];
        for (int i = 1; i <= n; i++) {   
            System.out.println("Ingrese el nombre del vértice " + i + " X:");
            verticesX[i] = scanner.nextLine();
        }
        for (int i = 1; i <= n; i++) {
            for (int l = 1; l <= n; l++) {
                System.out.println("¿Es adyacente el vertice " + verticesX[i] + " con el vertice " + verticesX[l] + "? (1 para si, 0 para no): ");
                matrizAdyacenciaX[i][l] = scanner.nextInt();
            }
        }
        for (int j = 1; j <= m; j++) {
            for (int k = 1; k <= m; k++) {
                System.out.println("¿Es adyacente el vertice " + verticesY[j] + " con el vertice " + verticesY[k] + "? (1 para si, 0 para no): ");
                matrizAdyacenciaY[j][k] = scanner.nextInt();
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int l = 1; l <= n; l++) {
                if (matrizAdyacenciaX[i][l] == 1) {
                    BooleanoX = 1;
                    break;
                }
            }
            if (BooleanoX == 1) break;
        }
        for (int j = 1; j <= m; j++) {
            for (int k = 1; k <= m; k++) {
                if (matrizAdyacenciaY[j][k] == 1) {
                    BooleanoY = 1;
                    break;
                }
            }
            if (BooleanoY == 1) break;
        }
        if (BooleanoX == 0 && BooleanoY == 0) {
            System.out.println("El grafo es bipartido"); 
        } else {
            System.out.println("El grafo no es bipartido");
        }
        scanner.close();
    }
}


 