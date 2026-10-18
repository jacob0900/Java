
import java.util.Scanner;


public class vaccaciones6 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int sw, numesp, tam, num, l;
        sw =0;
        System.out.println("Digite el tamaño del vector");
        tam = scanner.nextInt();
        System.out.println("Digite el numero especial");
        numesp = scanner.nextInt();
        int Vector[] = new int[tam+1];
        for (int i=0;i<tam;i++){
            System.out.println("Digite el numero "+(i+1));
            num = scanner.nextInt();
            Vector[i]= num;
        }
        for(int i=0;i<tam;i++){
            l= Vector[i];
           if( l == numesp){
                sw=1;
                System.out.println("el numero especial es "+l+" y se encuentra en la pocision"+ (i+1));
           }
        }


            if(sw == 0){
                System.out.println("Ninguno de los numero en el vector es el especial");
            }




    }
}
