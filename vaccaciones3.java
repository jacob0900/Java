import java.util.Scanner;
public class vaccaciones3 {
    public static void main(String[] args) throws Exception {
     Scanner scanner = new Scanner(System.in);
     int tam,sum,elem;
     System.out.println("Digite el tamaño del vector");
     tam = scanner.nextInt();
     int Vector []= new int[tam+1];
     sum=0;


       for (int i=1; i<tam+1; i++){
            System.out.println("Digite el valor del  "+i+"  elemento");
            elem =  scanner.nextInt();
            sum = sum + elem;
        }

        System.out.println("La suma de todos los elementos es de  "+sum);



    }
}
