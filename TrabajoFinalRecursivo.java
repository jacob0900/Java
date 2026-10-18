import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class TrabajoFinalRecursivo {

    public static BigInteger[][] leerMatrizCuadrada(Scanner scanner) {
        Random random = new Random();
        System.out.print("Ingrese el tamaño de la matriz (n x n): ");
        int tamaño = scanner.nextInt();
        BigInteger[][] matriz = new BigInteger[tamaño][tamaño];
        for (int fila = 0; fila < tamaño; fila++) {
            for (int columna = 0; columna < tamaño; columna++) {
                matriz[fila][columna] = BigInteger.valueOf(random.nextInt(10));
            }
        }
        return matriz;
    }

    private static void calcularTranspuesta(BigInteger[][] matriz, BigInteger[][] transpuesta, int fila, int columna) {
        int tamaño = matriz.length;

        if (fila >= tamaño) {
            return;
        }

        transpuesta[columna][fila] = matriz[fila][columna];

        if (columna < tamaño - 1) {
            calcularTranspuesta(matriz, transpuesta, fila, columna + 1);
        } else {
            calcularTranspuesta(matriz, transpuesta, fila + 1, 0);
        }
    }

    private static BigInteger[][] crearTranspuesta(BigInteger[][] matriz) {
        int tamaño = matriz.length;
        BigInteger[][] transpuesta = new BigInteger[tamaño][tamaño];
        calcularTranspuesta(matriz, transpuesta, 0, 0);
        return transpuesta;
    }

    private static void inicializarMatriz(BigInteger[][] matriz, int tamaño) {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                matriz[i][j] = BigInteger.ZERO;
            }
        }
    }

    private static void multiplicarRecursivo(BigInteger[][] matrizA, BigInteger[][] matrizB, BigInteger[][] resultado, int fila, int columna, int k) {
        int tamaño = matrizA.length;

        if (fila >= tamaño) {
            return;
        }

        if (k < tamaño) {
            resultado[fila][columna] = resultado[fila][columna].add(
                matrizA[fila][k].multiply(matrizB[k][columna])
            );
            multiplicarRecursivo(matrizA, matrizB, resultado, fila, columna, k + 1);
        } else {
            if (columna < tamaño - 1) {
                multiplicarRecursivo(matrizA, matrizB, resultado, fila, columna + 1, 0);
            } else {
                multiplicarRecursivo(matrizA, matrizB, resultado, fila + 1, 0, 0);
            }
        }
    }

    public static BigInteger[][] multiplicarMatrices(BigInteger[][] matrizA, BigInteger[][] matrizB) {
        int tamaño = matrizA.length;
        BigInteger[][] resultado = new BigInteger[tamaño][tamaño];
        inicializarMatriz(resultado, tamaño);
        multiplicarRecursivo(matrizA, matrizB, resultado, 0, 0, 0);
        return resultado;
    }

    public static BigInteger[][] potenciaMatriz(BigInteger[][] matriz, int potencia) {
        int tamaño = matriz.length;

        if (potencia == 0) {
            return identidad(tamaño);
        }

        if (potencia == 1) {
            return matriz;
        }

        BigInteger[][] mitadPotencia = potenciaMatriz(matriz, potencia / 2);

        BigInteger[][] resultado = multiplicarMatrices(mitadPotencia, mitadPotencia);
        if (potencia % 2 == 1) {
            resultado = multiplicarMatrices(resultado, matriz);
        }

        return resultado;
    }

    private static BigInteger[][] identidad(int tamaño) {
        BigInteger[][] identidad = new BigInteger[tamaño][tamaño];
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                identidad[i][j] = (i == j) ? BigInteger.ONE : BigInteger.ZERO;
            }
        }
        return identidad;
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

        // Se pide l antes de medir, para no contar el tiempo que tarda el usuario en escribir
        System.out.print("Ingrese el número de veces que desea multiplicar el producto por sí mismo (l): ");
        int potencia = scanner.nextInt();

        long inicioTotal = System.nanoTime();
        BigInteger[][] matrizTranspuesta = crearTranspuesta(matriz);
        BigInteger[][] producto = multiplicarMatrices(matriz, matrizTranspuesta);
        BigInteger[][] resultadoPotencia = potenciaMatriz(producto, potencia);

        long finTotal = System.nanoTime();
        double tiempoTotalSegundos = (finTotal - inicioTotal) / 1_000_000_000.0;

        System.out.println("La matriz transpuesta es:");
        imprimirMatriz(matrizTranspuesta);

        System.out.println("El producto de la matriz original por su transpuesta es:");
        imprimirMatriz(producto);

        System.out.println("El producto multiplicado por sí mismo " + potencia + " veces es:");
        imprimirMatriz(resultadoPotencia);

        System.out.printf("El tiempo total para todas las operaciones es: %.9f segundos%n", tiempoTotalSegundos);
        scanner.close();
    }
}