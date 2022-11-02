package ifp.kikeverea;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EditorTextual {

    private static final String CABECERA =
            "\n***************************************\n" +
                    "             EDITOR DE TEXTO            \n" +
                    "***************************************\n";
    private static final String MENU =
            "Seleccione una opción:\n" +
                    "\t1) Crear un fichero\n" +
                    "\t2) Editar un fichero\n" +
                    "\t3) Borrar un fichero\n" +
                    "\t4) Crear una carpeta\n" +
                    "\t5) Borrar una carpeta\n" +
                    "\t0) Salir\n";

    static final int CREAR_FICHERO = 1;
    static final int EDITAR_FICHERO = 2;
    static final int BORRAR_FICHERO = 3;
    static final int CREAR_CARPETA = 4;
    static final int BORRAR_CARPETA = 5;
    static final int SALIR = 0;

    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int opcion;
        String resultado;

        // imprime la cabecera
        System.out.println(CABECERA);

        // itera hasta que el usuario introduzca la opción salir (0)
        while (true) {

            // imprime el menu
            System.out.print(MENU);

            // solicita opción al usuario
            opcion = solicitarEntero("Opción: ");
            scanner.nextLine();

            // si es SALIR (0), termina el programa
            if (opcion == SALIR)
                break;

            // ejecuta la opción
            resultado = ejecutarOpcion(opcion);

            // imprime el resultado
            System.out.println("\n*****************************************");
            System.out.println(resultado);
            System.out.println("*****************************************\n");
        }

        System.out.println("\nPrograma finalizado");
    }

    private static String ejecutarOpcion(int opcion) {
        File fichero;
        boolean accionRealizada;

        switch (opcion) {
            case CREAR_FICHERO :
                fichero = new File(solicitarTexto("Introduzca el nombre del nuevo fichero: "));

                if (fichero.exists() && !seguirAccion("Fichero existente ¿Desea sobrescribirlo? "))
                    return "El fichero no se ha sobreescrito";

                accionRealizada = crearFichero(fichero);
                return accionRealizada ? "Fichero creado correctamente" : "El fichero no se ha podido crear";

            case EDITAR_FICHERO :
                fichero = new File(solicitarTexto("Introduzca el nombre del fichero a editar: "));

                if (!fichero.exists())
                    return "Fichero Inexistente";

                String nuevoTexto = solicitarTexto("Introduzca el texto que desea añadir al fichero: ");

                accionRealizada = editarFichero(fichero, nuevoTexto);
                return accionRealizada ? "Documento editado correctamente" : "El documento no ha sido editado";

            case BORRAR_FICHERO:
                fichero = new File(solicitarTexto("Introduzca el nombre del fichero a borrar: "));

                if (!fichero.exists())
                    return "Fichero Inexistente";

                accionRealizada = seguirAccion("¿Está seguro de que desea borrarlo (Afirmación S)? ", "S") &&
                                borrarFichero(fichero);

                return accionRealizada ? "Fichero eliminado correctamente" : "El fichero no se borró del sistema";

            case CREAR_CARPETA:
                fichero = new File(solicitarTexto("Introduzca el nombre de la carpeta a crear: "));

                if (fichero.exists())
                    return "Carpeta ya existente";

                accionRealizada = crearCarpeta(fichero);

                return accionRealizada ? "Carpeta creada correctamente" : "La carpeta no ha sido creada";

            case BORRAR_CARPETA:
                fichero = new File(solicitarTexto("Introduzca el nombre de la carpeta a borrar: "));

                if (!fichero.exists())
                    return "La carpeta no existe";

                accionRealizada = seguirAccion("¿Está seguro de que desea borrar la carpeta (Afirmación S)? ", "S") &&
                                borrarCarpeta(fichero);

                return accionRealizada ? "Carpeta borrada correctamente" : "La carpeta no se borró del sistema";

            default:
                return "Opción errónea";
        }

    }

    private static String solicitarTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static int solicitarEntero(String mensaje) {
        // itera hasta que el usuario introduzca un número válido
        while (true) {
            try {
                System.out.print(mensaje);
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                // consume el resto de la línea, para evitar un bucle infinito
                scanner.nextLine();
                System.out.println("Entrada no válida, por favor introduzca un número");
            }
        }
    }

    private static boolean seguirAccion(String mensaje) {
        return seguirAccion(mensaje, "si");
    }

    private static boolean seguirAccion(String mensaje, String valorAfirmacion) {
        return solicitarTexto(mensaje).equalsIgnoreCase(valorAfirmacion);
    }

    public static boolean crearFichero(File fichero) {
        try {
            new PrintWriter(fichero).close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean crearCarpeta(File carpeta) {
        return carpeta.mkdir();
    }

    public static boolean borrarFichero(File fichero) {
        return fichero.delete();
    }

    public static boolean borrarCarpeta(File carpeta) {
        File[] ficheros = carpeta.listFiles();

        if(ficheros != null && ficheros.length > 0)
            for (File fichero : ficheros)
                if (!fichero.delete())
                    return false;

        return carpeta.delete();
    }

    public static boolean editarFichero(File fichero, String nuevaLinea) {
        try (FileWriter writer = new FileWriter(fichero, true);
             PrintWriter printer = new PrintWriter(writer))
        {
            printer.println(nuevaLinea);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}






