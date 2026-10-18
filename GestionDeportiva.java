import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GestionDeportiva {
    private static final String[] ESPECIALIDADES_VALIDAS = {"fuerza", "velocidad", "resistencia", "flexibilidad", "fisioterapia"};
    private static Calendar ultimoReset = Calendar.getInstance();
    
    private enum TipoRegistro {
        ENTRENADOR("entrenadores.txt", 0, 15),
        DEPORTISTA("deportistas.txt", 1, 4);

        final String archivo;
        final int columnaCedula;
        final int maxSesiones;

        TipoRegistro(String archivo, int columnaCedula, int maxSesiones) {
            this.archivo = archivo;
            this.columnaCedula = columnaCedula;
            this.maxSesiones = maxSesiones;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            verificarYResetearSesiones();
            System.out.println("\n=== SISTEMA DE GESTIÓN DEPORTIVA ===");
            System.out.println("1. Gestionar entrenadores\n2. Gestionar deportistas\n3. Gestionar sesiones");
            System.out.println("4. Ver/Eliminar sesiones\n5. Eliminar entrenador\n6. Eliminar deportista\n7. Salir");
            System.out.print("Seleccione: ");
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1": gestionarRegistros(TipoRegistro.ENTRENADOR, scanner); break;
                case "2": gestionarRegistros(TipoRegistro.DEPORTISTA, scanner); break;
                case "3": gestionarSesion(scanner); break;
                case "4": verYEliminarSesiones(scanner); break;
                case "5": eliminarRegistro(TipoRegistro.ENTRENADOR, scanner); break;
                case "6": eliminarRegistro(TipoRegistro.DEPORTISTA, scanner); break;
                case "7": System.exit(0);
            }
        }
    }

    private static void verificarYResetearSesiones() {
        Calendar ahora = Calendar.getInstance();
        if (ahora.get(Calendar.MONTH) != ultimoReset.get(Calendar.MONTH) || 
            ahora.get(Calendar.YEAR) != ultimoReset.get(Calendar.YEAR)) {
            resetearSesiones(TipoRegistro.ENTRENADOR);
            resetearSesiones(TipoRegistro.DEPORTISTA);
            ultimoReset = Calendar.getInstance();
        }
    }

    private static void resetearSesiones(TipoRegistro tipo) {
        String archivo = tipo.archivo;
        String tempArchivo = "temp_" + archivo;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
             FileWriter writer = new FileWriter(tempArchivo)) {
            writer.write(br.readLine() + "\n");
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 4);
                writer.write(String.format("%s,%s,%s,0\n", datos[0], datos[1], datos[2]));
            }
        } catch (IOException e) {
            System.out.println("Error al resetear sesiones: " + e.getMessage());
        }
        new java.io.File(archivo).delete();
        new java.io.File(tempArchivo).renameTo(new java.io.File(archivo));
    }

    private static void gestionarRegistros(TipoRegistro tipo, Scanner scanner) {
        String archivo = tipo.archivo;
        crearArchivoConEncabezado(archivo);
        while (true) {
            System.out.println("\n=== GESTIÓN DE " + tipo.name() + " ===\n1. Registrar\n2. Incrementar sesiones\n3. Volver");
            System.out.print("Seleccione: ");
            String opcion = scanner.nextLine();
            
            if (opcion.equals("1")) registrarNuevo(tipo, archivo, scanner);
            else if (opcion.equals("2")) incrementarSesiones(tipo, archivo, scanner);
            else if (opcion.equals("3")) break;
        }
    }

    private static void crearArchivoConEncabezado(String archivo) {
        java.io.File file = new java.io.File(archivo);
        if (file.exists()) return;
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(archivo.equals("entrenadores.txt") ? 
                "Cédula,Nombre,Especialidad,Sesiones\n" : "Nombre,Cédula,Especialidad,Sesiones\n");
        } catch (IOException e) {
            System.out.println("Error al crear archivo: " + e.getMessage());
        }
    }

    private static void registrarNuevo(TipoRegistro tipo, String archivo, Scanner scanner) {
        String cedula = obtenerCedulaValida(tipo, archivo, scanner);
        String nombre = obtenerNombre(tipo, scanner);
        String especialidad = obtenerEspecialidadValida(scanner);
        guardarRegistro(tipo, archivo, cedula, nombre, especialidad);
    }

    private static String obtenerCedulaValida(TipoRegistro tipo, String archivo, Scanner scanner) {
        while (true) {
            System.out.print("Cédula (10 dígitos): ");
            String cedula = scanner.nextLine().trim();
            if (cedula.matches("\\d{10}") && !existeCedula(tipo, archivo, cedula)) return cedula;
            System.out.println("Cédula inválida o ya registrada");
        }
    }

    private static boolean existeCedula(TipoRegistro tipo, String archivo, String cedula) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 4);
                if (datos[tipo.columnaCedula].equals(cedula)) return true;
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
        return false;
    }

    private static String obtenerNombre(TipoRegistro tipo, Scanner scanner) {
        System.out.print(tipo == TipoRegistro.ENTRENADOR ? "Nombre entrenador: " : "Nombre deportista: ");
        return scanner.nextLine().trim();
    }

    private static String obtenerEspecialidadValida(Scanner scanner) {
        while (true) {
            System.out.print("Especialidad (" + String.join("/", ESPECIALIDADES_VALIDAS) + "): ");
            String esp = scanner.nextLine().trim().toLowerCase();
            for (String valida : ESPECIALIDADES_VALIDAS) if (valida.equals(esp)) return esp;
        }
    }

    private static void guardarRegistro(TipoRegistro tipo, String archivo, String cedula, String nombre, String especialidad) {
        try (FileWriter writer = new FileWriter(archivo, true)) {
            writer.write(tipo == TipoRegistro.ENTRENADOR ? 
                String.format("%s,%s,%s,0\n", cedula, nombre, especialidad) :
                String.format("%s,%s,%s,0\n", nombre, cedula, especialidad));
        } catch (IOException e) {
            System.out.println("Error al guardar registro: " + e.getMessage());
        }
    }

    private static void incrementarSesiones(TipoRegistro tipo, String archivo, Scanner scanner) {
        System.out.print("Cédula: ");
        String cedula = scanner.nextLine().trim();
        String tempArchivo = "temp_" + archivo;
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
             FileWriter writer = new FileWriter(tempArchivo)) {
            writer.write(br.readLine() + "\n");
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 4);
                if (datos[tipo.columnaCedula].equals(cedula)) {
                    int sesiones = Integer.parseInt(datos[3]);
                    if (sesiones >= tipo.maxSesiones) {
                        System.out.println("Límite de sesiones alcanzado");
                        writer.write(linea + "\n");
                    } else {
                        sesiones++;
                        writer.write(String.format("%s,%s,%s,%d\n", datos[0], datos[1], datos[2], sesiones));
                        System.out.println("Actualizado");
                    }
                    encontrado = true;
                } else writer.write(linea + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al incrementar sesiones: " + e.getMessage());
        }
        if (encontrado) {
            new java.io.File(archivo).delete();
            new java.io.File(tempArchivo).renameTo(new java.io.File(archivo));
        } else new java.io.File(tempArchivo).delete();
    }

    private static void gestionarSesion(Scanner scanner) {
        String[] deportista = obtenerDatosDeportista(scanner);
        if (deportista == null) return;
        
        if (tieneSesionProgramada(deportista[0])) {
            System.out.println("El deportista ya tiene una sesión programada");
            mostrarSesionesDeportista(deportista[0]);
            return;
        }
        
        if (contarSesiones(TipoRegistro.DEPORTISTA, deportista[0]) >= TipoRegistro.DEPORTISTA.maxSesiones) {
            System.out.println("El deportista ha alcanzado el límite de sesiones este mes");
            return;
        }
        
        // Solo se listan los entrenadores que no tienen una sesión programada,
        // para que el número que se muestra coincida con el que se selecciona
        List<String> entrenadores = new ArrayList<>();
        for (String e : buscarEntrenadoresDisponibles(deportista[2])) {
            if (!tieneSesionProgramadaEntrenador(e.split(",")[0])) entrenadores.add(e);
        }
        if (entrenadores.isEmpty()) {
            System.out.println("No hay entrenadores disponibles");
            mostrarSesionesDeportista(deportista[0]);
            return;
        }

        System.out.println("\nEntrenadores disponibles:");
        for (int i = 0; i < entrenadores.size(); i++) {
            String[] datos = entrenadores.get(i).split(",");
            System.out.printf("%d. %s (%s)\n", i + 1, datos[1], datos[0]);
        }
        System.out.print("Seleccione: ");
        int opcion = leerEntero(scanner);
        if (opcion < 1 || opcion > entrenadores.size()) return;
        String entrenador = entrenadores.get(opcion - 1);
        String[] datosEntrenador = entrenador.split(",");

        Date fecha = obtenerFechaValida(scanner);
        String codigo = generarCodigoUnico();
        guardarSesion(codigo, deportista[0], entrenador, fecha);
        actualizarSesiones(deportista[0], datosEntrenador[0]);
        System.out.println("Código: " + codigo);
    }

    // Lee un número; si el usuario escribe algo que no es número devuelve -1
    private static int leerEntero(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Una sesión está programada si su fecha es hoy o posterior
    private static boolean esSesionFutura(String fecha) {
        try {
            Date fechaSesion = new SimpleDateFormat("yyyy-MM-dd").parse(fecha.trim());
            Calendar hoy = Calendar.getInstance();
            hoy.set(Calendar.HOUR_OF_DAY, 0);
            hoy.set(Calendar.MINUTE, 0);
            hoy.set(Calendar.SECOND, 0);
            hoy.set(Calendar.MILLISECOND, 0);
            return !fechaSesion.before(hoy.getTime());
        } catch (ParseException e) {
            return false;
        }
    }

    private static String[] obtenerDatosDeportista(Scanner scanner) {
        System.out.print("Cédula deportista: ");
        String cedula = scanner.nextLine().trim();
        try (BufferedReader br = new BufferedReader(new FileReader("deportistas.txt"))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 4);
                if (datos[1].equals(cedula)) return new String[]{cedula, datos[0], datos[2]};
            }
        } catch (IOException e) {
            System.out.println("Error al leer deportistas: " + e.getMessage());
        }
        System.out.println("No encontrado");
        return null;
    }

    private static String[] buscarEntrenadoresDisponibles(String especialidad) {
        List<String> resultados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("entrenadores.txt"))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 4);
                if (datos[2].equalsIgnoreCase(especialidad) && 
                    Integer.parseInt(datos[3]) < TipoRegistro.ENTRENADOR.maxSesiones) 
                    resultados.add(datos[0] + "," + datos[1]);
            }
        } catch (IOException e) {
            System.out.println("Error al buscar entrenadores: " + e.getMessage());
        }
        return resultados.toArray(new String[0]);
    }

    private static Date obtenerFechaValida(Scanner scanner) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            System.out.print("Fecha (AAAA-MM-DD): ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    Date fecha = sdf.parse(input);
                    Calendar fechaCal = Calendar.getInstance();
                    fechaCal.setTime(fecha);
                    if (fechaCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH) &&
                        fechaCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                        esSesionFutura(input)) return fecha;
                    System.out.println("La fecha debe ser de hoy en adelante y dentro del mes actual");
                } catch (ParseException e) {
                    System.out.println("Fecha inválida");
                }
            } else {
                System.out.println("Formato de fecha incorrecto");
            }
        }
    }

    private static String generarCodigoUnico() {
        String codigo;
        do {
            codigo = String.format("%08d", new Random().nextInt(100000000));
        } while (existeCodigo(codigo));
        return codigo;
    }

    private static boolean existeCodigo(String codigo) {
        if (!new java.io.File("sesiones.txt").exists()) return false;
        try (BufferedReader br = new BufferedReader(new FileReader("sesiones.txt"))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith(codigo + ",")) return true;
            }
        } catch (IOException e) {
            System.out.println("Error al verificar código: " + e.getMessage());
        }
        return false;
    }

    private static void guardarSesion(String codigo, String deportista, String entrenador, Date fecha) {
        try (FileWriter writer = new FileWriter("sesiones.txt", true)) {
            if (new java.io.File("sesiones.txt").length() == 0) writer.write("Código,CédulaDeportista,CédulaEntrenador,NombreEntrenador,Fecha\n");
            String[] datos = entrenador.split(",");
            writer.write(String.format("%s,%s,%s,%s,%tF\n", codigo, deportista, datos[0], datos[1], fecha));
        } catch (IOException e) {
            System.out.println("Error al guardar sesión: " + e.getMessage());
        }
    }

    private static void actualizarSesiones(String cedulaDeportista, String cedulaEntrenador) {
        incrementarSesiones(TipoRegistro.DEPORTISTA, "deportistas.txt", new Scanner(cedulaDeportista));
        incrementarSesiones(TipoRegistro.ENTRENADOR, "entrenadores.txt", new Scanner(cedulaEntrenador));
    }

    private static void verYEliminarSesiones(Scanner scanner) {
        if (!new java.io.File("sesiones.txt").exists()) {
            System.out.println("No hay sesiones");
            return;
        }
        // Se guardan todas las sesiones para que el número mostrado
        // corresponda a la misma línea que se elimina
        List<String[]> sesiones = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("sesiones.txt"))) {
            br.readLine();
            String linea;
            int contador = 1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 5);
                boolean futura = esSesionFutura(datos[4]);
                System.out.printf("%d. [%s] %s con %s - %s%s\n",
                    contador, datos[0], datos[1], datos[3], datos[4],
                    futura ? "" : " (Realizada)");
                sesiones.add(datos);
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Error al leer sesiones: " + e.getMessage());
            return;
        }

        // El archivo ya está cerrado: en Windows no se puede borrar un archivo abierto
        System.out.print("Número a eliminar (0 cancelar, solo sesiones futuras): ");
        int opcion = leerEntero(scanner);
        if (opcion == 0) return;

        if (opcion < 1 || opcion > sesiones.size() || !esSesionFutura(sesiones.get(opcion - 1)[4])) {
            System.out.println("Solo se pueden eliminar sesiones futuras");
            return;
        }
        eliminarSesion(opcion, sesiones.get(opcion - 1));
    }

    private static void eliminarSesion(int numero, String[] datosSesion) {
        String tempArchivo = "temp_sesiones.txt";
        try (BufferedReader br = new BufferedReader(new FileReader("sesiones.txt"));
             FileWriter writer = new FileWriter(tempArchivo)) {
            writer.write(br.readLine() + "\n");
            int contador = 1;
            String linea;
            while ((linea = br.readLine()) != null) {
                if (contador++ != numero) {
                    writer.write(linea + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar sesión: " + e.getMessage());
        }
        new java.io.File("sesiones.txt").delete();
        new java.io.File(tempArchivo).renameTo(new java.io.File("sesiones.txt"));
        
        disminuirSesiones(TipoRegistro.DEPORTISTA, datosSesion[1]);
        disminuirSesiones(TipoRegistro.ENTRENADOR, datosSesion[2]);
    }

    private static void eliminarRegistro(TipoRegistro tipo, Scanner scanner) {
        System.out.print("Cédula a eliminar: ");
        String cedula = scanner.nextLine().trim();
        String archivo = tipo.archivo;
        String tempArchivo = "temp_" + archivo;
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
             FileWriter writer = new FileWriter(tempArchivo)) {
            writer.write(br.readLine() + "\n");
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 4);
                if (!datos[tipo.columnaCedula].equals(cedula)) writer.write(linea + "\n");
                else encontrado = true;
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar registro: " + e.getMessage());
        }
        if (encontrado) {
            new java.io.File(archivo).delete();
            new java.io.File(tempArchivo).renameTo(new java.io.File(archivo));
            eliminarSesionesRelacionadas(cedula, tipo == TipoRegistro.ENTRENADOR);
            System.out.println("Eliminado");
        } else {
            System.out.println("No encontrado");
            new java.io.File(tempArchivo).delete();
        }
    }

    private static void eliminarSesionesRelacionadas(String cedula, boolean esEntrenador) {
        if (!new java.io.File("sesiones.txt").exists()) return;
        String tempArchivo = "temp_sesiones.txt";
        try (BufferedReader br = new BufferedReader(new FileReader("sesiones.txt"));
             FileWriter writer = new FileWriter(tempArchivo)) {
            writer.write(br.readLine() + "\n");
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 5);
                String cedulaEnSesion = esEntrenador ? datos[2] : datos[1];
                if (!cedulaEnSesion.equals(cedula)) writer.write(linea + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar sesiones relacionadas: " + e.getMessage());
        }
        new java.io.File("sesiones.txt").delete();
        new java.io.File(tempArchivo).renameTo(new java.io.File("sesiones.txt"));
    }

    private static int contarSesiones(TipoRegistro tipo, String cedula) {
        String archivo = tipo.archivo;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 4);
                if (datos[tipo.columnaCedula].equals(cedula))
                    return Integer.parseInt(datos[3]);
            }
        } catch (IOException e) {
            System.out.println("Error al contar sesiones: " + e.getMessage());
        }
        return 0;
    }

    private static boolean tieneSesionProgramada(String cedulaDeportista) {
        if (!new java.io.File("sesiones.txt").exists()) return false;
        try (BufferedReader br = new BufferedReader(new FileReader("sesiones.txt"))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 5);
                if (datos[1].equals(cedulaDeportista) && esSesionFutura(datos[4])) return true;
            }
        } catch (IOException e) {
            System.out.println("Error al verificar sesión programada: " + e.getMessage());
        }
        return false;
    }

    private static boolean tieneSesionProgramadaEntrenador(String cedulaEntrenador) {
        if (!new java.io.File("sesiones.txt").exists()) return false;
        try (BufferedReader br = new BufferedReader(new FileReader("sesiones.txt"))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 5);
                if (datos[2].equals(cedulaEntrenador) && esSesionFutura(datos[4])) return true;
            }
        } catch (IOException e) {
            System.out.println("Error al verificar sesión programada para entrenador: " + e.getMessage());
        }
        return false;
    }

    private static void mostrarSesionesDeportista(String cedula) {
        if (!new java.io.File("sesiones.txt").exists()) {
            System.out.println("No hay sesiones programadas para este deportista");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader("sesiones.txt"))) {
            br.readLine();
            String linea;
            boolean tieneSesiones = false;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 5);
                if (datos[1].equals(cedula)) {
                    System.out.printf("[%s] con %s - %s\n", datos[0], datos[3], datos[4]);
                    tieneSesiones = true;
                }
            }
            if (!tieneSesiones) System.out.println("No hay sesiones programadas para este deportista");
        } catch (IOException e) {
            System.out.println("Error al mostrar sesiones: " + e.getMessage());
        }
    }

    private static void disminuirSesiones(TipoRegistro tipo, String cedula) {
        String archivo = tipo.archivo;
        String tempArchivo = "temp_" + archivo;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
             FileWriter writer = new FileWriter(tempArchivo)) {
            writer.write(br.readLine() + "\n");
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", 4);
                if (datos[tipo.columnaCedula].equals(cedula)) {
                    int sesiones = Integer.parseInt(datos[3]);
                    if (sesiones > 0) sesiones--;
                    writer.write(String.format("%s,%s,%s,%d\n", datos[0], datos[1], datos[2], sesiones));
                } else {
                    writer.write(linea + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al disminuir sesiones: " + e.getMessage());
        }
        new java.io.File(archivo).delete();
        new java.io.File(tempArchivo).renameTo(new java.io.File(archivo));
    }
}
