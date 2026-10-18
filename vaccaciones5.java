import java.util.Scanner;
import java.util.Random;
public class vaccaciones5 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int tam,l;
        System.out.println("Digite el tamaño de su vector");
        tam = scanner.nextInt();
        int Vector[] = new int[tam+1];
        for(int i=0;i<tam;i++){
            Vector[i]= random.nextInt(100);
        }
        for(int i=0;i<tam;i++){
            System.out.println("el elemento "+(i+1)+"  :"+Vector[i]);
        }
        for(int i = 0; i < tam / 2; i++){
            l = Vector[i];
            Vector[i]= Vector[tam-i-1];
            Vector[tam-i-1] = l;
        }
        for(int i=0;i<tam;i++){
            System.out.println("el elemento "+(i+1)+":"+Vector[i]);
        }






    }


}
