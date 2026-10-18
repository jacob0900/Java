import java.util.Scanner;
import java.util.Random;
public class vaccaciones4 {
    public static void main(String[] args) throws Exception {
     Scanner scanner = new Scanner(System.in);
     Random random = new Random();
     int tam, tamPar,tamImpar,contPar,contImpar,l,mod;
     System.out.println("Digite el tamaño del vector original");
     tam = scanner.nextInt();
     int Vector[] = new int[tam+1];
     
       for(int i=1;i<tam+1;i++){

         Vector[i]= random.nextInt(100);
         
       }
       contPar=0;
       contImpar=0;
       
       for(int i=1;i<tam+1;i++){
            l = Vector[i];
            mod=l%2;
             if(mod == 0){
            contPar++;
          }else{
            contImpar++;
          }
      }
      
      tamImpar=contImpar;
      tamPar=contPar;
      int VectorPar[] = new int[tamPar+1];
      int VectorImpar[] = new int[tamImpar+1];
       int j = 1; // Índice para VectorPar (se usa desde la posición 1)
        int k = 1; // Índice para VectorImpar (se usa desde la posición 1)
        for(int i=1;i<tam+1;i++){
            l = Vector[i];
            mod = l%2;
            if (mod == 0){
                VectorPar[j]=l;
                j++;
            }else{
                VectorImpar[k]=l;
                k++;
            }


        }
      

      System.out.println("Contenido del vector Par");
        for ( j = 1; j < tamPar+1; j++) {
            System.out.println("Elemento en el índice " + j + ": " + VectorPar[j]);
        }

        System.out.println("Contenido del vector Impar");
        for ( k = 1; k < tamImpar+1; k++) {
            System.out.println("Elemento en el índice " + k + ": " + VectorImpar[k]);
        }




    }
}
