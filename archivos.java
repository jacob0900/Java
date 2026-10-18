import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


public class archivos {
    public static void main(String[] args) throws Exception {
        try{

            Scanner sc = new Scanner(System.in);

            System.out.println("digite el nombre del archivo");
           String file_name = sc.nextLine();

            FileWriter Escritor_archivo = new FileWriter(file_name + ".txt", false);
            PrintWriter registro_ejemplo = new PrintWriter(Escritor_archivo);

            for (int i=0; i<10;i++){
                registro_ejemplo.println("Linea "+ i);
            }
            registro_ejemplo.close(); // sin cerrar, el archivo queda vacío
        }catch (IOException ex) {
            System.out.println("No se pudo crear el archivo");
            ex.printStackTrace();
        }
        

        




      }   
    }