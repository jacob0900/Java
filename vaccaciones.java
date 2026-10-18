import java.util.Scanner;
public class vaccaciones {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int sueldo;
        System.out.print("Ingrese el nombre del empleado :");
        String empleado = scanner .nextLine();
        System.out.print("Ingrese el valor de una hora :");
        int valor_hora = scanner.nextInt();
        System.out.print("Ingrese el numero de horas que se trabajaron :");
        int numero_horas = scanner.nextInt();
        sueldo = numero_horas *  valor_hora;
        System.out.print("el empleado :"+ empleado +" tuvo como salario " +sueldo);
    }


}