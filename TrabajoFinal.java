import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class TrabajoFinal {

    public static BigInteger[][] leerMatrizCuadrada(Scanner scanner) {
        Random random = new Random();

        System.out.print("Ingrese el tamaño de la matriz (n x n): ");
        int n = scanner.nextInt();

        BigInteger[][] matriz = new BigInteger[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = BigInteger.valueOf(random.nextInt(100));
            }
        }
        return matriz;
    }

    public static BigInteger[][] calcularTranspuesta(BigInteger[][] matriz) {
        int size = matriz.length;
        BigInteger[][] transpuesta = new BigInteger[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                transpuesta[j][i] = matriz[i][j];
            }
        }

        return transpuesta;
    }

    public static BigInteger[][] multiplicarMatrices(BigInteger[][] matrizA, BigInteger[][] matrizB) {
        int size = matrizA.length;
        BigInteger[][] resultado = new BigInteger[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                resultado[i][j] = BigInteger.ZERO;
                for (int k = 0; k < size; k++) {
                    resultado[i][j] = resultado[i][j].add(matrizA[i][k].multiply(matrizB[k][j]));
                }
            }
        }

        return resultado;
    }

    public static BigInteger[][] potenciaMatriz(BigInteger[][] matriz, int l) {
        BigInteger[][] resultado = matriz;

        for (int n = 1; n < l; n++) {
            resultado = multiplicarMatrices(resultado, matriz);
        }

        return resultado;
    }

    public static void imprimirMatriz(BigInteger[][] matriz) {
        for (BigInteger[] fila : matriz) {
            for (BigInteger valor : fila) {
                System.out.print(valor + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigInteger[][] matriz = leerMatrizCuadrada(scanner);

        System.out.println("La matriz original es:");
        imprimirMatriz(matriz);

        long inicioTranspuesta = System.nanoTime();
        BigInteger[][] matrizTranspuesta = calcularTranspuesta(matriz);
        long finTranspuesta = System.nanoTime();

        long inicioProducto = System.nanoTime();
        BigInteger[][] producto = multiplicarMatrices(matriz, matrizTranspuesta);
        long finProducto = System.nanoTime();

        System.out.print("Ingrese el número de potencias para el producto de la matriz original por su transpuesta : ");
        int l = scanner.nextInt();

        long inicioPotencia = System.nanoTime();
        BigInteger[][] potencia = potenciaMatriz(producto, l);
        long finPotencia = System.nanoTime();

        System.out.println("El producto multiplicado por sí mismo " + l + " veces es:");
        imprimirMatriz(potencia);

        long tiempoTotalNanosegundos = (finTranspuesta - inicioTranspuesta) +(finProducto - inicioProducto) +                                       (finPotencia - inicioPotencia);
        double tiempoTotalSegundos = tiempoTotalNanosegundos / 1_000_000_000.0;

        System.out.printf("El tiempo total para todas las operaciones es: %.9f segundos%n", tiempoTotalSegundos);

        scanner.close();
    }
}
