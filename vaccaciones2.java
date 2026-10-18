import java.util.Scanner;
public class vaccaciones2 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escriba cuantas notas desea");
        int numnotas, MaxNota, MinNota;
        double prom;
        int acumnotas;
        numnotas = scanner.nextInt();
        if (numnotas <= 0) {
            System.out.println("Debe ingresar al menos una nota");
            return;
        }
        acumnotas=0;
        int Vector []= new int[numnotas+1];

        for (int i=1; i <= numnotas; i++){
          System.out.println("Digite el valor de la "+ i +" nota");
          int nota;
          nota = scanner.nextInt();
          acumnotas = acumnotas + nota;

          Vector[i]= nota;
          
        }
        prom=(double) acumnotas/numnotas;

         MaxNota=0;


          for(int i=1; i <= numnotas; i++){
           if (i==1) {
            MaxNota= Vector[1];
           } else if(MaxNota<Vector[i]){
            MaxNota= Vector[i];
           }
        }  
            MinNota=0;

        for(int i=1; i <= numnotas; i++){
            if (i==1) {
             MinNota= Vector[1];
            } else if(MinNota>Vector[i]){
             MinNota= Vector[i];
            }
         } 

        System.out.println("El valor del promedio es de "+ prom);
        System.out.println("La nota minima es de "+ MinNota);
        System.out.println("La nota maxima es de "+ MaxNota );
    }
}